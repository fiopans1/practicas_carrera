package es.udc.ws.app.restservice.dto;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.model.evento.Evento;

public class EventoToRestEventoDtoConversor {
    public static List<RestEventoDto> toRestEventosDtos(List<Evento> eventos) {
        List<RestEventoDto> eventoDtos = new ArrayList<>();
        for (int i = 0; i < eventos.size(); i++) {
            eventoDtos.add(toRestEventoDto(eventos.get(i)));
        }
        return eventoDtos;
    }

    public static List<Evento> toEventos(List<RestEventoDto> restEventos) {
        List<Evento> eventos = new ArrayList<>();
        for (int i = 0; i < restEventos.size(); i++) {
            eventos.add(toEvento(restEventos.get(i)));
        }
        return eventos;
    }

    public static RestEventoDto toRestEventoDto(Evento evento) {
        return new RestEventoDto(evento.getNombreEvento(), evento.getDescripcionEvento(),
                evento.getFechaHoraCelebracionEvento(), evento.getIdentificadorEvento(),
                evento.getAsistentesEvento(), (evento.getAsistentesEvento() + evento.getNoAsistentesEvento()),
                evento.getDuracionEvento(), evento.isCancelledEvento());
    }

    public static Evento toEvento(RestEventoDto restEvento) {
        return new Evento(restEvento.getIdentificadorEvento(), restEvento.getNombreEvento(),
                restEvento.getDescripcionEvento(),
                restEvento.getFechaHoraCelebracionEvento(), restEvento.getDuracionEvento(),
                restEvento.getAsistentesEvento(),
                (restEvento.getTotalRespuestas() - restEvento.getAsistentesEvento()), restEvento.isCancelledEvento());

    }
}
