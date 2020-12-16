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
    vap_demographic_sql_file = state_dir+'PopulateVAPDemographics.sql'
    county_names_map = {}

    with open(vap_demographic_sql_file, 'w') as sql:
        #with zipfile.ZipFile('data/'+state_name+'/'+state_name+'DemographicData.zip', 'r') as zip_ref:
        #        zip_ref.extractall('data/'+state_name)
        demographic_data_file = 'data/'+state_name+'/'+state_name+'VotingAgeDemoData.csv'
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
                sql.write("UPDATE PrecinctDemographics SET `votingAgePopulation` = %d WHERE `precinctID` = '%s' AND `demographic` = '%s';\n" % (population, precinctID, demographic))

        # generate sql for total population 
        for _, row in demographic_data.iterrows():
            precinctID = row[-1]
            sql.write("UPDATE Precincts SET `votingAgePopulation` = %d WHERE `ID` = '%s';\n" % (int(row[2]), precinctID))

def main(args):
    process_state(args[1])

if __name__ == '__main__':
    main(sys.argv)
