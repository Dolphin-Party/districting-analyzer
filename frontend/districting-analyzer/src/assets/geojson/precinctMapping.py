import json

input_file = 'NC_frontend_precincts.json'
output_file = 'NC_precinct_mapping.json'
with open(input_file) as f:
  fileInput = json.load(f)

precincts = fileInput["precincts"]

precinctDict = {}

for precinct in precincts:
    precinctID = precinct['precinctId']
    shapeDict = json.loads(precinct["shape"])
    precinctGeometry = shapeDict["features"][0]["geometry"] #we may need to parse this
    precinctDict[precinctID] = precinctGeometry

with open(output_file, 'w+') as out_file:
  out_json = json.dumps(precinctDict)
  out_file.write(out_json)
