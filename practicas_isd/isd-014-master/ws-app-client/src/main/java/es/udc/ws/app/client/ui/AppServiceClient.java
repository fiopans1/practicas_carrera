package es.udc.ws.app.client.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import es.udc.ws.app.client.service.ClientEventoService;
import es.udc.ws.app.client.service.ClientEventoServiceFactory;
import es.udc.ws.app.client.service.dto.ClientEventoDto;
import es.udc.ws.app.client.service.dto.ClientRespuestaDto;
import es.udc.ws.app.client.service.exceptions.ClientEventoAlreadyCancelledException;
import es.udc.ws.app.client.service.exceptions.ClientEventoAlreadyRespondedException;
import es.udc.ws.app.client.service.exceptions.ClientEventoOutOfTimeException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class AppServiceClient {
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsageAndExit();
        }
        ClientEventoService clientEventoService = ClientEventoServiceFactory.getService();
        if ("-addEvento".equalsIgnoreCase(args[0])) {

            // mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.AppServiceClient"
            // -Dexec.args="-addEvento 'Fiesta' 'Fiesta Verano' '2023-08-15T17:00'
            // '2023-08-16T00:00'"

            validateArgs(args, 5, new int[] {});

            try {
                ClientEventoDto evento = clientEventoService
                        .addEvento(new ClientEventoDto(args[1], args[2], LocalDateTime.parse(args[3]),
                                LocalDateTime.parse(args[4])));

                System.out.println("Evento " + evento.toString() + " created sucessfully. ");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        } else if ("-findEventos".equalsIgnoreCase(args[0])) {

            // mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.AppServiceClient"
            // -Dexec.args="-findEventos '2023-12-01'"

            validateArgs(args, 2, new int[] {});

            try {
                List<ClientEventoDto> eventos = clientEventoService.findEventos(LocalDate.parse(args[1]),
                        (args.length >= 3) ? args[2] : null);
                System.out.println("Found " + eventos.size() +
                        " evento(s) " + ((args.length >= 3) ? " with keywords '" + args[2] : "") + "'");
                for (int i = 0; i < eventos.size(); i++) {
                    ClientEventoDto eventoDto = eventos.get(i);
                    System.out.println(eventoDto.toString());
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        } else if ("-findEvento".equalsIgnoreCase(args[0])) {

            // mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.AppServiceClient"
            // -Dexec.args="-findEvento 3"

            validateArgs(args, 2, new int[] { 1 });

            try {
                Long id = Long.valueOf(args[1]);
                ClientEventoDto foundEvento = clientEventoService.findEvento(id);
                System.out.println("Event with id \"" + id + "\" was found.\n");
                System.out.println(foundEvento.toString());
            } catch (NumberFormatException | InstanceNotFoundException e) {
                e.printStackTrace(System.err);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        } else if ("-cancelar".equalsIgnoreCase(args[0])) {

            // mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.AppServiceClient"
            // -Dexec.args="-cancelar 3"

            validateArgs(args, 2, new int[] { 1 });

            try {
                Long id = Long.valueOf(args[1]);
                clientEventoService.cancelEvento(id);
                System.out.println("Event with id " + args[1] + " was cancelled successfully. ");
            } catch (NumberFormatException | InstanceNotFoundException | ClientEventoAlreadyCancelledException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        } else if ("-findResponses".equalsIgnoreCase(args[0])) {

            // mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.AppServiceClient"
            // -Dexec.args="-findResponses 'user1@udc.es' true"

            validateArgs(args, 3, new int[] {});

            try {
                List<ClientRespuestaDto> respuestas = clientEventoService.obtenerRespuestas(args[1],
                        Boolean.parseBoolean(args[2]));
                System.out.println("Found " + respuestas.size() +
                        " answer(s) with email \"" + args[1] + "\" and asistency '" + args[2] + "'. ");
                for (int i = 0; i < respuestas.size(); i++) {
                    ClientRespuestaDto respuestaDto = respuestas.get(i);
                    System.out.println(respuestaDto.toString());
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        } else if ("-responderEvento".equalsIgnoreCase(args[0])) {
            // mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.AppServiceClient"
            // -Dexec.args="-responderEvento 'user1@udc.es' 1 true"

            validateArgs(args, 4, new int[] { 2 });

            try {
                ClientRespuestaDto respuesta = clientEventoService
                        .responderEvento(Long.parseLong(args[2]), args[1], Boolean.parseBoolean(args[3]));

                System.out.println("The response was successfully accepted: " + respuesta.toString() + ". ");

            } catch (NumberFormatException | InputValidationException | ClientEventoAlreadyCancelledException
                    | ClientEventoAlreadyRespondedException | ClientEventoOutOfTimeException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        } else {
            printUsage();
        }
    }

    public static void validateArgs(String[] args, int expectedArgs,
            int[] numericArguments) {
        if (expectedArgs > args.length) {
            printUsageAndExit();
        }
        for (int i = 0; i < numericArguments.length; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch (NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "Format LocalDateTime: 2023-07-14T17:45:55" +
                "Format LocalDate 2023-07-14" +
                "    [Add]              AppServiceClient -addEvento <Name> <Description> <LocalDateTime> <LocalDateTime>\n"+
                "    [Find Events]      AppServiceClient -findEventos <LocalDate> <keywords>\n" +
                "    [Find Event]       AppServiceClient -findEvento <eventoId>\n" +
                "    [Responder Evento] AppServiceClient -responderEvento <email> <eventoId> <asistencia>\n" +
                "    [Cancel]           AppServiceClient -cancelar <movieId>\n" +
                "    [Find Responses]   AppServiceClient -findResponses <email> <asistencia>\n");
    }
}