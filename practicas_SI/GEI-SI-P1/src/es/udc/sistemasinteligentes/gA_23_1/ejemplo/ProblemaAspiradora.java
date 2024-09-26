package es.udc.sistemasinteligentes.gA_23_1.ejemplo;

import es.udc.sistemasinteligentes.gA_23_1.Accion;
import es.udc.sistemasinteligentes.gA_23_1.Estado;
import es.udc.sistemasinteligentes.gA_23_1.ProblemaBusqueda;

public class ProblemaAspiradora extends ProblemaBusqueda {
    public static class EstadoAspiradora extends Estado {
        public enum PosicionRobot {IZQ, DER};
        public enum PosicionBasura {AMBAS, DER, IZQ, NINGUNA};
        //un estado tiene dos componentes que va siendo la posicion del robot y de la basura
        private PosicionRobot posicionRobot;//esto son sus atributos internos
        private PosicionBasura posicionBasura;

        public EstadoAspiradora(PosicionRobot posicionRobot, PosicionBasura posicionBasura) {
            this.posicionRobot = posicionRobot;
                this.posicionBasura = posicionBasura;
        }//constructor

        @Override
        public String toString() {
            return "(" + posicionRobot + "," + posicionBasura + ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EstadoAspiradora that = (EstadoAspiradora) o;

            if (posicionRobot != that.posicionRobot) return false;
            return posicionBasura == that.posicionBasura;
        }

        @Override
        public int hashCode() {
            int result = posicionRobot.hashCode();
            result = 31 * result + posicionBasura.hashCode();
            return result;
        }
    }

    public static class AccionAspiradora extends Accion{
        public enum Tipo {IZQ, DER, ASP};//estas son las acciones que puede realizar(moverse o aspirar)

        private Tipo tipo;

        public AccionAspiradora(Tipo tipo) {
            this.tipo = tipo;
        }//constructor, con un tipo de accion a aplicar(le decimos nosotros)

        @Override
        public String toString() {
            return tipo.name();
        }

        @Override
        public boolean esAplicable(Estado es) {
            return true;
        }//a cualquier estado puedes aplicar cualquier accion

        @Override
        public Estado aplicaA(Estado es) {//metodo para aplicar
            EstadoAspiradora esAs = (EstadoAspiradora)es;//variables dele stado que se pasa como parametro
            EstadoAspiradora.PosicionRobot nuevaPosicionRobot=esAs.posicionRobot;
            EstadoAspiradora.PosicionBasura nuevaPosicionBasura=esAs.posicionBasura;

            if (tipo==Tipo.IZQ)
                nuevaPosicionRobot = EstadoAspiradora.PosicionRobot.IZQ;
            else if (tipo==Tipo.DER)
                nuevaPosicionRobot = EstadoAspiradora.PosicionRobot.DER;
            else if (tipo==Tipo.ASP) {
                if (esAs.posicionRobot==EstadoAspiradora.PosicionRobot.IZQ) { //Aspiramos izquierda
                    if ((esAs.posicionBasura==EstadoAspiradora.PosicionBasura.DER) ||
                            (esAs.posicionBasura==EstadoAspiradora.PosicionBasura.AMBAS)) {
                        nuevaPosicionBasura = EstadoAspiradora.PosicionBasura.DER;
                    }
                    else
                        nuevaPosicionBasura = EstadoAspiradora.PosicionBasura.NINGUNA;
                }
                else{ //Aspiramos derecha
                    if ((esAs.posicionBasura==EstadoAspiradora.PosicionBasura.IZQ) ||
                            (esAs.posicionBasura==EstadoAspiradora.PosicionBasura.AMBAS)) {
                        nuevaPosicionBasura = EstadoAspiradora.PosicionBasura.IZQ;
                    }
                    else
                        nuevaPosicionBasura = EstadoAspiradora.PosicionBasura.NINGUNA;
                }
            }
            return new EstadoAspiradora(nuevaPosicionRobot, nuevaPosicionBasura);//devuelve un estado con posicion robot y basura
        }
    }

    //Como toda las acciones se pueden aplicar en cualquier estado y son pocas,
    //podemos mantenerlas en un array para cuando nos las pidan con el método acciones.
    private Accion[] listaAcciones;

    public ProblemaAspiradora(EstadoAspiradora estadoInicial) {//le pasas el estado inicial de tu aspiradora como parametro
        super(estadoInicial);
        //Inicializamos la lista de acciones
        listaAcciones = new Accion[]{new AccionAspiradora(AccionAspiradora.Tipo.IZQ),
                new AccionAspiradora(AccionAspiradora.Tipo.DER),
                new AccionAspiradora(AccionAspiradora.Tipo.ASP)};
    }//inicializamos un array con las 3 posibles acciones a ejecutar

    public Accion[] acciones(Estado es){
        //No es necesario generar las acciones dinámicamente a partir del estado porque todas las acciones se pueden
        //aplicar a todos los estados
        return listaAcciones;
    }


    @Override
    public boolean esMeta(Estado es) {//es meta si no queda basura
        return ((EstadoAspiradora)es).posicionBasura == EstadoAspiradora.PosicionBasura.NINGUNA;
    }
}
