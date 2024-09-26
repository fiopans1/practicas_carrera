package e1.filtros;

import e1.Billete;
import e1.Criterios;

public final class Precio implements Criterios{
    private final float precio;

    public Precio(float precio) {
        this.precio = precio;
    }

    public float getPrecio() {
        return precio;
    }


    @Override
    public boolean cumpleCriterio(Billete o) {
        if(o.getPrecio()<=precio){
            return true;
        }else{
            return false;
        }
    }
}
