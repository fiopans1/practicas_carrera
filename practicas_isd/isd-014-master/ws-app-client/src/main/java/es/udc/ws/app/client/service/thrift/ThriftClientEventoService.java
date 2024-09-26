package es.udc.ws.app.client.service.thrift;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import es.udc.ws.app.client.service.ClientEventoService;
import es.udc.ws.app.client.service.dto.ClientEventoDto;
import es.udc.ws.app.client.service.dto.ClientRespuestaDto;
import es.udc.ws.app.client.service.exceptions.ClientEventoAlreadyCancelledException;
import es.udc.ws.app.client.service.exceptions.ClientEventoAlreadyRespondedException;
import es.udc.ws.app.client.service.exceptions.ClientEventoOutOfTimeException;
import es.udc.ws.app.thrift.*;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class ThriftClientEventoService implements ClientEventoService {
    private final static String ENDPOINT_ADDRESS_PARAMETER = "ThriftClientEventoService.endpointAddress";

    private final static String endpointAddress = ConfigurationParametersManager
            .getParameter(ENDPOINT_ADDRESS_PARAMETER);

    @Override
    public ClientEventoDto addEvento(ClientEventoDto evento) throws InputValidationException {
        ThriftEventoService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try {
            transport.open();
            return ClientEventoDtoToThriftEventoDtoConversor.toClientEventoDto(client.addEvento(
                    ClientEventoDtoToThriftEventoDtoConversor.toThriftEventoDto(evento)));
        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }

    }

    @Override
    public List<ClientEventoDto> findEventos(LocalDate fechafinEvento, String keywords) {
        ThriftEventoService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();
        try {
            transport.open();

            return ClientEventoDtoToThriftEventoDtoConversor
                    .toClientEventoDtos(client.findEventos(fechafinEvento.toString(), keywords));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }
    
    @Override
    public ClientEventoDto findEvento(Long eventoId) throws InstanceNotFoundException {
        ThriftEventoService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();
        try{
            transport.open();
            return ClientEventoDtoToThriftEventoDtoConversor.toClientEventoDto(client.findEvento(eventoId));
        }catch(ThriftInstanceNotFoundException e){
            
        throw new InstanceNotFoundException(e.getInstanceId(), e.getInstanceType());}
        catch(Exception e){
            throw new RuntimeException(e);
        }
        finally{
            transport.close();
        }
    }

    @Override
    public void cancelEvento(Long eventoId)
            throws InstanceNotFoundException, ClientEventoAlreadyCancelledException, ClientEventoOutOfTimeException {
        ThriftEventoService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try {

            transport.open();
            client.cancelEvento(eventoId);

        } catch (ThriftInstanceNotFoundException e) {
            throw new InstanceNotFoundException(e.getInstanceId(), e.getInstanceType());
        } catch (ThriftEventoAlreadyCancelled e) {
            throw new ClientEventoAlreadyCancelledException(e.getEventoId());
        } catch(ThriftOutOfTimeException e){
            throw new ClientEventoOutOfTimeException(eventoId, LocalDateTime.parse(e.getCelebrationDate()));
        }catch (Exception e) {
            throw new RuntimeException(e);
        } finally { 
            transport.close();
        }
        
    }

    @Override
    public List<ClientRespuestaDto> obtenerRespuestas (String emailEmpleado, boolean asistencia) throws InputValidationException{
        ThriftEventoService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try{
            transport.open();

            return ClientRespuestaDtoToThriftRespuestaDtoConversor
                    .toClientRespuestaDtos(client.obtenerRespuestas(emailEmpleado, asistencia));            
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public ClientRespuestaDto responderEvento(Long eventoId, String emailEmpleado, boolean asistencia)
            throws InputValidationException, ClientEventoAlreadyCancelledException,
            ClientEventoAlreadyRespondedException, ClientEventoOutOfTimeException, InstanceNotFoundException {
        
        ThriftEventoService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try {

       
            transport.open();
            return ClientRespuestaDtoToThriftRespuestaDtoConversor.toClientRespuestaDto(client.responderEvento(emailEmpleado, (long) eventoId, asistencia));

        } catch (ThriftInstanceNotFoundException e) {
            throw new InstanceNotFoundException(e.getInstanceId(), e.getInstanceType());
        } catch (ThriftEventoAlreadyCancelled e) {
            throw new ClientEventoAlreadyCancelledException(e.getEventoId());
        } catch(ThriftOutOfTimeException e){
            throw new ClientEventoOutOfTimeException(eventoId, LocalDateTime.parse(e.getCelebrationDate()));
        } catch(ThriftAlreadyRespondedException e){
            throw new ClientEventoAlreadyRespondedException(eventoId, e.getEmailEmpleado());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally { 
            transport.close();
        }
    }

    private ThriftEventoService.Client getClient() {

        try {

            TTransport transport = new THttpClient(endpointAddress);
            TProtocol protocol = new TBinaryProtocol(transport);

            return new ThriftEventoService.Client(protocol);

        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }

    }
}
