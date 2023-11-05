--Yuqi Zhou
--Assignment 2
--08 Feb 2023
--WEBD4201-01

-- Dropping table, clear out any existing data

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users(
	Id INT PRIMARY KEY NOT NULL,
	Password VARCHAR(100) NOT NULL,
	FirstName VARCHAR(40) NOT NULL,
	LastName VARCHAR(40) NOT NULL,
	EmailAddress VARCHAR(40), 
	LastAccess DATE,
	EnrolDate DATE,
	Enabled BOOLEAN NOT NULL, 
	Type CHAR
);
ALTER TABLE users OWNER TO webd4201_admin;
INSERT INTO users(Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type) 
	VALUES (100111111, encode(digest('password', 'sha1'), 'hex'), 'Mike', 'Jones', 'mike.jones@dcmail.ca', current_date, '2023-02-08', true, 's');
INSERT INTO users(Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type) 
	VALUES (100854558, encode(digest('100854558', 'sha1'), 'hex'), 'Yuqi', 'Zhou', 'yuqi.zhou@dcmail.ca', current_date, '2022-01-14', true, 's');
INSERT INTO users(Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type) 
	VALUES (100222333, encode(digest('wordpass', 'sha1'), 'hex'), 'Haley', 'Westner', 'haley.westner@dcmail.ca', current_date, '2022-09-01', true, 's');
INSERT INTO users(Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type) 
	VALUES (200202209, encode(digest('facultypass', 'sha1'), 'hex'), 'Matthew', 'Clarkson', 'matthew.clarkson@durhamcollege.ca', current_date, '2017-03-15', true, 'f');
INSERT INTO users(Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type) 
	VALUES (200937628, encode(digest('678678678', 'sha1'), 'hex'), 'Nicole', 'Firby', 'nicole.firby@durhamcollege.ca', current_date, '2020-11-22', true, 'f');
INSERT INTO users(Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type) 
	VALUES (200555674, encode(digest('1111aaaa', 'sha1'), 'hex'), 'Kelly', 'Liu', 'kelly.liu@durhamcollege.ca', current_date, '2021-07-03', true, 'f');

SELECT * FROM users;