package e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Jerarquico1 extends OrdenRealizacionTareas{
    /*implementacion de un algoritmo para hacer un recorrido por altura*/
    public Jerarquico1(List<Character> nodos, Map<Character, List<Character>> padres, Map<Character, List<Character>> hijos) {
        super(nodos, padres, hijos);
    }


    @Override
    public void inicializarGrado() {
        for (Character nodo : nodos) {//asignamos grado cada nodo
            if (hijos.containsKey(nodo)) {
                grado.add(0);
            } else {
                grado.add(1);
                cola.add(nodo);
            }
        }

    }
    @Override
    public void ordenar() {
        if (!cola.isEmpty()) {
            Collections.sort(cola);
            aux(cola);//con cada llamada recursiva se hace una altura
        }

    }

    private void aux(List<Character> cola2) {
        List<Character> aux;
        int aux1,aux2;
        Character c, d;
        List<Character> cola1 = new ArrayList<>();
        while (!cola2.isEmpty()) {
            c = cola2.get(0);
            cola2.remove(0);
            resultado.add(c);
            aux = padres.get(c);
            if (aux != null) {
                Collections.sort(aux);
                for (int i = 0; i < aux.size(); i++) {
                    d = aux.get(i);
                    aux1 = nodos.indexOf(d);
                    aux2 = grado.get(aux1);
                    if (aux2==0) {
                        grado.set(aux1, 1);
                        cola1.add(d);
                    }

                }
            }
        }
        if (!cola1.isEmpty()) {
            Collections.sort(cola1);
            aux(cola1);
        }
    }
}
