package e3;

import java.util.ArrayList;
import java.util.Objects;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class Melody {
    public enum Notes{DO,RE,MI,FA,SOL,LA,SI}
    public enum  Accidentals{NATURAL,SHARP,FLAT}
    private ArrayList<Notes> melodyNotes;
    private ArrayList<Accidentals> melodyAccidentals;
    private ArrayList<Float> melodyTiempos;

    public Melody() {
        melodyNotes = new ArrayList<Notes>();
        melodyAccidentals = new ArrayList<Accidentals>();
        melodyTiempos = new ArrayList<Float>();
    }

    public void addNote(Notes note, Accidentals accidental, float time){
        if(note!=null && accidental!=null && time !=0){
            melodyNotes.add(note);
            melodyAccidentals.add(accidental);
            melodyTiempos.add(time);
        }else{
            throw new IllegalArgumentException("Indice no valido");
        }
    }
    public Notes getNote(int index){
        if(index<melodyNotes.size() && index>=0){
            return melodyNotes.get(index);
        }else{
            throw new IllegalArgumentException("Indice no valido");
        }
    }

    public Accidentals getAccidental(int index) {
        if(index<melodyAccidentals.size() && index>=0){
            return melodyAccidentals.get(index);
        }else{
            throw new IllegalArgumentException("Indice no valido");
        }
    }

    public float getTime(int index){
        if(index<melodyTiempos.size() && index>=0){
            return melodyTiempos.get(index);
        }else{
            throw new IllegalArgumentException("Indice no valido");
        }
    }
    public int size(){
        return melodyNotes.size();
    }
    public float getDuration(){
        float contador=0;
        for(int i=0;i<melodyTiempos.size();i++){
            contador= contador + melodyTiempos.get(i);
        }
        return contador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Melody melody = (Melody) o;
        return conversionNotes(melodyNotes,melodyAccidentals).equals(conversionNotes(melody.melodyNotes,melody.melodyAccidentals))
                && conversionAccidentals(melodyNotes,melodyAccidentals).equals(conversionAccidentals(melody.melodyNotes,melody.melodyAccidentals))
                && melodyTiempos.equals(melody.melodyTiempos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversionNotes(melodyNotes,melodyAccidentals), conversionAccidentals(melodyNotes,melodyAccidentals), melodyTiempos);
    }

    private ArrayList conversionNotes(ArrayList<Notes> notescpy, ArrayList<Accidentals> accidentals){
       ArrayList<Notes> copianotas= new ArrayList<Notes>();
        for(int i=0;i<melodyNotes.size();i++){

            if((notescpy.get(i)==Notes.DO && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.RE && accidentals.get(i)==Accidentals.FLAT)){
                        copianotas.add(Notes.DO);
            }else if((notescpy.get(i)==Notes.RE && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.MI && accidentals.get(i)==Accidentals.FLAT)){
                copianotas.add(Notes.RE);
            }else if((notescpy.get(i)==Notes.MI && accidentals.get(i)==Accidentals.NATURAL) ||
                    (notescpy.get(i)==Notes.FA && accidentals.get(i)==Accidentals.FLAT)){
                copianotas.add(Notes.MI);
            }else if((notescpy.get(i)==Notes.MI && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.FA && accidentals.get(i)==Accidentals.NATURAL)){
                copianotas.add(Notes.MI);
            }else if((notescpy.get(i)==Notes.FA && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.SOL && accidentals.get(i)==Accidentals.FLAT)){
                copianotas.add(Notes.FA);
            }else if((notescpy.get(i)==Notes.SOL && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.LA && accidentals.get(i)==Accidentals.FLAT)){
                copianotas.add(Notes.SOL);
            }else if((notescpy.get(i)==Notes.LA && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.SI  && accidentals.get(i)==Accidentals.FLAT)){
                copianotas.add(Notes.LA);
            }else if((notescpy.get(i)==Notes.SI && accidentals.get(i)==Accidentals.NATURAL) ||
                    (notescpy.get(i)==Notes.DO && accidentals.get(i)==Accidentals.FLAT)){
                copianotas.add(Notes.SI);
            }else if((notescpy.get(i)==Notes.SI && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.DO && accidentals.get(i)==Accidentals.NATURAL)) {
                copianotas.add(Notes.SI);
            }else{
                copianotas.add(notescpy.get(i));
            }
        }
       return copianotas;
    }
    private ArrayList conversionAccidentals(ArrayList<Notes> notescpy, ArrayList<Accidentals> accidentals){
        ArrayList<Accidentals> copiaacc= new ArrayList<Accidentals>();
        for(int i=0;i<melodyNotes.size();i++){

            if((notescpy.get(i)==Notes.DO && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.RE && accidentals.get(i)==Accidentals.FLAT)){
                copiaacc.add(Accidentals.SHARP);
            }else if((notescpy.get(i)==Notes.RE && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.MI && accidentals.get(i)==Accidentals.FLAT)){
                copiaacc.add(Accidentals.SHARP);
            }else if((notescpy.get(i)==Notes.MI && accidentals.get(i)==Accidentals.NATURAL) ||
                    (notescpy.get(i)==Notes.FA && accidentals.get(i)==Accidentals.FLAT)){
                copiaacc.add(Accidentals.NATURAL);
            }else if((notescpy.get(i)==Notes.MI && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.FA && accidentals.get(i)==Accidentals.NATURAL)){
                copiaacc.add(Accidentals.SHARP);
            }else if((notescpy.get(i)==Notes.FA && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.SOL && accidentals.get(i)==Accidentals.FLAT)){
                copiaacc.add(Accidentals.SHARP);
            }else if((notescpy.get(i)==Notes.SOL && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.LA && accidentals.get(i)==Accidentals.FLAT)){
                copiaacc.add(Accidentals.SHARP);
            }else if((notescpy.get(i)==Notes.LA && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.SI  && accidentals.get(i)==Accidentals.FLAT)){
                copiaacc.add(Accidentals.SHARP);
            }else if((notescpy.get(i)==Notes.SI && accidentals.get(i)==Accidentals.NATURAL) ||
                    (notescpy.get(i)==Notes.DO && accidentals.get(i)==Accidentals.FLAT)){
                copiaacc.add(Accidentals.NATURAL);
            }else if((notescpy.get(i)==Notes.SI && accidentals.get(i)==Accidentals.SHARP) ||
                    (notescpy.get(i)==Notes.DO && accidentals.get(i)==Accidentals.NATURAL)) {
                copiaacc.add(Accidentals.SHARP);
            }else{
                copiaacc.add(accidentals.get(i));
            }
        }
        return copiaacc;
    }

    @Override
    public String toString() {
        StringBuilder melodia = new StringBuilder();//RE#(7.0)
        for(int i=0; i<melodyNotes.size();i++){
            if(melodyAccidentals.get(i)==Accidentals.NATURAL){
                melodia.append(melodyNotes.get(i)).append("(").append(melodyTiempos.get(i)).append(")");
            }else if(melodyAccidentals.get(i)==Accidentals.FLAT){
                melodia.append(melodyNotes.get(i)).append("b").append("(").append(melodyTiempos.get(i)).append(")");
            }else if(melodyAccidentals.get(i)==Accidentals.SHARP){
                melodia.append(melodyNotes.get(i)).append("#").append("(").append(melodyTiempos.get(i)).append(")");
            }
            if(i<melodyNotes.size()-1){
                melodia.append(" ");
            }
        }
        return melodia.toString();
    }
}
