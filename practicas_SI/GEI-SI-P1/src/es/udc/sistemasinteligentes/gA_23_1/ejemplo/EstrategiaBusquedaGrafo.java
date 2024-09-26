package es.udc.sistemasinteligentes.gA_23_1.ejemplo;

import es.udc.sistemasinteligentes.gA_23_1.*;

import java.util.ArrayList;

public class EstrategiaBusquedaGrafo implements EstrategiaBusqueda {

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception {
        ArrayList<Nodo> Frontera= new ArrayList<>();//se utilizará como una cola FIFO
        ArrayList<Estado> Explorados= new ArrayList<>();
        Nodo nodoActual= new Nodo(p.getEstadoInicial(),null,null);
        Nodo[] H;
        int i=1;
        Frontera.add(nodoActual);
        while(!Frontera.isEmpty()){
            nodoActual=Frontera.get(0);
            Frontera.remove(0);
            System.out.println((i++) + " - Estado actual cambiado a " + nodoActual.getEstado());
            Explorados.add(nodoActual.getEstado());
            H = Nodo.Sucesores(nodoActual.getEstado(), nodoActual, p.acciones(nodoActual.getEstado()));
            for (Nodo h : H) {//añadimos cada uno de los sucesores a la solucion
                boolean pertenece = false;
                if(p.esMeta(h.getEstado())){
                    System.out.println((i++) + " - FIN - " + h.getEstado());
                    return Nodo.reconstruyeSolucion(h);
                }else {
                    System.out.println((i++) + " - " + nodoActual.getEstado() + " no es meta");

                    if (Explorados.contains(h.getEstado())) {
                        System.out.println((i++) + " - " + h.getEstado() + " ya explorado");
                        pertenece = true;
                    }

                    if (!pertenece) {
                        for (Nodo n : Frontera) {
                            if (h.getEstado().equals(n.getEstado())) {
                                pertenece = true;
                            }
                        }
                    }
                    if (!pertenece) {
                        System.out.println((i++) + " - " + h.getEstado() + " NO explorado");
                        Frontera.add(h);
                    }
                }
            }
        }

        throw new Exception("No se ha podido encontrar una solución");
    }
}



 /*   public Nodo[] soluciona(ProblemaBusqueda p) throws Exception{
        ArrayList<Estado> explorados = new ArrayList<Estado>();//guarda una lista de explorados
        Estado estadoActual = p.getEstadoInicial();//mira el estado inicial
        Nodo nodoActual= new Nodo(estadoActual,null,null);
        explorados.add(estadoActual);

        int i = 1;

        System.out.println((i++) + " - Empezando búsqueda en " + estadoActual);

        while (!p.esMeta(estadoActual)){//repetir mientras no sea meta
            System.out.println((i++) + " - " + estadoActual + " no es meta");
            Accion[] accionesDisponibles = p.acciones(estadoActual);//genera todas las acciones disponibles para ese estado
            boolean modificado = false;
            for (Accion acc: accionesDisponibles) {//para cada accion
                Estado sc = p.result(estadoActual, acc);
                Nodo padre= nodoActual;
                System.out.println((i++) + " - RESULT(" + estadoActual + ","+ acc + ")=" + sc);
                if (!explorados.contains(sc)) {//si no lo exploramos
                    estadoActual = sc;
                    System.out.println((i++) + " - " + sc + " NO explorado");
                    nodoActual= new Nodo(sc,padre,acc);
                    explorados.add(estadoActual);
                    modificado = true;
                    System.out.println((i++) + " - Estado actual cambiado a " + estadoActual);
                    break;
                }
                else//si ya lo exploramos
                    System.out.println((i++) + " - " + sc + " ya explorado");
            }
            if (!modificado) throw new Exception("No se ha podido encontrar una solución");//sino lo exploramos da error
        }
        System.out.println((i++) + " - FIN - " + estadoActual);
        return Nodo.reconstruyeSolucion(nodoActual);
    }*/
