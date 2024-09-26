package e1.filtros;

import e1.Billete;
import e1.Criterios;

public final class Origen implements Criterios{
    private final String origen;

    public Origen(String origen) {
        this.origen = origen;
    }

    public String getOrigen() {
        return origen;
    }


    @Override
    public boolean cumpleCriterio(Billete o) {
        return o.getOrigen().equals(origen);
    }

}
