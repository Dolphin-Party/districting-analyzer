from typing import Set
from precinct_node import PrecinctNode
import compactness as cmp


class PrecinctSubgraph(object):
    def __init__(self):
        self.__nodes: Set[PrecinctNode] = set()
        self.__neighbors: Set[PrecinctSubgraph] = set()

    @property
    def nodes(self):
        return self.__nodes

    @property
    def neighbors(self):
        return self.__neighbors

    @property
    def num_precincts(self):
        return len(self.__nodes)

    def merge(self, other: 'PrecinctSubgraph'):
        new_subgraph = PrecinctSubgraph()
        new_subgraph.__nodes = self.nodes.union(other.nodes)
        new_subgraph.__neighbors = self.neighbors.union(other.neighbors)
        new_subgraph.neighbors.remove(self)
        new_subgraph.neighbors.remove(other)

        for neighbor in self.neighbors:
            neighbor.neighbors.remove(self)
            neighbor.neighbors.add(new_subgraph)

        for neighbor in other.neighbors:
            neighbor.neighbors.remove(other)
            neighbor.neighbors.add(other)

        return new_subgraph

    @property
    def compactness(self):
        return cmp.compactness_score(self)
