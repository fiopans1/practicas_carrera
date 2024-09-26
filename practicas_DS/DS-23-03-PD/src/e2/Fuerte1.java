package e2;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Fuerte1 extends OrdenRealizacionTareas {
    /*implementacion de un algoritmo para realizar una ordenacion topologica*/
    public Fuerte1(List<Character> nodos, Map<Character, List<Character>> padres, Map<Character, List<Character>> hijos) {
        super(nodos, padres, hijos);
    }
    @Override
    public void inicializarGrado() {
        List<Character> aux;
        for (Character nodo : nodos) {//asignamos grado cada nodo
            if (hijos.containsKey(nodo)) {
                aux = hijos.get(nodo);
                grado.add(aux.size());
            } else {
                grado.add(0);
                cola.add(nodo);
            }
        }
    }
    @Override
    public void ordenar() {
        List<Character> aux;
        int aux1,aux2;
        char c,d;
        while (!cola.isEmpty()) {
            c = cola.get(0);
            cola.remove(0);
            resultado.add(c);
            aux = padres.get(c);
            if (aux != null) {
                Collections.sort(aux);
                for (Character character : aux) {
                    d = character;
                    aux1 = nodos.indexOf(d);
                    aux2 = grado.get(aux1);
                    aux2--;
                    grado.set(aux1, aux2);
                    if (grado.get(aux1) == 0) {
                        cola.add(d);
                        Collections.sort(cola);
                    }

                }
            }
        }
    }


}
