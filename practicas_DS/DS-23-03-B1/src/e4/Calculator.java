package e4;

/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class Calculator {
    private float resultado;
    StringBuilder operacion;
    Operaciones tiposcalculo;
    public Calculator(){
        operacion= new StringBuilder();
    }
    public void cleanOperations(){//poner operando a 0
        operacion.setLength(0);
    }
    public void addOperation(String operation, float... values){
        if(operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/")) {
            if (operacion.length() == 0) {//miramos si es la primera vez que insertamos o no
                operacion.append("[").append(operation).append("]").append(values[0]).append("_").append(values[1]);
            } else {
                operacion.append("[").append(operation).append("]").append(values[0]);
            }
        }else{
            throw new IllegalArgumentException("Argumentos no validos");
        }


        }
    public float executeOperations(){//vamos a coger la cadena, a dividirla en trozos y a ejecutarla
        String separadores= "[\\ \\_ \\[ \\] ]";
        String[] partes=operacion.toString().split(separadores);//el primer elemento siempre es un espacio, así que empezamos en 1
        if(partes[1].equals("+")){
            this.tiposcalculo= Operaciones.SUMAR;
            resultado=calculo(Float.parseFloat(partes[2]),Float.parseFloat(partes[3]));
        }else if(partes[1].equals("-")){
            this.tiposcalculo= Operaciones.RESTAR;
            resultado=calculo(Float.parseFloat(partes[2]),Float.parseFloat(partes[3]));
        }else if(partes[1].equals("*")){
            this.tiposcalculo= Operaciones.MULTIPLICAR;
            resultado=calculo(Float.parseFloat(partes[2]),Float.parseFloat(partes[3]));
        }else if(partes[1].equals("/")){
            this.tiposcalculo= Operaciones.DIVIDIR;
            if(Float.parseFloat(partes[3])!=0) {
                resultado = calculo(Float.parseFloat(partes[2]), Float.parseFloat(partes[3]));
            }else{
                cleanOperations();
                throw new ArithmeticException("Error division entre 0");
            }
        }//una vez ejecutada la primera vez ahora es lo mismo
        for(int i=4;i< partes.length-1;i++){
            if(partes[i].equals("+")){
                this.tiposcalculo= Operaciones.SUMAR;
                resultado=calculo(resultado,Float.parseFloat(partes[i+1]));
            }else if(partes[i].equals("-")){
                this.tiposcalculo= Operaciones.RESTAR;
                resultado=calculo(resultado,Float.parseFloat(partes[i+1]));
            }else if(partes[i].equals("*")){
                this.tiposcalculo= Operaciones.MULTIPLICAR;
                resultado=calculo(resultado,Float.parseFloat(partes[i+1]));
            }else if(partes[i].equals("/")){
                this.tiposcalculo= Operaciones.DIVIDIR;
                if(Float.parseFloat(partes[i+1])!=0) {
                    resultado = calculo(resultado, Float.parseFloat(partes[i+1]));
                }else{
                    cleanOperations();
                    throw new ArithmeticException("Error division entre 0");
                }
            }
        }
        cleanOperations();
        return resultado;
    }
    @Override
    public String toString(){

        return "[STATE:"+operacion.toString()+"]";
    }//el state solo es el string
    public float calculo(float operand1, float operand2){
        return tiposcalculo.calculo(operand1,operand2);
    }


}
