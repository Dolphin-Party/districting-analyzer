from collections import Counter
import fileinput, json

district_ids = None
district_geojson = {}

with open('NC_district_precincts.json', 'r') as f:
    data = f.read()
    district_ids = json.loads(data)

with open('NC_districting_1.json', 'r') as f:
    data = f.read()
    district_geojson[1] = json.loads(data)["features"]
with open('NC_districting_2.json', 'r') as f:
    data = f.read()
    district_geojson[2] = json.loads(data)["features"]
with open('NC_districting_3.json', 'r') as f:
    data = f.read()
    district_geojson[3] = json.loads(data)["features"]
with open('NC_districting_5.json', 'r') as f:
    data = f.read()
    district_geojson[5] = json.loads(data)["features"]
with open('NC_districting_6.json', 'r') as f:
    data = f.read()
    district_geojson[6] = json.loads(data)["features"]
with open('NC_districting_7.json', 'r') as f:
    data = f.read()
    district_geojson[7] = json.loads(data)["features"]

district_id_map = {}
for d in district_ids.values():
    for di_id, precincts in d.items():
        precincts_counter = Counter(precincts)
        for features in district_geojson.values():
            for d_geojson in features:
                if precincts_counter == Counter(d_geojson['precinctIDs']):
                    district_id_map[str(di_id)] = d_geojson['geometry']
                    break

with open('NC_district_ids_geojson.json', 'w+') as f:
    json.dump(district_id_map,f)

