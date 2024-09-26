package es.udc.ws.app.client.service.rest.json;

import java.io.InputStream;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import es.udc.ws.app.client.service.exceptions.ClientEventoAlreadyCancelledException;
import es.udc.ws.app.client.service.exceptions.ClientEventoAlreadyRespondedException;
import es.udc.ws.app.client.service.exceptions.ClientEventoOutOfTimeException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

public class JsonToClientExceptionConversor {

    public static Exception fromBadRequestErrorCode(InputStream ex) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(ex);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("InputValidation")) {
                    return toInputValidationException(rootNode);
                } else {
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static InputValidationException toInputValidationException(JsonNode rootNode) {
        String message = rootNode.get("message").textValue();
        return new InputValidationException(message);
    }

    public static Exception fromNotFoundErrorCode(InputStream ex) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(ex);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("InstanceNotFound")) {
                    return toInstanceNotFoundException(rootNode);
                } else {
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static InstanceNotFoundException toInstanceNotFoundException(JsonNode rootNode) {
        String instanceId = rootNode.get("instanceId").textValue();
        String instanceType = rootNode.get("instanceType").textValue();
        return new InstanceNotFoundException(instanceId, instanceType);
    }

    private static ClientEventoAlreadyCancelledException toClientEventoAlreadyCancelledException(JsonNode rootNode) {
        Long eventoId = rootNode.get("eventoId").longValue();
        return new ClientEventoAlreadyCancelledException(eventoId);
    }

    private static ClientEventoAlreadyRespondedException toAlreadyRespondedEventException(JsonNode rootNode) {
        Long eventoId = rootNode.get("eventoId").longValue();
        String email = rootNode.get("email").toString();
        return new ClientEventoAlreadyRespondedException(eventoId, email);
    }

    private static ClientEventoOutOfTimeException toEventoOutOfTimeException(JsonNode rootNode) {
        Long eventoId = rootNode.get("eventoId").longValue();
       String fechaCelebracionString = rootNode.get("celebrationDate").textValue();
		LocalDateTime fechaHorDateTime = null;
		if (fechaCelebracionString != null) {
			fechaHorDateTime = LocalDateTime.parse(fechaCelebracionString);
		}
        return new ClientEventoOutOfTimeException(eventoId, fechaHorDateTime);
    }

    

    public static Exception fromForbiddenErrorCode(InputStream content) {
        return null;
    }

    public static Exception fromGoneErrorCode(InputStream content) {
        try {
			ObjectMapper objectMapper = ObjectMapperFactory.instance();
			JsonNode rootNode = objectMapper.readTree(content);
			if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
				throw new ParsingException("Unrecognized JSON (object expected)");
			} else {
				String errorType = rootNode.get("errorType").textValue();
				if (errorType.equals("EventoAlreadyCancelled")) {
					return toClientEventoAlreadyCancelledException(rootNode);
				}else if(errorType.equals("EventOutOfTime")){
                    return toEventoOutOfTimeException(rootNode);

                } else if(errorType.equals("AlreadyRespondedToThisEvent")){
                    return toAlreadyRespondedEventException(rootNode);
                }
                else {
					throw new ParsingException("Unrecognized error type: " + errorType);
				}
			}
		} catch (ParsingException e) {
			throw e;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
    }

}
