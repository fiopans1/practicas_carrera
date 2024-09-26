package es.udc.ws.app.client.service.rest.json;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.udc.ws.app.client.service.dto.ClientRespuestaDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

public class JsonToClientRespuestaDtoConversor {

    public static List<ClientRespuestaDto> toClientRespuestaDtos(InputStream content) {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(content);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY) {
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode respuestasArray = (ArrayNode) rootNode;
                List<ClientRespuestaDto> respuestaDtos = new ArrayList<>();
                for (JsonNode respuestaNode : respuestasArray) {
                    respuestaDtos.add(toClientRespuestaDto(respuestaNode));
                }
                return respuestaDtos;
            }

        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientRespuestaDto toClientRespuestaDto(JsonNode respuestaNode) throws ParsingException {
        if (respuestaNode.getNodeType() != JsonNodeType.OBJECT) {
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            ObjectNode respuestaObject = (ObjectNode) respuestaNode;

            JsonNode respuestaIdNode = respuestaObject.get("respuestaId");
            Long respuestaID = (respuestaIdNode != null) ? respuestaIdNode.longValue() : -1;

            String email = respuestaObject.get("email").textValue().trim();


            JsonNode asistenciaNode = respuestaObject.get("asistencia");
            Boolean asistencia = (asistenciaNode != null) ? asistenciaNode.booleanValue() : false;


            JsonNode idEventoAlQueRespondeNode = respuestaObject.get("eventoId");
            Long idEventoAlQueResponde = (idEventoAlQueRespondeNode != null) ? idEventoAlQueRespondeNode.longValue()
                    : -1;

            return new ClientRespuestaDto(respuestaID, email, asistencia, idEventoAlQueResponde);
        }
    }

    public static ClientRespuestaDto toClientRespuestaDto(InputStream jsonRespuesta) {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonRespuesta);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");

            } else {
                return toClientRespuestaDto(rootNode);
            }

        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

}
