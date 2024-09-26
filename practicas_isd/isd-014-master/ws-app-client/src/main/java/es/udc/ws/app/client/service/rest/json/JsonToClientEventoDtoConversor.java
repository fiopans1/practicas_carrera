package es.udc.ws.app.client.service.rest.json;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.udc.ws.app.client.service.dto.ClientEventoDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;
import static java.time.temporal.ChronoUnit.HOURS;

public class JsonToClientEventoDtoConversor {
    public static ObjectNode toObjectNode(ClientEventoDto evento) throws IOException {
        ObjectNode eventoObject = JsonNodeFactory.instance.objectNode();

        if (evento.getIdentificadorEvento() != null) {
            eventoObject.put("identificadorEvento", evento.getIdentificadorEvento());
        }
        eventoObject.put("nombreEvento", evento.getNombreEvento())
                .put("descripcionEvento", evento.getDescripcionEvento())
                .put("fechaHoraCelebracionEvento", evento.getFechaHoraCelebracionEvento().toString())
                .put("duracionEvento",
                        (HOURS.between(evento.getFechaHoraCelebracionEvento(), evento.getFechaHoraFinEvento())));
        // si quieres completarlo hacer con el resto de campos igua que con
        // identificador
        return eventoObject;
    }

    public static ClientEventoDto toClientEventoDto(InputStream jsonEvento) {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonEvento);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");

            } else {
                return toClientEventoDto(rootNode);
            }

        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientEventoDto toClientEventoDto(JsonNode eventoNode) throws ParsingException {
        if (eventoNode.getNodeType() != JsonNodeType.OBJECT) {
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            ObjectNode eventoObject = (ObjectNode) eventoNode;

            JsonNode eventoIdNode = eventoObject.get("identificadorEvento");
            Long eventoID = (eventoIdNode != null) ? eventoIdNode.longValue() : -1;

            String nombreEvento = eventoObject.get("nombreEvento").textValue().trim();
            String descripcionEvento = eventoObject.get("descripcionEvento").textValue().trim();
            LocalDateTime fechaHoraCelebracionEvento = LocalDateTime
                    .parse(eventoObject.get("fechaHoraCelebracionEvento").textValue().trim());
            JsonNode duracionNode = eventoObject.get("duracionEvento");
            Integer duracionEvento = (duracionNode != null) ? duracionNode.intValue() : -1;
            JsonNode asistentesNode = eventoObject.get("asistentesEvento");
            Integer asistentesEvento = (asistentesNode != null) ? asistentesNode.intValue() : -1;
            JsonNode totalNode = eventoObject.get("totalRespuesta");
            Integer totalRespuestas = (totalNode != null) ? totalNode.intValue() : -1;
            JsonNode cancelledNode = eventoObject.get("cancelledEvento");
            Boolean cancelledEvento = (cancelledNode != null) ? cancelledNode.booleanValue() : false;

            return new ClientEventoDto(eventoID, nombreEvento, descripcionEvento, fechaHoraCelebracionEvento,
                    fechaHoraCelebracionEvento.plusHours(duracionEvento), asistentesEvento, totalRespuestas,
                    cancelledEvento);
        }
    }

    public static List<ClientEventoDto> toClientEventoDtos(InputStream jsonEventos) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonEventos);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY) {
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode eventosArray = (ArrayNode) rootNode;
                List<ClientEventoDto> eventoDtos = new ArrayList<>();
                for (JsonNode eventoNode : eventosArray) {
                    eventoDtos.add(toClientEventoDto(eventoNode));
                }
                return eventoDtos;
            }

        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

}
