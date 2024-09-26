package e1.filtros;

import e1.Billete;
import e1.Criterios;

import java.time.LocalDate;

public final class Fecha implements Criterios{
    private final LocalDate fecha;

    public Fecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }


    @Override
    public boolean cumpleCriterio(Billete o) {
        return o.getFecha().equals(fecha);
    }
}
