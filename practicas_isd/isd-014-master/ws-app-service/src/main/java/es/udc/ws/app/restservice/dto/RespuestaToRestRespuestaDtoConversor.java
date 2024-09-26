package es.udc.ws.app.restservice.dto;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.model.respuesta.Respuesta;

public class RespuestaToRestRespuestaDtoConversor {
    public static List<RestRespuestaDto> toRestRespuestaDtos(List<Respuesta> respuestas) {
        List<RestRespuestaDto> respuestaDtos = new ArrayList<>();
        for (int i = 0; i < respuestas.size(); i++) {
            respuestaDtos.add(toRestRespuestaDto(respuestas.get(i)));
        }
        return respuestaDtos;
    }

    public static List<Respuesta> toRespuestas(List<RestRespuestaDto> restRespuestas) {
        List<Respuesta> respuestas = new ArrayList<>();
        for (int i = 0; i < restRespuestas.size(); i++) {
            respuestas.add(toRespuesta(restRespuestas.get(i)));
        }
        return respuestas;
    }

    public static RestRespuestaDto toRestRespuestaDto(Respuesta respuesta) {
        return new RestRespuestaDto(respuesta.getIdentificadorRespuesta(), respuesta.getEmailEmpleado(),
                respuesta.isAsistencia(), respuesta.getIdEventoAlQueResponde());
    }

    public static Respuesta toRespuesta(RestRespuestaDto restRespuesta) {
        return new Respuesta(restRespuesta.getIdentificadorRespuesta(), restRespuesta.getEmailEmpleado(),
                restRespuesta.getIdEventoAlQueResponde(), restRespuesta.isAsistencia());
    }

}
