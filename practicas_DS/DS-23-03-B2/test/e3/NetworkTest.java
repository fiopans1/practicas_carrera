package e3;

//import jdk.incubator.foreign.MappedMemorySegments;
import org.junit.jupiter.api.Test;
import e3.*;

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import e3.*;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
class NetworkTest {
    //AMBOS TEST SON EXACTAMENTE IGUALES, LO UNICO QUE CAMBIA ES QUE UNO SE INICIALIZA COMO MAPMANAGER Y OTRO COMO TABLAMANAGER

    @Test
     void testMapa() {
        NetworkManager o1=new MapManager();
       Network netw = new Network(o1);
       List<TopicOfInterest> intereses = new ArrayList<>();
       intereses.add(TopicOfInterest.ENTRETENIMIENTO);
       intereses.add(TopicOfInterest.SOCIAL);
       intereses.add(TopicOfInterest.VIDEOJUEGO);
       //test fallos
       assertThrows(IllegalArgumentException.class,()->netw.añadirUsuarios(null,intereses));
       assertThrows(IllegalArgumentException.class,()->netw.añadirUsuarios("juan",null));
       assertThrows(IllegalArgumentException.class,()->netw.añadirTemas(null,null));
       //xtema
       netw.añadirUsuarios("Juan",intereses);
       intereses.add(TopicOfInterest.BELLEZA);
       netw.añadirUsuarios("Manolo",intereses);
       List<String> usersnames=new ArrayList<>();
       usersnames.add("Manolo");
       usersnames.add("Juan");
       assertEquals(usersnames,netw.usuariosInteresadosXTema(TopicOfInterest.ENTRETENIMIENTO));
       //eliminado

       netw.eliminarTemas("Manolo",TopicOfInterest.BELLEZA);
       intereses.remove(TopicOfInterest.BELLEZA);
       assertEquals(intereses,netw.obtenerIntereses());
       usersnames.remove("Manolo");
       netw.eliminarUsuario("Manolo");
       assertEquals(usersnames,netw.obtenerUsuarios());
       //añadir
       netw.eliminarUsuario("Juan");
       List<String> usersnames1=new ArrayList<>();
       assertEquals(usersnames1,netw.obtenerUsuarios());
       netw.añadirUsuarios("Juan",intereses);
       assertEquals(usersnames,netw.obtenerUsuarios());
       assertEquals(intereses,netw.obtenerIntereses());
       intereses.add(TopicOfInterest.BELLEZA);
       netw.añadirTemas("Juan",TopicOfInterest.BELLEZA);
       assertEquals(intereses,netw.obtenerIntereses());
       //comunes

       intereses.add(TopicOfInterest.BELLEZA);
       netw.añadirUsuarios("Manolo",intereses);
       intereses.remove(TopicOfInterest.BELLEZA);
       assertEquals(intereses,netw.interesesComunes("Juan","Manolo"));
       //obtener
        assertEquals(intereses,netw.obtenerTemas("Juan"));
        //ver info
        assertEquals("Manolo(ENTRETENIMIENTO/SOCIAL/VIDEOJUEGO/BELLEZA/)\n" +
                "Juan(ENTRETENIMIENTO/SOCIAL/VIDEOJUEGO/BELLEZA/)\n",netw.verInfo());




    }
   @Test
   void testTable() {
      NetworkManager o1=new TablaManager();
      Network netw = new Network(o1);
      List<TopicOfInterest> intereses = new ArrayList<>();
      intereses.add(TopicOfInterest.ENTRETENIMIENTO);
      intereses.add(TopicOfInterest.SOCIAL);
      intereses.add(TopicOfInterest.VIDEOJUEGO);
      //test fallos
      assertThrows(IllegalArgumentException.class,()->netw.añadirUsuarios(null,intereses));
      assertThrows(IllegalArgumentException.class,()->netw.añadirUsuarios("juan",null));
      assertThrows(IllegalArgumentException.class,()->netw.añadirTemas(null,null));
      //xtema
      netw.añadirUsuarios("Juan",intereses);
      intereses.add(TopicOfInterest.BELLEZA);
      netw.añadirUsuarios("Manolo",intereses);
      List<String> usersnames=new ArrayList<>();
      usersnames.add("Juan");
      usersnames.add("Manolo");
      assertEquals(usersnames,netw.usuariosInteresadosXTema(TopicOfInterest.ENTRETENIMIENTO));
      //eliminado

      netw.eliminarTemas("Manolo",TopicOfInterest.BELLEZA);
      intereses.remove(TopicOfInterest.BELLEZA);
      assertEquals(intereses,netw.obtenerIntereses());
      usersnames.remove("Manolo");
      netw.eliminarUsuario("Manolo");
      assertEquals(usersnames,netw.obtenerUsuarios());
      //añadir
      netw.eliminarUsuario("Juan");
      List<String> usersnames1=new ArrayList<>();
      assertEquals(usersnames1,netw.obtenerUsuarios());
      netw.añadirUsuarios("Juan",intereses);
      assertEquals(usersnames,netw.obtenerUsuarios());
      assertEquals(intereses,netw.obtenerIntereses());
      intereses.add(TopicOfInterest.BELLEZA);
      netw.añadirTemas("Juan",TopicOfInterest.BELLEZA);
      assertEquals(intereses,netw.obtenerIntereses());
      //comunes

      intereses.add(TopicOfInterest.BELLEZA);
      netw.añadirUsuarios("Manolo",intereses);
      intereses.remove(TopicOfInterest.BELLEZA);
      assertEquals(intereses,netw.interesesComunes("Juan","Manolo"));
      //obtener
       assertEquals(intereses,netw.obtenerTemas("Juan"));
       //ver info
       assertEquals("Juan(ENTRETENIMIENTO/SOCIAL/VIDEOJUEGO/BELLEZA/)\n" +
               "Manolo(ENTRETENIMIENTO/SOCIAL/VIDEOJUEGO/BELLEZA/)\n",netw.verInfo());




   }
    @Test
    void testCompararLosDos() {
        //PARA PROBAR ESTE TEST TENEMOS QUE TENER EN CUENTA QUE LA TABLAMANAGER TIENE UN ORDEN DEFINIDO EN LA LISTA
        //DE LOS ELEMENTOS DE TOPICOFINTEREST, ASI QUE EL ORDEN DE AÑADIR A INTERESES EN EL TEST TIENE QUE SER IGUAL AL ORDEN DE LA
        //LISTA DEFINIDA EN TABLAMANAGER, SINO OTRA OPCION SERÍA HACER UN FOR PARA VER QUE LAS DOS LISTAS TIENEN LOS MISMOS
        //ELEMENTOS PERO EN DIFERENTES ORDENES DEPENDIENDO DE LA IMPLEMENTACION
        NetworkManager o1=new MapManager();
        NetworkManager o2=new TablaManager();
        Network netwMap = new Network(o1);
        Network netwTabla= new Network(o2);
        List<TopicOfInterest> intereses = new ArrayList<>();
        intereses.add(TopicOfInterest.ENTRETENIMIENTO);
        intereses.add(TopicOfInterest.SOCIAL);
        intereses.add(TopicOfInterest.VIDEOJUEGO);
        intereses.add(TopicOfInterest.EDUCACION);
        intereses.add(TopicOfInterest.ANIMALES);
        intereses.add(TopicOfInterest.PROGRAMACION);
        netwMap.añadirUsuarios("Juan", intereses);
        netwTabla.añadirUsuarios("Juan", intereses);
        assertEquals(netwMap.obtenerTemas("Juan"), netwTabla.obtenerTemas("Juan"));

    }



}