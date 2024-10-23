DROP DATABASE IF EXISTS eventdb;
CREATE DATABASE eventDB;

USE eventDB;

DROP USER IF EXISTS 'eventDB';
CREATE USER 'eventDB' IDENTIFIED BY 'eventDBpassword';
GRANT ALL ON eventDB.* TO 'eventDB';
FLUSH PRIVILEGES;

CREATE TABLE students
(
    name    VARCHAR(255) UNIQUE,
    program VARCHAR(255)
);

CREATE TABLE studyPrograms
(
    programName        VARCHAR(255),
    programResponsible VARCHAR(255)
);

CREATE TABLE guests
(
    guestName VARCHAR(255),
    invitedBy VARCHAR(255)
);

CREATE TABLE teachers
(
    name         VARCHAR(255),
    studyProgram VARCHAR(255)
);
INSERT INTO teachers (name, studyProgram)
VALUES ('Kittie', 'Front End'),
       ('Charleen', 'Back End'),
       ('Loida', 'Back End'),
       ('Cordelia', 'Cybersecurity'),
       ('Lera', 'Cybersecurity'),
       ('Jacquelynn', 'Cybersecurity'),
       ('Elli', 'Back End'),
       ('Tinisha', 'Front End'),
       ('Rachel', 'E-Business'),
       ('Kevin', 'Front End');


INSERT INTO studyPrograms (programName, programResponsible)
VALUES ('E-Business', 'Kjartan'),
       ('Front End', 'Lauritzen'),
       ('Back End', 'Viggo'),
       ('Cybersecurity', 'Onkel P');

INSERT INTO guests (guestName, invitedBy)
VALUES ('Bob', 'Margrete'),
       ('Barbra', 'Margrete'),
       ('Ronald', 'Olive'),
       ('Kinoko', 'Ayako'),
       ('Hulio', 'Remedios');
INSERT INTO students (name, program)
VALUES ('Margarete', 'Front End'),
       ('Charles', 'Cybersecurity'),
       ('Vanesa', 'E-Business'),
       ('Dulcie', 'Back End'),
       ('Corey', 'Back End'),
       ('Tiana', 'Front End'),
       ('Johnnie', 'Cybersecurity'),
       ('Clotilde', 'Back End'),
       ('Wendie', 'E-Business'),
       ('Ayako', 'E-Business'),
       ('Blossom', 'E-Business'),
       ('Elaine', 'Cybersecurity'),
       ('Kayleigh', 'Cybersecurity'),
       ('Remedios', 'Back End'),
       ('Demetria', 'Back End'),
       ('Alma', 'Cybersecurity'),
       ('Vicki', 'Cybersecurity'),
       ('Katharine', 'Front End'),
       ('Jaimie', 'Front End'),
       ('Olive', 'Front End'),
       ('Marchelle', 'Front End'),
       ('Noel', 'Front End'),
       ('Ivy', 'Front End'),
       ('Cuc', 'Cybersecurity'),
       ('Melony', 'E-Business'),
       ('Alease', 'E-Business'),
       ('Leia', 'Back End'),
       ('Pam', 'Back End'),
       ('Kiersten', 'E-Business'),
       ('Latashia', 'Cybersecurity'),
       ('Ashlee', 'Cybersecurity'),
       ('Esperanza', 'E-Business'),
       ('Ardella', 'E-Business'),
       ('Camila', 'Back End'),
       ('Earnestine', 'Cybersecurity'),
       ('Fiona', 'Front End'),
       ('Niesha', 'Back End'),
       ('Lucilla', 'Front End'),
       ('Blythe', 'E-Business'),
       ('Albert', 'Front End'),
       ('Gerri', 'E-Business'),
       ('Queenie', 'E-Business'),
       ('Kaitlin', 'Cybersecurity'),
       ('Suzanne', 'Back End'),
       ('Genevive', 'Front End'),
       ('Devona', 'Back End'),
       ('Augustine', 'Back End'),
       ('Chastity', 'Front End'),
       ('Gale', 'Cybersecurity'),
       ('Norman', 'Front End');


SELECT *
FROM Students;
SELECT *
FROM teachers;
SELECT *
FROM guests;
select *
FROM studyPrograms;
show tables;