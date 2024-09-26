package e2;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Debil1 extends OrdenRealizacionTareas{
    /*implementacion de un algoritmo para hacer un recorrido por altura variando en que se ordena
    * las opciones a escoger por orden alfabético*/
    public Debil1(List<Character> nodos, Map<Character, List<Character>> padres, Map<Character, List<Character>> hijos) {
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
                Collections.sort(cola);
            }
        }
    }
    @Override
    public void ordenar() {
        List<Character> aux;
        int aux1,aux2;
        char c,d;
        while(!cola.isEmpty()){
            c=cola.get(0);
            cola.remove(0);
            resultado.add(c);
            aux=padres.get(c);
            if(aux!=null) {
                Collections.sort(aux);
                for (Character character : aux) {
                    d = character;
                    aux1 = nodos.indexOf(d);
                    aux2 = grado.get(aux1);
                    if (aux2==0) {
                        grado.set(aux1, 1);
                        cola.add(d);
                    }

                }
            }
            Collections.sort(cola);
        }
    }

}
