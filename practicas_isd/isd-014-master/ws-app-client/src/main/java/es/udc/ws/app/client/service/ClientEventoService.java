package es.udc.ws.app.client.service;

import java.time.LocalDate;
import java.util.List;

import es.udc.ws.app.client.service.dto.ClientEventoDto;
import es.udc.ws.app.client.service.dto.ClientRespuestaDto;
import es.udc.ws.app.client.service.exceptions.ClientEventoAlreadyCancelledException;
import es.udc.ws.app.client.service.exceptions.ClientEventoAlreadyRespondedException;
import es.udc.ws.app.client.service.exceptions.ClientEventoOutOfTimeException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface ClientEventoService {
    public ClientEventoDto addEvento(ClientEventoDto evento) throws InputValidationException;

    public void cancelEvento(Long eventoID) throws InstanceNotFoundException, ClientEventoAlreadyCancelledException, ClientEventoOutOfTimeException;

    public ClientEventoDto findEvento(Long eventoID) throws InstanceNotFoundException;

    public List<ClientEventoDto> findEventos(LocalDate fechafinEvento, String keywords);

    public List<ClientRespuestaDto> obtenerRespuestas(String emailEmpleado, boolean asistencia) throws InputValidationException;

    public ClientRespuestaDto responderEvento(Long eventoID, String emailEmpleado, boolean asistencia) throws InputValidationException, InstanceNotFoundException, ClientEventoAlreadyCancelledException, ClientEventoAlreadyRespondedException, ClientEventoOutOfTimeException;

}
