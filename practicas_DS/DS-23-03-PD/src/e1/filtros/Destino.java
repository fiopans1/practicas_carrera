package e1.filtros;

import e1.Billete;
import e1.Criterios;

public final class Destino implements Criterios {
    private final String destino;

    public Destino(String destino) {
        this.destino = destino;
    }

    public String getDestino() {
        return destino;
    }


    @Override
    public boolean cumpleCriterio(Billete o) {
        return o.getDestino().equals(destino);
    }
}
