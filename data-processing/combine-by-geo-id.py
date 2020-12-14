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
    state_dir = 'data/'+state_name+'/'
    demographic_sql_file = state_dir+'PopulateDemographics.sql'
    precincts_sql_file = state_dir+'PopulatePrecincts.sql'
    counties_sql_file = state_dir+'PopulateCounties.sql'
    neighbors_sql_file = state_dir+'PopulateNeighbors.sql'
    county_names_map = {}

    with open(demographic_sql_file, 'w') as demographic_sql, \
            open(precincts_sql_file, 'w') as precincts_sql, \
            open(counties_sql_file, 'w') as counties_sql, \
            open(neighbors_sql_file, 'w') as neighbors_sql:
        #with zipfile.ZipFile('data/'+state_name+'/'+state_name+'DemographicData.zip', 'r') as zip_ref:
        #        zip_ref.extractall('data/'+state_name)
        demographic_data_file = 'data/'+state_name+'/'+state_name+'DemographicData.csv'
        demographic_data = pandas.read_csv(demographic_data_file, header=[1])
        id_column = demographic_data.columns[0]
        demographic_data[primary_key] = [r[9:] for r in demographic_data[id_column].values.tolist()]
         
        # generate sql for demographic data
        for _, row in demographic_data.iterrows():
            precinctID = row[-1]
            countyID = precinctID[0:5]
            match = re.fullmatch('.*, (?P<county>.*), .*', row[1]) 
            countyName = match.group('county')
            county_names_map[countyID] = countyName
            for col_name, population in zip(demographic_data.columns[1:-1], row[1:-1]):
                demographic = demographics_map.get(col_name) 
                if not demographic:
                    continue
                demographic_sql.write("INSERT INTO PrecinctDemographics (`precinctID`, `demographic`, `population`) VALUES ('%s', '%s', '%s');\n" % (precinctID, demographic, population)) 
    
        # precinct geo data
        precinct_geos = pandas.DataFrame();
        directory = 'data/'+state_name+'/geodata'
        for filename in os.listdir(directory):
            match = re.fullmatch('tl_2010_(?P<state>\d\d)(?P<county>\d\d\d)_.*', filename)
            stateId = match.group('state')
            countyId = '' + stateId + match.group('county')
            countyName = county_names_map[countyId]
            counties_sql.write("INSERT INTO Counties (`ID`, `name`, `stateID`) VALUES (%d, '%s', %d);\n" % (int(countyId), countyName, int(stateId))) 
            shp_file = geopandas.read_file('zip://'+os.path.abspath(directory)+'/'+filename)
            def format_geoid(geoid):
                assert countyId == geoid[:5]
                precinctId = geoid[5:]
                return ('%s%06s' % (countyId, precinctId)).replace(' ', '0')
            geoids = [format_geoid(r) for r in shp_file['GEOID10'].values.tolist()]
            shp_file[primary_key] = geoids 
            precinct_geos = precinct_geos.append(shp_file[[primary_key, 'geometry']])

            # generate sql for geo data
            for _, row in shp_file.iterrows():
                precinctID = row[-1]
                geometry = geopandas.GeoSeries([row[-2]]).to_json()
                precincts_sql.write("INSERT INTO Precincts (`ID`, `countyID`, `shape`) VALUES ('%s', %d, '%s');\n" % (precinctID, int(countyId), geometry)) 

        # generate sql for total population 
        for _, row in demographic_data.iterrows():
            precinctID = row[-1]
            precincts_sql.write("UPDATE Precincts SET `population` = %d WHERE `ID` = '%s';\n" % (int(row[2]), precinctID))

        #identify neighboring precincts
        for _, row in precinct_geos.iterrows():
            #print(row[primary_key])
            for _, row2 in precinct_geos.iterrows():
                if row[primary_key] <= row2[primary_key]:
                    continue
                if row['geometry'].touches(row2['geometry']) or row['geometry'].overlaps(row2['geometry']):
                    neighbors_sql.write("INSERT INTO PrecinctNeighbors (`precinctID`, `neighborID`) VALUES ('%s', '%s');\n" % (row[primary_key], row2[primary_key]))
        
        # sanity check
        demographic_geoids = set(demographic_data[primary_key])
        precinct_geoids = set(precinct_geos[primary_key])
        if demographic_geoids - precinct_geoids:
            print('difference', len(demographic_geoids), len(precinct_geoids), len(list(demographic_geoids - precinct_geoids)))
            print('difference', list(demographic_geoids)[:3], list(precinct_geoids)[:3], list(demographic_geoids - precinct_geoids)[:3])

def main(args):
    process_state(args[1])

if __name__ == '__main__':
    main(sys.argv)
