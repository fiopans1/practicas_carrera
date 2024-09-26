package e1.pers;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public enum ProfesionesDocentes {
    DEFENSA(500),TRANSFORMACIONES(400),POCIONES(350),HERBOLOGIA(250),HISTORIA(200);
    private int sueldo;
    ProfesionesDocentes(int sueldo){
        this.sueldo=sueldo ;
    }

    public int getSueldo() {
        return sueldo;
    }
}
