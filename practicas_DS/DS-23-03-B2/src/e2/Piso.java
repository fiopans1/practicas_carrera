package e2;

import java.util.Objects;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */

public class Piso implements Comparable<Piso>  {
    private int numRef;
    private int precioBase;
    private int precioGaraje;
    private int metrosCuadrados;
    private int codigoPostal;
    private int plazasGaraje;
    private String titulo;

    Piso(int numref, int preciobase, int preciogaraje, int metroscuadrados, int CP, String titulo, int plazasGaraje){
        if(numref>=0 && preciobase>=0 && preciogaraje>=0 && metroscuadrados>0 && CP>=0 && titulo!=null && plazasGaraje>0) {
            this.titulo = titulo;
            this.numRef = numref;
            this.precioBase = preciobase;
            this.precioGaraje = preciogaraje;
            this.metrosCuadrados = metroscuadrados;
            this.codigoPostal = CP;
            this.plazasGaraje=plazasGaraje;
        }else{
            throw new IllegalArgumentException("Argumentos no validos");
        }

    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public int getMetroscuadrados() {
        return metrosCuadrados;
    }

    public int getNumreferencia() {
        return numRef;
    }

    public int getPreciobase() {
        return precioBase;
    }

    public int getPrecioGaraje() {
        return precioGaraje;
    }

    public int getPlazasGaraje() {
        return plazasGaraje;
    }

    public int precioTotal(){
        return (precioGaraje*plazasGaraje)+precioBase;
    }

    public String getTitulo() {
        return titulo;
    }
    //mostrarpiso que sea un metodo que muetra pisos
    @Override
    public int compareTo(Piso o) {
        return (this.numRef-o.numRef);
    }
    public String getmostrarPiso(){
        StringBuilder s= new StringBuilder();
       s.append("TituloAnuncio: ").append(getTitulo()).append(" - NumRef: ").append(getNumreferencia()).append("(CP:").append(getCodigoPostal()).append(") ").append(" - PrecioT:").append(precioTotal()).append(
                "€(").append(getPreciobase()).append("€ base +").append(getPrecioGaraje()).append("*").append(getPlazasGaraje()).append("€ garaje) - ").append(getMetroscuadrados()).append("m^2\n");
       return s.toString();
    }

    @Override
    public boolean equals(Object o) {//no tenemos en cuenta el titulo del piso
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piso piso = (Piso) o;
        return precioBase == piso.precioBase && precioGaraje == piso.precioGaraje && metrosCuadrados == piso.metrosCuadrados && codigoPostal == piso.codigoPostal && plazasGaraje == piso.plazasGaraje;
    }

    @Override
    public int hashCode() {//no tenemos en cuenta el titulo piso
        return Objects.hash(precioBase, precioGaraje, metrosCuadrados, codigoPostal,plazasGaraje);
    }
}
