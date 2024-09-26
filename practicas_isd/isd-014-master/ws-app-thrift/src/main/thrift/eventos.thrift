namespace java es.udc.ws.app.thrift

struct ThriftEventoDto{
    1: i64 identificadorEvento
    2: string nombreEvento
    3: string descripcionEvento
    4: string fechaHoraCelebracionEvento
    5: i32 asistentesEvento
    6: i32 totalRespuestas
    7: i32 duracionEvento
    8: bool cancelledEvento
}
struct ThriftRespuestaDto{
    1: i64 identificadorRespuesta
    2: string emailEmpleado
    3: bool asistencia
    4: i64 idEventoAlQueResponde
}
exception ThriftInputValidationException{
    1: string message
}
exception ThriftInstanceNotFoundException{
    1: string instanceId
    2: string instanceType
}
exception ThriftEventoAlreadyCancelled {
    1: i64 eventoId
}
exception ThriftOutOfTimeException {
    1: i64 eventoId
    2: string celebrationDate
}
exception ThriftAlreadyRespondedException{
    1: i64 eventoId
    2: string emailEmpleado
}
service ThriftEventoService{
    ThriftEventoDto addEvento(1: ThriftEventoDto eventoDto) throws (1: ThriftInputValidationException e)

    list<ThriftEventoDto> findEventos(1:string fechaFinEvento, 2: string keywords)

    ThriftEventoDto findEvento(1: i64 eventoId) throws (1: ThriftInstanceNotFoundException e)

    void cancelEvento(1: i64 eventoId) throws (1: ThriftInstanceNotFoundException e, 2: ThriftEventoAlreadyCancelled ee, 3: ThriftOutOfTimeException eee)

    ThriftRespuestaDto responderEvento(1: string emailEmpleado, 2: i64 eventoId, 3: bool asistencia) throws (1: ThriftInstanceNotFoundException e, 2: ThriftInputValidationException ee, 3: ThriftEventoAlreadyCancelled eee, 4: ThriftOutOfTimeException eeee, 5: ThriftAlreadyRespondedException eeeee)

    list<ThriftRespuestaDto> obtenerRespuestas(1: string emailEmpleado, 2: bool asistencia) throws (1: ThriftInputValidationException e)

}