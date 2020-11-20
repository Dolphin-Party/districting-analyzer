class PrecinctNode(object):
    def __init__(self, id: int, vap: int, xvap: int):
        self.__id = id
        self.__vap = vap
        self.__xvap = xvap
        self.__adjacent_precincts = []

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

            precinct1.adjacent_precincts.append(precinct2)
            precinct2.adjacent_precincts.append(precinct1)
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