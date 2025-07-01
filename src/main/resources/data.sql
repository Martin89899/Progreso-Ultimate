DROP TABLE IF EXISTS curso;

CREATE TABLE curso (
    idEstudiante INTEGER AUTO_INCREMENT PRIMARY KEY,
    rutEstudiante VARCHAR(255) NOT NULL,
    nombreEstudiante VARCHAR(255) NOT NULL,
    asistencia INT NOT NULL,
    notas INT NOT NULL
);

INSERT INTO curso (rutEstudiante, nombreEstudiante, asistencia, notas) VALUES
('12345678-9', 'Carlos Ramírez', 90, 60),
('98765432-1', 'Ana Torres', 95, 50),
('11223344-5', 'Luis Martínez', 88, 55),
('55667788-0', 'Patricia Herrera', 93, 45),
('22334455-6', 'Diego Silva', 85, 80),
('33445566-7', 'Valeria Soto', 97, 70),
('44556677-8', 'Javier Rojas', 91, 65);
