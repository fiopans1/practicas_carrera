package e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class OrdenRealizacionTareas {
    protected List<Character> cola=new ArrayList<>();
    protected List<Character> resultado= new ArrayList<>();
    protected List<Integer> grado=new ArrayList<>();
    protected List<Character> nodos;
    protected Map<Character, List<Character>> padres;
    protected Map<Character, List<Character>> hijos;
    public OrdenRealizacionTareas(List<Character> nodos, Map<Character, List<Character>> padres, Map<Character, List<Character>> hijos){
        this.nodos=nodos;
        this.padres=padres;
        this.hijos=hijos;
    }
    public String ejecutar(){
        inicializarGrado();
        ordenar();
        return resultado.toString();
    }
    /*ejecutar es el metodo plantilla*/

    public abstract void inicializarGrado();
    /*ininicalizarGrado se utiliza para
    * inicializar una lista con el numero
    * de aristas que llegan a cada nodo*/

    public abstract void ordenar();
    /*ordenar decide el orden de salida de
    * los distintos nodos*/
}
