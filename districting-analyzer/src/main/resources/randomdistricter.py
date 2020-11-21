import argparse
import json
import random

from helpers import *

#  load data out of JSON file

parser = argparse.ArgumentParser(description='Generate random districtings given parameters')
parser.add_argument('params', metavar='P', type=argparse.FileType('r'))
parser.add_argument('data', metavar='D', type=argparse.FileType('r'))

args = parser.parse_args()

#  Unpack JSON files

precincts: List[PrecinctNode] = json.load(args.data, object_hook=PrecinctNode.from_json)

params: DistrictingParameters = json.load(args.params, object_hook=DistrictingParameters.from_json)

#  Generate seed districting

precinct_graph = PrecinctGraph(precincts)

while precinct_graph.num_districts > params.num_districts:
    #  combine subgraphs with random neighbors until we reached the requisite number of districtings
    checked_set = set()
    new_subgraphs_set = set()
    merged_subgraphs = 0
    for subgraph in precinct_graph.subgraphs:
        if subgraph in checked_set:
            continue

        possible_merges = [x for x in subgraph.neighbors if x not in checked_set and x not in new_subgraphs_set]

        if len(possible_merges) == 0 or precinct_graph.num_districts - merged_subgraphs <= params.num_districts:
            new_subgraphs_set.add(subgraph)
            continue

        selected_merge = random.choice(possible_merges)
        merged_subgraph = subgraph.merge(selected_merge)

        new_subgraphs_set.add(merged_subgraph)
        checked_set.add(subgraph)
        checked_set.add(selected_merge)
        merged_subgraphs += 1

    precinct_graph.subgraphs = merged_subgraphs

#  precinct_graph now contains our seed districting
#  Rebalance for X iterations
