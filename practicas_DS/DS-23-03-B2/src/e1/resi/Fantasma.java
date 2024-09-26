package e1.resi;
import e1.Residentes;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class Fantasma extends Residentes{
    private static final int g_h=80;
    public Fantasma(String nombre, String apellido, int edad, int horrocruxs, Residentes.Casas casa) {
        super(nombre, apellido, edad,horrocruxs, casa);
    }


    @Override
    public String getRecompensa() {
        int reco;
        StringBuilder s= new StringBuilder();
        if(super.getCasa()==Casas.Slytherin) {
            reco=g_h * super.getHorrocruxs()*2;
        }else{
            reco= g_h * super.getHorrocruxs();
        }
        s.append(super.getNombre()).append(" ").append(super.getApellido()).append("(").append(
                "Fastasma de ").append(super.getCasa().toString()).append(",").append(super.getHorrocruxs()).append(
                " horrocruxes):").append(reco).append(" galeones\n");
        return s.toString();
    }

    @Override
    public int ngetRecompensa() {
        if(super.getCasa()==Casas.Slytherin) {
            return g_h * super.getHorrocruxs()*2;
        }else{
            return g_h * super.getHorrocruxs();
        }
    }
}
