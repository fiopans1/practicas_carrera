package es.udc.ws.app.client.service.exceptions;

import java.time.LocalDateTime;

public class ClientEventoOutOfTimeException extends Exception {
    private Long idEvento;
    private LocalDateTime fechaHoraCelebracionEvento;

    public ClientEventoOutOfTimeException(Long idEvento, LocalDateTime fechaHoraCelebracionEvento) {
        super("Se ha pasado el plazo para el evento con id=\"" + idEvento + "\" con fecha de celebración \""
                + fechaHoraCelebracionEvento + "\".");
        this.idEvento = idEvento;
        this.fechaHoraCelebracionEvento = fechaHoraCelebracionEvento;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public LocalDateTime getFechaHoraCelebracionEvento() {
        return fechaHoraCelebracionEvento;
    }

    public void setFechaHoraCelebracionEvento(LocalDateTime fechaHoraCelebracionEvento) {
        this.fechaHoraCelebracionEvento = fechaHoraCelebracionEvento;
    }
}
