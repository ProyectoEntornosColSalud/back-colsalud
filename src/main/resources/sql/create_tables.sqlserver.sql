-- Tabla person
CREATE TABLE app.person (
    person_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    lastname NVARCHAR(100) NOT NULL,
    gender NVARCHAR(10),
    birthday DATE,
    document_type NVARCHAR(20) NOT NULL,
    document_number NVARCHAR(50) NOT NULL,
    email NVARCHAR(100),
    phone NVARCHAR(20),
    CONSTRAINT unique_document UNIQUE (document_type, document_number),
    CONSTRAINT unique_email UNIQUE (email)
);
GO

-- Tabla user (se evita usar "user" como nombre por ser palabra reservada)
CREATE TABLE app.users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(100) NOT NULL,
    id_person INT NOT NULL,
    role NVARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    FOREIGN KEY (id_person) REFERENCES app.person(person_id)
);
GO

-- Tabla doctor
CREATE TABLE app.doctor (
    doctor_id INT IDENTITY(1,1) PRIMARY KEY,
    person_id INT NOT NULL,
    license_number NVARCHAR(50) NOT NULL UNIQUE,
    FOREIGN KEY (person_id) REFERENCES app.person(person_id)
);
GO

-- Tabla specialty
CREATE TABLE app.specialty (
    specialty_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE,
    description NVARCHAR(MAX),
    active BIT DEFAULT 1
);
GO

-- Tabla doctor_specialty
CREATE TABLE app.doctor_specialty (
    doctor_id INT NOT NULL,
    specialty_id INT NOT NULL,
    PRIMARY KEY (doctor_id, specialty_id),
    FOREIGN KEY (doctor_id) REFERENCES app.doctor(doctor_id),
    FOREIGN KEY (specialty_id) REFERENCES app.specialty(specialty_id)
);
GO

-- Tabla appointment
CREATE TABLE app.appointment (
    appointment_id INT IDENTITY(1,1) PRIMARY KEY,
    person_id INT NOT NULL,
    doctor_id INT NOT NULL,
    specialty_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    FOREIGN KEY (person_id) REFERENCES app.person(person_id),
    FOREIGN KEY (doctor_id) REFERENCES app.doctor(doctor_id),
    FOREIGN KEY (specialty_id) REFERENCES app.specialty(specialty_id)
);
GO
alter table app.appointment
    add status varchar(50) default 'PENDIENTE' not null
go


INSERT INTO app.person (name, lastname, gender, birthday, document_type, document_number, email, phone)
VALUES ( N'María', N'Gómez', 'Female', '1990-03-15', 'CC', '1002345678', 'maria.gomez@mail.com', '320-456-7890'),
       ('Carlos', N'Ramírez', 'Male', '1985-07-22', 'TI', '900123456', 'carlos.ramirez@mail.com', '310-987-6543'),
       ('Ana', N'López', 'Female', '1993-12-10', 'CC', '1015678902', 'ana.lopez@mail.com', '300-112-3344'),
       ('Pedro', N'Martínez', 'Male', '1978-09-30', 'CC', '987654321', 'pedro.martinez@mail.com', '315-223-4455'),
       (N'Lucía', N'Fernández', 'Female', '2000-05-05', 'CC', '1023456789', 'lucia.fernandez@mail.com', '322-556-7788'),
       ('Javier', N'Sánchez', 'Male', '1995-08-18', 'TI', '901234567', 'javier.sanchez@mail.com', '312-667-8899'),
       ('Elena', N'Díaz', 'Female', '1982-11-25', 'CC', '876543210', 'elena.diaz@mail.com', '317-778-9900'),
       ('Fernando', 'Ruiz', 'Male', '1998-02-14', 'CC', '1034567890', 'fernando.ruiz@mail.com', '319-889-0011'),
       ('Patricia', 'Torres', 'Female', '1975-06-29', 'CE', '3311234567', 'patricia.torres@mail.com', '318-990-1122'),
       ('Miguel', N'Hernández', 'Male', '1989-04-08', 'CC', '765432109', 'miguel.hernandez@mail.com', '311-223-3344'),
       (N'Sofía', 'Morales', 'Female', '1997-10-12', 'CC', '1045678901', 'sofia.morales@mail.com', '321-334-4455'),
       (N'Raúl', 'Castro', 'Male', '1980-01-20', 'CE', '112345678', 'raul.castro@mail.com', '320-445-5566'),
       ('Isabel', 'Ortega', 'Female', '1994-07-07', 'CC', '1056789012', 'isabel.ortega@mail.com', '322-556-6677'),
       ('Daniel', 'Vargas', 'Male', '1987-09-15', 'TI', '902345678', 'daniel.vargas@mail.com', '310-667-7788'),
       ('Camila', N'Gutiérrez', 'Female', '2001-12-03', 'CC', '1067890123', 'camila.gutierrez@mail.com', '312-778-8899'),
       ('Alejandro', 'Mendoza', 'Male', '1979-03-27', 'CC', '654321098', 'alejandro.mendoza@mail.com', '315-889-9900'),
       ('Valeria', 'Rojas', 'Female', '1992-06-14', 'CE', '1133456789', 'valeria.rojas@mail.com', '317-990-0011'),
       ('Hugo', 'Salazar', 'Male', '1983-11-09', 'CC', '543210987', 'hugo.salazar@mail.com', '319-112-2233'),
       ('Andrea', N'Pérez', 'Female', '1996-04-23', 'CC', '1078901234', 'andrea.perez@mail.com', '311-223-3399'),
       ('Ricardo', N'Córdoba', 'Male', '1984-02-05', 'TI', '903456789', 'ricardo.cordoba@mail.com', '321-334-4488');
GO

INSERT INTO app.users (username, password, id_person)
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
GO



INSERT INTO app.doctor (person_id, license_number)
VALUES (1, 'DOC-001'),
       (2, 'DOC-002'),
       (3, 'DOC-003'),
       (4, 'DOC-004'),
       (5, 'DOC-005'),
       (6, 'DOC-006');
GO

INSERT INTO app.specialty (name, description)
VALUES (N'Cardiología',N'Especialidad en enfermedades del corazón'),
       (N'Pediatría',N'Atención médica para niños'),
       (N'Dermatología',N'Especialidad médica que trata las enfermedades de la piel'),
       (N'Neurología',N'Rama de la medicina que estudia el sistema nervioso y sus trastornos'),
       (N'Ginecología',N'Especialidad médica enfocada en el sistema reproductor femenino'),
       (N'Oftalmología',N'Rama de la medicina que trata las enfermedades de los ojos'),
       (N'Psiquiatría',N'Especialidad dedicada al estudio y tratamiento de los trastornos mentales'),
       (N'Oncología',N'Especialidad médica que estudia y trata el cáncer');
GO

INSERT INTO app.doctor_specialty (doctor_id, specialty_id)
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
GO

UPDATE app.specialty SET description = N'Enfermedades del corazón'
WHERE specialty_id = 1;
UPDATE app.specialty SET description = 'Salud infantil' WHERE specialty_id = 2;
UPDATE app.specialty SET description = 'Problemas en la piel' WHERE specialty_id = 3;
UPDATE app.specialty SET description = 'Trastornos del sistema nervioso' WHERE specialty_id = 4;
UPDATE app.specialty SET description = 'Salud del aparato reproductor femenino' WHERE specialty_id = 5;
UPDATE app.specialty SET description = 'Salud visual y enfermedades de los ojos' WHERE specialty_id = 6;
UPDATE app.specialty SET description = 'Trastornos mentales y emocionales' WHERE specialty_id = 7;
UPDATE app.specialty SET description = N'Diagnóstico y tratamiento del cáncer'
WHERE specialty_id = 8;

alter table app.person
    alter column gender nvarchar(25) null
go
