package es.udc.sistemasinteligentes.gA_23_1.apartado2b;

import es.udc.sistemasinteligentes.gA_23_1.Estado;
import es.udc.sistemasinteligentes.gA_23_1.Heuristica;
import es.udc.sistemasinteligentes.gA_23_1.apartado2a.ProblemaCuadradoMagico.*;

public class HeuristicaCuadradoMagico extends Heuristica {//mañana buscar una que sea decente
    private boolean contiene(int[][] arr, int z){//funcion auxiliar para ver si z esta en arr
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
    public float evalua(Estado e) {
        //para la heuristica simplemente miramos si la matriz aun puede ser solucion, si puede entonces devolvemos el numero
        //de casillas que faltan por rellenar, sino devolvemos infinito(un numero muy alto para que nunca mire ese estado)
        EstadoCuadrado esAs= (EstadoCuadrado)e;
        int [][] prub= esAs.getEstado();
        int diag=0,diag1=0;
        int sum=0;
        boolean casillas=false, casillas1=false;
        int cumplir=(((prub.length)*(prub.length*prub.length+1))/2);
        int z=prub.length;
        int limit= (int)Math.pow(prub.length,2);
        for(int i=0;i<prub.length;i++){//miramos que ninguna diagonal se pase de N*(N^2+1)/2
            diag+=prub[i][i];
            if(prub[i][i]==0){
                casillas=true;
            }
        }
        if(diag!=cumplir && !casillas){
            return 100000;
        }
        diag=0;
        casillas=false;
        for(int i=0;i<prub.length;i++){
            if(z>0){
                z--;
            }

            diag+=prub[z][i];
            if(prub[z][i]==0){
                casillas=true;
            }
        }
        if(diag!=cumplir && !casillas){
            return 100000;
        }
        casillas=false;
        casillas1=false;
        for(int i=0;i<prub.length;i++){//hacemos lo mismo que con las diagonales en las filas y columnas
            //y ademas sumamos el numero de casillas en blanco
            diag=0;
            diag1=0;
            for(int j=0;j<prub[i].length;j++){
                if(prub[i][j]==0){
                    sum++;
                }
                diag+=prub[i][j];
                if(prub[i][j]==0){
                    casillas=true;
                }
                diag1+=prub[j][i];
                if(prub[j][i]==0){
                    casillas1=true;
                }
            }
            if(diag!=cumplir && !casillas){
                return 100000;
            }
            if(diag1!=cumplir && !casillas1){
                return 100000;
            }
        }
        return sum;//si el estado es posible devolvemos el numero de casillas en blanco

    }
}