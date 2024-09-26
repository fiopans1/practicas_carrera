package es.udc.ws.app.client.service.dto;

public class ClientRespuestaDto {
    private Long identificadorRespuesta;
    private String emailEmpleado;
    private boolean asistencia;
    private Long idEventoAlQueResponde;

    public ClientRespuestaDto() {

    }

    public ClientRespuestaDto(Long identificadorRespuesta, String emailEmpleado, boolean asistencia,
            Long idEventoAlQueResponde) {
        this.identificadorRespuesta = identificadorRespuesta;
        this.emailEmpleado = emailEmpleado;
        this.asistencia = asistencia;
        this.idEventoAlQueResponde = idEventoAlQueResponde;
    }

    public String getEmailEmpleado() {
        return emailEmpleado;
    }

    public Long getIdEventoAlQueResponde() {
        return idEventoAlQueResponde;
    }

    public Long getIdentificadorRespuesta() {
        return identificadorRespuesta;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    public void setEmailEmpleado(String emailEmpleado) {
        this.emailEmpleado = emailEmpleado;
    }

    public void setIdEventoAlQueResponde(Long idEventoAlQueResponde) {
        this.idEventoAlQueResponde = idEventoAlQueResponde;
    }

    public void setIdentificadorRespuesta(Long identificadorRespuesta) {
        this.identificadorRespuesta = identificadorRespuesta;
    }

    @Override
    public String toString() {
        return "ClientRespuestaDto [" +
                "identificadorRespuesta=" + identificadorRespuesta +
                ", emailEmpleado='" + emailEmpleado +
                ", asistencia=" + asistencia +
                ", idEventoAlQueResponde=" + idEventoAlQueResponde +
                ']';
    }
}
