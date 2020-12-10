#!/usr/bin/python3
import geopandas
import numpy as np
import os.path
import pandas
import re
import sys
import zipfile

demographics_map = { 
    'Total!!Population of one race!!White alone': 'whiteNonHispanic',
    '': 'hispanic',
    'Total!!Population of one race!!Black or African American alone': 'black',
    'Total!!Population of one race!!Asian alone': 'asian',
    'Total!!Population of one race!!American Indian and Alaska Native alone': 'americanIndian',
    'Total!!Population of one race!!Native Hawaiian and Other Pacific Islander alone': 'pacific',
    'Total!!Two or More Races': 'twoOrMoreRaces',
}
def process_state(state_name):
    primary_key = 'Final_GEO_ID'
    sql_script = 'data/'+state_name+'/'+state_name+'Tables.sql'
    county_names_map = {}

    with open(sql_script, 'w') as sql:
        demographic_data_file = 'data/'+state_name+'/'+state_name+'DemographicData.csv'
        demographic_data = pandas.read_csv(demographic_data_file, header=[1])
        id_column = demographic_data.columns[0]
        demographic_data[primary_key] = [r[9:] for r in demographic_data[id_column].values.tolist()]
         
        # generate sql for demographic data
        for _, row in demographic_data.iterrows():
            precinctID = row[-1]
            countyID = precinctID[2:5]
            match = re.fullmatch('.*, (?P<county>.*), .*', row[1]) 
            countyName = match.group('county')
            county_names_map[countyID] = countyName
            for col_name, population in zip(demographic_data.columns[1:-1], row[1:-1]):
                demographic = demographics_map.get(col_name) 
                if not demographic:
                    continue
                sql.write("INSERT INTO 'PrecinctDemographics' ('precinctID', 'demographic', 'population') VALUES ('%s', '%s', '%s');\n" % (precinctID, demographic, population)) 
            sql.write("INSERT INTO 'Precincts' ('population') VALUES (%d);\n" % row[2])

        # precinct geo data
        precinct_geoids = set()
        state_set = False
        directory = 'data/'+state_name+'/geodata'
        for filename in os.listdir(directory):
            match = re.fullmatch('tl_2010_(?P<state>\d\d)(?P<county>\d\d\d)_.*', filename)
            stateId = match.group('state')
            if not state_set:
                sql.write("INSERT INTO 'States' ('ID', 'name') VALUES ('%s', '%s');\n" % (stateId, state_name))
                state_set = True
            countyId = match.group('county')
            countyName = county_names_map[countyId]
            sql.write("INSERT INTO 'Counties' ('ID', 'name', 'stateID') VALUES ('%s', '%s', '%s');\n" % (countyId, countyName, stateId)) 
            shp_file = geopandas.read_file('zip://'+os.path.abspath(directory)+'/'+filename)
            def format_geoid(geoid):
                assert stateId+countyId == geoid[:5]
                precinctId = geoid[5:]
                return ('%s%s%06s' % (stateId, countyId, precinctId)).replace(' ', '0')
            geoids = [format_geoid(r) for r in shp_file['GEOID10'].values.tolist()]
            shp_file[primary_key] = geoids 
            precinct_geoids.update(geoids)

            # generate sql for geo data
            for _, row in shp_file.iterrows():
                precinctID = row[-1]
                geometry = geopandas.GeoSeries([row[-2]]).to_json()
                sql.write("INSERT INTO 'Precincts' ('ID', 'countyID', 'shape') VALUES ('%s', '%s', '%s');\n" % (precinctID, countyId, geometry)) 

        # sanity check
        demographic_geoids = set(demographic_data[primary_key])
        if demographic_geoids - precinct_geoids:
            print('difference', len(demographic_geoids), len(precinct_geoids), len(list(demographic_geoids - precinct_geoids)))
            print('difference', list(demographic_geoids)[:3], list(precinct_geoids)[:3], list(demographic_geoids - precinct_geoids)[:3])

def main(args):
    process_state(args[1])

if __name__ == '__main__':
    main(sys.argv)
