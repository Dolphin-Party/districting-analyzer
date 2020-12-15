class DistrictingParameters(object):
    def __init__(self, num_districts: int, compactness: float, iterations: int,
                 num_districtings: int, percent_diff: float):
        self.__num_districts = num_districts
        self.__compactness = compactness
        self.__iterations = iterations
        self.__num_districtings = num_districtings
        self.__percent_diff = percent_diff

    @property
    def num_districtings(self):
        return self.__num_districtings

    @property
    def num_districts(self):
        return self.__num_districts

    @property
    def compactness(self):
        return self.__compactness

    @property
    def iterations(self):
        return self.__iterations

    @property
    def percent_diff(self):
        return self.__percent_diff

    @staticmethod
    def from_json(json: dict):
        return DistrictingParameters(json["numDistricts"], json["compactness"], json["iterations"],
                                     json["numDistrictings"], json["percentDiff"])
