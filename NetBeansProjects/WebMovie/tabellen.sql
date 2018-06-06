create table FILM
(
	ID INTEGER not null primary key,
	TITLE VARCHAR(80),
	YEARS INTEGER,
	DIRECTOR VARCHAR(40),
	GENRE VARCHAR(30)
)


create table USERS
(
	ID INTEGER not null primary key,
	NAME VARCHAR(30),
	PASSWORD VARCHAR(40)
)

CREATE TABLE USER_FILMS
(
USER_ID INTEGER,
FILM_ID INTEGER,
CONSTRAINT usfm_pk primary key (user_id,film_id),
CONSTRAINT us_fk FOREIGN KEY (user_id) REFERENCES users(id),
CONSTRAINT fm_fk FOREIGN KEY (film_id) REFERENCES film(id)
)



CREATE TABLE ACTOR 
(
ID INTEGER,
NAME VARCHAR(60),
SURNAME VARCHAR(30),
FILM_ID INTEGER,
CONSTRAINT si_pk primary key (id),
CONSTRAINT sk_fk FOREIGN KEY (FILM_ID) REFERENCES film(id)
)

create sequence seq_id start with 100;
