package e2;

import java.io.BufferedReader;
import java.io.FileReader;

public final class LeerArchivo {
    public String leerTxt(String direccion){//introducimos direccion del archivo
        StringBuilder texto;
        try{
            BufferedReader bf=new BufferedReader(new FileReader(direccion));
            StringBuilder temp=new StringBuilder();
            String bfRead;
            while((bfRead = bf.readLine())!=null){
                //hacer el bucle mientras bfRead tengas datos
                temp.append(bfRead);

            }
            return temp.toString();
        }catch(Exception e){
            throw new IllegalArgumentException("Argumento no valido");
        }

    }
}