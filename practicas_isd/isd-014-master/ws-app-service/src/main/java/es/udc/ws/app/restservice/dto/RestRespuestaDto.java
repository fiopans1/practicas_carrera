package es.udc.ws.app.restservice.dto;

public class RestRespuestaDto {
    private Long identificadorRespuesta;
    private String emailEmpleado;
    // private LocalDateTime fechaHoraRespuesta; NOS LO CARGAMOS
    private boolean asistencia;
    private Long idEventoAlQueResponde;

    public RestRespuestaDto() {
    }

    public RestRespuestaDto(Long identificadorRespuesta, String emailEmpleado, boolean asistencia,
            Long idEventoAlQueResponde) {
        this.identificadorRespuesta = identificadorRespuesta;
        this.emailEmpleado = emailEmpleado;
        this.asistencia = asistencia;
        this.idEventoAlQueResponde = idEventoAlQueResponde;
    }

    public void setIdentificadorRespuesta(Long identificadorRespuesta) {
        this.identificadorRespuesta = identificadorRespuesta;
    }

    public void setEmailEmpleado(String emailEmpleado) {
        this.emailEmpleado = emailEmpleado;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    public void setIdEventoAlQueResponde(Long idEventoAlQueResponde) {
        this.idEventoAlQueResponde = idEventoAlQueResponde;
    }

    public Long getIdentificadorRespuesta() {
        return identificadorRespuesta;
    }

    public String getEmailEmpleado() {
        return emailEmpleado;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public Long getIdEventoAlQueResponde() {
        return idEventoAlQueResponde;
    }

}
