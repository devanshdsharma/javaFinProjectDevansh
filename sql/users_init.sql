
CREATE TABLE USERS(LoginName VARCHAR(20) PRIMARY KEY NOT NULL, Password VARCHAR(10) NOT NULL);
INSERT INTO USERS VALUES ('ds@epita.fr','dsepita');
INSERT INTO USERS VALUES ('sk@epita.fr','skepita');

select * from USERS;