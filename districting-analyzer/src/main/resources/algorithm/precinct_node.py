from typing import Set
from shapely.geometry import shape
import json as j


class PrecinctNode(object):
    def __init__(self, id: int, vap: int, precinct_shape):
        self.__id = id
        self.__vap = vap
        self.__adjacent_precincts: Set[PrecinctNode] = set()
        self.__shape = precinct_shape

    @property
    def id(self):
        return self.__id

    @property
    def vap(self):
        return self.__vap

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
            s = j.loads(precinct["shape"])
            res[precinct["precinctId"]] = PrecinctNode(precinct["precinctId"], precinct["population"],
                                                       shape(s))
        for edge in json["edges"]:
            precinct1 = res[edge["precinctId"]]
            precinct2 = res[edge["neighborId"]]

            precinct1.adjacent_precincts.add(precinct2)
            precinct2.adjacent_precincts.add(precinct1)
        return list(res.values())

    def __repr__(self):
        return f'PRECINCT: Id: {self.id}, VAP: {self.vap}'

