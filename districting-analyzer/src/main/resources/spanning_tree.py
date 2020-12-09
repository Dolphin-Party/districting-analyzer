from typing import Set, Tuple

from precinct_node import PrecinctNode
from precinct_subgraph import PrecinctSubgraph


class SpanningTree(object):
    def __init__(self, dist1: PrecinctSubgraph, dist2: PrecinctSubgraph):
        self.__nodes = dist1.nodes | dist2.nodes
        marked_nodes = set()
        self.__edges = set()
        for node in self.__nodes:
            if node in marked_nodes:
                continue
            marked_nodes.add(node)
            neighbors_not_in_tree = node.adjacent_precincts - marked_nodes
            edges: Set[Tuple[PrecinctNode, PrecinctNode]] = set(map(lambda n: (node, n), neighbors_not_in_tree))

            self.__edges = self.__edges | edges

    @property
    def nodes(self):
        return self.__nodes

    @property
    def edges(self):
        return self.__edges

    def cut(self, edge: Tuple[PrecinctNode, PrecinctNode]):
        if edge not in self.__edges:
            raise ValueError('edge must be an edge in the tree')

        cut_set = self.__edges - set(edge)
        res: Set[Set[PrecinctNode]] = set()
        for e in cut_set:
            set1 = next(filter(lambda s: e[0] in s, res), None)
            set2 = next(filter(lambda s: e[1] in s, res), None)

            if set1 is None and set2 is None:
                res.add({e[0], e[1]})
            elif set1 is None:
                set2.add(e[1])
            elif set2 is None:
                set1.add(e[0])
            else:
                res.remove(set1)
                res.remove(set2)
                res.add(set1 | set2)
        return PrecinctSubgraph.from_set(res.pop()), PrecinctSubgraph.from_set(res.pop())
