CREATE DATABASE medical_appointments;
\c medical_appointments;

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
    CONSTRAINT unique_email UNIQUE (email),
    CONSTRAINT unique_phone UNIQUE (phone)
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
    start_time     TIMESTAMP NOT NULL,
    end_time       TIMESTAMP NOT NULL
);