package e2;

import e1.Billete;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class   TareasTest {

    @Test
    void test() {
        Tareas o1=new Tareas();
        //probamos que funcione las excepciones a la hora de leer un fichero:
        assertThrows(IllegalArgumentException.class,()->o1.anadirTareas(null));
        //aádimos el arbol
        o1.anadirTareas("tareas.txt");
        //comprobamos que se añadiera bien
        assertEquals("A->([B, D]\n" +
                "B->([E]\n" +
                "C->([A, F]\n" +
                "D->([E]\n" +
                "F->([E, J]\n" +
                "G->([F, H]\n" +
                "H->([J]\n",o1.printSonPadres());
        assertEquals("A->([C]\n" +
                "B->([A]\n" +
                "D->([A]\n" +
                "E->([B, D, F]\n" +
                "F->([C, G]\n" +
                "H->([G]\n" +
                "J->([F, H]\n",o1.printSonHijos());
        assertEquals("[A, B, C, D, E, F, G, H, J]",o1.printNodos());
        //comprobamos que funcionen los criterios de ordenacion
        OrdenRealizacionTareas fuerte1=new Fuerte1(o1.getNodos(),o1.getSonPadres(),o1.getSonHijos());
        assertEquals("[C, A, B, D, G, F, E, H, J]",o1.realizar(fuerte1));
        OrdenRealizacionTareas debil1=new Debil1(o1.getNodos(),o1.getSonPadres(),o1.getSonHijos());
        assertEquals("[C, A, B, D, E, F, G, H, J]",o1.realizar(debil1));
        OrdenRealizacionTareas jerarquico1=new Jerarquico1(o1.getNodos(),o1.getSonPadres(),o1.getSonHijos());
        assertEquals("[C, G, A, F, H, B, D, E, J]",o1.realizar(jerarquico1));
    }
}
