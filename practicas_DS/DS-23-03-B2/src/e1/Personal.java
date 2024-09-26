package e1;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public abstract class Personal extends Integrantes {
    private int sueldo;
    public Personal(String nombre, String apellido, int edad,int horrocruxs,int sueldo) {
        super(nombre, apellido, edad, horrocruxs);
            this.sueldo = sueldo;

    }
    public int ngetSueldo(){
        return sueldo;
    }
    public abstract String getSueldo();
}
