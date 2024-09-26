package es.udc.ws.app.model.eventoservicio;

import es.udc.ws.app.model.evento.Evento;
import es.udc.ws.app.model.eventoservicio.exceptions.*;
import es.udc.ws.app.model.respuesta.Respuesta;
import es.udc.ws.util.exceptions.InputValidationException;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.time.LocalDate;
import java.util.List;

public interface EventoService {
        public Evento addEvento(Evento evento) throws InputValidationException;

        public void cancelEvento(Long eventoID)
                        throws OutOfTimeException, AlreadyCancelledEventException, InstanceNotFoundException;

        public Evento findEvento(Long eventoID) throws InstanceNotFoundException;

        public List<Evento> findEventos(LocalDate fechaInicioEvento, LocalDate fechafinEvento, String keywords);

        public List<Respuesta> obtenerRespuestas(String emailEmpleado, boolean asistencia)
                        throws InputValidationException;

        public Respuesta responderEvento(String emailEmpleado, Long eventoID, boolean asistencia)
                        throws OutOfTimeException,
                        AlreadyCancelledEventException, AlreadyRespondedException, InstanceNotFoundException,
                        InputValidationException;
}
