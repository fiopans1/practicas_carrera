package es.udc.ws.app.test.model.appservice;

import static es.udc.ws.app.model.util.ModelConstants.APP_DATA_SOURCE;

import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import es.udc.ws.app.model.evento.Evento;
import es.udc.ws.app.model.eventoservicio.EventoService;
import es.udc.ws.app.model.evento.SqlEventoDao;
import es.udc.ws.app.model.evento.SqlEventoDaoFactory;
import es.udc.ws.app.model.eventoservicio.EventoServicioFactory;
import es.udc.ws.app.model.eventoservicio.exceptions.AlreadyCancelledEventException;
import es.udc.ws.app.model.eventoservicio.exceptions.AlreadyRespondedException;
import es.udc.ws.app.model.eventoservicio.exceptions.OutOfTimeException;
import es.udc.ws.app.model.respuesta.Respuesta;
import es.udc.ws.app.model.respuesta.SqlRespuestaDao;
import es.udc.ws.app.model.respuesta.SqlRespuestaDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

public class AppServiceTest {

    private final long NON_EXISTENT_EVENTO_ID = -1;
    private final String NON_VALID_USER_EMAIL = "hfcoiewhfowh.es";
    private final String EMAIL_USER1 = "blanca@udc.es";
    private final String EMAIL_USER2 = "diego@udc.es";
    private final String EMAIL_USER3 = "ruben@udc.es";

    private static EventoService eventoService = null;
    private static SqlRespuestaDao respuestaDao = null;
    private static SqlEventoDao eventoDao = null;

    @BeforeAll
    public static void init() {

        DataSource dataSource = new SimpleDataSource();
        DataSourceLocator.addDataSource(APP_DATA_SOURCE, dataSource);

        eventoService = EventoServicioFactory.getService();
        eventoDao = SqlEventoDaoFactory.getDao();
        respuestaDao = SqlRespuestaDaoFactory.getDao();
    }

    /* ===================== funciones ================== */

    /* RUBÉN */
    // getEvento(String nombreEvento);
    private Evento getValidEvento(String nombreEvento, LocalDateTime date) {
        return new Evento(nombreEvento, "Descripción evento", date, 10);
    }

    // removeEvento(Long eventoID);
    private void removeEvento(Long eventoID) throws SQLException {

        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                eventoDao.remove(connection, eventoID);
                connection.commit();
            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw new RuntimeException(e);
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }
        }

    }

    /* DIEGO */
    private void removeRespuesta(Long idRespuesta) throws SQLException {
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                respuestaDao.remove(connection, idRespuesta);
                connection.commit();
            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw new RuntimeException(e);
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }
        }
    }

    /* BLANCA */
    // getValidEvento();
    private Evento getValidEvento() {
        LocalDateTime date = LocalDateTime.of(2024, 4, 1, 0, 0, 0);
        return getValidEvento("April Fool's", date);
    }

    // createEvento(Evento evento);
    private Evento createEvento(Evento evento) {
        Evento addedEvento = null;
        try {
            addedEvento = eventoService.addEvento(evento);
        } catch (InputValidationException e) {
            throw new RuntimeException(e);
        }
        return addedEvento;
    }

    /*
     * ===================== test =================== (por favor cumplan el orden en
     * el que están las cosas).
     */

    /* RUBÉN */
    // valid addEvento
    @Test
    public void testAddEventoAndFindEvento() throws InputValidationException, InstanceNotFoundException, SQLException {

        Evento evento = getValidEvento();
        Evento addedEvento = null;

        try {

            // Create Evento
            LocalDateTime beforeCreationDate = LocalDateTime.now().withNano(0);
            addedEvento = eventoService.addEvento(evento);
            LocalDateTime afterCreationDate = LocalDateTime.now().withNano(0);

            // Find Evento
            Evento foundEvento = eventoService.findEvento(addedEvento.getIdentificadorEvento());
            assertEquals(addedEvento, foundEvento);
            assertEquals(foundEvento.getNombreEvento(), evento.getNombreEvento());
            assertEquals(foundEvento.getDescripcionEvento(), evento.getDescripcionEvento());
            assertEquals(foundEvento.getFechaHoraCelebracionEvento(), evento.getFechaHoraCelebracionEvento());
            assertEquals(foundEvento.getDuracionEvento(), evento.getDuracionEvento());
            assertEquals(foundEvento.getAsistentesEvento(), evento.getAsistentesEvento());
            assertEquals(foundEvento.getNoAsistentesEvento(), evento.getNoAsistentesEvento());
            assertEquals(foundEvento.isCancelledEvento(), evento.isCancelledEvento());
            assertTrue((foundEvento.getFechaHoraAltaEvento().compareTo(beforeCreationDate) >= 0)
                    && (foundEvento.getFechaHoraAltaEvento().compareTo(afterCreationDate) <= 0));

        } finally {
            // Clear Database
            if (addedEvento != null) {
                removeEvento(addedEvento.getIdentificadorEvento());
            }
        }
    }

    // invalid InputValidation addEvento
    @Test
    public void testAddInvalidEvento() {

        // Comprobar que el título no sea null
        assertThrows(InputValidationException.class, () -> {
            Evento evento = getValidEvento();
            evento.setNombreEvento(null);
            Evento addedEvento = eventoService.addEvento(evento);
            removeEvento(addedEvento.getIdentificadorEvento());
        });

        // Comprobar que el título no esté vacío
        assertThrows(InputValidationException.class, () -> {
            Evento evento = getValidEvento();
            evento.setNombreEvento("");
            Evento addedEvento = eventoService.addEvento(evento);
            removeEvento(addedEvento.getIdentificadorEvento());
        });

        // Comprobar que la duración >= 0
        assertThrows(InputValidationException.class, () -> {
            Evento evento = getValidEvento();
            evento.setDuracionEvento(-1);
            Evento addedEvento = eventoService.addEvento(evento);
            removeEvento(addedEvento.getIdentificadorEvento());
        });

        // Comprobar que la descripción no sea null
        assertThrows(InputValidationException.class, () -> {
            Evento evento = getValidEvento();
            evento.setDescripcionEvento(null);
            Evento addedEvento = eventoService.addEvento(evento);
            removeEvento(addedEvento.getIdentificadorEvento());
        });

        // Comprobar que la descripción no esté vacía
        assertThrows(InputValidationException.class, () -> {
            Evento evento = getValidEvento();
            evento.setDescripcionEvento("");
            Evento addedEvento = eventoService.addEvento(evento);
            removeEvento(addedEvento.getIdentificadorEvento());
        });

        // Comprobar que la fecha de celebración no sea null
        assertThrows(InputValidationException.class, () -> {
            Evento evento = getValidEvento();
            evento.setFechaHoraCelebracionEvento(null);
            Evento addedEvento = eventoService.addEvento(evento);
            removeEvento(addedEvento.getIdentificadorEvento());
        });

        // Comprobar que la fecha de celebración no sea anterior a la de alta de evento
        assertThrows(InputValidationException.class, () -> {
            Evento evento = getValidEvento();
            evento.setFechaHoraCelebracionEvento(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
            Evento addedEvento = eventoService.addEvento(evento);
            removeEvento(addedEvento.getIdentificadorEvento());
        });

    }

    // valid findEventos
    @Test
    public void testFindEventos() throws SQLException { // Falla

        // Add eventos
        List<Evento> eventos = new LinkedList<Evento>();
        Evento evento1 = createEvento(getValidEvento("Halloween", LocalDateTime.of(2023, 10, 31, 0, 0, 0)));
        eventos.add(evento1);
        Evento evento2 = createEvento(getValidEvento("XMas", LocalDateTime.of(2023, 12, 25, 0, 0, 0)));
        eventos.add(evento2);
        Evento evento3 = createEvento(getValidEvento("Carnival", LocalDateTime.of(2023, 2, 16, 0, 0, 0)));
        eventos.add(evento3);

        try {
            List<Evento> foundEventos = eventoService.findEventos(LocalDate.now(),
                    LocalDate.of(2023, 12, 31), null);

            assertEquals(eventos.size(), foundEventos.size());
            foundEventos = eventoService.findEventos(LocalDate.now(),
                    LocalDate.of(2023, 8, 31), null);
            assertEquals(1, foundEventos.size());

            assertEquals(eventos.get(2).hashCode(), foundEventos.get(0).hashCode());
            foundEventos = eventoService.findEventos(LocalDate.now(),
                    LocalDate.of(2021, 12, 31), null);
            assertEquals(0, foundEventos.size());

        } finally {
            // Clear Database
            for (Evento evento : eventos) {
                removeEvento(evento.getIdentificadorEvento());
            }
        }

    }

    /* DIEGO */
    // valid findEvento
    // invalid InstanceNotFound findEvento
    @Test
    public void testFindNonExistEvento() {
        assertThrows(InstanceNotFoundException.class, () -> {
            eventoService.findEvento(NON_EXISTENT_EVENTO_ID);
        });
    }

    /* BLANCA */
    // valid test ResponderEvento
    @Test
    public void testResponderEventoCorrecto() throws SQLException, OutOfTimeException, AlreadyCancelledEventException,
            AlreadyRespondedException, InstanceNotFoundException, InputValidationException {
        Evento evento = createEvento(getValidEvento());
        List<Respuesta> respuestas = new ArrayList<>();

        try {
            // ResponderEvento Correctamente.
            respuestas.add(eventoService.responderEvento(EMAIL_USER1, evento.getIdentificadorEvento(), false));
            assertEquals(evento.getIdentificadorEvento(), respuestas.get(0).getIdEventoAlQueResponde());

            evento = eventoService.findEvento(evento.getIdentificadorEvento());
            assertEquals(1, evento.getNoAsistentesEvento());
            respuestas.add(eventoService.responderEvento(EMAIL_USER2, evento.getIdentificadorEvento(), true));
            assertEquals(evento.getIdentificadorEvento(), respuestas.get(1).getIdEventoAlQueResponde());

            evento = eventoService.findEvento(evento.getIdentificadorEvento());
            assertEquals(1, evento.getNoAsistentesEvento());
            assertEquals(1, evento.getAsistentesEvento());

            respuestas.add(eventoService.responderEvento(EMAIL_USER3, evento.getIdentificadorEvento(), true));
            assertEquals(evento.getIdentificadorEvento(), respuestas.get(1).getIdEventoAlQueResponde());

            evento = eventoService.findEvento(evento.getIdentificadorEvento());
            assertEquals(1, evento.getNoAsistentesEvento());
            assertEquals(2, evento.getAsistentesEvento());

            Evento foundEvento = eventoService.findEvento(evento.getIdentificadorEvento());
            assertEquals(evento, foundEvento);
            assertEquals(foundEvento.getNombreEvento(), evento.getNombreEvento());
            assertEquals(foundEvento.getDescripcionEvento(), evento.getDescripcionEvento());
            assertEquals(foundEvento.getFechaHoraCelebracionEvento(), evento.getFechaHoraCelebracionEvento());
            assertEquals(foundEvento.getDuracionEvento(), evento.getDuracionEvento());
            assertEquals(foundEvento.getAsistentesEvento(), evento.getAsistentesEvento());
            assertEquals(foundEvento.getNoAsistentesEvento(), evento.getNoAsistentesEvento());
            assertEquals(foundEvento.isCancelledEvento(), evento.isCancelledEvento());

            List<Respuesta> foundRespuesta = eventoService.obtenerRespuestas(EMAIL_USER1, false);
            assertEquals(foundRespuesta.get(0).equals(respuestas.get(0)), true);
            assertEquals(foundRespuesta.get(0).getEmailEmpleado(), respuestas.get(0).getEmailEmpleado());
            assertEquals(foundRespuesta.get(0).isAsistencia(), respuestas.get(0).isAsistencia());
            assertEquals(foundRespuesta.get(0).getIdEventoAlQueResponde(),
                    respuestas.get(0).getIdEventoAlQueResponde());
            assertEquals(foundRespuesta.get(0).getFechaHoraRespuesta(), respuestas.get(0).getFechaHoraRespuesta());

            foundRespuesta.addAll(eventoService.obtenerRespuestas(EMAIL_USER3, true));
            assertEquals(foundRespuesta.get(1).isAsistencia(), respuestas.get(2).isAsistencia());

        } finally {

            // Clear Database
            for (Respuesta respuesta : respuestas) {
                removeRespuesta(respuesta.getIdentificadorRespuesta());
            }
            removeEvento(evento.getIdentificadorEvento());

        }
    }

    // Invalid AlreadyResponded ResponderEvento
    @Test
    public void testAlreadyRespondedResponderEvento() throws AlreadyCancelledEventException, OutOfTimeException,
            AlreadyRespondedException, InstanceNotFoundException, SQLException, InputValidationException {

        Evento evento1 = createEvento(getValidEvento());
        Respuesta respuesta2 = null;

        try {

            respuesta2 = eventoService.responderEvento(EMAIL_USER1, evento1.getIdentificadorEvento(), false);
            assertThrows(AlreadyRespondedException.class, () -> {
                eventoService.responderEvento(EMAIL_USER1, evento1.getIdentificadorEvento(), false);
            });

        } finally {

            removeRespuesta(respuesta2.getIdentificadorRespuesta());
            removeEvento(evento1.getIdentificadorEvento());

        }

    }

    // Comprobar que la fecha de celebración no sea anterior a la de alta de evento
    // Invalid AlreadyCancelled ResponderEvento
    @Test
    public void testAlreadyCancelledResponderEvento() throws OutOfTimeException, AlreadyCancelledEventException,
            AlreadyRespondedException, InstanceNotFoundException, SQLException {

        Evento evento1 = createEvento(getValidEvento());
        try {
            eventoService.cancelEvento(evento1.getIdentificadorEvento());
            assertThrows(AlreadyCancelledEventException.class, () -> {
                eventoService.responderEvento(EMAIL_USER1, evento1.getIdentificadorEvento(), false);
            });
        } finally {
            removeEvento(evento1.getIdentificadorEvento());
        }

    }

    // Invalida NotFoundResponderEvento - la gente que responde y buscar.
    @Test
    public void testNotFoundResponderEvento() throws SQLException {
        Evento halloween = createEvento(getValidEvento());

        try {
            assertThrows(InstanceNotFoundException.class, () -> {
                eventoService.responderEvento(EMAIL_USER3, NON_EXISTENT_EVENTO_ID, false);
            });
        } finally {
            removeEvento(halloween.getIdentificadorEvento());
        }

    }

    // Invalid Email.
    @Test
    public void testNonValidEmailResponderEvento()
            throws SQLException, OutOfTimeException, AlreadyCancelledEventException,
            AlreadyRespondedException, InstanceNotFoundException, InputValidationException {
        Evento evento = createEvento(getValidEvento());

        try {
            assertThrows(InputValidationException.class, () -> {
                eventoService.responderEvento(NON_VALID_USER_EMAIL, evento.getIdentificadorEvento(), false);
            });

        } finally {
            removeEvento(evento.getIdentificadorEvento());
        }

    }

    /* DIEGO */
    // Invalid OutOfTime ResponderEvento (Porque haya pasado y porque no dé tiempo a
    // responder.).
    @Test
    public void testOutOfTimeResponderEvento() throws SQLException {
        LocalDateTime fechaCelebracionDateTime = LocalDateTime.now().withNano(0); // sumarle horas
        Evento halloween = createEvento(getValidEvento("Halloween", fechaCelebracionDateTime));

        try {
            assertThrows(OutOfTimeException.class, () -> {
                eventoService.responderEvento(EMAIL_USER2, halloween.getIdentificadorEvento(), false);
            });
        } finally {
            removeEvento(halloween.getIdentificadorEvento());
        }

    }

    @Test
    public void testValidCancelEvento()
            throws SQLException, OutOfTimeException, AlreadyCancelledEventException, InstanceNotFoundException {
        Evento evento = getValidEvento();
        Evento addedEvento = createEvento(evento);// lo metemos a la BD

        try {
            eventoService.cancelEvento(addedEvento.getIdentificadorEvento());// lo cancelamos
            Evento evento2 = eventoService.findEvento(addedEvento.getIdentificadorEvento());// obtenemos el actualizado
            assertEquals(true, evento2.isCancelledEvento());
        } finally {
            removeEvento(addedEvento.getIdentificadorEvento());
        }

    }

    // invalid OutOfTime cancelEvento
    @Test
    public void tesTOutOfTimeCancelEvento() throws SQLException {
        LocalDateTime fechaCelebracionDateTime = LocalDateTime.now().withNano(0);
        Evento halloween = createEvento(getValidEvento("Halloween", fechaCelebracionDateTime));

        try {
            assertThrows(OutOfTimeException.class, () -> {
                eventoService.cancelEvento(halloween.getIdentificadorEvento());
            });

        } finally {
            removeEvento(halloween.getIdentificadorEvento());
        }

    }

    // invalid AlreadyCancelled cancelEvento
    @Test
    public void testAlreadyCancelledCancelEvento()
            throws SQLException, OutOfTimeException, AlreadyCancelledEventException, InstanceNotFoundException {
        Evento evento = createEvento(getValidEvento());

        try {
            eventoService.cancelEvento(evento.getIdentificadorEvento());
            assertThrows(AlreadyCancelledEventException.class, () -> {
                eventoService.cancelEvento(evento.getIdentificadorEvento());
            });

        } finally {
            removeEvento(evento.getIdentificadorEvento());
        }

    }

    // invalid NotFound cancelEvento
    @Test
    public void testNotFoundCalcelEvento() {
        assertThrows(InstanceNotFoundException.class, () -> {
            eventoService.cancelEvento(NON_EXISTENT_EVENTO_ID);
        });
    }

    /* RUBÉN */
    // Valid obtenerRespuestas
    @Test
    public void testObtenerRespuestas() throws InputValidationException, SQLException, OutOfTimeException,
            AlreadyCancelledEventException, AlreadyRespondedException, InstanceNotFoundException {
        // obtenerRespuestas(String emailEmpleado, boolean asistencia)

        Evento evento1 = createEvento(getValidEvento());
        Evento evento2 = createEvento(getValidEvento());
        Evento evento3 = createEvento(getValidEvento());
        List<Respuesta> respuestas = new ArrayList<>();
        List<Respuesta> foundRespuestas = new ArrayList<>();

        try {
            respuestas.add(eventoService.responderEvento(EMAIL_USER1, evento1.getIdentificadorEvento(), false));
            respuestas.add(eventoService.responderEvento(EMAIL_USER2, evento1.getIdentificadorEvento(), false));
            respuestas.add(eventoService.responderEvento(EMAIL_USER3, evento1.getIdentificadorEvento(), true));
            respuestas.add(eventoService.responderEvento(EMAIL_USER1, evento2.getIdentificadorEvento(), true));
            respuestas.add(eventoService.responderEvento(EMAIL_USER3, evento2.getIdentificadorEvento(), false));
            respuestas.add(eventoService.responderEvento(EMAIL_USER3, evento3.getIdentificadorEvento(), true));

            foundRespuestas = eventoService.obtenerRespuestas(EMAIL_USER1, true);
            assertEquals(1, foundRespuestas.size());
            assertEquals(foundRespuestas.get(0).getIdEventoAlQueResponde(), evento2.getIdentificadorEvento());

            foundRespuestas = eventoService.obtenerRespuestas(EMAIL_USER2, true);
            assertEquals(0, foundRespuestas.size());

            foundRespuestas = eventoService.obtenerRespuestas(EMAIL_USER3, false);
            assertEquals(3, foundRespuestas.size());
            assertEquals(foundRespuestas.get(1).isAsistencia(), false);

        } finally {

            for (Respuesta respuesta : respuestas) {
                removeRespuesta(respuesta.getIdentificadorRespuesta());
            }

            removeEvento(evento1.getIdentificadorEvento());
            removeEvento(evento2.getIdentificadorEvento());
            removeEvento(evento3.getIdentificadorEvento());

        }
    }

    @Test
    public void testNonValidEmailObtenerRespuestas() throws SQLException, InputValidationException {
        assertThrows(InputValidationException.class, () -> {
            eventoService.obtenerRespuestas(NON_VALID_USER_EMAIL, false);
        });
    }

}