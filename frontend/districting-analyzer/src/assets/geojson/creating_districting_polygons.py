from shapely.ops import unary_union
from shapely.geometry import shape, mapping
import json

# recieving a districting type (i.e random, extreme, average)
         # district        district
#{random: [[precinctIds], [precinctIds]]} -- input
#{random: [[districtShape], [districtShape]]} -- output

# Job has list of districtings, with specific IDs for average, and extreme
# We need to query each districting (Random, Extreme) and take the list of districts

# [[[precinctID, precinctID]]]
# Districting IDs --> each has list of districtID --> each has list of precinctID


precincts_file = 'VA_precinct_mapping.json'
input_file = 'VA_districting12.json'
output_file = 'VA_districting_12.json'
districtingType = 'Random1'
with open(precincts_file) as f:
  precinctDict = json.load(f)

with open(input_file) as f:
  districting = json.load(f)

featureCollection = {"type": "FeatureCollection", "features": []}
districtingDict = []


for i in range(0, len(districting)): #district1, district2
    print("creating a district...")
    districtID = "district"+str(i+1)
    # districtingDict[districtID] = []
    district = districting[i]
    c = []
    for j in range(0, len(district)):
        # GET Precinct Geometry
        precinct = precinctDict[district[j]]
        c.append(shape(precinct))
    district_shape = unary_union(c)
    feature = {"type": "Feature", "geometry": mapping(district_shape)}
    districtingDict.append(feature)
featureCollection["features"] = districtingDict

with open(output_file, 'w+') as out_file:
  out_json = json.dumps(featureCollection)
  out_file.write(out_json)
