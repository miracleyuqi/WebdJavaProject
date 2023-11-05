--Yuqi Zhou
--Assignment 2
--08 Feb 2023
--WEBD4201-01

-- Dropping table, clear out any existing data

DROP TABLE IF EXISTS students;
CREATE TABLE students(
	id INT PRIMARY KEY NOT NULL REFERENCES users(Id),
    ProgramCode VARCHAR(4), 
    ProgramDescription VARCHAR(50),
    Year INT
);

ALTER TABLE students OWNER TO webd4201_admin;
INSERT INTO students(id, ProgramCode, ProgramDescription, Year) 
	VALUES (100111111, 'CSTY', 'Computer System Technology', 3);
INSERT INTO students(id, ProgramCode, ProgramDescription, Year) 
	VALUES (100854558, 'CPGA', 'Computer Programming and Analysis', 2);
INSERT INTO students(id, ProgramCode, ProgramDescription, Year) 
	VALUES (100222333, 'CPGA', 'Computer Programming and Analysis', 2);

SELECT * FROM students;