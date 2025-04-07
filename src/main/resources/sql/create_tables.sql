CREATE DATABASE medical;
\c medical;

CREATE SCHEMA app;
SET search_path TO app;


CREATE TABLE person
(
    person_id       SERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    lastname        VARCHAR(100) NOT NULL,
    gender          VARCHAR(10),
    birthday        DATE,
    document_type   VARCHAR(20)  NOT NULL,
    document_number VARCHAR(50)  NOT NULL,
    email           VARCHAR(100),
    phone           VARCHAR(20),

    CONSTRAINT unique_document UNIQUE (document_type, document_number),
    CONSTRAINT unique_email UNIQUE (email)
);

CREATE TABLE "user"
(
    user_id   SERIAL PRIMARY KEY,
    username  VARCHAR(50)  NOT NULL UNIQUE,
    password  VARCHAR(100) NOT NULL,
    id_person INTEGER      NOT NULL REFERENCES person (person_id),
    role      VARCHAR(50)  NOT NULL DEFAULT 'ROLE_USER'
);

CREATE TABLE doctor
(
    doctor_id      SERIAL PRIMARY KEY,
    person_id      INTEGER     NOT NULL REFERENCES person (person_id),
    license_number VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE specialty
(
    specialty_id SERIAL PRIMARY KEY,
    name         VARCHAR(100) NOT NULL UNIQUE,
    description  TEXT,
    active       BOOLEAN DEFAULT TRUE
);

CREATE TABLE doctor_specialty
(
    doctor_id    INTEGER NOT NULL REFERENCES doctor (doctor_id),
    specialty_id INTEGER NOT NULL REFERENCES specialty (specialty_id),
    PRIMARY KEY (doctor_id, specialty_id)
);

CREATE TABLE appointment
(
    appointment_id SERIAL PRIMARY KEY,
    person_id      INTEGER   NOT NULL REFERENCES person (person_id),
    doctor_id      INTEGER   NOT NULL REFERENCES doctor (doctor_id),
    specialty_id   INTEGER   NOT NULL REFERENCES specialty (specialty_id),
    start_time     TIMESTAMP NOT NULL
);



INSERT INTO person (name, lastname, gender, birthday, document_type, document_number, email, phone)
VALUES ('María', 'Gómez', 'Female', '1990-03-15', 'CC', '1002345678', 'maria.gomez@mail.com', '320-456-7890'),
       ('Carlos', 'Ramírez', 'Male', '1985-07-22', 'TI', '900123456', 'carlos.ramirez@mail.com', '310-987-6543'),
       ('Ana', 'López', 'Female', '1993-12-10', 'CC', '1015678902', 'ana.lopez@mail.com', '300-112-3344'),
       ('Pedro', 'Martínez', 'Male', '1978-09-30', 'CC', '987654321', 'pedro.martinez@mail.com', '315-223-4455'),
       ('Lucía', 'Fernández', 'Female', '2000-05-05', 'CC', '1023456789', 'lucia.fernandez@mail.com', '322-556-7788'),
       ('Javier', 'Sánchez', 'Male', '1995-08-18', 'TI', '901234567', 'javier.sanchez@mail.com', '312-667-8899'),
       ('Elena', 'Díaz', 'Female', '1982-11-25', 'CC', '876543210', 'elena.diaz@mail.com', '317-778-9900'),
       ('Fernando', 'Ruiz', 'Male', '1998-02-14', 'CC', '1034567890', 'fernando.ruiz@mail.com', '319-889-0011'),
       ('Patricia', 'Torres', 'Female', '1975-06-29', 'CE', '3311234567', 'patricia.torres@mail.com', '318-990-1122'),
       ('Miguel', 'Hernández', 'Male', '1989-04-08', 'CC', '765432109', 'miguel.hernandez@mail.com', '311-223-3344'),
       ('Sofía', 'Morales', 'Female', '1997-10-12', 'CC', '1045678901', 'sofia.morales@mail.com', '321-334-4455'),
       ('Raúl', 'Castro', 'Male', '1980-01-20', 'CE', '112345678', 'raul.castro@mail.com', '320-445-5566'),
       ('Isabel', 'Ortega', 'Female', '1994-07-07', 'CC', '1056789012', 'isabel.ortega@mail.com', '322-556-6677'),
       ('Daniel', 'Vargas', 'Male', '1987-09-15', 'TI', '902345678', 'daniel.vargas@mail.com', '310-667-7788'),
       ('Camila', 'Gutiérrez', 'Female', '2001-12-03', 'CC', '1067890123', 'camila.gutierrez@mail.com', '312-778-8899'),
       ('Alejandro', 'Mendoza', 'Male', '1979-03-27', 'CC', '654321098', 'alejandro.mendoza@mail.com', '315-889-9900'),
       ('Valeria', 'Rojas', 'Female', '1992-06-14', 'CE', '1133456789', 'valeria.rojas@mail.com', '317-990-0011'),
       ('Hugo', 'Salazar', 'Male', '1983-11-09', 'CC', '543210987', 'hugo.salazar@mail.com', '319-112-2233'),
       ('Andrea', 'Pérez', 'Female', '1996-04-23', 'CC', '1078901234', 'andrea.perez@mail.com', '311-223-3399'),
       ('Ricardo', 'Córdoba', 'Male', '1984-02-05', 'TI', '903456789', 'ricardo.cordoba@mail.com', '321-334-4488');

INSERT INTO "user" (username, password, id_person)
VALUES ('1002345678', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 1),
       ('900123456', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 2),
       ('1015678902', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 3),
       ('987654321', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 4),
       ('1023456789', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 5),
       ('901234567', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 6),
       ('876543210', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 7),
       ('1034567890', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 8),
       ('3311234567', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 9),
       ('765432109', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 10),
       ('1045678901', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 11),
       ('112345678', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 12),
       ('1056789012', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 13),
       ('902345678', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 14),
       ('1067890123', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 15),
       ('654321098', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 16),
       ('1133456789', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 17),
       ('543210987', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 18),
       ('1078901234', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 19),
       ('903456789', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 20);




INSERT INTO doctor (person_id, license_number)
VALUES (1, 'DOC-001'),
       (2, 'DOC-002'),
       (3, 'DOC-003'),
       (4, 'DOC-004'),
       (5, 'DOC-005'),
       (6, 'DOC-006');


INSERT INTO specialty (name, description)
VALUES ('Cardiología', 'Especialidad en enfermedades del corazón'),
       ('Pediatría', 'Atención médica para niños'),
       ('Dermatología', 'Especialidad médica que trata las enfermedades de la piel'),
       ('Neurología', 'Rama de la medicina que estudia el sistema nervioso y sus trastornos'),
       ('Ginecología', 'Especialidad médica enfocada en el sistema reproductor femenino'),
       ('Oftalmología', 'Rama de la medicina que trata las enfermedades de los ojos'),
       ('Psiquiatría', 'Especialidad dedicada al estudio y tratamiento de los trastornos mentales'),
       ('Oncología', 'Especialidad médica que estudia y trata el cáncer');


INSERT INTO doctor_specialty (doctor_id, specialty_id)
VALUES
(1, 1),
(2, 1),

(3, 2),
(4, 2),

(5, 3),
(6, 3),

(2, 4),
(3, 4),

(4, 5),
(5, 5),

(5, 6),
(6, 6),

(1, 7),
(2, 7),

(3, 8),
(1, 8);