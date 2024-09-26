package es.udc.ws.app.model.respuesta;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public abstract class AbstractSqlRespuestaDao implements SqlRespuestaDao{

    protected AbstractSqlRespuestaDao() {
    }

    @Override
    public List<Respuesta> findRespuestas(Connection connection, String emailUser, boolean asistencia) {

        String queryString = "SELECT respuestaID, emailEmpleado, idEventoAlQueResponde, asistencia, fechaHoraRespuesta"
                + " FROM Respuesta WHERE LOWER(emailEmpleado) LIKE LOWER(?)";

        if (asistencia) {
            queryString += " AND asistencia=1";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            int j = 1;
            preparedStatement.setString(j++, emailUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Respuesta> respuestas = new ArrayList<>();

            while (resultSet.next()) {

                int i = 1;
                // respuestaID
                Long respuestaID = resultSet.getLong(i++);
                // emailEmpleado
                String emailEmpleado = resultSet.getString(i++);
                // idEventoAlQueResponde
                Long idEventoAlQueResponde = resultSet.getLong(i++);
                // asistenciaAlEvento
                boolean asistenciaAlEvento = resultSet.getBoolean(i++);
                // fechaHoraRespuesta
                Timestamp fechaHoraRespuestaAsTimestamp = resultSet.getTimestamp(i++);
                LocalDateTime fechaHoraRespuesta = fechaHoraRespuestaAsTimestamp.toLocalDateTime();

                respuestas.add(new Respuesta(respuestaID, emailEmpleado, idEventoAlQueResponde,
                        asistenciaAlEvento, fechaHoraRespuesta));
            }
            return respuestas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Connection connection, Long respuestaID) throws InstanceNotFoundException {
        String queryString = "DELETE FROM Respuesta WHERE" + " respuestaID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            int i = 1;
            preparedStatement.setLong(i++, respuestaID);

            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(respuestaID, Respuesta.class.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsRespuesta(Connection connection, String emailUser, Long eventoID) {
        String queryString = "SELECT COUNT(*) FROM Respuesta WHERE idEventoAlQueResponde = ? AND emailEmpleado = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString)){
            /*  Llenar "preparedStatement" */
            int i = 1;
            preparedStatement.setLong(i++, eventoID);
            preparedStatement.setString(i++, emailUser);

            /* Ejecutar query */
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                throw new SQLException("Error obteniendo el número de respuestas para el evento con id " + eventoID);
                }
            
            /* Obtención de resultados */
            i = 1;
            Long numeroRespuestas = resultSet.getLong(i++); 

            /* Devolución resultados */
            if(numeroRespuestas > 0){
                return true;
            } else {
                return false;
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        
    }


}
