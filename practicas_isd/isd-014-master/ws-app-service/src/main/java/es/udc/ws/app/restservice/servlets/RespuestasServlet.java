package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.udc.ws.app.model.eventoservicio.EventoServicioFactory;
import es.udc.ws.app.model.eventoservicio.exceptions.AlreadyCancelledEventException;
import es.udc.ws.app.model.eventoservicio.exceptions.AlreadyRespondedException;
import es.udc.ws.app.model.eventoservicio.exceptions.OutOfTimeException;
import es.udc.ws.app.model.respuesta.Respuesta;
import es.udc.ws.app.restservice.dto.RespuestaToRestRespuestaDtoConversor;
import es.udc.ws.app.restservice.dto.RestRespuestaDto;
import es.udc.ws.app.restservice.json.AppExceptionToJsonConversor;
import es.udc.ws.app.restservice.json.JsonToRestRespuestaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.RestHttpServletTemplate;
import es.udc.ws.util.servlet.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RespuestasServlet extends RestHttpServletTemplate {

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InputValidationException, InstanceNotFoundException {
        ServletUtils.checkEmptyPath(req);
        Long eventoId = ServletUtils.getMandatoryParameterAsLong(req, "eventoID");
        String email = ServletUtils.getMandatoryParameter(req, "email");
        boolean asistencia = Boolean.parseBoolean(ServletUtils.getMandatoryParameter(req, "asistencia"));

        Respuesta respuesta;
        try {
            respuesta = EventoServicioFactory.getService().responderEvento(email, eventoId, asistencia);
            RestRespuestaDto respuestaDto = RespuestaToRestRespuestaDtoConversor.toRestRespuestaDto(respuesta);

            String respuestaURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/"
                    + respuesta.getIdentificadorRespuesta().toString();
            Map<String, String> headers = new HashMap<>(1);
            headers.put("Location", respuestaURL);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                    JsonToRestRespuestaDtoConversor.toObjectNode(respuestaDto), headers);
        } catch (OutOfTimeException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    AppExceptionToJsonConversor.toEventoOutOfTimeExceptionException(e),
                    null);
        } catch (AlreadyCancelledEventException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    AppExceptionToJsonConversor.toAlreadyCancelledEventExceptionException(e),
                    null);
        } catch (AlreadyRespondedException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    AppExceptionToJsonConversor.toAlreadyRespondedEventExceptionException(e),
                    null);
        }

    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InputValidationException, InstanceNotFoundException {
        ServletUtils.checkEmptyPath(req);
        String email = ServletUtils.getMandatoryParameter(req, "email");
        boolean asistencia = Boolean.parseBoolean(ServletUtils.getMandatoryParameter(req, "asistencia"));

        List<Respuesta> respuestas = EventoServicioFactory.getService().obtenerRespuestas(email, asistencia);
        List<RestRespuestaDto> respuestaDto = RespuestaToRestRespuestaDtoConversor.toRestRespuestaDtos(respuestas);
    
         ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                JsonToRestRespuestaDtoConversor.toArrayNode(respuestaDto), null);

    }

}
