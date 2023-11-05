--Yuqi Zhou
--Assignment 2
--08 Feb 2023
--WEBD4201-01

-- Dropping table, clear out any existing data

DROP TABLE IF EXISTS faculty;
CREATE TABLE faculty(
	id INT PRIMARY KEY NOT NULL REFERENCES users(Id),
    SchoolCode VARCHAR(4), 
    SchoolDescription VARCHAR(40),
    Office VARCHAR(4),
    Extension INT
);

ALTER TABLE faculty OWNER TO webd4201_admin;
INSERT INTO faculty(id, SchoolCode, SchoolDescription, Office, Extension) 
	VALUES (200202209, 'DC', 'Durham College', 'C301', 2256);
INSERT INTO faculty(id, SchoolCode, SchoolDescription, Office, Extension) 
	VALUES (200937628, 'DC', 'Durham College', 'C319', 2390);
INSERT INTO faculty(id, SchoolCode, SchoolDescription, Office, Extension) 
	VALUES (200555674, 'UOIT', 'Ontario Tech University', 'M008', 7988);

SELECT * FROM faculty;