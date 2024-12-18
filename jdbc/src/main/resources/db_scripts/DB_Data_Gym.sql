INSERT INTO PERSONA (ID, DNI, nombre, telefono)
Values(1,'A11111111','Miguel','+34 677 555 444');

INSERT INTO PERSONA (ID, DNI, nombre, telefono)
Values(2,'B11111111','Angel','+34 677 222 999');

INSERT INTO PERSONA (ID, DNI, nombre, telefono)
VALUES (3, 'B22222222', 'Maria', '+34 688 333 888');

INSERT INTO PERSONA (ID, DNI, nombre, telefono)
VALUES (4, 'B33333333', 'Javier', '+34 699 444 777');

INSERT INTO PERSONA (ID, DNI, nombre, telefono)
VALUES (5, 'B44444444', 'Lucia', '+34 677 555 666');

INSERT INTO PERSONA (ID, DNI, nombre, telefono)
VALUES (6, 'B55555555', 'Carlos', '+34 688 666 555');

INSERT INTO PERSONA (ID, DNI, nombre, telefono)
VALUES (7, 'B66666666', 'Sofia', '+34 677 777 444');

INSERT INTO PERSONA (ID, DNI, nombre, telefono)
VALUES (8, 'B77777777', 'Luis', '+34 688 888 333');

INSERT INTO PERSONA (ID, DNI, nombre, telefono)
VALUES (9, 'B88888888', 'Elena', '+34 699 999 222');

INSERT INTO PERSONA (ID, DNI, nombre, telefono)
VALUES (10, 'B99999999', 'David', '+34 677 101 010');

INSERT INTO PERSONA (ID, DNI, nombre, telefono)
VALUES (11, 'C11111111', 'Clara', '+34 688 202 020');

INSERT INTO PERSONA (ID, DNI, nombre, telefono)
VALUES (12, 'C22222222', 'Pablo', '+34 699 303 030');

INSERT INTO persona (ID, DNI, nombre, telefono) 
VALUES (13, 'C33333333', 'Rocio', '+34 677 404 040');

INSERT INTO persona (ID, DNI, nombre, telefono) 
VALUES (14, 'C44444444', 'Manuel', '+34 688 505 050');

INSERT INTO persona (ID, DNI, nombre, telefono) 
VALUES (15, 'C55555555', 'Alba', '+34 699 606 060');

INSERT INTO persona (ID, DNI, nombre, telefono) 
VALUES (16, 'C66666666', 'Diego', '+34 677 707 070');

INSERT INTO persona (ID, DNI, nombre, telefono) 
VALUES (17, 'C77777777', 'Paula', '+34 688 808 080');

INSERT INTO persona (ID, DNI, nombre, telefono) 
VALUES (18, 'C88888888', 'Victor', '+34 699 909 090');

INSERT INTO persona (ID, DNI, nombre, telefono) 
VALUES (19, 'C99999999', 'Sara', '+34 677 010 010');

INSERT INTO persona (ID, DNI, nombre, telefono) 
VALUES (20, 'D11111111', 'Adrian', '+34 688 111 111');

INSERT INTO persona (ID, DNI, nombre, telefono) 
VALUES (21, 'D22222222', 'Lorenzo', '+34 688 222 118');

INSERT INTO persona (ID, DNI, nombre, telefono) 
VALUES (22, 'D33333333', 'Anuel', '+34 689 111 556');


INSERT INTO gimnasio (id, nombre, aforo, ubicacion) VALUES
(1, 'Fitness First', 50, 'Madrid'),
(2, 'Gold Gym', 70, 'Barcelona'),
(3, 'CrossFit Elite', 30, 'Valencia');

INSERT INTO socio (edad, email, fecha_inscripcion, cuota_mensual, id_persona, id_gimnasio) VALUES
(30, 'miguel.gym@example.com', '2023-01-10', 38.50, 1, 1),
(27, 'angel.gym@example.com', '2023-02-15', 40.25, 2, 1),
(27, 'maria.gym@example.com', '2023-03-20', 43.00, 3, 2),
(32, 'javier.gym@example.com', '2023-04-25', 37.25, 4, 2),
(28, 'lucia.gym@example.com', '2023-05-15', 44.50, 5, 3),
(31, 'carlos.gym@example.com', '2023-06-10', 41.75, 6, 3),
(29, 'sofia.gym@example.com', '2023-07-20', 39.25, 7, 1),
(26, 'luis.gym@example.com', '2023-08-05', 35.50, 8, 2),
(24, 'elena.gym@example.com', '2023-09-10', 38.75, 9, 3),
(33, 'david.gym@example.com', '2023-10-15', 42.00, 10, 3),
(22, 'clara.gym@example.com', '2023-11-01', 35.00, 11, 1),
(28, 'pablo.gym@example.com', '2023-12-20', 43.75, 12, 1),
(27, 'rocio.gym@example.com', '2023-01-05', 39.50, 13, 1),
(35, 'manuel.gym@example.com', '2023-02-25', 40.00, 14, 2),
(23, 'alba.gym@example.com', '2023-03-15', 42.75, 15, 2),
(31, 'diego.gym@example.com', '2023-04-20', 37.50, 16, 3),
(29, 'paula.gym@example.com', '2023-05-30', 35.25, 17, 3),
(34, 'victor.gym@example.com', '2023-06-12', 41.50, 18, 3),
(26, 'sara.gym@example.com', '2023-07-01', 36.75, 19, 2),
(25, 'adrian.gym@example.com', '2023-08-15', 39.00, 20, 1);

INSERT INTO entrenador (id, especialidad, id_persona) VALUES
(1, 'CrossFit', 21),
(2, 'Yoga', 22);