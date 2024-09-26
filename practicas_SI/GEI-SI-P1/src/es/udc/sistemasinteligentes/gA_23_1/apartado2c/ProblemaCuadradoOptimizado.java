package es.udc.sistemasinteligentes.gA_23_1.apartado2c;

import es.udc.sistemasinteligentes.gA_23_1.Accion;
import es.udc.sistemasinteligentes.gA_23_1.Estado;
import es.udc.sistemasinteligentes.gA_23_1.ProblemaBusqueda;

import java.util.ArrayList;
import java.util.Objects;

public class ProblemaCuadradoOptimizado extends ProblemaBusqueda {
    //es igual al del 2a menos el Accion[] de ProblemaBusqueda que es donde hacemos los cambios!
    public static class EstadoCuadrado extends Estado {
        private final int [][] estado;
        public EstadoCuadrado(int [][] estado){
            this.estado=estado;

        }
        @Override
        public String toString() {
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

        public int[][] getEstado() {
            return estado;
        }

        @Override
        public boolean equals(Object o) {
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
        }
    }
    public static class AccionCuadrado extends Accion {
        private int x,y,valor;
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
        public boolean esAplicable(Estado es) {
            EstadoCuadrado esAs= (EstadoCuadrado)es;
            if(esAs.estado[x][y]!=0){
                return false;

            }
            return true;
        }

        @Override
        public Estado aplicaA(Estado es) {
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

    public ProblemaCuadradoOptimizado(Estado estadoInicial) {
        super(estadoInicial);
    }


    @Override
    public boolean esMeta(Estado es) {
        EstadoCuadrado esAs= (EstadoCuadrado)es;
        int [][] prub= esAs.estado;
        int diag=0,diag1=0;
        int cumplir=(((prub.length)*(prub.length*prub.length+1))/2);
        int z=prub.length;
        int limit= (int)Math.pow(prub.length,2);
        for(int i=0;i<prub.length;i++){
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

        for(int i=0;i<prub.length;i++){
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
        for(int i=1;i<=(prub.length* prub.length);i++){
            if(!contiene(prub,i)){
                return false;
            }
        }


        return true;
    }
    private boolean contiene(int[][] arr, int z){
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
    public Accion[] acciones(Estado es) {//optimizamos el generar acciones
        int x=0,y=0;
        boolean encontrado=false;
        int cnt=0;
        EstadoCuadrado esAs= (EstadoCuadrado) es;
        ArrayList<AccionCuadrado> accs= new ArrayList<>();
        int [][] estado= esAs.estado;
        int form= (int) (estado.length*(Math.pow(estado.length,2)+1))/2;
        int lim= (int)Math.pow(estado.length,2);
        int cntfil=0, cntcol=0;
        int diag=0,diag1=0;
        int cnt0fil=0,cnt0col=0;
        int z= estado.length;
        //buscamos una casilla en vacia para rellenar
        for(int i=0;i<estado.length;i++){
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

        //miramos que las dos diagonales sean validas, sino lo son no devolvemos aciones de este estado
        for(int i=0;i<estado.length;i++){
            diag+=estado[i][i];
            if(estado[i][i]!=0){
                cnt++;
            }
        }
        if((diag!=form && cnt==estado.length) || diag>form){
            return new Accion[0];
        }
        cnt=0;
        diag=0;
        for(int i=0;i<estado.length;i++){
            if(z>0){
                z--;
            }

            diag+=estado[z][i];
           if(estado[z][i]!=0){
                cnt++;
            }

        }
        if((diag!=form && cnt==estado.length) || diag>form){
            return new Accion[0];
        }
        //miramos que las filas y columnas sean validas, sino lo son no devolvemos aciones de este estado
        for(int i=0;i<estado.length;i++){
            diag=0;
            diag1=0;
            cnt0fil=0;
            cnt0col=0;
            for(int j=0;j<estado[i].length;j++){
                if(0>estado[i][j] || estado[i][j]>lim){
                    return new Accion[0];
                }
                diag+=estado[i][j];
                diag1+=estado[j][i];
                if(estado[i][j]==0){
                    cnt0fil++;
                }
                if(estado[j][i]==0){
                    cnt0col++;
                }

            }
            if((diag!=form && cnt0fil==0) || (diag1!=form && cnt0col==0)){
                return new Accion[0];
            }
        }
        for(int i=0;i< estado.length;i++){
            cntfil+=estado[x][i];
            cntcol+=estado[i][y];
        }
        for (int i = 1; i <= lim && i<=(form-cntfil) && i<=(form-cntcol); i++) {//tenemos como condicion que el limite sea menor que lo que falta para completar la fila/columna
            if(!contiene(estado,i)) {//miramos que no se repita ningun numero en la matriz
                AccionCuadrado acc=new AccionCuadrado(x, y, i);
                if(acc.esAplicable(es)) {//miramos si es aplicable la accion
                    accs.add(acc);
                }
            }
        }
            Accion[] accs1= new Accion[accs.size()];
            accs.toArray(accs1);
            return accs1;
        }
    }

}

