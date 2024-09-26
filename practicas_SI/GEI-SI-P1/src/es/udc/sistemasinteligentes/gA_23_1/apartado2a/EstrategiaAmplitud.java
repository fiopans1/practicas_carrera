package es.udc.sistemasinteligentes.gA_23_1.apartado2a;

import es.udc.sistemasinteligentes.gA_23_1.*;

import java.util.ArrayList;

public class EstrategiaAmplitud implements EstrategiaBusqueda {
    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception {//hacemos una implementacion de la busqueda en amplitud vista en clase
        //creamos la lista de frontera y de los estados explrados, y además variables que usaremos
        ArrayList<Nodo> Frontera= new ArrayList<>();//se utilizará como una cola FIFO
        ArrayList<Estado> Explorados= new ArrayList<>();
        Nodo nodoActual= new Nodo(p.getEstadoInicial(),null,null);
        Nodo[] H;
        int i=1;
        Frontera.add(nodoActual);
        while(!Frontera.isEmpty()){
            nodoActual=Frontera.get(0);//para que funcione la Frontera como una FIFO sacamos el primero y lo eliminamos
            Frontera.remove(0);
            System.out.println((i++) + " - Estado actual cambiado a " + nodoActual.getEstado());
            Explorados.add(nodoActual.getEstado());
            H = Nodo.Sucesores(nodoActual.getEstado(), nodoActual, p.acciones(nodoActual.getEstado()));
            for (Nodo h : H) {//añadimos cada uno de los sucesores a la solucion
                boolean pertenece = false;
                if(p.esMeta(h.getEstado())){//miramos si es meta
                    System.out.println((i++) + " - FIN - " + h.getEstado());
                    return Nodo.reconstruyeSolucion(h);
                }else {//y sino miramos sino está ni en explorados ni en frontera
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
                    if (!pertenece) {//si no está lo añadimos a la frontera
                        System.out.println((i++) + " - " + h.getEstado() + " NO explorado");
                        Frontera.add(h);
                    }
                }
            }
        }

        throw new Exception("No se ha podido encontrar una solución");
    }
}
