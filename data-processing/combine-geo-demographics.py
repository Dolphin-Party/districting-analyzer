import numpy as np
import pandas
import geopandas
import sys

def main(args):
    state_name = 'Arkansas'
    demographic_data_file = 'data/Arkansas/ArkansasDemographicData.csv'
    precinct_data_file = 'data/Arkansas/ArkansasPrecinctData.json'
    demographic_data = pandas.read_csv(demographic_data_file, header=[1])
    #print(demographic_data.columns)
    precinct_data = geopandas.read_file(precinct_data_file) 
    print(precinct_data.columns)
    precinct_name_columns = precinct_data.loc[:, ['precinct', 'county_nam']]
    print(precinct_name_columns[0:1])
    precinct_data['Geographic Area Name'] = ['{0} Voting District, {1} County, {2}'.format(r[0].title(), r[1], state_name) for r in precinct_name_columns.values.tolist()]
    print(precinct_data.head())
    demographic_area_names = set(demographic_data["Geographic Area Name"])
    precinct_area_names = set(precinct_data["Geographic Area Name"])
    print('difference', list(demographic_area_names - precinct_area_names)[0]) 
    print('difference2', list(precinct_area_names - demographic_area_names)[0]) 

if __name__ == '__main__':
    main(sys.argv)
