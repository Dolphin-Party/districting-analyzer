import fileinput, json

ugly_json = fileinput.input()
pretty_json = "".join(ugly_json)
pretty_json = json.dumps(json.loads(pretty_json), sort_keys=True, indent=2)
print(pretty_json)

