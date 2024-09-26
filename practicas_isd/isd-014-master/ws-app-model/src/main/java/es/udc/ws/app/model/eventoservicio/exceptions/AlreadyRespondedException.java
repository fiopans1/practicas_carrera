package es.udc.ws.app.model.eventoservicio.exceptions;

public class AlreadyRespondedException extends Exception{
    private Long idEvento;
    private String userEmail;

    public AlreadyRespondedException(Long idEvento, String userEmail) {
        super("Imposible responder al evento con id=\"" + idEvento + "\". El usuario con email \"" + userEmail + "\" ya ha respondido a este evento. ");
        this.idEvento = idEvento;
        this.userEmail = userEmail;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
