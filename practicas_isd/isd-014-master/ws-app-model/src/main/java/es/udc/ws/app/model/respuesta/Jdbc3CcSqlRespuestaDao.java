package es.udc.ws.app.model.respuesta;

import java.sql.*;


public class Jdbc3CcSqlRespuestaDao extends AbstractSqlRespuestaDao{

    @Override
    public Respuesta create(Connection connection, Respuesta respuesta) {
        String queryString= "INSERT INTO Respuesta" +
                " (emailEmpleado, idEventoAlQueResponde, asistencia, fechaHoraRespuesta) VALUES (?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS)){
            int i =1;
            preparedStatement.setString(i++,respuesta.getEmailEmpleado());
            preparedStatement.setLong(i++,respuesta.getIdEventoAlQueResponde());
            preparedStatement.setBoolean(i++,respuesta.isAsistencia());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(respuesta.getFechaHoraRespuesta()));

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(!resultSet.next()){
                throw new SQLException("JDBC driver did not return generated key.");
            }

            Long respuestaID = resultSet.getLong(1);
            return new Respuesta(respuestaID, respuesta.getEmailEmpleado(), respuesta.getIdEventoAlQueResponde(),respuesta.isAsistencia(), respuesta.getFechaHoraRespuesta());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
