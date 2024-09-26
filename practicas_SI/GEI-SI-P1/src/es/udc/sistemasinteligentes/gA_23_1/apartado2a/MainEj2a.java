package es.udc.sistemasinteligentes.gA_23_1.apartado2a;

import es.udc.sistemasinteligentes.gA_23_1.*;

public class MainEj2a {
    public static void main(String[] args) throws Exception {
        int[][] est = {{4, 9, 2}, {3, 5, 0},{0, 1, 0}};
        ProblemaCuadradoMagico.EstadoCuadrado estadoInicial = new ProblemaCuadradoMagico.EstadoCuadrado(est);//definimos un estado inicial
        ProblemaBusqueda cuadradoMagico = new ProblemaCuadradoMagico(estadoInicial);//creamos un nuevo problema busqueda con nuestro estado inicial

        //comenta y descomenta la que quieras usar:
        EstrategiaBusqueda buscador = new EstrategiaAmplitud();//busqueda en amplitud
        //EstrategiaBusqueda buscador = new EstrategiaProfundidad();//busqueda en profundidad

        Nodo[] nodos = buscador.soluciona(cuadradoMagico);
        for (Nodo n : nodos) {//imprimimos los nodos que recorrimos
            System.out.println(n.toString());
        }
    }
}
