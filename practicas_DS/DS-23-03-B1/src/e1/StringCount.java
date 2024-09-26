package e1;
import java.lang.String;
import java.lang.invoke.VarHandle;
import java.util.StringTokenizer;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class StringCount {

    public static int countWords(String text){
        String y;
        int palabras=1;
        if(text==null){ //text.isEmpty()
            return 0;
        }else {
            y=text.trim();//saca los espacios de delante y detras
            for(int i=0;i<y.length();i++){
                if(y.charAt(i)==' ' && y.charAt(i+1)!=' '){
                    palabras++;
                }
            }
            return palabras;
        }
    }
    public static int countChar(String text, char c){
        int n=0;
        if(text==null){
            return 0;
        }else{
            for(int i=0;i<text.length();i++){
                if(text.charAt(i)==c){
                    n++;

                }
            }
            return n;
        }

    }
    public static int countCharIgnoringCase(String text, char c){
        char C= Character.toUpperCase(c);
        char m = Character.toLowerCase(c);
        int n=0;
        if(text==null){
            return 0;
        }else{
            for(int i=0;i<text.length();i++){
                if(text.charAt(i)==c || text.charAt(i)==C || text.charAt(i)==m){
                    n++;
                }
            }
            return n;
        }
    }
    public static boolean isPasswordSafe(String password){
        char c;
        boolean minus=false, mayus=false, digit=false, special=false;
        if(password==null){
            return false;
        }else {
            if (password.length() >= 8) {
                for (int i = 0; i < password.length(); i++) {
                    c = password.charAt(i);
                    if (Character.isLowerCase(c)) {
                        minus = true;
                    }
                    if (Character.isUpperCase(c)) {
                        mayus = true;
                    }
                    if (Character.isDigit(c)) {
                        digit = true;
                    }
                    if (c == '?' || c == '@' || c == '#' || c == '$' || c == '.' || c == ',') {
                        special = true;
                    }

                }
                return minus && mayus && digit && special;
            } else {
                return false;
            }
        }

    }
}
