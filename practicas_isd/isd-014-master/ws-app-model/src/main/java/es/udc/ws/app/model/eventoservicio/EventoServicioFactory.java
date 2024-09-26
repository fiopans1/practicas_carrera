package es.udc.ws.app.model.eventoservicio;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class EventoServicioFactory {

    private final static String CLASS_NAME_PARAMETER = "EventoServicioFactory.className";
    private static EventoService service = null;

    private EventoServicioFactory() {
    }

    @SuppressWarnings("rawtypes")
    private static EventoService getInstance() {
        try {
            String serviceClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class serviceClass = Class.forName(serviceClassName);
            return (EventoService) serviceClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static EventoService getService() {
        if (service == null) {
            service = getInstance();
        }
        return service;
    }

}
