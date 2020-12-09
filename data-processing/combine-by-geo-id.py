#!/usr/bin/python3
import geopandas
import numpy as np
import pandas
import re
import sys

counter_dict = {}

def convert_shapefiles():
    shp_file = geopandas.read_file('myshpfile.shp')
    shp_file.to_file('myshpfile.geojson', driver='GeoJSON')

def main(args):
    state_name = 'Arkansas'
    primary_key_name = 'Key'
    demographic_data_file = 'data/Arkansas/ArkansasDemographicData.csv'
    precinct_data_file = 'data/Arkansas/ArkansasPrecinctData.json'
    demographic_data = pandas.read_csv(demographic_data_file, header=[1])
    #print(demographic_data.columns)
    precinct_data = geopandas.read_file(precinct_data_file) 
    print(precinct_data.columns)
    precinct_name_columns = precinct_data.loc[:, ['precinct', 'county_nam']]
    print(precinct_name_columns[0:1])
    precinct_data[primary_key_name] = ['{0}|{1}'.format(r[0].lower(), r[1].lower()) for r in precinct_name_columns.values.tolist()]
    demographic_data[primary_key_name] = ['{0}|{1}'.format(*parse_geographic_area_name(r)) for r in demographic_data["Geographic Area Name"].values.tolist()]
    print(precinct_data.head())
    demographic_area_names = set(demographic_data[primary_key_name])
    precinct_area_names = set(precinct_data[primary_key_name])
    print('difference', list(demographic_area_names - precinct_area_names)[0]) 
    print('difference2', list(precinct_area_names - demographic_area_names)[0]) 
    for t in sorted([(len(v),k) for k,v in counter_dict.items()]):
        print(t) 
    print(counter_dict[r'(?P<precinct>.* precinct \d+\w*), (?P<county>.*) county, .*'])

if __name__ == '__main__':
    main(sys.argv)
