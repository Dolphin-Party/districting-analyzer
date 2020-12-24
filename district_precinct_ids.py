import MySQLdb

db = MySQLdb.connect(
	host="mysql3.cs.stonybrook.edu",
	port=3306,
	user="dolphins",
	passwd="solongandthanks",
	db="dolphins"
)

cursor = db.cursor()

query = "SELECT ID FROM Districtings WHERE jobID = 259"
cursor.execute(query)
rows = cursor.fetchall()

districtingIds = [t[0] for t in rows]

districting_map = {}

query = "SELECT ID FROM Districts WHERE districtingID=%s"
query_2 = "SELECT precinctID FROM DistrictPrecincts WHERE districtID=%s"
for d in districtingIds:
    cursor.execute(query, (d,))
    rows = cursor.fetchall()
    districtIds = [t[0] for t in rows]

    district_map = {}
    for di in districtIds:
        cursor.execute(query_2, (di,))
        rows = cursor.fetchall()
        precinctIds = [t[0] for t in rows]

        district_map[str(di)] = precinctIds

    districting_map[str(d)] = district_map

print(districting_map)

