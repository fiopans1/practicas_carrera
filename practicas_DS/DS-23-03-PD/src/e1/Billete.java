package e1;

import java.time.LocalDate;
import java.util.Objects;

public final class Billete {
    private final String origen;
    private final String destino;
    private final float precio;
    private final LocalDate fecha;
    Billete(String origen, String destino, float precio, LocalDate fecha){
        if(origen==null || destino==null || fecha==null || precio<0){
            throw new IllegalArgumentException("Argumento no valido");
        }else {
            this.origen = origen;
            this.destino = destino;
            this.precio = precio;
            this.fecha = fecha;
        }
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public float getPrecio() {
        return precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Billete billete = (Billete) o;
        return Float.compare(billete.precio, precio) == 0 && origen.equals(billete.origen) && destino.equals(billete.destino) && fecha.equals(billete.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origen, destino, precio, fecha);
    }

    @Override
    public String toString() {
        StringBuilder s=new StringBuilder();
        s.append("Billete{").append(
                "origen='").append( origen).append( '\'').append(
                ", destino='").append( destino ).append( '\'').append(
                ", precio=").append( precio).append(
                ", fecha=").append( fecha).append(
                '}');
        return s.toString();
    }
}

