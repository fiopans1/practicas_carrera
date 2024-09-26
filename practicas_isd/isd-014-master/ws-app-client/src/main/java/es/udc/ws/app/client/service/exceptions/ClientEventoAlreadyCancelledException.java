package es.udc.ws.app.client.service.exceptions;

public class ClientEventoAlreadyCancelledException extends Exception {

    private Long eventoID;

    public ClientEventoAlreadyCancelledException(Long eventoID) {
        super("No se puede realizar la acción porque el evento con id=\"" + eventoID + "\" ya ha sido cancelado. ");
        this.eventoID = eventoID;
    }

    public Long getEventoID() {
        return eventoID;
    }
    public void setEventoID(Long eventoID) {
        this.eventoID = eventoID;
    }
    
}
