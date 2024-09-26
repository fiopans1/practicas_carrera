package e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import e2.*;

import javax.print.DocFlavor;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class Agencia {
    private String nombre;//esta variable solo sirve para identificar a nuestra agencia, es mas adorno que funcional, ignorar
    private ArrayList<Piso> pisosEnAlquiler;
    private Comparator<Piso> comparador;

    Agencia(String nombre){
        this.nombre=nombre; //esta variable solo sirve para identificar a nuestra agencia, es mas adorno que funcional, ignorar
        pisosEnAlquiler=new ArrayList<Piso>();

    }
    public void añadirPisos(Piso p){
        if(p!=null) {
            pisosEnAlquiler.add(p);
            Collections.sort(pisosEnAlquiler);
        }else{
            throw new IllegalArgumentException("Piso no valido");
        }
    }
    public String mostrarPisos(){
        StringBuilder resultado=new StringBuilder();
        for(int i=0;i< pisosEnAlquiler.size();i++){
            resultado.append(pisosEnAlquiler.get(i).getmostrarPiso());
        }
        System.out.printf(resultado.toString());
        return resultado.toString();
    }
    public void ordenarPorCriterio(Comparator<Piso> o){
        comparador=o;
        if(comparador==null){
            Collections.sort(pisosEnAlquiler);
        }else {
            Collections.sort(pisosEnAlquiler, comparador);
        }
    }
    public ArrayList<Piso> listaPisos(){
        return pisosEnAlquiler;
    }
}
