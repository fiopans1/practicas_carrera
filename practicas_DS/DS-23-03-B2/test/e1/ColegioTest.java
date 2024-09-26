package e1;

import e1.pers.Docente;
import e1.pers.ProfesionesDocentes;
import e1.resi.Estudiante;
import e1.resi.Fantasma;

import static org.junit.jupiter.api.Assertions.*;
import e1.*;
import e1.resi.*;
import e1.pers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
class ColegioTest {


    @Test
    void ExcepcionesañadirElementosEstudiantesyFantasmas() {
        //test crear estudiantes y fantasmas
        assertThrows(IllegalArgumentException.class, () -> new Estudiante(null, "apellido", 12, 2, Residentes.Casas.Gryffindor));
        assertThrows(IllegalArgumentException.class, () -> new Estudiante("nombre", null, 12, 2, Residentes.Casas.Gryffindor));
        assertThrows(IllegalArgumentException.class, () -> new Estudiante("nombre", "apellido", -1, 2, Residentes.Casas.Gryffindor));
        assertThrows(IllegalArgumentException.class, () -> new Estudiante("nombre", "apellido", 12, -4, Residentes.Casas.Gryffindor));
        assertThrows(IllegalArgumentException.class, () -> new Estudiante("nombre", "apellido", 12, -4, null));
        assertThrows(IllegalArgumentException.class, () -> new Estudiante("nombre", "apellido", 12, 4, null));
        assertThrows(IllegalArgumentException.class, () -> new Estudiante(null, null, -12, -4, null));
        assertThrows(IllegalArgumentException.class, () -> new Fantasma(null, "apellido", 12, 2, Residentes.Casas.Gryffindor));
        assertThrows(IllegalArgumentException.class, () -> new Fantasma("nombre", null, 12, 2, Residentes.Casas.Gryffindor));
        assertThrows(IllegalArgumentException.class, () -> new Fantasma("nombre", "apellido", -1, 2, Residentes.Casas.Gryffindor));
        assertThrows(IllegalArgumentException.class, () -> new Fantasma("nombre", "apellido", 12, -4, Residentes.Casas.Gryffindor));
        assertThrows(IllegalArgumentException.class, () -> new Fantasma("nombre", "apellido", 12, -4, null));
        assertThrows(IllegalArgumentException.class, () -> new Fantasma(null, null, -12, -4, null));

    }
    @Test
    void ExcepcionesañadirElementosGuardabosquesyConserjes() {
        //crear guardabosques y conserjes
        assertThrows(IllegalArgumentException.class, () -> new Guardabosques(null, "apellido", 12, 2, 2));
        assertThrows(IllegalArgumentException.class, () -> new Guardabosques("nombre", null, 12, 2, 2));
        assertThrows(IllegalArgumentException.class, () -> new Guardabosques("nombre", "apellido", -8, 2, 2));
        assertThrows(IllegalArgumentException.class, () -> new Guardabosques("nombre", "apellido", 12, -5, 2));
        assertThrows(IllegalArgumentException.class, () -> new Guardabosques("nombre", "apellido", 12, 2, -2));
        assertThrows(IllegalArgumentException.class, () -> new Guardabosques(null, null, -12, -2, -2));
        assertThrows(IllegalArgumentException.class, () -> new Conserje(null, "apellido", 12, 2, 2));
        assertThrows(IllegalArgumentException.class, () -> new Conserje("nombre", null, 12, 2, 2));
        assertThrows(IllegalArgumentException.class, () -> new Conserje("nombre", "apellido", -8, 2, 2));
        assertThrows(IllegalArgumentException.class, () -> new Conserje("nombre", "apellido", 12, -5, 2));
        assertThrows(IllegalArgumentException.class, () -> new Conserje("nombre", "apellido", 12, 2, -2));
        assertThrows(IllegalArgumentException.class, () -> new Conserje(null, null, -12, -2, -2));
    }
    @Test
    void ExcepcionesañadirElementosDocentes(){
        //Docentes
        assertThrows(IllegalArgumentException.class,()->new Docente(null,"apellido",12,6, ProfesionesDocentes.HERBOLOGIA));
        assertThrows(IllegalArgumentException.class,()->new Docente("nombre",null,12,6, ProfesionesDocentes.HERBOLOGIA));
        assertThrows(IllegalArgumentException.class,()->new Docente("nombre","apellido",-12,6, ProfesionesDocentes.HERBOLOGIA));
        assertThrows(IllegalArgumentException.class,()->new Docente("nombre","apellido",12,-6, ProfesionesDocentes.HERBOLOGIA));
    }
    @Test
    void testGetsEstudianteyFantasma() {
        Estudiante est1 = new Estudiante("name", "apellido", 20, 2, Residentes.Casas.Gryffindor);
        Fantasma fans1 = new Fantasma("name", "apellido", 20, 2, Residentes.Casas.Gryffindor);
        Estudiante est2 = new Estudiante("name1", "apellido1", 20, 2, Residentes.Casas.Slytherin);
        Fantasma fans2 = new Fantasma("name1", "apellido1", 20, 2, Residentes.Casas.Slytherin);
        assertEquals("name", est1.getNombre());
        assertEquals("apellido", est1.getApellido());
        assertEquals(20, est1.getEdad());
        assertEquals(2, est1.getHorrocruxs());
        assertEquals(Residentes.Casas.Gryffindor, est1.getCasa());
        assertEquals("name", fans1.getNombre());
        assertEquals("apellido", fans1.getApellido());
        assertEquals(20, fans1.getEdad());
        assertEquals(2, fans1.getHorrocruxs());
        assertEquals(Residentes.Casas.Gryffindor, fans1.getCasa());
        assertEquals(Residentes.Casas.Gryffindor, fans1.getCasa());
        assertEquals(180, est1.ngetRecompensa());
        assertEquals(360, est2.ngetRecompensa());
        assertEquals(160, fans1.ngetRecompensa());
        assertEquals(320, fans2.ngetRecompensa());
        assertEquals("name apellido(Fastasma de Gryffindor,2 horrocruxes):160 galeones\n", fans1.getRecompensa());
        assertEquals("name1 apellido1(Fastasma de Slytherin,2 horrocruxes):320 galeones\n", fans2.getRecompensa());
        assertEquals("name apellido(Estudiante de Gryffindor,2 horrocruxes):180 galeones\n", est1.getRecompensa());
        assertEquals("name1 apellido1(Estudiante de Slytherin,2 horrocruxes):360 galeones\n", est2.getRecompensa());
    }
    @Test
    void testGetsConserjeyGyardabosques() {
        //conserje y guardabosques
        Conserje cons = new Conserje("name", "apellido", 20, 2, 2);
        Guardabosques guarda = new Guardabosques("name", "apellido", 20, 2, 2);
        assertEquals("name", cons.getNombre());
        assertEquals("apellido", cons.getApellido());
        assertEquals(20, cons.getEdad());
        assertEquals(2, cons.getHorrocruxs());
        assertEquals("name", guarda.getNombre());
        assertEquals("apellido", guarda.getApellido());
        assertEquals(20, guarda.getEdad());
        assertEquals(2, guarda.getHorrocruxs());
        assertEquals("name apellido(Guardabosques): 190 galeones\n", guarda.getSueldo());
        assertEquals("name apellido(Conserje): 170 galeones\n", cons.getSueldo());
        assertEquals(190, guarda.ngetSueldo());
        assertEquals(170, cons.ngetSueldo());
        assertEquals(130, cons.ngetRecompensa());
        assertEquals(150, guarda.ngetRecompensa());
        assertEquals("name apellido(Conserje,2 horrocruxes):130 galeones\n", cons.getRecompensa());
        assertEquals("name apellido(Guardabosques,2 horrocruxes):150 galeones\n", guarda.getRecompensa());
    }
    @Test
    void testDocentes() {
        //docente
        Docente donc1=new Docente("name","apellido",20,2,ProfesionesDocentes.DEFENSA);
        Docente donc2=new Docente("name","apellido",20,2,ProfesionesDocentes.HERBOLOGIA);
        assertEquals("name",donc1.getNombre());
        assertEquals("apellido",donc1.getApellido());
        assertEquals(20,donc1.getEdad());
        assertEquals(2,donc1.getHorrocruxs());
        assertEquals(75,donc1.ngetRecompensa());
        assertEquals(100,donc2.ngetRecompensa());
        assertEquals(500,donc1.ngetSueldo());
        assertEquals("name apellido(Docente de DEFENSA,2 horrocruxes):75 galeones\n",donc1.getRecompensa());
        assertEquals("name apellido(Docente de HERBOLOGIA,2 horrocruxes):100 galeones\n",donc2.getRecompensa());
        assertEquals("name apellido(Docente de DEFENSA): 500 galeones\n",donc1.getSueldo());


    }
    @Test
    void probarColegio(){
        Colegio cole= new Colegio();
        Estudiante est1= new Estudiante("name1", "apellido", 20, 2, Residentes.Casas.Gryffindor);
        Docente donc1=new Docente("name2","apellido",20,2,ProfesionesDocentes.DEFENSA);
        Fantasma fans1 = new Fantasma("name3", "apellido", 20, 2, Residentes.Casas.Gryffindor);
        Conserje cons = new Conserje("name4", "apellido", 20, 2, 2);
        Guardabosques guarda = new Guardabosques("name5", "apellido", 20, 2, 2);
        assertThrows(IllegalArgumentException.class,()->cole.añadirResidente(null));
        assertThrows(IllegalArgumentException.class,()->cole.añadirPersonal(null));
        cole.añadirPersonal(cons);
        cole.añadirPersonal(donc1);
        cole.añadirPersonal(guarda);
        cole.añadirResidente(est1);
        cole.añadirResidente(fans1);
        assertEquals("name1 apellido(Estudiante de Gryffindor,2 horrocruxes):180 galeones\n" +
                "name3 apellido(Fastasma de Gryffindor,2 horrocruxes):160 galeones\n" +
                "name4 apellido(Conserje,2 horrocruxes):130 galeones\n" +
                "name2 apellido(Docente de DEFENSA,2 horrocruxes):75 galeones\n" +
                "name5 apellido(Guardabosques,2 horrocruxes):150 galeones\n" +
                "La recompensa total del Colegio Hogwarts es de 695 galeones\n",cole.imprimirRecompensa());
        assertEquals("name4 apellido(Conserje): 170 galeones\n" +
                "name2 apellido(Docente de DEFENSA): 500 galeones\n" +
                "name5 apellido(Guardabosques): 190 galeones\n" +
                "El gasto de Hogwarts en personal es de 860 galeones\n",cole.imprimirSalarios());



    }

    @Test
    void probarEqualsyHash(){
        Estudiante est1= new Estudiante("name1", "apellido", 20, 2, Residentes.Casas.Gryffindor);
        Estudiante est2= new Estudiante("name1", "apellido", 20, 2, Residentes.Casas.Gryffindor);
        Estudiante est3= new Estudiante("name2", "apellido1", 200, 20, Residentes.Casas.Hufflepuff);
        assertEquals(est1,est2);
        assertEquals(est1.hashCode(),est2.hashCode());
        assertNotEquals(est1,est3);
        assertNotEquals(est1.hashCode(),est3.hashCode());

    }

}