import json

input_file = 'VA_frontend_precincts.json'
output_file = 'VA_formatted_precincts.json'
with open(input_file) as f:
  fileInput = json.load(f)

precincts = fileInput["precincts"]
features = []

newFile = {"type": "FeatureCollection", "features": []}
for precinct in precincts:
    demographicDict = precinct["demographics"]
    demographicDict["totalPop"] = precinct["population"]
    demographics = {"demographicData": demographicDict}
    shapeDict = json.loads(precinct["shape"])
    precinctGeometry = shapeDict["features"][0]["geometry"] #we may need to parse this
    feature = {"type": "Feature", "properties": demographics, "geometry": precinctGeometry}
    features.append(feature)
newFile["features"]= features

with open(output_file, 'w+') as out_file:
  out_json = json.dumps(newFile)
  out_file.write(out_json)
