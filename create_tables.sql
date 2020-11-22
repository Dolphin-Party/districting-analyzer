USE dolphins;

-- TODO: shape type needs to be changed to represent geographic data
-- TODO: add NOT NULL to columns as needed

CREATE TABLE IF NOT EXISTS States (
	ID int PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255),
    canonicalDistrictingID int
);

CREATE TABLE IF NOT EXISTS Counties (
	ID int PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255),
    stateID int NOT NULL,
    FOREIGN KEY (stateID) REFERENCES States(ID),
    shape varchar(255)
);

CREATE TABLE IF NOT EXISTS Precincts (
	ID int PRIMARY KEY AUTO_INCREMENT,
    countyID int NOT NULL,
    FOREIGN KEY (countyID) REFERENCES Counties(ID),
    shape varchar(255),
    population int
);

CREATE TABLE IF NOT EXISTS PrecinctDemographics (
	precinctID int NOT NULL,
	demographic varchar(255) NOT NULL,
	population int(11) DEFAULT NULL,
	PRIMARY KEY (precinctID,demographic)
);

CREATE TABLE IF NOT EXISTS PrecinctNeighbors (
	precinctID int NOT NULL,
	neighborID int NOT NULL,
	PRIMARY KEY (precinctID,neighborID)
);

CREATE TABLE IF NOT EXISTS Jobs (
	ID int PRIMARY KEY AUTO_INCREMENT,
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
	ID int PRIMARY KEY AUTO_INCREMENT,
    jobID int,
    FOREIGN KEY (jobID) REFERENCES Jobs(ID),
    targetDemographic varchar(255),
    districtingIndex int
);

ALTER TABLE States
ADD CONSTRAINT FK_CanonicalDistricting
FOREIGN KEY (canonicalDistrictingID) REFERENCES Districtings(ID);

CREATE TABLE IF NOT EXISTS Districts (
	ID int PRIMARY KEY AUTO_INCREMENT,
    districtingID int,
    FOREIGN KEY (districtingID) REFERENCES Districtings(ID),
    numberCounties int,
    targetDemographic varchar(255),
    targetDemographicPercentVap double
);

CREATE TABLE IF NOT EXISTS DistrictPrecincts (
	districtID int NOT NULL,
	FOREIGN KEY (districtID) REFERENCES Districts(ID),
	precinctID int NOT NULL,
	FOREIGN KEY (precinctID) REFERENCES Precincts(ID)
);

CREATE TABLE IF NOT EXISTS BoxWhiskers (
	ID int PRIMARY KEY AUTO_INCREMENT,
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
