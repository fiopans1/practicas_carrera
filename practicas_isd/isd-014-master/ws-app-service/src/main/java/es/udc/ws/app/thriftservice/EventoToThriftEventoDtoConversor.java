package es.udc.ws.app.thriftservice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.model.evento.Evento;
import es.udc.ws.app.thrift.ThriftEventoDto;

public class EventoToThriftEventoDtoConversor {

    public static Evento toEvento(ThriftEventoDto evento) {
        return new Evento(evento.getIdentificadorEvento(), evento.getNombreEvento(),
                evento.getDescripcionEvento(),
                LocalDateTime.parse(evento.getFechaHoraCelebracionEvento()),
                evento.getDuracionEvento(),
                evento.getAsistentesEvento(),
                (evento.getTotalRespuestas() - evento.getAsistentesEvento()),
                evento.isCancelledEvento());

    }

    public static List<ThriftEventoDto> toThriftEventoDtos(List<Evento> eventos) {
        List<ThriftEventoDto> dtos = new ArrayList<>();
        for (Evento evento : eventos) {
            dtos.add(toThriftEventoDto(evento));
        }
        return dtos;
    }

    public static ThriftEventoDto toThriftEventoDto(Evento evento) {
        return new ThriftEventoDto(evento.getIdentificadorEvento(),
                evento.getNombreEvento(),
                evento.getDescripcionEvento(), evento.getFechaHoraCelebracionEvento().toString(),
                evento.getAsistentesEvento(),
                (evento.getAsistentesEvento() + evento.getNoAsistentesEvento()), evento.getDuracionEvento(),
                evento.isCancelledEvento());
    }

}