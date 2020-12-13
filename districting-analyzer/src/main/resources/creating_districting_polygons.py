from shapely.ops import unary_union
from shapely.geometry import shape, mapping
import json

# recieving a districting type (i.e random, extreme, average)
         # district        district
#{random: [[precinctIds], [precinctIds]]} -- input
#{random: [[districtShape], [districtShape]]} -- output
with open('sample_districtings.json') as f: #OR however we may get this data - this assumes it receives json
  districtings = json.load(f)

districtings_shapes = {}
for key in districtings:
    districting = districtings[key] #districting type (random, average, etc)
    districtings_shapes[key] = {}
    for i in range(0, len(districting)): #district1, district2
        districtID = "district"+str(i+1)
        districtings_shapes[key][districtID] = []
        district = districting[i]
        c = []
        for i in range(0, len(district)):
            c.append(shape(district[i]["geometry"]))
        district_shape = unary_union(c)
        feature = {"geometry": mapping(district_shape)}
        districtings_shapes[key][districtID].append(feature)
print(json.dumps(districtings_shapes)) #or return this way, or however we may store it.
