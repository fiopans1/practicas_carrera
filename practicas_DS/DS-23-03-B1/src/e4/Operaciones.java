package e4;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public enum Operaciones {
    SUMAR("+") {
        @Override
        public float calculo(float operand1, float operador2) {
            return operand1 + operador2;
        }
    },
    RESTAR("-"){
        @Override
        public float calculo(float operador1, float operador2) {
            return operador1 - operador2;
        }
    },
    MULTIPLICAR("*"){
        @Override
        public float calculo(float operador1, float operador2) {
            return operador1 * operador2;
        }
    },
    DIVIDIR("/"){
        @Override
        public float calculo(float operador1, float operador2) {
            return operador1 / operador2;
        }
    };

    private final String operator;


    Operaciones(String s) {
        this.operator = s;
    }

    public abstract float calculo(float operand1, float operador2);
}
