package es.udc.ws.app.client.service.rest;

import es.udc.ws.app.client.service.dto.ClientEventoDto;
import es.udc.ws.app.client.service.dto.ClientRespuestaDto;
import es.udc.ws.app.client.service.exceptions.ClientEventoAlreadyCancelledException;
import es.udc.ws.app.client.service.exceptions.ClientEventoAlreadyRespondedException;
import es.udc.ws.app.client.service.exceptions.ClientEventoOutOfTimeException;
import es.udc.ws.app.client.service.rest.json.JsonToClientEventoDtoConversor;
import es.udc.ws.app.client.service.rest.json.JsonToClientExceptionConversor;
import es.udc.ws.app.client.service.rest.json.JsonToClientRespuestaDtoConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ObjectMapperFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.udc.ws.app.client.service.ClientEventoService;

public class RestClientEventoService implements ClientEventoService {
    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientEventoService.endpointAddress";
    private String endpointAddress;

    @Override
    public ClientEventoDto addEvento(ClientEventoDto evento) throws InputValidationException {
        try {
            HttpResponse response = Request.Post(getEndpointAddress() + "eventos")
                    .bodyStream(toInputStream(evento), ContentType.create("application/json")).execute()
                    .returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientEventoDtoConversor.toClientEventoDto(response.getEntity().getContent());

        } catch (InputValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cancelEvento(Long eventoID)
            throws InstanceNotFoundException, ClientEventoAlreadyCancelledException,
            ClientEventoOutOfTimeException {
        try {

            HttpResponse response = Request.Post(getEndpointAddress() + "eventos/" + eventoID).execute()
                    .returnResponse();

            validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

        } catch (InstanceNotFoundException
                | ClientEventoAlreadyCancelledException e) {
            throw e;
        } catch (ClientEventoOutOfTimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // BLANCA.

    @Override
    public ClientEventoDto findEvento(Long eventoID) throws InstanceNotFoundException {
        try {

            HttpResponse response = Request.Get(getEndpointAddress() + "eventos/" + eventoID).execute()
                    .returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientEventoDtoConversor.toClientEventoDto(
                    response.getEntity().getContent());
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ClientEventoDto> findEventos(LocalDate fechafinEvento, String keywords) {

        try {
            HttpResponse response = Request.Get(getEndpointAddress() + "eventos?fechaFinEvento="
                    + URLEncoder.encode(fechafinEvento.toString(), "UTF-8") + "&keywords=" +
                    ((keywords != null) ? URLEncoder.encode(keywords, "UTF-8") : "")).execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientEventoDtoConversor.toClientEventoDtos(response.getEntity().getContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // RUBÉN

    @Override
    public List<ClientRespuestaDto> obtenerRespuestas(String emailEmpleado, boolean asistencia) throws InputValidationException {
        try {
            HttpResponse response = Request.Get(getEndpointAddress() + "respuestas?email="
                    + URLEncoder.encode(emailEmpleado.toString(), "UTF-8") + "&asistencia=" +
                    URLEncoder.encode(String.valueOf(asistencia), "UTF-8")).execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientRespuestaDtoConversor.toClientRespuestaDtos(response.getEntity().getContent());

        } catch (InputValidationException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientRespuestaDto responderEvento(Long eventoID, String emailEmpleado, boolean asistencia)
            throws InputValidationException, InstanceNotFoundException, ClientEventoAlreadyCancelledException,
            ClientEventoAlreadyRespondedException, ClientEventoOutOfTimeException {
        try {
            HttpResponse response = Request.Post(getEndpointAddress() + "respuestas").bodyForm(
                    Form.form().add("eventoID", Long.toString(eventoID)).add("email", emailEmpleado)
                            .add("asistencia", String.valueOf(asistencia)).build())
                    .execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientRespuestaDtoConversor.toClientRespuestaDto(response.getEntity().getContent());

        } catch (InputValidationException | InstanceNotFoundException | ClientEventoAlreadyCancelledException
                | ClientEventoOutOfTimeException
                | ClientEventoAlreadyRespondedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    private synchronized String getEndpointAddress() {
        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager
                    .getParameter(ENDPOINT_ADDRESS_PARAMETER);
        }
        return endpointAddress;
    }

    private InputStream toInputStream(ClientEventoDto evento) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientEventoDtoConversor.toObjectNode(evento));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateStatusCode(int successCode, HttpResponse response) throws Exception {
        try {
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == successCode) {
                return;
            }
            switch (statusCode) {
                case HttpStatus.SC_NOT_FOUND:
                    throw JsonToClientExceptionConversor.fromNotFoundErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_BAD_REQUEST:
                    throw JsonToClientExceptionConversor.fromBadRequestErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_FORBIDDEN:
                    throw JsonToClientExceptionConversor.fromForbiddenErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_GONE:
                    throw JsonToClientExceptionConversor.fromGoneErrorCode(
                            response.getEntity().getContent());

                default:
                    throw new RuntimeException("HTTP error; status code = "
                            + statusCode);

            }
        } catch (IOException e) { // ponerla como IOException mas adelante
            throw new RuntimeException(e);
        }
    }

}
