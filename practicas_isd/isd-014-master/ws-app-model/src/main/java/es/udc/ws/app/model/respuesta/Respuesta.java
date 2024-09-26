package es.udc.ws.app.model.respuesta;

import java.time.LocalDateTime;

public class Respuesta {
    private Long            identificadorRespuesta;
    private String          emailEmpleado;
    private LocalDateTime   fechaHoraRespuesta;
    private boolean         asistencia;
    private Long            idEventoAlQueResponde;

    public Respuesta(Long respuestaID, String emailEmpleado, Long idEventoAlQueResponde, boolean asistencia){
        this.identificadorRespuesta     = respuestaID;
        this.emailEmpleado              = emailEmpleado;
        this.idEventoAlQueResponde      = idEventoAlQueResponde;
        this.asistencia                 = asistencia;
    }
    /* Constructor para usar cuando se genere una respuesta nueva y se le asigne un ID nuevo. */
    public Respuesta(String emailEmpleado, Long idEventoAlQueResponde, boolean asistencia, LocalDateTime fechaHoraRespuesta){
        this.emailEmpleado              = emailEmpleado;
        this.idEventoAlQueResponde      = idEventoAlQueResponde;
        this.asistencia                 = asistencia;
        this.fechaHoraRespuesta         = (fechaHoraRespuesta != null) ? fechaHoraRespuesta.withNano(0) : null;
    }
    public Respuesta(Long respuestaID, String emailEmpleado, Long idEventoAlQueResponde, boolean asistencia, LocalDateTime fechaHoraRespuesta){
        this.identificadorRespuesta     = respuestaID;
        this.emailEmpleado              = emailEmpleado;
        this.idEventoAlQueResponde      = idEventoAlQueResponde;
        this.asistencia                 = asistencia;
        this.fechaHoraRespuesta         = (fechaHoraRespuesta != null) ? fechaHoraRespuesta.withNano(0) : null;
    }

    public Long getIdentificadorRespuesta() {
        return identificadorRespuesta;
    }

    public void setIdentificadorRespuesta(Long identificadorRespuesta) {
        this.identificadorRespuesta = identificadorRespuesta;
    }

    public String getEmailEmpleado() {
        return emailEmpleado;
    }

    public void setEmailEmpleado(String emailEmpleado) {
        this.emailEmpleado = emailEmpleado;
    }

    public LocalDateTime getFechaHoraRespuesta() {
        return fechaHoraRespuesta;
    }

    public void setFechaHoraRespuesta(LocalDateTime fechaHoraRespuesta) {
        this.fechaHoraRespuesta = fechaHoraRespuesta;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    public Long getIdEventoAlQueResponde() {
        return idEventoAlQueResponde;
    }

    public void setIdEventoAlQueResponde(Long idEventoAlQueResponde) {
        this.idEventoAlQueResponde = idEventoAlQueResponde;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Respuesta respuesta = (Respuesta) object;
        return identificadorRespuesta == respuesta.identificadorRespuesta &&
                asistencia == respuesta.asistencia &&
                idEventoAlQueResponde == respuesta.idEventoAlQueResponde &&
                java.util.Objects.equals(emailEmpleado, respuesta.emailEmpleado) &&
                java.util.Objects.equals(fechaHoraRespuesta, respuesta.fechaHoraRespuesta);
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), identificadorRespuesta, emailEmpleado, fechaHoraRespuesta,
                asistencia, idEventoAlQueResponde);
    }

    @Override
    public String toString() {
        String aux = "Nombre del evento ->\t\t" + this.idEventoAlQueResponde + "\n" +
                "Email ->\t\t\t" + this.emailEmpleado + "\n" +
                "ID Respuesta ->\t\t\t" + this.identificadorRespuesta + "\n" +
                "Asiste? ->\t\t\t" + this.asistencia + "\n" +
                "Fecha y hora de respuesta ->\t" + this.fechaHoraRespuesta + "\n";
        return aux;
    }

}
