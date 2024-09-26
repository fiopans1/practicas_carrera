package es.udc.sistemasinteligentes.gA_23_1.apartado2a;
import es.udc.sistemasinteligentes.gA_23_1.*;

import java.util.ArrayList;
import java.util.Objects;

public class ProblemaCuadradoMagico extends ProblemaBusqueda{
    public static class EstadoCuadrado extends Estado{
        private final int [][] estado;//para implementar el estado de manera interna usamos una matriz que pasaremos como
                                        //parametro del constructor
        public EstadoCuadrado(int [][] estado){
            this.estado=estado;

        }
        @Override
        public String toString() {//imprimimos la matriz
            StringBuilder sol=new StringBuilder();
            sol.append("\n");
            for(int i=0;i<estado.length;i++){
                sol.append("[");
                for(int j=0;j<estado[i].length;j++){
                    sol.append(estado[i][j]);
                    sol.append(" ");
                }
                sol.append("]\n");
            }
            return sol.toString();
        }

        public int[][] getEstado() {//devuelve la matriz
            return estado;
        }

        @Override
        public boolean equals(Object o) {//compara una matriz con otra
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EstadoCuadrado that = (EstadoCuadrado) o;
            for (int i = 0; i < estado.length; i++) {
                for (int j = 0; j < estado[i].length; j++) {
                    if (estado[i][j] != that.estado[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hash(estado);
        }//devuelve un hashcode en funcion de la matriz
    }
    public static class AccionCuadrado extends Accion{
        private final int x,y,valor;//para aplicar la accion simplemente nos quedamos co las coordenadas x,y y el
        public AccionCuadrado(int x,int y,int valor){
            this.x=x;
            this.y=y;
            this.valor=valor;
        }
        @Override
        public String toString() {
            return ("Casilla["+x+"]["+y+"]=>["+valor+"]");
        }

        @Override
        public boolean esAplicable(Estado es) {//miramos si es aplicable el estado(si la casilla donde vamos a meter el numero está a 0)
            EstadoCuadrado esAs= (EstadoCuadrado)es;
            if(esAs.estado[x][y]!=0){
                return false;

            }
            return true;
        }

        @Override
        public Estado aplicaA(Estado es) {//aplica el estado, primero haciendo una copia del estado actual(sino solo modificamos el actual) y modifica la copia
            EstadoCuadrado esAs= (EstadoCuadrado)es;
            int[][] estado= esAs.estado;
            int [][] est= new int[estado.length][estado.length];
            for(int i=0;i<estado.length;i++){
                for(int j=0;j<estado[i].length;j++){
                    est[i][j]=estado[i][j];
                }
            }
            est[x][y]=valor;
            return new EstadoCuadrado(est);
        }
    }

    public ProblemaCuadradoMagico(Estado estadoInicial) {
        super(estadoInicial);
    }//constructor del problema


    @Override
    public boolean esMeta(Estado es) {//comprobamos si una matriz es meta
        EstadoCuadrado esAs= (EstadoCuadrado)es;
        int [][] prub= esAs.estado;
        int diag=0,diag1=0;
        int cumplir=(((prub.length)*(prub.length*prub.length+1))/2);
        int z=prub.length;
        int limit= (int)Math.pow(prub.length,2);
        for(int i=0;i<prub.length;i++){//miramos que cada diagonal sume lo que tiene que dar
            diag+=prub[i][i];
        }
        if(diag!=cumplir){
            return false;
        }
        diag=0;
        for(int i=0;i<prub.length;i++){
            if(z>0){
                z--;
            }

            diag+=prub[z][i];
        }
        if(diag!=cumplir){
            return false;
        }

        for(int i=0;i<prub.length;i++){//miramos que cada uno de las filas y las columnas sume lo que tiene que dar
            diag=0;
            diag1=0;
            for(int j=0;j<prub[i].length;j++){
                if(1>prub[i][j] || prub[i][j]>limit){
                    return false;
                }
                diag+=prub[i][j];
                diag1+=prub[j][i];
            }
            if(diag!=cumplir || diag1!=cumplir){
                return false;
            }
        }
        for(int i=1;i<=(prub.length* prub.length);i++){//miramos que la matriz contenga todos los numeros de 1 a N^2(con lo que tambien nos aseguramos que no repita)
            if(!contiene(prub,i)){
                return false;
            }
        }

        return true;
    }
    private boolean contiene(int[][] arr, int z){//funcion auxiliar
        for(int i=0;i<arr.length;i++){
            for(int j=0; j<arr[i].length;j++){
                if(arr[i][j]==z){
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public Accion[] acciones(Estado es) {//generamos las acciones posibles buscando la primera posicion en blanco y rellenando con todos los numeros de 1 a N^2
        int x=0,y=0;
        boolean encontrado=false;
        EstadoCuadrado esAs= (EstadoCuadrado) es;
        ArrayList<AccionCuadrado> accs= new ArrayList<>();
        int [][] estado= esAs.estado;
        int lim= (int)Math.pow(estado.length,2);
        for(int i=0;i< estado.length;i++){
            for(int j=0;j< estado[i].length;j++){
                if(estado[i][j]==0 && !encontrado){
                    x=i;
                    y=j;
                    encontrado=true;

                }
            }
        }
        if(!encontrado){
            return new Accion[0];
        }else{
            for(int i=1;i<= lim;i++){
                AccionCuadrado acc=new AccionCuadrado(x,y,i);
                if(acc.esAplicable(es)) {
                    accs.add(acc);
                }
            }
        }
        Accion[] accs1= new Accion[accs.size()];
        accs.toArray(accs1);
        return accs1;
    }

}