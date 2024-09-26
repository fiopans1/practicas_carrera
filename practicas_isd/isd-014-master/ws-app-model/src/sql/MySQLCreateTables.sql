-- ----------------------------------------------------------------------------
-- Model
-- -----------------------------------------------------------------------------

DROP TABLE Respuesta;
DROP TABLE Evento;


-- --------------------------------- Evento ------------------------------------

CREATE TABLE Evento (    eventoID BIGINT NOT NULL AUTO_INCREMENT,
                         nombreEvento VARCHAR(255) COLLATE latin1_bin NOT NULL,
                         asistentesEvento SMALLINT NOT NULL,
                         noAsistentesEvento SMALLINT NOT NULL,
                         descripcionEvento VARCHAR(1024) COLLATE latin1_bin NOT NULL,
                         duracionEvento FLOAT NOT NULL,
                         cancelledEvento BOOLEAN NOT NULL,
                         fechaHoraCelebracionEvento DATETIME NOT NULL,
                         fechaHoraAltaEvento DATETIME NOT NULL,
                         CONSTRAINT EventoPK PRIMARY KEY(eventoID),
                         CONSTRAINT validDuracionEvento CHECK ( duracionEvento > 0 )) ENGINE = InnoDB;

-- --------------------------------- Respuesta ------------------------------------

CREATE TABLE Respuesta (    respuestaID BIGINT NOT NULL AUTO_INCREMENT,
                            idEventoAlQueResponde BIGINT NOT NULL,
                            emailEmpleado VARCHAR(40) COLLATE latin1_bin NOT NULL,
                            fechaHoraRespuesta DATETIME NOT NULL,
                            asistencia BOOLEAN NOT NULL,
                            CONSTRAINT RespuestaPK PRIMARY KEY(respuestaID),
                            CONSTRAINT EventoAlQueResponde FOREIGN KEY(idEventoAlQueResponde)
                            REFERENCES Evento(eventoID) ON DELETE CASCADE ) ENGINE = InnoDB;




