import json

summary = None
district_ids_geojson = None

with open('summary.json', 'r') as f:
    data = f.read()
    summary = json.loads(data)
with open('NC_district_ids_geojson.json', 'r') as f:
    data = f.read()
    district_ids_geojson = json.loads(data)

for i in range(len(summary['districtings'])):
    d = summary['districtings'][i]
    for j in range(len(d['congressionalDistrictsGeoJSON']['features'])):
        di = d['congressionalDistrictsGeoJSON']['features'][j]
        di_id = str(di['properties']['districtID'])
        if di_id in district_ids_geojson:
            di['geometry'] = district_ids_geojson[di_id]
            d['congressionalDistrictsGeoJSON']['features'][j] = di
    summary['districtings'][i] = d

with open('summary_final.json', 'w+') as f:
    json.dump(summary,f)

