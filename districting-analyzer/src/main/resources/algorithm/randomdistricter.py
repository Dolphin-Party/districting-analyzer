import argparse
import json
import sys
import seed_district_generator
import math
from districting_job import generate_districting
from parameters import DistrictingParameters
from precinct_graph import *
from multiprocessing import Pool
from os import cpu_count
from mpi4py import MPI


if __name__ == '__main__':
    sys.setrecursionlimit(10000)
    comm = MPI.COMM_WORLD
    rank = comm.Get_rank()

    parser = argparse.ArgumentParser(description='Generate random districtings given parameters')
    parser.add_argument('params', metavar='P', type=argparse.FileType('r'))
    parser.add_argument('data', metavar='D', type=argparse.FileType('r'))

    parser.add_argument('outdir', metavar='O')

    args = parser.parse_args()

    #  Unpack JSON files
    params: DistrictingParameters = json.load(args.params, object_hook=DistrictingParameters.from_json)

    if rank == 0:
        x = json.load(args.data)
        precincts = PrecinctNode.from_json(x)
        #  Generate seed districting
        seed_districting = seed_district_generator.generate_seed_district(precincts, params.num_districts)
    else:
        seed_districting = None
    comm.barrier()
    seed_districting = comm.bcast(seed_districting, root=0)
    idx = [x for x in range(params.num_districtings)]
    sublist_n = math.ceil(len(idx) / comm.size)
    idx = [idx[x:x+sublist_n] for x in range(0, len(idx), sublist_n)]
    idx = comm.scatter(idx, root=0)
    comm.barrier()

    target_pop = seed_districting.population / params.num_districts
    pop_variance = seed_districting.population * params.percent_diff

    #  Initiate process pool and start generating the specified number of districtings
    with Pool(processes=cpu_count()) as pool:
        res = [pool.apply_async(generate_districting, (seed_districting, params.iterations, target_pop,
                                                       pop_variance, params.compactness, args.outdir, i))
               for i in idx]
        districting_file_paths = [r.get() for r in res]
    districting_file_paths = comm.gather(districting_file_paths, root=0)
    comm.barrier()
    if rank == 0:
        districting_file_paths = [path for sublist in districting_file_paths for path in sublist]
        print("[", end='')
        for districting in districting_file_paths:
            with open(districting, 'r') as f:
                j = json.load(f)
                json.dump(j, sys.stdout)
                if districting_file_paths.index(districting) != len(districting_file_paths) - 1:
                    print(',', end='')
        print("]", end='')
