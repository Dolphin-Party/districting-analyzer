import argparse
import json
from helpers import *

#  load data out of JSON file

parser = argparse.ArgumentParser(description='Generate random districtings given parameters')
parser.add_argument('params', metavar='P', type=argparse.FileType('r'))
parser.add_argument('data', metavar='D', type=argparse.FileType('r'))

args = parser.parse_args()

#  Unpack JSON files

precinctGraph: PrecinctNode = json.load(args.data, object_hook=PrecinctNode.from_json)

params: DistrictingParameters = json.load(args.params, object_hook=DistrictingParameters.from_json)

#  Generate seed districting
#  Rebalance for X iterations

