from typing import Set, Tuple, List

from precinct_node import PrecinctNode
from precinct_subgraph import PrecinctSubgraph


class SpanningTree(object):
    def __init__(self, dist1: PrecinctSubgraph, dist2: PrecinctSubgraph):
        self.__nodes = dist1.nodes | dist2.nodes
        marked_nodes = set()
        self.__edges = set()

        res = [list(self.__nodes)[0]]
        marked_nodes.add(res[0])
        while len(res) != 0:
            current = res.pop(0)
            for neighbor in (current.adjacent_precincts & self.__nodes) - marked_nodes:
                self.__edges.add((current, neighbor))
                marked_nodes.add(neighbor)
                res.append(neighbor)
        #for node in self.__nodes:
        #    if node in marked_nodes:
        #        continue
        #    marked_nodes.add(node)
        #    neighbors_to_consider = node.adjacent_precincts.intersection(marked_nodes)

        #    edges: Set[Tuple[PrecinctNode, PrecinctNode]] = set(map(lambda n: (node, n), neighbors_to_consider))

        #    self.__edges = self.__edges | edges

    @property
    def nodes(self):
        return self.__nodes

    @property
    def edges(self):
        return self.__edges

    def cut(self, edge: Tuple[PrecinctNode, PrecinctNode]):
        if edge not in self.__edges:
            raise ValueError('edge must be an edge in the tree')

        edges = self.__edges.copy()
        edges.remove(edge)
        res = [{edge[0]}, {edge[1]}]
        while len(edges) != 0:
            e = edges.pop()
            set1 = next(filter(lambda s: e[0] in s, res), None)
            set2 = next(filter(lambda s: e[1] in s, res), None)

            if set1 is None and set2 is None:
                res.append({*e})
            elif set1 is None:
                set2.add(e[0])
            elif set2 is None:
                set1.add(e[1])
            else:
                new_set = set1 | set2
                res.remove(set1)
                res.remove(set2)
                res.append(new_set)

        return PrecinctSubgraph.from_set(res[0]), PrecinctSubgraph.from_set(res[1])
