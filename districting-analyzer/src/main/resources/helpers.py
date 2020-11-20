from typing import List, Set


class PrecinctNode(object):
    def __init__(self, id: int, vap: int, xvap: int):
        self.__id = id
        self.__vap = vap
        self.__xvap = xvap
        self.__adjacent_precincts: Set[PrecinctNode] = set()

    @property
    def id(self):
        return self.__id

    @property
    def vap(self):
        return self.__vap

    @property
    def xvap(self):
        return self.__xvap

    @property
    def adjacent_precincts(self):
        return self.__adjacent_precincts

    @staticmethod
    def from_json(json: dict):
        res = {}
        for precinct in json["precincts"]:
            res[precinct["id"]] = PrecinctNode(precinct["id"], precinct["vap"], precinct["xvap"])
        for edge in json["edges"]:
            precinct1 = res[edge["id1"]]
            precinct2 = res[edge["id2"]]

            precinct1.adjacent_precincts.add(precinct2)
            precinct2.adjacent_precincts.add(precinct1)
        return list(res.values())


class DistrictingParameters(object):
    def __init__(self, num_districts: int, compactness: float, percent_vap: float):
        self.__num_districts = num_districts
        self.__compactness = compactness
        self.__percent_vap = percent_vap

    @property
    def num_districts(self):
        return self.__num_districts

    @property
    def compactness(self):
        return self.__compactness

    @property
    def percent_vap(self):
        return self.__percent_vap

    @staticmethod
    def from_json(json: dict):
        return DistrictingParameters(json["numDistricts"], json["compactness"], json["percentVap"])


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
