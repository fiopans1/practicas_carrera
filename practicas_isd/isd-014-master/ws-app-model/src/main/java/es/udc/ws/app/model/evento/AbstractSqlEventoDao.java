package es.udc.ws.app.model.evento;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlEventoDao implements SqlEventoDao {

    protected AbstractSqlEventoDao() {
    }

    /*
     * String nombreEvento, String descripcionEvento, LocalDateTime
     * fechaHoraCelebracionEvento,
     * int duracionEvento, int asistentesEvento, int noAsistentesEvento, boolean
     * cancelledEvento
     */
    @Override
    public Evento findEvento(Connection connection, Long eventoID) throws InstanceNotFoundException {
        /* Crear queryString */
        String queryString = "SELECT nombreEvento, descripcionEvento, "
                + " fechaHoraCelebracionEvento, duracionEvento, asistentesEvento, noAsistentesEvento, cancelledEvento, fechaHoraAltaEvento"
                + " FROM Evento WHERE eventoID = ?"; // obtener la fechaAlta
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            /* poner "preparedStatement" */
            int i = 1;
            preparedStatement.setLong(i++, eventoID.longValue());

            /* Ejecutar Query */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(eventoID, Evento.class.getName());
            }

            /* Obtener resultados */
            i = 1;
            String nombreEvento = resultSet.getString(i++);
            String descripcionEvento = resultSet.getString(i++);
            LocalDateTime fechaHoraCelebracionEvento = resultSet.getTimestamp(i++).toLocalDateTime();
            int duracionEvento = resultSet.getInt(i++);
            int asistentesEvento = resultSet.getInt(i++);
            int noAsistentesEvento = resultSet.getInt(i++);
            boolean cancelledEvento = resultSet.getBoolean(i++);
            LocalDateTime fechaHoraAltaEvento = resultSet.getTimestamp(i++).toLocalDateTime();

            /* DEVOLUCIÓN EVENTO */
            /*
             * public Evento(Long identificadorEvento, String nombreEvento, String
             * descripcionEvento,
             * LocalDateTime fechaHoraCelebracionEvento, int duracionEvento,
             * int asistentesEvento, int noAsistentesEvento, boolean cancelEvento)
             */
            return new Evento(eventoID, nombreEvento, descripcionEvento, fechaHoraCelebracionEvento, duracionEvento,
                    fechaHoraAltaEvento,
                    asistentesEvento, noAsistentesEvento, cancelledEvento);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * at es.udc.ws.app.model.evento.AbstractSqlEventoDao.findEventos(
     * AbstractSqlEventoDao.java:82)
     */
    @Override
    public List<Evento> findEventos(Connection connection, LocalDate fechaInicio, LocalDate fechaFin, String keywords) {
        /* Create "queryString". */
        String[] words = keywords != null ? keywords.split(" ") : null;
        String queryString = "SELECT eventoID, nombreEvento, descripcionEvento, fechaHoraCelebracionEvento, "
                + "duracionEvento, fechaHoraAltaEvento, asistentesEvento, noAsistentesEvento, cancelledEvento FROM Evento "
                + "WHERE fechaHoraCelebracionEvento BETWEEN ? AND ?";

        if (words != null && words.length > 0) {
            for (int i = 0; i < words.length; i++) {
                queryString += " AND LOWER(descripcionEvento) LIKE LOWER(?)";
            }
        }
        queryString += "ORDER BY descripcionEvento";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int j = 1;
            preparedStatement.setTimestamp(j++, Timestamp.valueOf(fechaInicio.atStartOfDay()));
            preparedStatement.setTimestamp(j++, Timestamp.valueOf(fechaFin.atStartOfDay()));

            if (words != null) {
                /* Fill "preparedStatement". */
                for (int i = 0; i < words.length; i++) {
                    preparedStatement.setString(j++, "%" + words[i] + "%");
                }
            }

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read evento. */
            List<Evento> events = new ArrayList<>();

            while (resultSet.next()) {

                int i = 1;

                // identificadorEvento
                Long identificadorEvento = resultSet.getLong(i++);

                // nombreEvento
                String nombreEvento = resultSet.getString(i++);

                // descripcionEvento
                String descripcionEvento = resultSet.getString(i++);

                // fechaHoraCelebracionEvento
                Timestamp fechaHoraCelebracionEventoAsTimestamp = resultSet.getTimestamp(i++);
                LocalDateTime fechaHoraCelebracionEvento = fechaHoraCelebracionEventoAsTimestamp.toLocalDateTime();

                // duracionEvento
                int duracionEvento = resultSet.getInt(i++);

                // fechaHoraAltaEvento
                Timestamp fechaHoraAltaEventoAsTimestamp = resultSet.getTimestamp(i++);
                LocalDateTime fechaHoraAltaEvento = fechaHoraAltaEventoAsTimestamp.toLocalDateTime();

                // asistentesEvento
                int asistentesEvento = resultSet.getInt(i++);

                // noAsistentesEvento
                int noAsistentesEvento = resultSet.getInt(i++);

                // cancelledEvento
                boolean cancelledEvento = resultSet.getBoolean(i++);

                events.add(new Evento(identificadorEvento, nombreEvento, descripcionEvento, fechaHoraCelebracionEvento,
                        duracionEvento, fechaHoraAltaEvento, asistentesEvento, noAsistentesEvento, cancelledEvento));

            }

            /* Return Eventos. */
            return events;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Connection connection, Evento evento) throws InstanceNotFoundException {

        // Creamos string
        String queryString = "UPDATE Evento"
                + " SET nombreEvento = ?, descripcionEvento = ?, fechaHoraCelebracionEvento = ?, duracionEvento = ?, asistentesEvento = ?,"
                + " noAsistentesEvento = ?, cancelledEvento = ? WHERE eventoID = ?";

        /* try with resources */
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            // add parameters
            int i = 1;
            preparedStatement.setString(i++, evento.getNombreEvento());
            preparedStatement.setString(i++, evento.getDescripcionEvento());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(evento.getFechaHoraCelebracionEvento()));
            preparedStatement.setInt(i++, evento.getDuracionEvento());
            preparedStatement.setInt(i++, evento.getAsistentesEvento());
            preparedStatement.setInt(i++, evento.getNoAsistentesEvento());
            preparedStatement.setBoolean(i++, evento.isCancelledEvento());
            preparedStatement.setLong(i++, evento.getIdentificadorEvento());

            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(evento.getIdentificadorEvento(), Evento.class.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Connection connection, Long eventoID) throws InstanceNotFoundException {
        // preparamos String
        String queryString = "DELETE FROM Evento WHERE eventoID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            int i = 1;

            preparedStatement.setLong(i++, eventoID);
            int removedRows = preparedStatement.executeUpdate();
            if (removedRows == 0) {
                throw new InstanceNotFoundException(eventoID, Evento.class.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}
