package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.udc.ws.app.model.evento.Evento;
import es.udc.ws.app.model.eventoservicio.EventoServicioFactory;
import es.udc.ws.app.restservice.dto.EventoToRestEventoDtoConversor;
import es.udc.ws.app.restservice.dto.RestEventoDto;
import es.udc.ws.app.restservice.json.AppExceptionToJsonConversor;
import es.udc.ws.app.restservice.json.JsonToRestEventoDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.RestHttpServletTemplate;
import es.udc.ws.util.servlet.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import es.udc.ws.app.model.eventoservicio.exceptions.OutOfTimeException;
import es.udc.ws.app.model.eventoservicio.exceptions.AlreadyCancelledEventException;

public class EventosServlet extends RestHttpServletTemplate {

        
        private void addEventos(HttpServletRequest req, HttpServletResponse resp)
                        throws IOException, InputValidationException {
                ServletUtils.checkEmptyPath(req); // el post no puede llevar nada en el path, si lo lleva debe saltar
                                                  // error

                RestEventoDto eventoDto = JsonToRestEventoDtoConversor.toRestEventoDto(req.getInputStream());
                Evento evento = EventoToRestEventoDtoConversor.toEvento(eventoDto);

                evento = EventoServicioFactory.getService().addEvento(evento);

                eventoDto = EventoToRestEventoDtoConversor.toRestEventoDto(evento);
                String eventoURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/"
                                + evento.getIdentificadorEvento();
                Map<String, String> headers = new HashMap<>(1);
                headers.put("Location", eventoURL);
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                                JsonToRestEventoDtoConversor.toObjectNode(eventoDto), headers);
        }

        @Override
        protected void processPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, 
        InputValidationException, InstanceNotFoundException{
                String path = req.getPathInfo();
                if (path == null || path == "/") {
                        addEventos(req, resp);
                } else {
                        cancelEventos(req, resp);
                }
        }


        // RUBEN

        private void cancelEventos(HttpServletRequest req, HttpServletResponse resp) throws IOException,
                        InputValidationException, InstanceNotFoundException {
                Long eventoId = ServletUtils.getIdFromPath(req, "evento");

                try {
                        EventoServicioFactory.getService().cancelEvento(eventoId);
                } catch (OutOfTimeException e) {
                        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                                        AppExceptionToJsonConversor.toEventoOutOfTimeExceptionException(e),
                                        null);
                } catch (AlreadyCancelledEventException e) {
                        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                                        AppExceptionToJsonConversor.toAlreadyCancelledEventExceptionException(e),
                                        null);
                }

                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
        }

        @Override
        protected void processGet(HttpServletRequest req, HttpServletResponse resp)
                        throws InputValidationException, IOException, InstanceNotFoundException {
                String path = req.getPathInfo();
                if (path == null || path == "/") {
                        getFindEventos(req, resp);
                } else {
                        getFindEvento(req, resp);
                }
        }

        private void getFindEventos(HttpServletRequest req, HttpServletResponse resp)
                        throws InputValidationException, IOException {
                ServletUtils.checkEmptyPath(req);
                String sFechaFinEvento = req.getParameter("fechaFinEvento");
                String keywords = req.getParameter("keywords");
                LocalDate fechaFinEvento = LocalDate.parse(sFechaFinEvento);

                List<Evento> eventos = EventoServicioFactory.getService().findEventos(LocalDate.now(), fechaFinEvento,
                                (keywords != "") ? keywords : null);

                List<RestEventoDto> eventoDtos = EventoToRestEventoDtoConversor.toRestEventosDtos(eventos);
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                                JsonToRestEventoDtoConversor.toArrayNode(eventoDtos), null);

        }

        // BLANCA

        private void getFindEvento(HttpServletRequest req, HttpServletResponse resp)
                        throws InputValidationException, IOException, InstanceNotFoundException {
                Long eventoID = ServletUtils.getIdFromPath(req, "eventoID");
                Evento foundEvento = EventoServicioFactory.getService().findEvento(eventoID);
                RestEventoDto eventoDto = EventoToRestEventoDtoConversor.toRestEventoDto(foundEvento);
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                                JsonToRestEventoDtoConversor.toObjectNode(eventoDto), null);
        }
}
