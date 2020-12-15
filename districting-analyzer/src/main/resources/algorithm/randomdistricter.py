import argparse
import json
import sys
import seed_district_generator
from districting_job import generate_districting
from parameters import DistrictingParameters
from precinct_graph import *
from multiprocessing import Pool
from os import cpu_count


if __name__ == '__main__':
    sys.setrecursionlimit(10000)
    parser = argparse.ArgumentParser(description='Generate random districtings given parameters')
    parser.add_argument('params', metavar='P', type=argparse.FileType('r'))
    parser.add_argument('data', metavar='D', type=argparse.FileType('r'))

    parser.add_argument('outdir', metavar='O')

    args = parser.parse_args()

    #  Unpack JSON files
    x = json.load(args.data)
    precincts = PrecinctNode.from_json(x)
    params: DistrictingParameters = json.load(args.params, object_hook=DistrictingParameters.from_json)

    #  Generate seed districting
    seed_districting = seed_district_generator.generate_seed_district(precincts, params.num_districts)

    target_pop = seed_districting.population / params.num_districts
    pop_variance = seed_districting.population * params.percent_diff

    #for i in range(params.num_districtings):
    #    generate_districting(seed_districting, params.iterations, target_pop, pop_variance, params.compactness, args.outdir, i)

    #  Initiate process pool and start generating the specified number of districtings
    with Pool(processes=cpu_count()) as pool:
        res = [pool.apply_async(generate_districting, (seed_districting, params.iterations, target_pop,
                                                       pop_variance, params.compactness, args.outdir, i))
               for i in range(params.num_districtings)]
        districting_file_paths = [r.get() for r in res]

    print("[", end='')
    for districting in districting_file_paths:
        with open(districting, 'r') as f:
            j = json.load(f)
            json.dump(j, sys.stdout)
            if districting_file_paths.index(districting) != len(districting_file_paths) - 1:
                print(',', end='')
    print("]", end='')
