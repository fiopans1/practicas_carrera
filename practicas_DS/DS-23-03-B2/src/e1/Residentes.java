package e1;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public abstract class Residentes extends Integrantes{
    public enum Casas{Gryffindor,
        Hufflepuff, Ravenclaw,  Slytherin}

    private Casas casa;

    public Residentes(String nombre, String apellido, int edad,int horrocruxs, Casas casa) {
        super(nombre,apellido,edad,horrocruxs);
        if(casa!=null) {
            this.casa = casa;
        }else{
            throw new IllegalArgumentException("Argumentos no validos");
        }
    }

    protected Casas getCasa() {
        return casa;
    }
}
