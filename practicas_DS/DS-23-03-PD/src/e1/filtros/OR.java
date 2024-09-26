package e1.filtros;

import e1.Billete;
import e1.Criterios;

public final class OR implements Criterios {
    private final Criterios e1, e2;
    public OR(Criterios e1,Criterios e2){
        this.e1=e1;
        this.e2=e2;
    }


    @Override
    public boolean cumpleCriterio(Billete o) {
        return (e1.cumpleCriterio(o) || e2.cumpleCriterio(o));
    }
}
