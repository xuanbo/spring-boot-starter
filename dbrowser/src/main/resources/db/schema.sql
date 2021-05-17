CREATE TABLE IF NOT EXISTS DBRO_DATABASE (
	ID VARCHAR(50) PRIMARY KEY,
	TYPE VARCHAR(50) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	DESCRIPTION VARCHAR(50) DEFAULT NULL,
	URL VARCHAR(500) NOT NULL,
	USERNAME VARCHAR(255) NOT NULL,
	PASSWORD VARCHAR(255) NOT NULL
);