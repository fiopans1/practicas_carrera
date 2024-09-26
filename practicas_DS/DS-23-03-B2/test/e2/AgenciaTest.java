package e2;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import e2.comparadores.*;

import static org.junit.jupiter.api.Assertions.*;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
class AgenciaTest {
    @Test
    void testBasicoPiso(){
        assertThrows(IllegalArgumentException.class,()->new Piso(-232,5000,65,45,4,"Piso Grande",1));
        assertThrows(IllegalArgumentException.class,()->new Piso(-232,-5000,65,45,4,"Piso Grande",1));
        assertThrows(IllegalArgumentException.class,()->new Piso(-232,5000,-65,45,4,"Piso Grande",1));
        assertThrows(IllegalArgumentException.class,()->new Piso(-232,5000,65,-45,4,"Piso Grande",1));
        assertThrows(IllegalArgumentException.class,()->new Piso(-232,5000,65,-45,0,"Piso Grande",1));
        assertThrows(IllegalArgumentException.class,()->new Piso(-232,5000,65,-45,0,null,1));
        assertThrows(IllegalArgumentException.class,()->new Piso(-232,5000,65,-45,0,"Piso Grande",-11));
        Piso e1=new Piso(2433,5000,65,45,4,"Piso Grande",1);
        assertEquals(2433,e1.getNumreferencia());
        assertEquals(5000,e1.getPreciobase());
        assertEquals(65,e1.getPrecioGaraje());
        assertEquals(5065,e1.precioTotal());
        assertEquals(4,e1.getCodigoPostal());
        assertEquals("Piso Grande", e1.getTitulo());
        Piso e2=new Piso(32,1,21,74,4,"Piso distinto",1);
        assertEquals("TituloAnuncio: Piso distinto - NumRef: 32(CP:4)  - PrecioT:22€(1€ base +21*1€ garaje) - 74m^2\n",e2.getmostrarPiso());
        Agencia agencia=new Agencia("BuscoPisos");
        assertThrows(IllegalArgumentException.class,()->agencia.añadirPisos(null));
    }

    @Test
    void Equalstest(){
        Piso e1=new Piso(2433,5000,65,45,4,"Piso Grande",1);
        Piso e2=new Piso(2650,5000,65,45,4,"Piso pequeño",1);
        Piso e3=new Piso(32,1,21,74,4,"Piso distinto",1);
        assertEquals(e1,e2);
        assertNotEquals(null,e1);
        assertNotEquals("HOLA",e1);
        assertNotEquals(e1,e3);

    }

    @Test
    void hasCodetest(){
        Piso e1=new Piso(2433,5000,65,45,4,"Piso Grande",1);
        Piso e2=new Piso(2650,5000,65,45,4,"Piso pequeño",1);
        Piso e3=new Piso(32,1,21,74,4,"Piso distinto",1);
        assertEquals(e1.hashCode(),e2.hashCode());
        assertNotEquals(e1.hashCode(),e3.hashCode());

    }
    @Test
    void comparadorTest(){
        Piso e1=new Piso(2433,5005,75,105,4,"Piso Grande",1);
        Piso e2=new Piso(2650,5000,15,45,4,"Piso pequeño",1);
        Piso e3=new Piso(32,1,21,74,4,"Piso distinto",1);
        Comparator<Piso> precioBase= new PrecioBase();
        Comparator<Piso> precioTotal= new PrecioTotal();
        Comparator<Piso> precioGaraje= new PrecioGaraje();
        Comparator<Piso> metrosCuadrados= new MetrosCuadrados();
        //creamos agencia y le metemos los valores
        Agencia agen= new Agencia("MiAgencia");
        agen.añadirPisos(e1);
        agen.añadirPisos(e2);
        agen.añadirPisos(e3);
        //creamos una lista para cada uno de los casos y la ordenamos a mano
        //lista ordenada preciobase
        List<Piso> base=new ArrayList<>();
        base.add(e3);
        base.add(e2);
        base.add(e1);
        //lista ordenada preciogaraje
        List<Piso> garaje=new ArrayList<>();
        garaje.add(e2);
        garaje.add(e3);
        garaje.add(e1);
        //lista orndenada preciototal
        List<Piso> total=new ArrayList<>();
        total.add(e3);
        total.add(e2);
        total.add(e1);
        //lista ordenada metroscuadrados
        List<Piso> metros=new ArrayList<>();
        metros.add(e2);
        metros.add(e3);
        metros.add(e1);
        List<Piso> numre=new ArrayList<>();
        numre.add(e3);
        numre.add(e1);
        numre.add(e2);
        Agencia agencia=new Agencia("MiAgencia");
        agencia.añadirPisos(e1);
        agencia.añadirPisos(e2);
        agencia.añadirPisos(e3);
        assertEquals(numre,agencia.listaPisos());
        //numero referencia
        assertEquals("TituloAnuncio: Piso distinto - NumRef: 32(CP:4)  - PrecioT:22€(1€ base +21*1€ garaje) - 74m^2\n" +
                "TituloAnuncio: Piso Grande - NumRef: 2433(CP:4)  - PrecioT:5080€(5005€ base +75*1€ garaje) - 105m^2\n" +
                "TituloAnuncio: Piso pequeño - NumRef: 2650(CP:4)  - PrecioT:5015€(5000€ base +15*1€ garaje) - 45m^2\n",agencia.mostrarPisos());
        assertEquals(numre,agencia.listaPisos());
        //precio base
        agencia.ordenarPorCriterio(precioBase);
        assertEquals("TituloAnuncio: Piso distinto - NumRef: 32(CP:4)  - PrecioT:22€(1€ base +21*1€ garaje) - 74m^2\n" +
                "TituloAnuncio: Piso pequeño - NumRef: 2650(CP:4)  - PrecioT:5015€(5000€ base +15*1€ garaje) - 45m^2\n" +
                "TituloAnuncio: Piso Grande - NumRef: 2433(CP:4)  - PrecioT:5080€(5005€ base +75*1€ garaje) - 105m^2\n",agencia.mostrarPisos());
        assertEquals(base,agencia.listaPisos());
        //precio total
        agencia.ordenarPorCriterio(precioTotal);
        assertEquals("TituloAnuncio: Piso distinto - NumRef: 32(CP:4)  - PrecioT:22€(1€ base +21*1€ garaje) - 74m^2\n" +
                "TituloAnuncio: Piso pequeño - NumRef: 2650(CP:4)  - PrecioT:5015€(5000€ base +15*1€ garaje) - 45m^2\n" +
                "TituloAnuncio: Piso Grande - NumRef: 2433(CP:4)  - PrecioT:5080€(5005€ base +75*1€ garaje) - 105m^2\n",agencia.mostrarPisos());
        assertEquals(total,agencia.listaPisos());
        //precio garaje
        agencia.ordenarPorCriterio(precioGaraje);
        assertEquals("TituloAnuncio: Piso pequeño - NumRef: 2650(CP:4)  - PrecioT:5015€(5000€ base +15*1€ garaje) - 45m^2\n" +
                "TituloAnuncio: Piso distinto - NumRef: 32(CP:4)  - PrecioT:22€(1€ base +21*1€ garaje) - 74m^2\n" +
                "TituloAnuncio: Piso Grande - NumRef: 2433(CP:4)  - PrecioT:5080€(5005€ base +75*1€ garaje) - 105m^2\n",agencia.mostrarPisos());
        assertEquals(garaje,agencia.listaPisos());
        //metros cuadrados
        agencia.ordenarPorCriterio(metrosCuadrados);
        assertEquals("TituloAnuncio: Piso pequeño - NumRef: 2650(CP:4)  - PrecioT:5015€(5000€ base +15*1€ garaje) - 45m^2\n" +
                "TituloAnuncio: Piso distinto - NumRef: 32(CP:4)  - PrecioT:22€(1€ base +21*1€ garaje) - 74m^2\n" +
                "TituloAnuncio: Piso Grande - NumRef: 2433(CP:4)  - PrecioT:5080€(5005€ base +75*1€ garaje) - 105m^2\n",agencia.mostrarPisos());
        assertEquals(metros,agencia.listaPisos());
        //por defecto(numref)
        agencia.ordenarPorCriterio(null);
        assertEquals("TituloAnuncio: Piso distinto - NumRef: 32(CP:4)  - PrecioT:22€(1€ base +21*1€ garaje) - 74m^2\n" +
                "TituloAnuncio: Piso Grande - NumRef: 2433(CP:4)  - PrecioT:5080€(5005€ base +75*1€ garaje) - 105m^2\n" +
                "TituloAnuncio: Piso pequeño - NumRef: 2650(CP:4)  - PrecioT:5015€(5000€ base +15*1€ garaje) - 45m^2\n",agencia.mostrarPisos());
        assertEquals(numre,agencia.listaPisos());

    }

}