package es.udc.ws.app.restservice.dto;

import java.time.LocalDateTime;

public class RestEventoDto {
    private String nombreEvento;
    private String descripcionEvento;
    private LocalDateTime fechaHoraCelebracionEvento;
    // private LocalDateTime fechaHoraAltaEvento; NOS LO CARGAMOS
    private Long identificadorEvento;
    private int asistentesEvento;
    // private int noAsistentesEvento; NOS LO CARGAMOS
    private int totalRespuestas; // aparece este que es la suma de asistentesEvento+noAsistentesEvento
    private int duracionEvento;
    private boolean cancelledEvento;

    public RestEventoDto() {
    }

    public RestEventoDto(String nombreEvento, String descripcionEvento, LocalDateTime fechaHoraCelebracionEvento,
            Long identificadorEvento, int asistentesEvento, int totalRespuestas, int duracionEvento,
            boolean cancelledEvento) {
        this.nombreEvento = nombreEvento;
        this.descripcionEvento = descripcionEvento;
        this.fechaHoraCelebracionEvento = fechaHoraCelebracionEvento;
        this.identificadorEvento = identificadorEvento;
        this.asistentesEvento = asistentesEvento;
        this.totalRespuestas = totalRespuestas;
        this.duracionEvento = duracionEvento;
        this.cancelledEvento = cancelledEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public void setFechaHoraCelebracionEvento(LocalDateTime fechaHoraCelebracionEvento) {
        this.fechaHoraCelebracionEvento = fechaHoraCelebracionEvento;
    }

    public void setIdentificadorEvento(Long identificadorEvento) {
        this.identificadorEvento = identificadorEvento;
    }

    public void setAsistentesEvento(int asistentesEvento) {
        this.asistentesEvento = asistentesEvento;
    }

    public void setTotalRespuestas(int totalRespuestas) {
        this.totalRespuestas = totalRespuestas;
    }

    public void setDuracionEvento(int duracionEvento) {
        this.duracionEvento = duracionEvento;
    }

    public void setCancelledEvento(boolean cancelledEvento) {
        this.cancelledEvento = cancelledEvento;
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

    public Long getIdentificadorEvento() {
        return identificadorEvento;
    }

    public int getAsistentesEvento() {
        return asistentesEvento;
    }

    public int getTotalRespuestas() {
        return totalRespuestas;
    }

    public int getDuracionEvento() {
        return duracionEvento;
    }

    public boolean isCancelledEvento() {
        return cancelledEvento;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombreEvento == null) ? 0 : nombreEvento.hashCode());
        result = prime * result + ((descripcionEvento == null) ? 0 : descripcionEvento.hashCode());
        result = prime * result + ((fechaHoraCelebracionEvento == null) ? 0 : fechaHoraCelebracionEvento.hashCode());
        result = prime * result + ((identificadorEvento == null) ? 0 : identificadorEvento.hashCode());
        result = prime * result + asistentesEvento;
        result = prime * result + totalRespuestas;
        result = prime * result + duracionEvento;
        result = prime * result + (cancelledEvento ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RestEventoDto other = (RestEventoDto) obj;
        if (nombreEvento == null) {
            if (other.nombreEvento != null)
                return false;
        } else if (!nombreEvento.equals(other.nombreEvento))
            return false;
        if (descripcionEvento == null) {
            if (other.descripcionEvento != null)
                return false;
        } else if (!descripcionEvento.equals(other.descripcionEvento))
            return false;
        if (fechaHoraCelebracionEvento == null) {
            if (other.fechaHoraCelebracionEvento != null)
                return false;
        } else if (!fechaHoraCelebracionEvento.equals(other.fechaHoraCelebracionEvento))
            return false;
        if (identificadorEvento == null) {
            if (other.identificadorEvento != null)
                return false;
        } else if (!identificadorEvento.equals(other.identificadorEvento))
            return false;
        if (asistentesEvento != other.asistentesEvento)
            return false;
        if (totalRespuestas != other.totalRespuestas)
            return false;
        if (duracionEvento != other.duracionEvento)
            return false;
        if (cancelledEvento != other.cancelledEvento)
            return false;
        return true;
    }

}
