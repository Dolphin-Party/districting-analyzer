from typing import List, Set
from shapely.geometry import shape
import compactness as cmp
from precinct_node import PrecinctNode
from precinct_subgraph import PrecinctSubgraph


class PrecinctGraph(object):
    def __init__(self, initial_nodes: List[PrecinctNode]):
        subgraphs = {}
        for node in initial_nodes:
            new_subgraph = PrecinctSubgraph()
            new_subgraph.nodes.add(node)
            subgraphs[node] = new_subgraph

        for node, subgraph in subgraphs.items():
            for neighbor in node.adjacent_precincts:
                subgraph.neighbors.add(subgraphs[neighbor])

        self.__subgraphs = set(subgraphs.values())

    @property
    def subgraphs(self):
        return self.__subgraphs

    @subgraphs.setter
    def subgraphs(self, new_subgraphs):
        self.__subgraphs = new_subgraphs

    @property
    def num_districts(self):
        return len(self.__subgraphs)

