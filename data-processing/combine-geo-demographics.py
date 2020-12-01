#!/usr/bin/python3
import geopandas
import numpy as np
import pandas
import re
import sys

def parse_geographic_area_name(geographic_area_name):
    source = geographic_area_name.lower()
    regexes = [
            r'(?P<precinct>.*) voting district, (?P<county>.*) county, .*',
            r'voting district (?P<precinct>.*), (?P<county>.*) county, .*',
            r'(?P<precinct>.* ward \d+), (?P<county>.*) county, .*',
            r'(?P<precinct>.*) city pct \d+, (?P<county>.*) county, .*',
            ]
    special_cases = [
            r'(?P<precinct>.*) precinct, (?P<county>.*) county, .*', #miller county
            r'(?P<precinct>.* precinct \d+\w?), (?P<county>.*) county, .*', #fletcher twp
            r'(?P<precinct>.* pct \d+), (?P<county>.*) county, .*', #fayetteville
            r'(?P<precinct>.* ward\d+), (?P<county>.*) county, .*', #jasper ward1
            r'(?P<precinct>precinct \d+), (?P<county>.*) county, .*', #precinct 54, mississippi county
            r'(?P<precinct>newport ward \d\w), (?P<county>.*) county, .*', #newport ward
            r'(?P<precinct>alma \d+), (?P<county>.*) county, .*', #alma
            r'(?P<precinct>.* jp \d+), (?P<county>.*) county, .*', #arkadelphia
            r'(?P<precinct>.* ward \d+\w), (?P<county>.*) county, .*', #camden ward
            r'(?P<precinct>.* ward \w), (?P<county>.*) county, .*', #rison ward
            r'(?P<precinct>.* ward), (?P<county>.*) county, .*', #bob ward
            r'(?P<precinct>.* ward \d pct\d), (?P<county>.*) county, .*', #el dorado ward
            r'(?P<precinct>stamps ward \d+ .*), (?P<county>.*) county, .*', #stamps
            r'(?P<precinct>lewisville ward \d+ .*), (?P<county>.*) county, .*', #lewisville
            r'(?P<precinct>.*) voting distrct, (?P<county>.*) county, .*', #searcy 3
            r'(?P<precinct>.* pct \d+ \(\d+\)), (?P<county>.*) county, .*', #blackfish
            r'(?P<precinct>.* ward \d-\d), (?P<county>.*) county, .*', #west memphis
            r'(?P<precinct>.* precinct \d+-\d), (?P<county>.*) county, .*', #gosnell
            r'(?P<precinct>pct \d+\w), (?P<county>.*) county, .*', #pulaski county
            r'(?P<precinct>j p dist \d+-\w), (?P<county>.*) county, .*', #franklin county
            r'(?P<precinct>.* district \d), (?P<county>.*) county, .*', #lonoke
            r'(?P<precinct>.* pct \d+ \w+), (?P<county>.*) county, .*', #jonesboro
            r'voting district (?P<precinct>.*); (?P<county>.*) county; .*', #sebastian county
        ]
    for regex in regexes:
        m = re.fullmatch(regex, source)
        if (m):
            return [m.group('precinct'), m.group('county')]
    for regex in special_cases: 
        m = re.fullmatch(regex, source)
        if (m):
            return [m.group('precinct'), m.group('county')]
    raise Exception("couldn't match: ", geographic_area_name) 
    

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

if __name__ == '__main__':
    main(sys.argv)
