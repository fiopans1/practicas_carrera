package es.udc.ws.app.client.service.thrift;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.client.service.dto.ClientEventoDto;
import es.udc.ws.app.thrift.ThriftEventoDto;
import static java.time.temporal.ChronoUnit.HOURS;

import java.time.LocalDateTime;

public class ClientEventoDtoToThriftEventoDtoConversor {
    public static ThriftEventoDto toThriftEventoDto(ClientEventoDto clientEventoDto) {
        Long eventoId = clientEventoDto.getIdentificadorEvento();

        return new ThriftEventoDto(
                (eventoId == null ? -1 : eventoId.longValue()), clientEventoDto.getNombreEvento(),
                clientEventoDto.getDescripcionEvento(), clientEventoDto.getFechaHoraCelebracionEvento().toString(),
                clientEventoDto.getAsistentesEvento(), clientEventoDto.getTotalRespuestas(),
                (int) (HOURS.between(clientEventoDto.getFechaHoraCelebracionEvento(),
                        clientEventoDto.getFechaHoraFinEvento())),
                clientEventoDto.isCancelledEvento());

    }

    public static List<ClientEventoDto> toClientEventoDtos(List<ThriftEventoDto> eventos) {
        List<ClientEventoDto> clientEventoDtos = new ArrayList<>();
        for (ThriftEventoDto evento : eventos) {
            clientEventoDtos.add(toClientEventoDto(evento));
        }
        return clientEventoDtos;
    }

    public static ClientEventoDto toClientEventoDto(ThriftEventoDto evento) {
        return new ClientEventoDto(evento.getIdentificadorEvento(), evento.getNombreEvento(),
                evento.getDescripcionEvento(), LocalDateTime.parse(evento.getFechaHoraCelebracionEvento()),
                LocalDateTime.parse(evento.getFechaHoraCelebracionEvento()).plusHours(evento.getDuracionEvento()),
                evento.getAsistentesEvento(), evento.getTotalRespuestas(), evento.isCancelledEvento());
    }
}
