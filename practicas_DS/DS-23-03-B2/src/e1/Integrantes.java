package e1;

import java.util.Objects;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public abstract class Integrantes {
    private String nombre;
    private String apellido;
    private int edad;
    private int horrocruxs;
    Integrantes(String nombre, String apellido, int edad, int horrocruxs){
        if(nombre!=null && apellido!=null && edad>0 && horrocruxs>=0) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.edad = edad;
            this.horrocruxs = horrocruxs;
        }else{
            throw new IllegalArgumentException("Argumentos no validos");
        }
    }
    public String getNombre(){
        return nombre;
    }
    public String getApellido(){
        return apellido;
    }
    public int getEdad(){
        return edad;
    }

    public int getHorrocruxs() {
        return horrocruxs;
    }

    public abstract String getRecompensa();
    public abstract int ngetRecompensa();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Integrantes that = (Integrantes) o;
        return edad == that.edad && horrocruxs == that.horrocruxs && Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, edad, horrocruxs);
    }
}
