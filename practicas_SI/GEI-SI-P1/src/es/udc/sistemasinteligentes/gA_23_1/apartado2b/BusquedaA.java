package es.udc.sistemasinteligentes.gA_23_1.apartado2b;

import es.udc.sistemasinteligentes.gA_23_1.*;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class BusquedaA implements EstrategiaBusquedaInformada {
    @Override
    public Nodo[] soluciona(ProblemaBusqueda p, Heuristica h) throws Exception {//implemetacion del algoritmo de busqueda A*
        //primero declaramos una cola de prioridad para la frontera, y una lista de estados para Explorados
        PriorityQueue<Nodo> Frontera= new PriorityQueue<>();
        ArrayList<Estado> Explorados= new ArrayList<>();
        Nodo nodoActual= new Nodo(p.getEstadoInicial(),null,null);
        int i=0;
        Frontera.add(nodoActual);
        Estado S=null;
        Nodo[] H;
        float cn;
        while(!Frontera.isEmpty()){
            nodoActual=Frontera.remove();//quitamos el primer nodod e frontera
            S=nodoActual.getEstado();
            System.out.println((i++) + " - Estado actual cambiado a " + S);
            if(p.esMeta(S)){//miramos si el estado es meta
                System.out.println((i++) + " - FIN - " + nodoActual.getEstado());
                return Nodo.reconstruyeSolucion(nodoActual);
            }else{//si el estado no es meta generamos sucesores y miramos si se añaden a frontera o no
                Explorados.add(S);
                H=Nodo.Sucesores(S,nodoActual,p.acciones(S));
                for(Nodo nh : H){
                    nh.setCoste(nodoActual.getCoste()+1);
                    nh.setFuncion(nh.getCoste()+h.evalua(nh.getEstado()));
                    boolean pertenece=false;
                    if (Explorados.contains(nh.getEstado())) {
                        System.out.println((i++) + " - " + nh.getEstado() + " ya explorado");
                        pertenece = true;
                    }

                    if (!pertenece) {
                        for (Nodo n : Frontera) {
                            if (nh.getEstado().equals(n.getEstado())) {
                                pertenece = true;
                                if(nh.getF()<n.getF()){
                                    System.out.println((i++) + " - Nodo frontera" + n.getEstado() + "actualizado a " + nh.getEstado());
                                    Frontera.remove(n);
                                    Frontera.add(nh);
                                }
                            }
                        }
                    }
                    if (!pertenece) {//si el estado no esta ni en frontera ni en explorados lo añadimos a frontera
                        //System.out.println((i++) + " - " + nh.getEstado() + " NO explorado");
                        Frontera.add(nh);
                    }

                }

            }
        }
        throw new Exception("No se ha podido encontrar una solución");
    }
}
