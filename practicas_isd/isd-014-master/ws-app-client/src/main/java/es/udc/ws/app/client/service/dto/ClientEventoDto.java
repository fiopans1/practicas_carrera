package es.udc.ws.app.client.service.dto;

import java.time.LocalDateTime;

public class ClientEventoDto {
    private Long identificadorEvento;
    private String nombreEvento;
    private String descripcionEvento;
    private LocalDateTime fechaHoraCelebracionEvento;
    private LocalDateTime fechaHoraFinEvento; // es coger la fechaHoraCelebracionEvento.plusHours(duracion),
    // lo que no estoy seguro es si es solo hora o fecha y hora, esto preguntarlo
    private int asistentesEvento;
    private int totalRespuestas;
    private boolean cancelledEvento;

    public ClientEventoDto(String nombreEvento, String descripcionEvento, LocalDateTime fechaHoraCelebracionEvento,
            LocalDateTime fechaHoraFinEvento) {
        this.nombreEvento = nombreEvento;
        this.descripcionEvento = descripcionEvento;
        this.fechaHoraCelebracionEvento = fechaHoraCelebracionEvento;
        this.fechaHoraFinEvento = fechaHoraFinEvento;
    }

    public ClientEventoDto(Long identificadorEvento, String nombreEvento, String descripcionEvento,
            LocalDateTime fechaHoraCelebracionEvento, LocalDateTime fechaHoraFinEvento, int asistentesEvento,
            int totalRespuestas, boolean cancelledEvento) {
        this.identificadorEvento = identificadorEvento;
        this.nombreEvento = nombreEvento;
        this.descripcionEvento = descripcionEvento;
        this.fechaHoraCelebracionEvento = fechaHoraCelebracionEvento;
        this.fechaHoraFinEvento = fechaHoraFinEvento;
        this.asistentesEvento = asistentesEvento;
        this.totalRespuestas = totalRespuestas;
        this.cancelledEvento = cancelledEvento;
    }

    public Long getIdentificadorEvento() {
        return identificadorEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public LocalDateTime getFechaHoraCelebracionEvento() {
        return fechaHoraCelebracionEvento;
    }

    public LocalDateTime getFechaHoraFinEvento() {
        return fechaHoraFinEvento;
    }

    public int getAsistentesEvento() {
        return asistentesEvento;
    }

    public int getTotalRespuestas() {
        return totalRespuestas;
    }

    public boolean isCancelledEvento() {
        return cancelledEvento;
    }

    public void setIdentificadorEvento(Long identificadorEvento) {
        this.identificadorEvento = identificadorEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public void setAsistentesEvento(int asistentesEvento) {
        this.asistentesEvento = asistentesEvento;
    }

    public void setFechaHoraCelebracionEvento(LocalDateTime fechaHoraCelebracionEvento) {
        this.fechaHoraCelebracionEvento = fechaHoraCelebracionEvento;
    }

    public void setFechaHoraFinEvento(LocalDateTime fechaHoraFinEvento) {
        this.fechaHoraFinEvento = fechaHoraFinEvento;
    }

    public void setTotalRespuestas(int totalRespuestas) {
        this.totalRespuestas = totalRespuestas;
    }

    public void setCancelledEvento(boolean cancelledEvento) {
        this.cancelledEvento = cancelledEvento;
    }

    @Override
    public String toString() {
        return "ClientEventoDto [" +
                "identificadorEvento=" + identificadorEvento +
                ", nombreEvento= " + nombreEvento +
                ", descripcionEvento= " + descripcionEvento +
                ", fechaHoraCelebracionEvento=" + fechaHoraCelebracionEvento +
                ", fechaHoraFinEvento=" + fechaHoraFinEvento +
                ", asistentesEvento=" + asistentesEvento +
                ", totalRespuestas=" + totalRespuestas +
                ", cancelledEvento=" + cancelledEvento +
                ']';
    }
}
