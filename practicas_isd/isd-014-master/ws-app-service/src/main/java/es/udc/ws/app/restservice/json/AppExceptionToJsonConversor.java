package es.udc.ws.app.restservice.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.udc.ws.app.model.eventoservicio.exceptions.AlreadyCancelledEventException;
import es.udc.ws.app.model.eventoservicio.exceptions.AlreadyRespondedException;
import es.udc.ws.app.model.eventoservicio.exceptions.OutOfTimeException;

public class AppExceptionToJsonConversor {

    public static ObjectNode toEventoOutOfTimeExceptionException(OutOfTimeException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "EventOutOfTime");
        exceptionObject.put("eventoId", (ex.getIdEvento() != null) ? ex.getIdEvento() : null);
        if (ex.getFechaHoraCelebracionEvento() != null) {
            exceptionObject.put("celebrationDate", ex.getFechaHoraCelebracionEvento().toString());
        } else {
            exceptionObject.set("celebrationDate", null);
        }

        return exceptionObject;
    }

    public static ObjectNode toAlreadyCancelledEventExceptionException(AlreadyCancelledEventException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "EventoAlreadyCancelled");
        exceptionObject.put("eventoId", (ex.getEventoID() != null) ? ex.getEventoID() : null);

        return exceptionObject;
    }

    public static JsonNode toAlreadyRespondedEventExceptionException(AlreadyRespondedException e) {
        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "AlreadyRespondedToThisEvent");
        exceptionObject.put("eventoId", (e.getIdEvento() != null) ? e.getIdEvento() : null);
        exceptionObject.put("email", (e.getUserEmail() != null) ? e.getUserEmail() : null);


        return exceptionObject;
    }



}
