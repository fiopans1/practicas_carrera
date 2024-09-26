package es.udc.sistemasinteligentes.gA_23_1.apartado2a;
import es.udc.sistemasinteligentes.gA_23_1.*;

import java.util.ArrayList;
import java.util.Stack;

public class EstrategiaProfundidad implements EstrategiaBusqueda {

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception {//implementamos la busqueda por profundidad vista en clase
        //creamos una Pila para frontera y una lista de estados para explorados
        Stack<Nodo> Frontera = new Stack<>();
        ArrayList<Estado> Explorados = new ArrayList<>();
        //declaramos otras variables
        int i = 0;
        Nodo nodoActual = new Nodo(p.getEstadoInicial(), null, null);
        Frontera.add(nodoActual);
        Estado S=null;
        while (!Frontera.empty()) {
            nodoActual = Frontera.pop();//sacamos el primer elemento de la pila y lo eliminamos
            S = nodoActual.getEstado();
            System.out.println((i++) + " - Estado actual cambiado a " + nodoActual.getEstado());
            if (p.esMeta(S)) {//miramos si el estado actual es meta
                System.out.println((i++) + " - FIN - " + nodoActual.getEstado());
                return Nodo.reconstruyeSolucion(nodoActual);
            } else {//si el estado no es meta generamos sucesores y miramos si se añaden a frontera o no
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
                    if (!pertenece) {//sino esta en explorados ni en frontera entonces añadimos ese nodo a frontera
                        System.out.println((i++) + " - " + h.getEstado() + " NO explorado");
                        Frontera.add(h);
                    }
                }
            }
        }
        throw new Exception("No se ha podido encontrar una solución");
    }
}
