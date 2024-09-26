package es.udc.ws.app.restservice.json;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.udc.ws.app.restservice.dto.RestEventoDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

//ojo como convertimos las fechas, a lo mejor hay que tocar un poco el parsher, de momento es temporal
public class JsonToRestEventoDtoConversor {
    public static ObjectNode toObjectNode(RestEventoDto evento) {
        ObjectNode eventoObject = JsonNodeFactory.instance.objectNode();

        if (evento.getIdentificadorEvento() != null) {
            eventoObject.put("identificadorEvento", evento.getIdentificadorEvento());

        }
        eventoObject.put("nombreEvento", evento.getNombreEvento())
                .put("descripcionEvento", evento.getDescripcionEvento())
                .put("fechaHoraCelebracionEvento", evento.getFechaHoraCelebracionEvento().toString())
                .put("asistentesEvento", evento.getAsistentesEvento())
                .put("totalRespuesta", evento.getTotalRespuestas())
                .put("duracionEvento", evento.getDuracionEvento())
                .put("cancelledEvento", evento.isCancelledEvento());

        return eventoObject;
    }

    public static ArrayNode toArrayNode(List<RestEventoDto> eventos) {
        ArrayNode eventosNode = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < eventos.size(); i++) {
            RestEventoDto eventoDto = eventos.get(i);
            ObjectNode eventoObject = toObjectNode(eventoDto);
            eventosNode.add(eventoObject);
        }
        return eventosNode;
    }

    public static RestEventoDto toRestEventoDto(InputStream jsonEvento) throws ParsingException, IOException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonEvento);

            if (rootNode.getNodeType() != JsonNodeType.OBJECT) { // tenemos que recibir un objeto, si recibimos otra
                                                                 // cosa exception
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode eventoObject = (ObjectNode) rootNode;
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
                JsonNode totalNode = eventoObject.get("totalRespuestas");
                Integer totalRespuestas = (totalNode != null) ? totalNode.intValue() : -1;
                JsonNode cancelledNode = eventoObject.get("cancelledEvento ");
                Boolean cancelledEvento = (cancelledNode != null) ? cancelledNode.booleanValue() : false;

                return new RestEventoDto(nombreEvento, descripcionEvento, fechaHoraCelebracionEvento,
                        eventoID, asistentesEvento, totalRespuestas, duracionEvento, cancelledEvento);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
}
