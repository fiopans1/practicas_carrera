package e2;

import java.util.*;

public final class Tareas {
    private final Map<Character, List<Character>> sonPadres;
    /*sonPadres guarda todos los padres como claves y una lista
    * con sus hijos de cada uno*/
    private final Map<Character, List<Character>> sonHijos;
    /*sonHijos guarda todos los hijos como claves y una lista
    * con sus padres de cada uno*/
    private final List<Character> nodos;
    Tareas(){
        sonPadres=new TreeMap<>();
        sonHijos=new TreeMap<>();
        nodos= new ArrayList<>();
    }
    public void anadirTareas(String direccion){
        LeerArchivo o=new LeerArchivo();
        String o1=o.leerTxt(direccion);
        List<Character> list;
        char p,h;
        for(int i=0;i<o1.length();i+=6){
            p=o1.charAt(i);
            h=o1.charAt(i+5);
            if(sonPadres.containsKey(p)){
                list=sonPadres.get(p);
                list.add(h);
                sonPadres.replace(p,list);
            }else{
                list=new ArrayList<>();
                list.add(h);
                sonPadres.put(p,list);
            }
            if(sonHijos.containsKey(h)){
                list=sonHijos.get(h);
                list.add(p);
                sonHijos.replace(h,list);
            }else{
                list=new ArrayList<>();
                list.add(p);
                sonHijos.put(h,list);
            }
            if(!nodos.contains(p)){
                nodos.add(p);
            }
            if(!nodos.contains(h)){
                nodos.add(h);
            }

        }
        Collections.sort(nodos);
    }
    public String printSonPadres() {//imprime los diferentes padres con sus correspondienes hijos
        List<Character> padresList = new ArrayList<>(sonPadres.keySet());
        StringBuilder padres = new StringBuilder();
        String h;
        for (Character character : padresList) {
            h = sonPadres.get(character).toString();
            padres.append(character).append("->(").append(h).append("\n");

        }
        return padres.toString();
    }
    public String printSonHijos(){//imprime los diferentes hijos con sus correspondientes padres
        List<Character> hijosList = new ArrayList<>(sonHijos.keySet());
        StringBuilder hijos = new StringBuilder();
        String h;
        for (Character character : hijosList) {
            h = sonHijos.get(character).toString();
            hijos.append(character).append("->(").append(h).append("\n");

        }
        return hijos.toString();
    }
    public String printNodos(){
        return nodos.toString();
    }


    public List<Character> getNodos() {
        return nodos;
    }

    public Map<Character, List<Character>> getSonHijos() {
        return sonHijos;
    }

    public Map<Character, List<Character>> getSonPadres() {
        return sonPadres;
    }

    public String realizar(OrdenRealizacionTareas o){
        return o.ejecutar();

    }


}
