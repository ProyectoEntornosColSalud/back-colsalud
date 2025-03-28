-- Insertar personas (paciente y doctor)
INSERT INTO person (name, lastname, gender, birthday, document_type, document_number, email, phone)
VALUES
('Juan', 'Perez', 'Male', '1985-05-20', 'DNI', '12345678', 'juan.perez@mail.com', '555-1234');

INSERT INTO person (name, lastname, gender, birthday, document_type, document_number, email, phone)
VALUES
('Ana', 'Gomez', 'Female', '1978-10-12', 'DNI', '87654321', 'ana.gomez@mail.com', '555-5678');

-- Insertar usuarios
INSERT INTO "user" (username, password, id_person)
VALUES
('juanp', 'hashed_password_juan', 1),
('anag', 'hashed_password_ana', 2);

-- Insertar doctor (Ana es doctora)
INSERT INTO doctor (person_id, license_number)
VALUES (2, 'DOC-001');

-- Insertar especialidades
INSERT INTO specialty (name, description, active)
VALUES
('Cardiología', 'Especialidad en enfermedades del corazón', TRUE),
('Pediatría', 'Atención médica para niños', TRUE);

-- Relacionar doctor con especialidad
INSERT INTO doctor_specialty (doctor_id, specialty_id)
VALUES
(1, 1), -- Ana es cardióloga
(1, 2); -- También pediatra

-- Insertar cita médica (Juan se atiende con Ana)
INSERT INTO appointment (person_id, doctor_id, specialty_id, start_time, end_time)
VALUES
(1, 1, 1, '2025-03-20 10:00:00', '2025-03-20 10:30:00');
