USE dolphins;

-- TODO: shape type needs to be changed to represent geographic data
-- TODO: add NOT NULL to columns as needed

CREATE TABLE IF NOT EXISTS States (
	ID int PRIMARY KEY,
    `name` varchar(255),
    shape JSON,
    population int(11),
    canonicalDistrictingID int
);

CREATE TABLE IF NOT EXISTS Counties (
	ID int PRIMARY KEY,
    `name` varchar(255),
    stateID int NOT NULL,
    FOREIGN KEY (stateID) REFERENCES States(ID),
    population int(11),
    shape JSON
);

CREATE TABLE IF NOT EXISTS Precincts (
	ID char(11) PRIMARY KEY,
    countyID int NOT NULL,
    FOREIGN KEY (countyID) REFERENCES Counties(ID),
    shape JSON,
    population int
);

CREATE TABLE IF NOT EXISTS PrecinctDemographics (
	precinctID char(11) NOT NULL,
    FOREIGN KEY (precinctID) REFERENCES Precincts(ID),
	demographic varchar(255) NOT NULL,
	population int(11) DEFAULT NULL,
	PRIMARY KEY (precinctID,demographic)
);

CREATE TABLE IF NOT EXISTS PrecinctNeighbors (
	precinctID char(11) NOT NULL,
	neighborID char(11) NOT NULL,
	PRIMARY KEY (precinctID,neighborID)
);

CREATE TABLE IF NOT EXISTS Jobs (
	ID int PRIMARY KEY,
    stateID int NOT NULL,
    FOREIGN KEY (stateID) REFERENCES States(ID),
    `status` varchar(255),
    numberDistrictings int,
    compactnessAmount varchar(255),
    targetDemographic varchar(255),
    isSeawulf boolean,
    averageDistricting int,
    extremeDistricting int
);

CREATE TABLE IF NOT EXISTS Districtings (
	ID int PRIMARY KEY,
    jobID int NULL,
    FOREIGN KEY (jobID) REFERENCES Jobs(ID),
    targetDemographic varchar(255),
    districtingIndex int
);

ALTER TABLE States
ADD CONSTRAINT FK_CanonicalDistricting
FOREIGN KEY (canonicalDistrictingID) REFERENCES Districtings(ID);

CREATE TABLE IF NOT EXISTS Districts (
	ID int PRIMARY KEY,
    districtingID int,
    FOREIGN KEY (districtingID) REFERENCES Districtings(ID),
    numberCounties int,
    targetDemographic varchar(255),
    targetDemographicPercentVap double,
    `order` int
);

CREATE TABLE IF NOT EXISTS DistrictPrecincts (
	districtID int NOT NULL,
	precinctID int NOT NULL,
	PRIMARY KEY (districtID,precinctID)
);

CREATE TABLE IF NOT EXISTS BoxWhiskers (
	ID int PRIMARY KEY,
    jobID int,
    FOREIGN KEY (jobID) REFERENCES Jobs(ID),
    `order` int,
    average double,
    min double,
    q1 double,
    median double,
    q3 double,
    max double
);

-- tables for hibernate sequence generation
CREATE TABLE IF NOT EXISTS JobIdSequence (
  `next_val` bigint(20) DEFAULT NULL
);
CREATE TABLE IF NOT EXISTS DistrictingIdSequence (
  `next_val` bigint(20) DEFAULT NULL
);
CREATE TABLE IF NOT EXISTS DistrictIdSequence (
  `next_val` bigint(20) DEFAULT NULL
);
CREATE TABLE IF NOT EXISTS BoxWhiskerIdSequence (
  `next_val` bigint(20) DEFAULT NULL
);

INSERT INTO JobIdSequence VALUE (1);
INSERT INTO DistrictingIdSequence VALUE (1);
INSERT INTO DistrictIdSequence VALUE (1);
INSERT INTO BoxWhiskerIdSequence VALUE (1);
