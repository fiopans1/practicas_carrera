package es.udc.ws.app.restservice.json;

import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.udc.ws.app.restservice.dto.RestRespuestaDto;

public class JsonToRestRespuestaDtoConversor {

    	public static ObjectNode toObjectNode(RestRespuestaDto respuesta) {

		ObjectNode respuestaNode = JsonNodeFactory.instance.objectNode();

        if (respuesta.getIdentificadorRespuesta() != null) {
        	respuestaNode.put("respuestaId", respuesta.getIdentificadorRespuesta());
        }
        respuestaNode.put("eventoId", respuesta.getIdEventoAlQueResponde()).
        	put("email", respuesta.getEmailEmpleado()).
        	put("asistencia", respuesta.isAsistencia());
        
        return respuestaNode;
    }

        public static ArrayNode toArrayNode(List<RestRespuestaDto> respuestas) {

            ArrayNode respuestasNode = JsonNodeFactory.instance.arrayNode();
            for (int i = 0; i < respuestas.size(); i++) {
                RestRespuestaDto respuestaDto = respuestas.get(i);
                ObjectNode respuestaObject = toObjectNode(respuestaDto);
                respuestasNode.add(respuestaObject);
            }

            return respuestasNode;
        }
    
}
