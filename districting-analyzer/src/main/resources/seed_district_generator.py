import random
from typing import List

from precinct_graph import PrecinctGraph, PrecinctNode


def generate_seed_district(precincts: List[PrecinctNode], num_districts: int):
    precinct_graph = PrecinctGraph(precincts)
    while precinct_graph.num_districts > num_districts:
        #  combine subgraphs with random neighbors until we reached the requisite number of districtings
        checked_set = set()
        new_subgraphs_set = set()
        merged_subgraphs = 0
        for subgraph in precinct_graph.subgraphs:
            if subgraph in checked_set:
                continue

            possible_merges = [x for x in subgraph.neighbors if x not in checked_set and x not in new_subgraphs_set]

            if len(possible_merges) == 0 or precinct_graph.num_districts - merged_subgraphs <= num_districts:
                new_subgraphs_set.add(subgraph)
                continue

            selected_merge = random.choice(possible_merges)
            merged_subgraph = subgraph.merge(selected_merge)

            new_subgraphs_set.add(merged_subgraph)
            checked_set.add(subgraph)
            checked_set.add(selected_merge)
            merged_subgraphs += 1

        precinct_graph.subgraphs = merged_subgraphs
    return precinct_graph
