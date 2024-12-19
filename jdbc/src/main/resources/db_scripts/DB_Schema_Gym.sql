CREATE DATABASE IF NOT EXISTS Gym;

USE Gym;

CREATE TABLE IF NOT EXISTS persona (
  id int(11) NOT NULL AUTO_INCREMENT,
  DNI varchar(9) DEFAULT NULL,
  nombre varchar(50) DEFAULT NULL,
  telefono varchar(50) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY DNI (DNI)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE IF NOT EXISTS gimnasio (
  id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(50) NOT NULL DEFAULT '0',
  aforo int(11) NOT NULL DEFAULT 0,
  ubicacion varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE IF NOT EXISTS socio (
  id int(11) NOT NULL AUTO_INCREMENT,
  edad smallint(6) DEFAULT NULL,
  email varchar(50) DEFAULT NULL,
  fecha_inscripcion varchar(20) DEFAULT NULL,
  cuota_mensual double DEFAULT NULL,
  id_persona int(11) DEFAULT NULL,
  id_gimnasio int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_socio_gimnasio (id_gimnasio),
  KEY FK_socio_persona (id_persona) USING BTREE,
  CONSTRAINT FK_socio_gimnasio FOREIGN KEY (id_gimnasio) REFERENCES gimnasio (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_socio_persona FOREIGN KEY (id_persona) REFERENCES persona (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE IF NOT EXISTS entrenador (
  id int(11) NOT NULL AUTO_INCREMENT,
  especialidad varchar(50) NOT NULL DEFAULT '0',
  id_persona int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  KEY FK_entrenador_persona (id_persona) USING BTREE,
  CONSTRAINT FK_entrenador_persona FOREIGN KEY (id_persona) REFERENCES persona (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE IF NOT EXISTS clase (
  id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(50) DEFAULT NULL,
  horario date DEFAULT NULL,
  aforo int(11) DEFAULT NULL,
  id_entrenador int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_clase_entrenador (id_entrenador),
  CONSTRAINT FK_clase_entrenador FOREIGN KEY (id_entrenador) REFERENCES entrenador (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE IF NOT EXISTS asistencia (
  id int(11) NOT NULL AUTO_INCREMENT,
  id_clase int(11) DEFAULT NULL,
  id_socio int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_asistencia_clase (id_clase),
  KEY FK_asistencia_socio (id_socio),
  CONSTRAINT FK_asistencia_clase FOREIGN KEY (id_clase) REFERENCES clase (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_asistencia_socio FOREIGN KEY (id_socio) REFERENCES socio (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;









