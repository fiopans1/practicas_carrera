package es.udc.ws.app.model.respuesta;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.Connection;
import java.util.List;

public interface SqlRespuestaDao {
    public Respuesta create(Connection connection, Respuesta respuesta);
    public List<Respuesta> findRespuestas(Connection connection, String emailEmpleado, boolean asistencia);
    public void remove(Connection connection, Long respuestaID) throws InstanceNotFoundException;
    public boolean existsRespuesta(Connection connection, String emailEmpleado, Long eventoID);
}
