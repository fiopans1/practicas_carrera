package es.udc.ws.app.model.evento;

import java.sql.*;


public class Jdbc3CcSqlEventoDao extends AbstractSqlEventoDao{

    @Override
    public Evento create(Connection connection, Evento evento) {
        String queryString = "INSERT INTO Evento" +
                "(nombreEvento, descripcionEvento, fechaHoraCelebracionEvento, fechaHoraAltaEvento, duracionEvento," +
                "asistentesEvento, noAsistentesEvento, cancelledEvento) VALUES (?,?,?,?,?,?,?,?)";
        try(PreparedStatement preparedStatement= connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS)){
            int i=1;
            preparedStatement.setString(i++,evento.getNombreEvento());
            preparedStatement.setString(i++, evento.getDescripcionEvento());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(evento.getFechaHoraCelebracionEvento()));
            preparedStatement.setTimestamp(i++,Timestamp.valueOf(evento.getFechaHoraAltaEvento()));
            preparedStatement.setInt(i++, evento.getDuracionEvento());
            preparedStatement.setInt(i++, evento.getAsistentesEvento());
            preparedStatement.setInt(i++, evento.getNoAsistentesEvento());
            preparedStatement.setBoolean(i++, evento.isCancelledEvento());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(!resultSet.next()){
                throw new SQLException("JDBC driver did not return generated key.");
            }

            Long eventoID = resultSet.getLong(1);

            return new Evento(eventoID, evento.getNombreEvento(), evento.getDescripcionEvento(), evento.getFechaHoraCelebracionEvento(), evento.getDuracionEvento(),
                    evento.getFechaHoraAltaEvento(),evento.getAsistentesEvento(), evento.getNoAsistentesEvento(), evento.isCancelledEvento());
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


}
