#!/usr/bin/python3
import geopandas
import numpy as np
import pandas
import re
import sys

counter_dict = {}

def parse_geographic_area_name(geographic_area_name):
    source = geographic_area_name.lower()
    regexes = [
            r'(?P<precinct>.*) (voting district|precinct), (?P<county>.*) county, .*',
            r'voting district (?P<precinct>.*), (?P<county>.*) county, .*',
            r'(?P<precinct>.* ward \d+), (?P<county>.*) county, .*',
            r'(?P<precinct>.*) city pct \d+, (?P<county>.*) county, .*',
            ]
    special_cases = [
#marion county?????
            (r'(?P<precinct>.*), (?P<county>marion) county, .*', '{precinct}'),
#rison - weird - have rison, wards 1a 1b 1c, geojson has inside/outside
            (r'(?P<precinct>rison .*), (?P<county>.*) county, .*', '{precinct}'),
#searcy 3 - there's searcy 1-3 voting districts but it doesn't match the other side 
            (r'(?P<precinct>searcy \d) voting distri?ct, (?P<county>.*) county, .*', '{precinct}'),
#blackfish - there are 2 blackfishes in the census data but only 1 in the precinct?
            (r'(?P<precinct>.* pct \d+ \(\d+\)), (?P<county>.*) county, .*', '{precinct}'),
#lewisville - DONE
            (r'(?P<precinct>lewisville ward \d+) .*, (?P<county>.*) county, .*', '{precinct} (out)'),
#stamps - DONE?
            (r'(?P<precinct>stamps ward 1), (?P<county>.*) county, .*', '{precinct}, pct 1'),
            (r'(?P<precinct>stamps ward 1) pct 2, (?P<county>.*) county, .*', '{precinct}, pct 2'),
            (r'(?P<precinct>stamps ward 1) pct 2 outside city, (?P<county>.*) county, .*', '{precinct}, pct 2 (out)'),
            (r'(?P<precinct>stamps ward 2) outside city, (?P<county>.*) county, .*', '{precinct} (out)'),
            (r'(?P<precinct>stamps ward \d), (?P<county>.*) county, .*', '{precinct}'),
#fletcher twp - DONE
            (r'(?P<precinct>fletcher twp precinct 30), (?P<county>.*) county, .*', '{precinct}'),
            (r'(?P<precinct>fletcher) voting district, (?P<county>.*) county, .*', '{precinct} twp'),
#mccrory - DONE
            (r'(?P<precinct>mccrory rural precinct 15), (?P<county>.*) county, .*', 'frgrnds\/mccrory rural-15'),
            (r'(?P<precinct>mccrory precinct 17), (?P<county>.*) county, .*', 'mccrory civic center-17'),
            (r'(?P<precinct>mccrory precinct 18), (?P<county>.*) county, .*', 'mccrory civic-18'),
#blytheville
            (r'(?P<precinct>(blytheville|victoria) precinct \d+\w*), (?P<county>.*) county, .*', '{precinct}'),
#bassett
            (r'(?P<precinct>bassett precinct \d+\w*), (?P<county>.*) county, .*', 'bassett ch'),
#mccrory?
            (r'(?P<precinct>.* precinct \d+\w*), (?P<county>.*) county, .*', '{precinct}'),
#forrest city
            (r'(?P<precinct>forrest city ward .* pct \d+), (?P<county>.*) county, .*', '{precinct}'),
#el dorado - not found in geojson?
            (r'(?P<precinct>el dorado ward .* pct \d+), (?P<county>.*) county, .*', '{precinct}'),
#hughes - merge all wards
            (r'(?P<precinct>hughes ward .* pct \d+), (?P<county>.*) county, .*', '{precinct}'),
#palestine - merge all wards
            (r'(?P<precinct>palestine ward .* pct \d+), (?P<county>.*) county, .*', '{precinct}'),
#fayetteville
            (r'(?P<precinct>.* pct \d+), (?P<county>.*) county, .*', '{precinct}'),
#jasper ward1
            (r'(?P<precinct>.* ward\d+), (?P<county>.*) county, .*', '{precinct}'),
#precinct 54, mississippi county
            (r'(?P<precinct>precinct \d+), (?P<county>.*) county, .*', '{precinct}'),
#newport ward
            (r'(?P<precinct>newport ward \d\w), (?P<county>.*) county, .*', '{precinct}'),
#alma - DONE
            (r'(?P<precinct>alma \d+), (?P<county>.*) county, .*', '{precinct}'),
#arkadelphia - DONE
            (r'(?P<precinct>(?P<name>.*) jp (?P<num>\d)), (?P<county>.*) county, .*', '{name} ward {num}'),
#camden/searcy ward - DONE
            (r'(?P<precinct>(searcy|camden) ward \d+\w), (?P<county>.*) county, .*', '{precinct}'),
#beebe ward - DONE - SINGLETON
            (r'(?P<precinct>beebe ward 3c), (?P<county>.*) county, .*', 'beebe ward 3 c'),
#bob ward - DONE - SINGLETONS
            (r'(?P<precinct>bob ward), (?P<county>.*) county, .*', 'anthonyville city hall'),
            (r'(?P<precinct>.*) bob ward 1, (?P<county>.*) county, .*', '{precinct}'),
#el dorado ward
            (r'(?P<precinct>.* ward \d pct\d), (?P<county>.*) county, .*', '{precinct}'),
#west memphis - you're gonna need the precinct_o for this one
            (r'(?P<precinct>.* ward \d-\d), (?P<county>.*) county, .*', '{precinct}'),
#gosnell - first two are ok, but then you've got a comm center and school in place of 12-3
            (r'(?P<precinct>.* precinct \d+-\d), (?P<county>.*) county, .*', '{precinct}'),
#pulaski county - PCT 909M, can't find in the geojson
            (r'(?P<precinct>pct \d+\w), (?P<county>.*) county, .*', '{precinct}'),
#franklin county
            (r'(?P<precinct>j p dist \d+-\w), (?P<county>.*) county, .*', '{precinct}'),
#lonoke - has just districts that are named in the geojson
            (r'(?P<precinct>.* district \d), (?P<county>.*) county, .*', '{precinct}'),
#jonesboro - not found in geojson?? looks like craighead just numbers its precincts like marion
            (r'(?P<precinct>.* pct \d+ \w+), (?P<county>.*) county, .*', '{precinct}'),
#sebastian county - needs to be formatted in general but specifically this case is 3 dists just combined
            (r'voting district (?P<precinct>.*); (?P<county>.*) county; .*', '{precinct}'),
        ]
    for regex, template in special_cases: 
        m = re.fullmatch(regex, source)
        if (m):
            if regex not in counter_dict:
               counter_dict[regex] = []
            counter_dict[regex].append(source) 
            return [template.format(**m.groupdict()), m.group('county')]
    for regex in regexes:
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
    for t in sorted([(len(v),k) for k,v in counter_dict.items()]):
        print(t) 
    print(counter_dict[r'(?P<precinct>.* precinct \d+\w*), (?P<county>.*) county, .*'])

if __name__ == '__main__':
    main(sys.argv)
