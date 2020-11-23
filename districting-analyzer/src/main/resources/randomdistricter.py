import argparse
import json

import seed_district_generator
from parameters import DistrictingParameters
from precinct_graph import *

#  load data out of JSON file

parser = argparse.ArgumentParser(description='Generate random districtings given parameters')
parser.add_argument('params', metavar='P', type=argparse.FileType('r'))
parser.add_argument('data', metavar='D', type=argparse.FileType('r'))

args = parser.parse_args()

#  Unpack JSON files

precincts: List[PrecinctNode] = json.load(args.data, object_hook=PrecinctNode.from_json)

params: DistrictingParameters = json.load(args.params, object_hook=DistrictingParameters.from_json)

#  Generate seed districting

seed_districting = seed_district_generator.generate_seed_district(precincts, params.num_districts)

#  Rebalance for X iterations
