package e2.comparadores;

import e2.Piso;

import java.util.Comparator;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class MetrosCuadrados implements Comparator<Piso> {
    @Override
    public int compare(Piso o1, Piso o2) {
        return (o1.getMetroscuadrados()-o2.getMetroscuadrados());
    }
}
