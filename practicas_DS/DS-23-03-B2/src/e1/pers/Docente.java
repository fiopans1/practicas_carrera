package e1.pers;
import e1.Personal;
import e1.pers.ProfesionesDocentes;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class Docente extends Personal {
    private static final int g_h=50;
    ProfesionesDocentes profesion;
    public Docente(String nombre, String apellido, int edad, int horrocruxs, ProfesionesDocentes profesion) {
        super(nombre, apellido, edad, horrocruxs, profesion.getSueldo());
            this.profesion = profesion;
    }

    public ProfesionesDocentes getProfesion() {
        return profesion;
    }
    @Override
    public String getSueldo() {
        StringBuilder s=new StringBuilder();
        s.append(super.getNombre()).append(" ").append(super.getApellido()).append("(Docente de ").append(getProfesion().toString()).append("): ").append(ngetSueldo()).append(" galeones\n");
        return s.toString();
    }
    @Override
    public String getRecompensa() {
        int reco;
        StringBuilder s= new StringBuilder();
        if(profesion==ProfesionesDocentes.DEFENSA){
            reco= (int) (super.getHorrocruxs()*g_h*0.75);
        }else{
            reco= super.getHorrocruxs()*g_h;
        }
        s.append(super.getNombre()).append(" ").append(super.getApellido()).append("(").append(
                "Docente de ").append(getProfesion().toString()).append(",").append(super.getHorrocruxs()).append(
                " horrocruxes):").append(reco).append(" galeones\n");
        return s.toString();
    }

    @Override
    public int ngetRecompensa() {
        if(profesion==ProfesionesDocentes.DEFENSA){
            return (int) (super.getHorrocruxs()*g_h*0.75);
        }else{
            return super.getHorrocruxs()*g_h;
        }
    }
}
