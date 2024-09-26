package es.udc.ws.app.model.evento;

import java.time.LocalDateTime;
import java.util.Objects;

public class Evento {
    private String          nombreEvento;
    private String          descripcionEvento;
    private LocalDateTime   fechaHoraCelebracionEvento;
    private LocalDateTime   fechaHoraAltaEvento;
    private Long            identificadorEvento;
    private int             asistentesEvento;
    private int             noAsistentesEvento;
    private int             duracionEvento;
    private boolean         cancelledEvento;

    public Evento( String nombreEvento, String descripcionEvento, LocalDateTime fechaHoraCelebracionEvento,
                   int duracionEvento){
        this.nombreEvento               = nombreEvento;
        this.descripcionEvento          = descripcionEvento;
        this.fechaHoraCelebracionEvento = (fechaHoraCelebracionEvento != null) ? fechaHoraCelebracionEvento.withNano(0) : null;
        this.duracionEvento             = duracionEvento;
    }
    public Evento(Long identificadorEvento, String nombreEvento, String descripcionEvento, LocalDateTime fechaHoraCelebracionEvento,
                  int duracionEvento, int asistentesEvento, int noAsistentesEvento, boolean cancelEvento){
        this.identificadorEvento        = identificadorEvento;
        this.nombreEvento               = nombreEvento;
        this.descripcionEvento          = descripcionEvento;
        this.fechaHoraCelebracionEvento = (fechaHoraCelebracionEvento != null) ? fechaHoraCelebracionEvento.withNano(0) : null;
        this.duracionEvento             = duracionEvento;
        this.asistentesEvento           = asistentesEvento;
        this.noAsistentesEvento         = noAsistentesEvento;
        this.cancelledEvento            = cancelEvento;
    }
    public Evento(Long identificadorEvento, String nombreEvento, String descripcionEvento,
                  LocalDateTime fechaHoraCelebracionEvento, int duracionEvento, LocalDateTime fechaHoraAltaEvento,
                  int asistentesEvento, int noAsistentesEvento, boolean cancelEvento){
        this.identificadorEvento        = identificadorEvento;
        this.nombreEvento               = nombreEvento;
        this.descripcionEvento          = descripcionEvento;
        this.fechaHoraCelebracionEvento = (fechaHoraCelebracionEvento != null) ? fechaHoraCelebracionEvento.withNano(0) : null;
        this.duracionEvento             = duracionEvento;
        this.fechaHoraAltaEvento        = (fechaHoraAltaEvento != null) ? fechaHoraAltaEvento.withNano(0) : null;
        this.asistentesEvento           = asistentesEvento;
        this.noAsistentesEvento         = noAsistentesEvento;
        this.cancelledEvento            = cancelEvento;
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

    public void setCancelledEvento(boolean cancelledEvento) {
        this.cancelledEvento = cancelledEvento;
    }

    public void setDuracionEvento(int duracionEvento) {
        this.duracionEvento = duracionEvento;
    }

    public void setFechaHoraAltaEvento(LocalDateTime fechaHoraAltaEvento) {
        this.fechaHoraAltaEvento = fechaHoraAltaEvento;
    }

    public void setFechaHoraCelebracionEvento(LocalDateTime fechaHoraCelebracionEvento) {
        this.fechaHoraCelebracionEvento = fechaHoraCelebracionEvento;
    }

    public void setIdentificadorEvento(Long identificadorEvento) {
        this.identificadorEvento = identificadorEvento;
    }

    public void setNoAsistentesEvento(int noAsistentesEvento) {
        this.noAsistentesEvento = noAsistentesEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public int getDuracionEvento() {
        return duracionEvento;
    }

    public LocalDateTime getFechaHoraAltaEvento() {
        return fechaHoraAltaEvento;
    }

    public int getAsistentesEvento() {
        return asistentesEvento;
    }

    public int getNoAsistentesEvento() {
        return noAsistentesEvento;
    }

    public LocalDateTime getFechaHoraCelebracionEvento() {
        return fechaHoraCelebracionEvento;
    }

    public Long getIdentificadorEvento() {
        return identificadorEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }


    public boolean isCancelledEvento() {
        return cancelledEvento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return identificadorEvento == evento.identificadorEvento &&
                asistentesEvento == evento.asistentesEvento &&
                noAsistentesEvento == evento.noAsistentesEvento &&
                Float.compare(evento.duracionEvento, duracionEvento) == 0 &&
                cancelledEvento == evento.cancelledEvento &&
                nombreEvento.toString().equals(evento.nombreEvento.toString()) &&
                descripcionEvento.toString().equals(evento.descripcionEvento.toString()) &&
                fechaHoraCelebracionEvento.equals(evento.fechaHoraCelebracionEvento) &&
                fechaHoraAltaEvento.equals(evento.fechaHoraAltaEvento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreEvento, descripcionEvento, fechaHoraCelebracionEvento, fechaHoraAltaEvento,
                identificadorEvento, asistentesEvento, noAsistentesEvento, duracionEvento, cancelledEvento);
    }

    @Override
    public String toString() {          //================================================================

        String aux =    "Nombre ->\t\t" + this.nombreEvento + "\n" +
                        "Descripción ->\t\t" + this.descripcionEvento + "\n" +
                        "Celebración ->\t\t" + this.fechaHoraCelebracionEvento + "\n" +
                        "Alta ->\t\t\t" + this.fechaHoraAltaEvento + "\n" +
                        "ID Evento ->\t\t" + this.identificadorEvento + "\n" +
                        "Asistentes ->\t\t" + this.asistentesEvento + "\n" +
                        "No Asistentes ->\t" + this.noAsistentesEvento + "\n" +
                        "Duración ->\t\t" + this.duracionEvento + "\n" +
                        "Cancelado? ->\t\t" + this.cancelledEvento + "\n";
        return aux;
    }

}
