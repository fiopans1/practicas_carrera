package e1;

import java.util.ArrayList;
import java.util.List;

import e1.pers.*;
import e1.resi.*;
import e1.Residentes.Casas;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class Colegio {
    //definimos una lista de cada uno de los posibles integrantes
    private List<Residentes> residentes1;
    private List<Personal> personal1;

    Colegio() {
        residentes1 = new ArrayList<Residentes>();
        personal1 = new ArrayList<Personal>();
    }

    public void añadirResidente(Residentes o) {
        if(o!=null) {
            residentes1.add(o);
        }else{
            throw new IllegalArgumentException("Residente no valaido");
        }
    }

    public void añadirPersonal(Personal o) {
        if(o!=null) {
            personal1.add(o);
        }else{
            throw new IllegalArgumentException("Personal no valido");
        }
    }

    public String imprimirRecompensa() {//modificar para que solo imprima una cadena
        StringBuilder resultado=new StringBuilder();
        int recogen = 0;
        //primero estudiantes
        for (int i = 0; i < residentes1.size(); i++) {
            resultado.append(residentes1.get(i).getRecompensa());
            recogen += residentes1.get(i).ngetRecompensa();
        }
        for (int i = 0; i < personal1.size(); i++) {
            resultado.append(personal1.get(i).getRecompensa());
            recogen += personal1.get(i).ngetRecompensa();
        }
        resultado.append("La recompensa total del Colegio Hogwarts es de " ).append( Integer.toString(recogen) ).append( " galeones\n");
        System.out.printf(resultado.toString());
        return resultado.toString();

    }

    public String imprimirSalarios() {
        StringBuilder resultado= new StringBuilder();
        int acusal = 0;
        for (int i = 0; i < personal1.size(); i++) {
            resultado.append(personal1.get(i).getSueldo());
            acusal += personal1.get(i).ngetSueldo();
        }
        resultado.append("El gasto de Hogwarts en personal es de " ).append( Integer.toString(acusal) ).append( " galeones\n");
        System.out.printf(resultado.toString());
        return resultado.toString();
    }


}
