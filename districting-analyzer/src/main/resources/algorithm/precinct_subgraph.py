from typing import Set
import compactness as cmp

from precinct_node import PrecinctNode


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
        self.neighbors.remove(other)
        other.neighbors.remove(self)
        new_subgraph = PrecinctSubgraph()
        new_subgraph.__nodes = self.nodes.union(other.nodes)
        new_subgraph.__neighbors = self.neighbors.union(other.neighbors)

        for neighbor in self.neighbors:
            neighbor.neighbors.remove(self)
            neighbor.neighbors.add(new_subgraph)

        for neighbor in other.neighbors:
            neighbor.neighbors.remove(other)
            neighbor.neighbors.add(new_subgraph)

        return new_subgraph

    @property
    def compactness(self):
        return cmp.compactness_score(self)

    @property
    def population(self):
        return sum(map(lambda n: n.vap, self.__nodes))

    @staticmethod
    def from_set(dist: Set[PrecinctNode]):
        res = PrecinctSubgraph()
        res.__nodes = dist
        return res

    def __repr__(self):
        return f'{self.nodes}'
