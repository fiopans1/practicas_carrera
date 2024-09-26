package e1.pers;
import e1.Personal;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class Conserje extends Personal{
    private static final int g_h=65;
    private static final int sueldobase=150;
    private int nocturnidad;
    public Conserje(String nombre, String apellido, int edad, int horrocruxs, int nocturnidad) {
        super(nombre, apellido, edad, horrocruxs,(sueldobase+10*nocturnidad));
        if(nocturnidad>=0) {
            this.nocturnidad = nocturnidad;
        }else{
            throw new IllegalArgumentException("Argumento no valido");
        }
    }

    @Override
    public String getSueldo() {
       StringBuilder s=new StringBuilder();
       s.append(super.getNombre()).append(" ").append(super.getApellido()).append("(Conserje): ").append(ngetSueldo()).append(" galeones\n");
       return s.toString();
    }


    @Override
    public String getRecompensa() {
        int reco;
        StringBuilder s= new StringBuilder();
        reco= g_h*super.getHorrocruxs();
        s.append(super.getNombre()).append(" ").append(super.getApellido()).append("(").append(
                "Conserje").append(",").append(super.getHorrocruxs()).append(
                " horrocruxes):").append(reco).append(" galeones\n");
        return s.toString();
    }

    @Override
    public int ngetRecompensa() {
        return g_h*super.getHorrocruxs();
    }


}
