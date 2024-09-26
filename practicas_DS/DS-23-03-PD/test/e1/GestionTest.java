package e1;
import e1.filtros.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionTest {
    @Test
    void exceptions() {
        //miramos los exceptions
        assertThrows(IllegalArgumentException.class,()->new Billete(null,"orense", 7, LocalDate.of(2021, 12, 1)));
        assertThrows(IllegalArgumentException.class,()->new Billete("Coruña", null, 7, LocalDate.of(2021, 12, 1)));
        assertThrows(IllegalArgumentException.class,()->new Billete("Coruña","orense", -7, LocalDate.of(2021, 12, 1)));
        assertThrows(IllegalArgumentException.class,()->new Billete("Coruña","orense", 7, null));
        Gestion gestoria = new Gestion();
        assertThrows(IllegalArgumentException.class,()->gestoria.addViaje(null));
    }
    @Test
    void getters() {
        //miramos que funcionen bien todos los getters
        Billete b1 = new Billete("Coruña", "Ourense", 7, LocalDate.of(2021, 12, 1));
        assertEquals("Coruña",b1.getOrigen());
        assertEquals("Ourense",b1.getDestino());
        assertEquals(7,b1.getPrecio());
        assertEquals(LocalDate.of(2021, 12, 1),b1.getFecha());
        assertEquals("Billete{origen='Coruña', destino='Ourense', precio=7.0, fecha=2021-12-01}",b1.toString());

        Origen origen1=new Origen("Santiago");
        assertEquals("Santiago",origen1.getOrigen());

        Destino dest1=new Destino("Ourense");
        assertEquals("Ourense",dest1.getDestino());

        Precio prec1=new Precio(15);
        assertEquals(15,prec1.getPrecio());

        Fecha fe1=new Fecha(LocalDate.of(2021, 12, 1));
        assertEquals(LocalDate.of(2021, 12, 1),fe1.getFecha());
    }
    @Test
    void equals() {
        //miramos que se distinga bien cuando dos elementos son iguales o diferentes
        Billete b1 = new Billete("Coruña", "Ourense", 7, LocalDate.of(2021, 12, 1));
        Billete b2 = new Billete("Coruña", "Ourense", 7, LocalDate.of(2021, 12, 1));
        Billete b3 = new Billete("Santiago", "Ourense", 25, LocalDate.of(2021, 12, 1));
        assertEquals(b1,b2);
        assertNotEquals(b1,b3);
    }
    @Test
    void hasCodetest(){
        //en funcion de si son iguales o diferentes que el hashcode lo acompañe
        Billete b1 = new Billete("Coruña", "Ourense", 7, LocalDate.of(2021, 12, 1));
        Billete b2 = new Billete("Coruña", "Ourense", 7, LocalDate.of(2021, 12, 1));
        Billete b3 = new Billete("Santiago", "Ourense", 25, LocalDate.of(2021, 12, 1));
        assertEquals(b1.hashCode(),b2.hashCode());
        assertNotEquals(b1.hashCode(),b3.hashCode());

    }
    @Test
    void filtrado() {
        //miramos que funcione bien el filtrado
        List<Billete> lista;
        List<Billete> lista1=new ArrayList<>();
        Billete b1 = new Billete("Coruña", "Ourense", 7, LocalDate.of(2021, 12, 1));
        Billete b2 = new Billete("Santiago", "Ourense", 25, LocalDate.of(2021, 12, 1));
        Billete b3 = new Billete("Pontevedra", "Ourense", 7, LocalDate.of(2021, 12, 1));
        Billete b4 = new Billete("Madrid", "Ourense", 7, LocalDate.of(2021, 12, 1));
        Billete b5 = new Billete("Asturias", "Ourense", 7, LocalDate.of(2021, 12, 1));
        Billete b6 = new Billete("Vigo", "Ourense", 7, LocalDate.of(2021, 12, 1));
        Billete b7 = new Billete("Coruña", "Pontevedra", 7, LocalDate.of(2021, 12, 1));
        Billete b8 = new Billete("Santiago", "Ourense", 7, LocalDate.of(2021, 12, 3));
        Gestion gestoria = new Gestion();
        gestoria.addViaje(b1);
        gestoria.addViaje(b2);
        gestoria.addViaje(b3);
        gestoria.addViaje(b4);
        gestoria.addViaje(b5);
        gestoria.addViaje(b6);
        gestoria.addViaje(b7);
        gestoria.addViaje(b8);
        //filtrar
        //ORIGEN CORUÑA Y SANTIAGO
        Criterios origen1=new Origen("Santiago");
        Criterios origen2=new Origen("Coruña");
        Criterios combi1=new OR(origen1,origen2);
        //ANTERIOR CON DESTINO ORENSE
        Criterios dest1=new Destino("Ourense");
        Criterios combi2=new AND(combi1,dest1);
        //ANTERIOR CON PRECIO 15
        Criterios prec1=new Precio(15);
        Criterios combi3=new AND(combi2,prec1);
        //OR FECHAS
        Criterios fe1=new Fecha(LocalDate.of(2021, 12, 1));
        Criterios fe2=new Fecha(LocalDate.of(2021, 12, 2));
        Criterios combi4=new OR(fe1,fe2);
        //combinado final
        Criterios fin=new AND(combi3, combi4);
        lista=gestoria.filtrar(fin);
        lista1.add(b1);
        assertEquals(lista1,lista);




    }




}