package es.udc.ws.app.thriftservice;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.model.evento.Evento;
import es.udc.ws.app.model.eventoservicio.*;
import es.udc.ws.app.model.eventoservicio.exceptions.AlreadyCancelledEventException;
import es.udc.ws.app.model.eventoservicio.exceptions.OutOfTimeException;
import es.udc.ws.app.model.eventoservicio.exceptions.AlreadyRespondedException;
import es.udc.ws.app.model.respuesta.Respuesta;
import es.udc.ws.app.thrift.*;

import java.time.LocalDate;
import java.util.List;

import org.apache.thrift.TException;

public class ThriftEventoServiceImpl implements ThriftEventoService.Iface {

    @Override
    public ThriftEventoDto addEvento(ThriftEventoDto eventoDto) throws ThriftInputValidationException, TException {
        Evento evento = EventoToThriftEventoDtoConversor.toEvento(eventoDto);
        try {
            Evento addedvento = EventoServicioFactory.getService().addEvento(evento);
            return EventoToThriftEventoDtoConversor.toThriftEventoDto(addedvento);

        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }
    }

    @Override
    public List<ThriftEventoDto> findEventos(String fechaFinEvento, String keywords) throws TException {
        // TODO Auto-generated method stub
        List<Evento> eventos = EventoServicioFactory.getService().findEventos(LocalDate.now(),
                LocalDate.parse(fechaFinEvento), (keywords != "") ? keywords : null);
        return EventoToThriftEventoDtoConversor.toThriftEventoDtos(eventos);
    }

    @Override
    public ThriftEventoDto findEvento(long eventoId)
            throws ThriftInstanceNotFoundException, TException {
        try {
            Evento evento = EventoServicioFactory.getService().findEvento(eventoId);
            return EventoToThriftEventoDtoConversor.toThriftEventoDto(evento);

        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(),
                    e.getInstanceType().substring(e.getInstanceType().lastIndexOf('.') + 1));
        }
    }

    @Override
    public void cancelEvento(long eventoId) throws ThriftInstanceNotFoundException, ThriftEventoAlreadyCancelled, ThriftOutOfTimeException, TException {
        try {
            EventoServicioFactory.getService().cancelEvento(eventoId);
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(),
                    e.getInstanceType().substring(e.getInstanceType().lastIndexOf('.') + 1));
        } catch (AlreadyCancelledEventException e) {
            throw new ThriftEventoAlreadyCancelled(e.getEventoID());
        } catch (OutOfTimeException e) {
            throw new ThriftOutOfTimeException(e.getIdEvento(), e.getFechaHoraCelebracionEvento().toString());
        }
    }

    @Override
    public ThriftRespuestaDto responderEvento(String emailEmpleado, long eventoId, boolean asistencia)
            throws ThriftInstanceNotFoundException, ThriftInputValidationException, ThriftEventoAlreadyCancelled,
            ThriftOutOfTimeException, ThriftAlreadyRespondedException, TException {

        try {
            Respuesta respuesta = EventoServicioFactory.getService().responderEvento(emailEmpleado, eventoId, asistencia);
            return RespuestaToThriftRespuestaDtoConversor.toThriftRespuestaDto(respuesta);
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(),
            e.getInstanceType().substring(e.getInstanceType().lastIndexOf('.') + 1));
        } catch (AlreadyCancelledEventException e) {
            throw new ThriftEventoAlreadyCancelled(e.getEventoID());
        } catch (AlreadyRespondedException e) {
            throw new ThriftAlreadyRespondedException(e.getIdEvento(), e.getUserEmail());
        } catch (OutOfTimeException e) {
            throw new ThriftOutOfTimeException(e.getIdEvento(), e.getFechaHoraCelebracionEvento().toString());
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }
    }

    @Override
    public List<ThriftRespuestaDto> obtenerRespuestas(String emailEmpleado, boolean asistencia)
            throws ThriftInputValidationException, TException {

        try {

            List<Respuesta> respuestas = EventoServicioFactory.getService().obtenerRespuestas(emailEmpleado, asistencia);
            return RespuestaToThriftRespuestaDtoConversor.toThriftRespuestaDtos(respuestas);
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }
    }

}
