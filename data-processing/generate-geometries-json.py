#!/usr/bin/python3
import geopandas
import numpy as np
import os.path
import pandas
import re
import sys
import zipfile

def process_state(state_name):
    state_dir = 'data/'+state_name+'/'
    precinct_geojson_file = state_dir+'GeoJson.json'
    primary_key = 'Final_GEO_ID'

    with open(precinct_geojson_file, 'w') as precinct_geojson:
        # precinct geo data
        precinct_geos = pandas.DataFrame();
        directory = 'data/'+state_name+'/geodata'
        for filename in os.listdir(directory):
            match = re.fullmatch('tl_2010_(?P<state>\d\d)(?P<county>\d\d\d)_.*', filename)
            countyId = ''+match.group('state')+match.group('county')
            shp_file = geopandas.read_file('zip://'+os.path.abspath(directory)+'/'+filename)
            def format_geoid(geoid):
                assert countyId == geoid[:5]
                precinctId = geoid[5:]
                return ('%s%06s' % (countyId, precinctId)).replace(' ', '0')
            geoids = [format_geoid(r) for r in shp_file['GEOID10'].values.tolist()]
            shp_file[primary_key] = geoids 
            precinct_geos = precinct_geos.append(shp_file[[primary_key, 'geometry']])

            # generate json for geo data
            for _, row in shp_file.iterrows():
                precinctID = row[-1]
                geometry = geopandas.GeoSeries([row[-2]]).to_json()
                precinct_geojson.write(geometry) 

def main(args):
    process_state(args[1])

if __name__ == '__main__':
    main(sys.argv)
