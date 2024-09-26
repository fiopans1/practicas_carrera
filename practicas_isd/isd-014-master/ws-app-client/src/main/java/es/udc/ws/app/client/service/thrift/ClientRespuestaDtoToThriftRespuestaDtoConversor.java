package es.udc.ws.app.client.service.thrift;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.client.service.dto.ClientRespuestaDto;
import es.udc.ws.app.thrift.ThriftRespuestaDto;

public class ClientRespuestaDtoToThriftRespuestaDtoConversor {

    public static ThriftRespuestaDto toThriftRespuestaDto(ClientRespuestaDto clientRespuestaDto) {

        Long respuestaId = clientRespuestaDto.getIdentificadorRespuesta();

        return new ThriftRespuestaDto(
                (respuestaId == null ? -1 : respuestaId.longValue()), clientRespuestaDto.getEmailEmpleado(),
                clientRespuestaDto.isAsistencia(), clientRespuestaDto.getIdEventoAlQueResponde());

    }
    
    public static List<ClientRespuestaDto> toClientRespuestaDtos(List<ThriftRespuestaDto> respuestas) {
        List<ClientRespuestaDto> clientRespuestaDtos = new ArrayList<>();
        for (ThriftRespuestaDto respuesta : respuestas) {
            clientRespuestaDtos.add(toClientRespuestaDto(respuesta));
        }
        return clientRespuestaDtos;
    }

    public static ClientRespuestaDto toClientRespuestaDto(ThriftRespuestaDto respuesta) {
        return new ClientRespuestaDto(respuesta.getIdentificadorRespuesta(), respuesta.getEmailEmpleado(),
                respuesta.isAsistencia(), respuesta.getIdEventoAlQueResponde());
    }
}
