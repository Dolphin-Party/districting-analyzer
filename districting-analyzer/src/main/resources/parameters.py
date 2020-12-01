class DistrictingParameters(object):
    def __init__(self, num_districts: int, compactness: float, percent_vap: float, iterations: int):
        self.__num_districts = num_districts
        self.__compactness = compactness
        self.__percent_vap = percent_vap
        self.__iterations = iterations

    @property
    def num_districts(self):
        return self.__num_districts

    @property
    def compactness(self):
        return self.__compactness

    @property
    def percent_vap(self):
        return self.__percent_vap

    @property
    def iterations(self):
        return self.__iterations

    @staticmethod
    def from_json(json: dict):
        return DistrictingParameters(json["numDistricts"], json["compactness"], json["percentVap"], json["iterations"])

