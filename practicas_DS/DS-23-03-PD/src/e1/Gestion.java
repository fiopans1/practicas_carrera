package e1;

import java.util.ArrayList;
import java.util.List;

public final class Gestion {
    private final List<Billete> listabilletes;


    Gestion(){
        listabilletes=new ArrayList<>();
    }
    public void addViaje(Billete o){
        if(o!=null){
            listabilletes.add(o);
        }else{
            throw new IllegalArgumentException("Argumento no valido");
        }
    }
    public List<Billete> filtrar(Criterios cri){
        List<Billete> filtro=new ArrayList<>();
        listabilletes.forEach(listabillete -> {
            if (cri.cumpleCriterio(listabillete)) {
                filtro.add(listabillete);

            }
        });
        return filtro;


    }


}
