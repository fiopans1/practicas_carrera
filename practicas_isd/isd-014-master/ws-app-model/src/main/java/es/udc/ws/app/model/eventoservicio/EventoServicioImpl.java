package es.udc.ws.app.model.eventoservicio;

import es.udc.ws.app.model.evento.Evento;
import es.udc.ws.app.model.evento.SqlEventoDao;
import es.udc.ws.app.model.evento.SqlEventoDaoFactory;
import es.udc.ws.app.model.eventoservicio.exceptions.AlreadyCancelledEventException;
import es.udc.ws.app.model.eventoservicio.exceptions.AlreadyRespondedException;
import es.udc.ws.app.model.eventoservicio.exceptions.OutOfTimeException;
import es.udc.ws.app.model.respuesta.Respuesta;
import es.udc.ws.app.model.respuesta.SqlRespuestaDao;
import es.udc.ws.app.model.respuesta.SqlRespuestaDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.sql.DataSourceLocator;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static es.udc.ws.app.model.util.ModelConstants.APP_DATA_SOURCE;

public class EventoServicioImpl implements EventoService {

    private final DataSource dataSource;
    private SqlEventoDao eventoDao = null;
    private SqlRespuestaDao respuestaDao = null;

    public EventoServicioImpl() {
        dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);
        eventoDao = SqlEventoDaoFactory.getDao();
        respuestaDao = (SqlRespuestaDao) SqlRespuestaDaoFactory.getDao();
    }

    private boolean validEvento(Evento evento) {
        if (evento == null) {
            return false;
        }
        if (evento.getNombreEvento() == null || evento.getNombreEvento().trim().length() == 0) {
            return false;
        }
        if (evento.getDescripcionEvento() == null || evento.getDescripcionEvento().trim().length() == 0) {
            return false;
        }

        if (evento.getFechaHoraCelebracionEvento() == null
                || evento.getFechaHoraCelebracionEvento().isBefore(LocalDateTime.now().withNano(0))) {
            return false;
        }
        if (evento.getDuracionEvento() <= 0) {
            return false;
        }
        return true;
    }

    // Diego.
    private static void patternMatches(String emailAddress, String regexPattern) {
        Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    private static boolean validEmail(String email) {

        String regexPattern = "^(.+)@(\\S+)$";
        patternMatches(email, regexPattern);
        if (email.contains("@") && email.contains(".")) {
            return true;
        }
        return false;
    }

    @Override
    public Evento addEvento(Evento evento) throws InputValidationException {
        // comprobamos entrada
        if (!validEvento(evento)) {
            throw new InputValidationException(evento.getNombreEvento()); // devuelve el nombre como mensaje de la
                                                                          // excepcion, a lo mejor cambiarlo
        }
        evento.setFechaHoraAltaEvento(LocalDateTime.now().withNano(0));
        evento.setAsistentesEvento(0);
        evento.setNoAsistentesEvento(0);
        evento.setCancelledEvento(false);
        try (Connection connection = dataSource.getConnection()) {
            try {
                // cambiamos valores transaccion
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                // creamos evento en la BD
                Evento returnedEvento = eventoDao.create(connection, evento);
                // commit
                connection.commit();
                // devolvemos
                return returnedEvento;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * at es.udc.ws.app.model.eventoservicio.EventoServicioImpl.findEventos(
     * EventoServicioImpl.java:104)
     */
    // Rubén.
    @Override
    public List<Evento> findEventos(LocalDate fechaInicioEvento, LocalDate fechafinEvento, String keywords) {
        try (Connection connection = dataSource.getConnection()) {
            return eventoDao.findEventos(connection, fechaInicioEvento, fechafinEvento, keywords);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Blanca.
    @Override
    public Evento findEvento(Long eventoID) throws InstanceNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            return eventoDao.findEvento(connection, eventoID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Blanca.
    @Override
    public Respuesta responderEvento(String emailEmpleado, Long eventoID, boolean asistencia)
            throws InstanceNotFoundException, OutOfTimeException, AlreadyCancelledEventException,
            AlreadyRespondedException, InputValidationException {
        if (!validEmail(emailEmpleado)) {
            throw new InputValidationException(emailEmpleado);
        }

        try (Connection connection = dataSource.getConnection()) {
            try {
                /* Preparar conexión. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Hacer el trabajo. */
                // Si no existe el evento con ID eventoID (provisional):
                Evento foundEvento = eventoDao.findEvento(connection, eventoID);

                if (foundEvento.isCancelledEvento()) {
                    throw new AlreadyCancelledEventException(eventoID);
                }

                if (respuestaDao.existsRespuesta(connection, emailEmpleado, eventoID)) {
                    throw new AlreadyRespondedException(eventoID, emailEmpleado);
                }

                LocalDateTime fechaActual = LocalDateTime.now().withNano(0); // findEvento(eventoID).getFechaHoraCelebracionEvento();

                // Si la fecha de ahora es menos que la del evento - 24 horas:
                if (fechaActual.plusHours(24).isAfter(foundEvento.getFechaHoraCelebracionEvento())) {
                    throw new OutOfTimeException(eventoID, fechaActual);
                }

                Respuesta respuesta = respuestaDao.create(connection,
                        new Respuesta(emailEmpleado, eventoID, asistencia, fechaActual));

                if (asistencia) {
                    foundEvento.setAsistentesEvento(foundEvento.getAsistentesEvento() + 1);
                } else {
                    foundEvento.setNoAsistentesEvento(foundEvento.getNoAsistentesEvento() + 1);
                }

                eventoDao.update(connection, foundEvento);

                /* Commit. */
                connection.commit();

                return respuesta;

            } catch (InstanceNotFoundException | OutOfTimeException | AlreadyCancelledEventException
                    | AlreadyRespondedException e) {
                connection.commit();
                throw e;

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Diego.
    @Override
    public void cancelEvento(Long eventoID)
            throws OutOfTimeException, AlreadyCancelledEventException, InstanceNotFoundException {

        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                // cogemos de la base de datos
                Evento evento = eventoDao.findEvento(connection, eventoID);

                if (evento.getFechaHoraCelebracionEvento().isBefore(LocalDateTime.now())) {
                    throw new OutOfTimeException(eventoID, evento.getFechaHoraCelebracionEvento());
                }
                if (evento.isCancelledEvento()) {
                    throw new AlreadyCancelledEventException(eventoID);
                }

                // cambiamos los datos
                evento.setCancelledEvento(true);

                // actualizamos los datos en la BD
                eventoDao.update(connection, evento);

                connection.commit();
            } catch (OutOfTimeException | AlreadyCancelledEventException | InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Rubén.
    @Override
    public List<Respuesta> obtenerRespuestas(String emailEmpleado, boolean asistencia) throws InputValidationException {
        if (!validEmail(emailEmpleado)) {
            throw new InputValidationException(emailEmpleado);
        }
        try (Connection connection = dataSource.getConnection()) {
            return respuestaDao.findRespuestas(connection, emailEmpleado, asistencia);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
