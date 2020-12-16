#!/usr/bin/python3
import geopandas
import json
import os.path
import pandas
import sys
import zipfile

demographics_map = { 
        3: 'white',
        4: 'black',
        5: 'americanIndian',
        6: 'asian',
        7: 'pacific',
        8: 'someOtherRace',
        9: 'twoOrMoreRaces',
        }

def process_districts():
    state_demographics_vectors = {}
    stateIds = {'05': 'Arkansas', '37': 'North_Carolina', '51': 'Virginia'}
    for state in stateIds:
        demographics_vectors = {}
        for dem in demographics_map:
            demographics_vectors[demographics_map[dem]] = []
        state_demographics_vectors[stateIds[state]] = demographics_vectors

    demographic_data = pandas.read_csv('data/CanonicalDistrictingDemographics.csv', header=[1])
    for _, row in demographic_data.iterrows():
        geoID = row[0][9:]
        stateID = geoID[:2]
        vectors = state_demographics_vectors[stateIds[stateID]]
        for dem in demographics_map:
            demographic_vector = vectors[demographics_map[dem]]
            demographic_vector.append(row[dem])
    for state in state_demographics_vectors:
        for dem in state_demographics_vectors[state]:
            state_demographics_vectors[state][dem].sort()
    state_demo_json = json.dumps(state_demographics_vectors)
    print(state_demo_json)
    with open('data/CanonicalDistrictingDemographics.json', 'w') as f:
        f.write(state_demo_json)

'''
    with zipfile.ZipFile('data/CanonicalDistrictingDemographics.zip', 'r') as zip_ref:
        zip_ref.extractall('data/')
    national_districts = geopandas.read_file('zip://'+os.path.abspath('data')+'/CongressionalDistricts.zip')
    print(national_districts.head())
    for _, row in national_districts.iterrows():
        stateId = row['GEOID'][:2]
        if (stateId == '05'):
            print('Arkansas:', row['GEOID'])  
        if (stateId == '37'):
            print('North_Carolina:', row['GEOID'])  
        if (stateId == '51'):
            print('Virginia:', row['GEOID'])  
'''

    
def main(args):
    process_districts()

if __name__ == '__main__':
    main(sys.argv)
