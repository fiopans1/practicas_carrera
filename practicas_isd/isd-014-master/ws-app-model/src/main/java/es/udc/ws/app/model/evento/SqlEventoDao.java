package es.udc.ws.app.model.evento;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public interface SqlEventoDao {
    public Evento create(Connection connection, Evento evento);
    public void update(Connection connection, Evento evento) throws InstanceNotFoundException;
    public Evento findEvento(Connection connection, Long eventoID) throws InstanceNotFoundException;
    public List<Evento> findEventos(Connection connection, LocalDate fechaInicio, LocalDate fechaFin, String keywords);
    public void remove(Connection connection, Long eventoID) throws InstanceNotFoundException;
}
