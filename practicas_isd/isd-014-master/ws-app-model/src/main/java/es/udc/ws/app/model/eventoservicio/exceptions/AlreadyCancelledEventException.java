package es.udc.ws.app.model.eventoservicio.exceptions;

public class AlreadyCancelledEventException extends Exception {
    private Long eventoID;

    public AlreadyCancelledEventException(Long eventoID) {
        super("Imposible cancelar el evento con id=\"" + eventoID + "\". Ya ha sido previamente cancelado. ");
        this.eventoID = eventoID;
    }

    public Long getEventoID() {
        return eventoID;
    }
    public void setEventoID(Long eventoID) {
        this.eventoID = eventoID;
    }

}


