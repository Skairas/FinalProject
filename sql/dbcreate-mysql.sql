-- ==============================================================
-- ST4Example DB creation script for MySQL
-- ==============================================================

SET NAMES utf8;

DROP DATABASE IF EXISTS st4db;
CREATE DATABASE st4db CHARACTER SET utf8 COLLATE utf8_bin;

USE st4db;
-- --------------------------------------------------------------
-- ROLES
-- users roles
-- --------------------------------------------------------------
CREATE TABLE roles(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL PRIMARY KEY,

-- name has the VARCHAR type - a string with a variable length
-- names values should not be repeated (UNIQUE)
	name VARCHAR(10) NOT NULL UNIQUE
);

-- this two commands insert data into roles table
-- --------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Role entity, so the numeration must started 
-- from 0 with the step equaled to 1
-- --------------------------------------------------------------
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'user');

-- --------------------------------------------------------------
-- USERS
-- --------------------------------------------------------------
CREATE TABLE users(

	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	
-- 'UNIQUE' means logins values should not be repeated in login column of table	
	login VARCHAR(20) NOT NULL UNIQUE,
	
-- not null string columns	
	password VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	preferred_lang VARCHAR(10) NOT NULL,
	balance INTEGER,
	banned BOOLEAN default 0,

-- this declaration contains the foreign key constraint	
-- role_id in users table is associated with id in roles table
-- role_id of user = id of role
	role_id INTEGER NOT NULL REFERENCES roles(id) 

-- removing a row with some ID from roles table implies removing 
-- all rows from users table for which ROLES_ID=ID
-- default value is ON DELETE RESTRICT, it means you cannot remove
-- row with some ID from the roles table if there are rows in 
-- users table with ROLES_ID=ID
		ON DELETE CASCADE 

-- the same as previous but updating is used insted deleting
		ON UPDATE RESTRICT
);

-- id = 1
INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'Ivan', 'Ivanov', 'en', 1000, DEFAULT, 0);
-- id = 2
INSERT INTO users VALUES(DEFAULT, 'client', 'client', 'Petr', 'Petrov', 'en', 500, DEFAULT, 1);
-- id = 3
INSERT INTO users VALUES(DEFAULT, 'петров', 'петров', 'Иван', 'Петров', 'en', 600, DEFAULT, 1);

-- --------------------------------------------------------------
-- STATUSES
-- statuses for orders
-- --------------------------------------------------------------
CREATE TABLE statuses(
	id INTEGER NOT NULL PRIMARY KEY,
	status_name VARCHAR(10) NOT NULL UNIQUE
);

-- --------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Status entity, so the numeration must started 
-- from 0 with the step equaled to 1
-- --------------------------------------------------------------
INSERT INTO statuses VALUES(0, 'ended');
INSERT INTO statuses VALUES(1, 'paid');

-- --------------------------------------------------------------
-- CATEGORIES
-- categories names of menu
-- --------------------------------------------------------------
CREATE TABLE categories(
	id INTEGER NOT NULL PRIMARY KEY,
	category_name VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO categories VALUES(1, 'Cooking'); -- кулинарные журналы
INSERT INTO categories VALUES(2, 'Traveling'); -- путешествия
INSERT INTO categories VALUES(3, 'Musical'); -- музыкальные журналы
INSERT INTO categories VALUES(4, 'Information technologies'); -- IT журналы

CREATE TABLE categories_translated(
    id INTEGER NOT NULL PRIMARY KEY auto_increment,
    translated_category_name VARCHAR(50) NOT NULL UNIQUE,
    locale VARCHAR(10),
    category_id INTEGER NOT NULL references categories(id)
);

INSERT INTO categories_translated VALUES(DEFAULT, 'Cooking', 'en', 1); -- кулинарные журналы
INSERT INTO categories_translated VALUES(DEFAULT, 'Traveling', 'en', 2); -- путешествия
INSERT INTO categories_translated VALUES(DEFAULT, 'Musical', 'en', 3); -- музыкальные журналы
INSERT INTO categories_translated VALUES(DEFAULT, 'Information technologies', 'en', 4); -- IT журналы

INSERT INTO categories_translated VALUES(DEFAULT, 'Кулинария', 'ru', 1); -- кулинарные журналы
INSERT INTO categories_translated VALUES(DEFAULT, 'Путешествия', 'ru', 2); -- путешествия
INSERT INTO categories_translated VALUES(DEFAULT, 'Музыка', 'ru', 3); -- музыкальные журналы
INSERT INTO categories_translated VALUES(DEFAULT, 'Информационные технологии', 'ru', 4); -- IT журналы

-- --------------------------------------------------------------
-- PERIODICALS
-- --------------------------------------------------------------
CREATE TABLE periodicals(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	periodical_name VARCHAR(100) NOT NULL UNIQUE,
	price INTEGER NOT NULL,
	category_id INTEGER NOT NULL REFERENCES categories(id)
);

INSERT INTO periodicals VALUES(DEFAULT , 'BonAppetite', 100, 1);
INSERT INTO periodicals VALUES(DEFAULT , 'Around the world', 150, 2);
INSERT INTO periodicals VALUES(DEFAULT , 'JazzTimes', 250, 3);
INSERT INTO periodicals VALUES(DEFAULT , 'IT Weekly', 50, 4);

-- --------------------------------------------------------------
-- SUBSCRIPTIONS
-- --------------------------------------------------------------
CREATE TABLE subscriptions(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES users(id),
	periodical_id INTEGER NOT NULL REFERENCES periodicals(id),
	status_id INTEGER NOT NULL REFERENCES statuses(id),
	ending_date VARCHAR(30)
);

INSERT INTO subscriptions VALUES(DEFAULT , 1, 4, 1, '2020-4-20');
INSERT INTO subscriptions VALUES(DEFAULT , 1, 2, 1, '2020-4-20');
INSERT INTO subscriptions VALUES(DEFAULT , 1, 1, 0, '2020-3-20');
INSERT INTO subscriptions VALUES(DEFAULT , 2, 3, 1, '2020-4-15');

-- --------------------------------------------------------------
-- test database:
-- --------------------------------------------------------------
SELECT * FROM users;
SELECT * FROM periodicals;
SELECT * FROM subscriptions;
SELECT * FROM categories;
SELECT * FROM categories_translated;
SELECT * FROM statuses;
SELECT * FROM roles;
