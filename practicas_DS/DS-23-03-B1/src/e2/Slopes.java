package e2;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class Slopes{
    public static int downTheSlope(char [][] slopeMap, int right, int down) throws IllegalArgumentException { //que es esto julio
        int arboles=0;
        int nfila = slopeMap.length;
        int ncol = slopeMap[0].length;
        int posfila=0, poscol= 0;
        if(right<1 || down<1){
            throw new IllegalArgumentException("Datos incorrectos");
        }
        for(int i=0;i<nfila;i++){
            if(slopeMap[i].length!=nfila){
                throw new IllegalArgumentException("Matriz incorrecta");
            }
        }
        for(int i=0;i<nfila;i++){
            for(int j=0;j<ncol;j++){
                if((slopeMap[i][j]!='.' && slopeMap[i][j]!='#')){
                    throw new IllegalArgumentException("Matriz incorrecta");
                }
            }
        }
        if(right<ncol && down<ncol && ncol==nfila) {
            while (posfila <= nfila-1) {
                for(int i=0; i<right; i++){
                    poscol++;
                    if(poscol==ncol){
                        poscol=0;
                    }
                    if(posfila<nfila && poscol<ncol) {
                        if (slopeMap[posfila][poscol] == '#') {
                            arboles++;
                        }
                    }
                }
                if(posfila<nfila-1) {
                    for (int i = 0; i < down; i++) {
                            posfila++;
                            if(posfila<nfila && poscol<ncol) {
                                if (slopeMap[posfila][poscol] == '#') {
                                    arboles++;

                                }
                            }
                    }
                }else{
                    posfila++;
                }
            }
            return arboles;
        }else{
            throw new IllegalArgumentException("Datos incorrectos");
        }

    }
    /*public static int jumptheSlopes(char [][] slopeMap,int right, int down){

    }/*
     */
    public static int jumpTheSlope(char [][] slopeMap, int right, int down){
        int arboles=0;
        int nfila = slopeMap.length;
        int ncol = slopeMap[0].length;
        int posfila=0, poscol= 0;
        if(right<1 || down<1){
            throw new IllegalArgumentException("Datos incorrectos");
        }
        for(int i=0;i<nfila;i++){
            if(slopeMap[i].length!=nfila){
                throw new IllegalArgumentException("Matriz incorrecta");
            }
        }
        for(int i=0;i<nfila;i++){
            for(int j=0;j<ncol;j++){
                if(slopeMap[i][j]!='.' && slopeMap[i][j]!='#'){
                    throw new IllegalArgumentException("Matriz incorrecta");
                }
            }
        }
        if(right<ncol && down<ncol && ncol==nfila) {
            while (posfila <= nfila-1) {
                for(int i=0; i<right; i++){
                    poscol++;
                    if(poscol==ncol){
                        poscol=0;
                    }
                }
                if(posfila<nfila-1) {
                    for (int i = 0; i < down; i++) {
                        posfila++;
                    }
                    if(posfila<nfila && poscol<ncol) {
                        if (slopeMap[posfila][poscol] == '#') {
                            arboles++;
                        }
                    }
                }else{
                    posfila++;
                }
            }
            return arboles;
        }else{
            throw new IllegalArgumentException("Datos incorrectos");
        }

    }

}