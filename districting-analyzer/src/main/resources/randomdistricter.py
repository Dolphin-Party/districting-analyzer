import argparse
import json
import seed_district_generator
from districting_job import generate_districting
from parameters import DistrictingParameters
from precinct_graph import *
from multiprocessing import Pool
from os import cpu_count

parser = argparse.ArgumentParser(description='Generate random districtings given parameters')
parser.add_argument('params', metavar='P', type=argparse.FileType('r'))
parser.add_argument('data', metavar='D', type=argparse.FileType('r'))

args = parser.parse_args()

#  Unpack JSON files
precincts: List[PrecinctNode] = json.load(args.data, object_hook=PrecinctNode.from_json)
params: DistrictingParameters = json.load(args.params, object_hook=DistrictingParameters.from_json)

#  Generate seed districting
seed_districting = seed_district_generator.generate_seed_district(precincts, params.num_districts)

#  Initiate process pool and start generating the specified number of districtings
with Pool(processes=cpu_count()) as pool:
    res = [pool.apply_async(generate_districting, (seed_districting, params.iterations))
           for i in range(params.num_districtings)]

    districtings = [r.get() for r in res]
