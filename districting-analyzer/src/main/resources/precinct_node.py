from typing import Set
from shapely.geometry import shape


class PrecinctNode(object):
    def __init__(self, id: int, vap: int, xvap: int, precinct_shape):
        self.__id = id
        self.__vap = vap
        self.__xvap = xvap
        self.__adjacent_precincts: Set[PrecinctNode] = set()
        self.__shape = precinct_shape

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

    @property
    def shape(self):
        return self.__shape

    @staticmethod
    def from_json(json: dict):
        res = {}
        for precinct in json["precincts"]:
            res[precinct["id"]] = PrecinctNode(precinct["id"], precinct["vap"], precinct["xvap"],
                                               shape(json["shape"]))
        for edge in json["edges"]:
            precinct1 = res[edge["id1"]]
            precinct2 = res[edge["id2"]]

            precinct1.adjacent_precincts.add(precinct2)
            precinct2.adjacent_precincts.add(precinct1)
        return list(res.values())
