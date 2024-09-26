package es.udc.sistemasinteligentes.gA_23_1.ejemplo;

import es.udc.sistemasinteligentes.gA_23_1.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.gA_23_1.Nodo;
import es.udc.sistemasinteligentes.gA_23_1.ProblemaBusqueda;

public class MainEj1 {
    public static void main(String[] args) throws Exception {
        ProblemaAspiradora.EstadoAspiradora estadoInicial = new ProblemaAspiradora.EstadoAspiradora(ProblemaAspiradora.EstadoAspiradora.PosicionRobot.IZQ,
                ProblemaAspiradora.EstadoAspiradora.PosicionBasura.AMBAS);//definimos un estado inicial
        ProblemaBusqueda aspiradora = new ProblemaAspiradora(estadoInicial);//creamos un nuevo problemabusqueda con nuestro estado inicial

        //descomenta la estrategia que quieres usar y comenta la otra
        //EstrategiaBusqueda buscador = new Estrategia4();//opcion 1
        EstrategiaBusqueda buscador = new EstrategiaBusquedaGrafo();//opcion 2

        Nodo[] nodos= buscador.soluciona(aspiradora);
        for (Nodo n: nodos) {
            System.out.println(n.toString());
        }
    }
}
