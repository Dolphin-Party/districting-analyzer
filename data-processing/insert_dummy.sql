USE dolphins;

-- Basics
INSERT INTO Districtings (`ID`, `jobID`, `targetDemographic`, `districtingIndex`)
VALUES (4, null, null, 0);

INSERT INTO States (`ID`, `name`, `shape`, `canonicalDistrictingID`)
VALUES (111, 'Dummy', null, 4);

INSERT INTO Counties (`ID`, `name`, `stateID`, `shape`)
VALUES (101, 'DU-1', 111, null);
INSERT INTO Counties (`ID`, `name`, `stateID`, `shape`)
VALUES (102, 'DU-2', 111, null);
INSERT INTO Counties (`ID`, `name`, `stateID`, `shape`)
VALUES (103, 'DU-3', 111, null);

-- Precincts
INSERT INTO Precincts (`ID`, `countyID`, `shape`, `population`)
VALUES (1001, 101, '{"fruit": "1001-apple"}', 1);
INSERT INTO Precincts (`ID`, `countyID`, `shape`, `population`)
VALUES (1002, 101, '{"fruit": "1002-pear"}', 11);
INSERT INTO Precincts (`ID`, `countyID`, `shape`, `population`)
VALUES (1003, 101, '{"fruit": "1003-orange"}', 111);

INSERT INTO Precincts (`ID`, `countyID`, `shape`, `population`)
VALUES ('2001', 102, '{"fruit": "2001-blueberry"}', 2);
INSERT INTO Precincts (`ID`, `countyID`, `shape`, `population`)
VALUES ('2002', 102, '{"fruit": "2002-strawberry"}', 22);
INSERT INTO Precincts (`ID`, `countyID`, `shape`, `population`)
VALUES ('2003', 102, '{"fruit": "2003-raspberry"}', 222);

INSERT INTO Precincts (`ID`, `countyID`, `shape`, `population`)
VALUES ('3001', 103, '{"fruit": "3001-kiwi"}', 3);
INSERT INTO Precincts (`ID`, `countyID`, `shape`, `population`)
VALUES ('3002', 103, '{"fruit": "3002-grape"}', 33);
INSERT INTO Precincts (`ID`, `countyID`, `shape`, `population`)
VALUES ('3003', 103, '{"fruit": "3003-lychee"}', 333);

-- Precinct Edges
INSERT INTO PrecinctNeighbors VALUES ('1001', '3002');
INSERT INTO PrecinctNeighbors VALUES ('1001', '3003');
INSERT INTO PrecinctNeighbors VALUES ('1002', '3001');
INSERT INTO PrecinctNeighbors VALUES ('1002', '3003');
INSERT INTO PrecinctNeighbors VALUES ('1003', '3001');
INSERT INTO PrecinctNeighbors VALUES ('1003', '3002');

INSERT INTO PrecinctNeighbors VALUES ('2001', '3002');
INSERT INTO PrecinctNeighbors VALUES ('2001', '3003');
INSERT INTO PrecinctNeighbors VALUES ('2002', '3001');
INSERT INTO PrecinctNeighbors VALUES ('2002', '3003');
INSERT INTO PrecinctNeighbors VALUES ('2003', '3001');
INSERT INTO PrecinctNeighbors VALUES ('2003', '3002');

INSERT INTO PrecinctNeighbors VALUES ('3001', '3002');
INSERT INTO PrecinctNeighbors VALUES ('3001', '3003');
INSERT INTO PrecinctNeighbors VALUES ('3002', '3001');
INSERT INTO PrecinctNeighbors VALUES ('3002', '3003');
INSERT INTO PrecinctNeighbors VALUES ('3003', '3001');
INSERT INTO PrecinctNeighbors VALUES ('3003', '3002');

-- Precinct Demographics
INSERT INTO PrecinctDemographics VALUES ('1001', 'americanIndian', 1);
INSERT INTO PrecinctDemographics VALUES ('1001', 'asian', 0);
INSERT INTO PrecinctDemographics VALUES ('1001', 'black', 0);
INSERT INTO PrecinctDemographics VALUES ('1001', 'pacific', 0);
INSERT INTO PrecinctDemographics VALUES ('1001', 'twoOrMoreRaces', 0);
INSERT INTO PrecinctDemographics VALUES ('1001', 'whiteNonHispanic', 0);