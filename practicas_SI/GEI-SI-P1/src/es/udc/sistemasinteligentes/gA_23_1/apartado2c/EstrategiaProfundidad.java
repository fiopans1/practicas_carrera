package es.udc.sistemasinteligentes.gA_23_1.apartado2c;
import es.udc.sistemasinteligentes.gA_23_1.*;

import java.util.ArrayList;
import java.util.Stack;

public class EstrategiaProfundidad implements EstrategiaBusqueda {

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception {//exactamente la misma que en el 2a
        Stack<Nodo> Frontera = new Stack<>();
        int i = 0;
        ArrayList<Estado> Explorados = new ArrayList<>();
        Nodo nodoActual = new Nodo(p.getEstadoInicial(), null, null);
        Frontera.add(nodoActual);
        Estado S=null;
        while (!Frontera.empty()) {
            nodoActual = Frontera.pop();
            S = nodoActual.getEstado();
            System.out.println((i++) + " - Estado actual cambiado a " + nodoActual.getEstado());
            if (p.esMeta(S)) {
                System.out.println((i++) + " - FIN - " + nodoActual.getEstado());
                return Nodo.reconstruyeSolucion(nodoActual);
            } else {
                System.out.println((i++) + " - " + nodoActual.getEstado() + " no es meta");
                Explorados.add(nodoActual.getEstado());
                Accion[] acc = p.acciones(S);
                Nodo[] H = Nodo.Sucesores(S, nodoActual, acc);
                for (Nodo h : H) {//añadimos cada uno de los sucesores a la solucion
                    boolean pertenece = false;
                    if(Explorados.contains(h.getEstado())){
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
