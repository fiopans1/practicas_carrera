package es.udc.ws.app.thriftservice;

import java.util.List;
import java.util.ArrayList;

import es.udc.ws.app.model.respuesta.Respuesta;
import es.udc.ws.app.thrift.ThriftRespuestaDto;

public class RespuestaToThriftRespuestaDtoConversor{

    public static List<ThriftRespuestaDto> toThriftRespuestaDtos(List<Respuesta> respuestas) {
        List<ThriftRespuestaDto> respuestaDtos = new ArrayList<>();
        for (int i = 0; i < respuestas.size(); i++) {
            respuestaDtos.add(toThriftRespuestaDto(respuestas.get(i)));
        }
        return respuestaDtos;
    }

    public static List<Respuesta> toRespuestas(List<ThriftRespuestaDto> restRespuestas) {
        List<Respuesta> respuestas = new ArrayList<>();
        for (int i = 0; i < restRespuestas.size(); i++) {
            respuestas.add(toRespuesta(restRespuestas.get(i)));
        }
        return respuestas;
    }

    public static ThriftRespuestaDto toThriftRespuestaDto(Respuesta respuesta) {
        return new ThriftRespuestaDto(respuesta.getIdentificadorRespuesta(), respuesta.getEmailEmpleado(),
                respuesta.isAsistencia(), respuesta.getIdEventoAlQueResponde());
    }

    public static Respuesta toRespuesta(ThriftRespuestaDto restRespuesta) {
        return new Respuesta(restRespuesta.getIdentificadorRespuesta(), restRespuesta.getEmailEmpleado(),
                restRespuesta.getIdEventoAlQueResponde(), restRespuesta.isAsistencia());
    }
    
}