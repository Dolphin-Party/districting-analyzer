#!/usr/bin/python3
import geopandas
import numpy as np
import os.path
import pandas
import re
import sys
import zipfile


def main(args):
    state_name = 'Arkansas'
    primary_key = 'Final_GEO_ID'
    demographic_data_file = 'data/Arkansas/ArkansasDemographicData.csv'
    demographic_data = pandas.read_csv(demographic_data_file, header=[1])
    demographic_data[primary_key] = [r[9:] for r in demographic_data['id'].values.tolist()]
    print(demographic_data[primary_key])
    demographic_geoids = set(demographic_data[primary_key])
    precinct_geoids = set()
    directory = 'data/'+state_name+'/geodata'
    for filename in os.listdir(directory):
        shp_file = geopandas.read_file('zip://'+os.path.abspath(directory)+'/'+filename)
        def format_geoid(geoid):
            county = geoid[:5]
            precinct = geoid[5:]
            return ('%s%06s' % (county, precinct)).replace(' ', '0')
        geoids = [format_geoid(r) for r in shp_file['GEOID10'].values.tolist()]
        shp_file[primary_key] = geoids 
        precinct_geoids.update(geoids)
    print('difference', len(demographic_geoids), len(precinct_geoids), len(list(demographic_geoids - precinct_geoids)))
    print('difference', list(demographic_geoids)[:3], list(precinct_geoids)[:3], list(demographic_geoids - precinct_geoids)[:3])

if __name__ == '__main__':
    main(sys.argv)
