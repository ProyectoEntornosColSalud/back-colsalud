INSERT INTO person (name, lastname, gender, birthday, document_type, document_number, email, phone)
VALUES
('Jon', 'Snow', 'Male', '1985-05-20', 'CC', '12345678', 'jonsnow@mail.com', '555-1234');

INSERT INTO person (name, lastname, gender, birthday, document_type, document_number, email, phone)
VALUES
('John', 'Cena', 'Male', '1978-10-12', 'CC', '87654321', 'johncena@mail.com', '555-5678');

INSERT INTO "user" (username, password, id_person)
VALUES
('12345678', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 1), -- Hola123
('87654321', '$2a$12$ZahQSG4JPZzpeNapI3/61OuOHq2BoLYhWGC9quB6R0sQW4e67lxpu', 2);

INSERT INTO doctor (person_id, license_number)
VALUES (1, 'DOC-001');


INSERT INTO specialty (name, description, active)
VALUES
('Cardiología', 'Especialidad en enfermedades del corazón', TRUE),
('Pediatría', 'Atención médica para niños', TRUE);


INSERT INTO doctor_specialty (doctor_id, specialty_id)
VALUES
(1, 1),
(1, 2);


INSERT INTO appointment (person_id, doctor_id, specialty_id, start_time, end_time)
VALUES
(2, 1, 1, '2025-03-20 10:00:00', '2025-03-20 10:30:00');
