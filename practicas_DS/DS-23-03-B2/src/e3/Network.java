package e3;

import java.util.ArrayList;
import java.util.List;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class Network {
    private NetworkManager usuarios;
    public Network(NetworkManager o){
        usuarios = o;
    }
    public void añadirUsuarios(String user, List<TopicOfInterest> topicOfInterests){
        if(user!=null && topicOfInterests!=null) {
            usuarios.addUser(user, topicOfInterests);
        }else{
            throw new IllegalArgumentException("Argumentos no validos");
        }
    }
    public void eliminarUsuario(String user){
        usuarios.removeUser(user);
    }
    public void añadirTemas(String user, TopicOfInterest topicOfInterest){
        if(topicOfInterest!=null && user!=null) {
            usuarios.addInterest(user, topicOfInterest);
        }else{
            throw new IllegalArgumentException("Argumentos no validos");
        }
    }
    public void eliminarTemas(String user, TopicOfInterest topicOfInterest){
        usuarios.removeInterest(user,topicOfInterest);
    }
    public List<TopicOfInterest> obtenerTemas(String user){
        return usuarios.getInterestsUser(user);
    }

    public List<String> usuariosInteresadosXTema(TopicOfInterest topicOfInterest){
        List<String> preusuarios=usuarios.getUsers();
        List<String> resultado=new ArrayList<>();
        List<TopicOfInterest> listasdeintereses;
        for(int i=0;i<preusuarios.size();i++){
            listasdeintereses=usuarios.getInterestsUser(preusuarios.get(i));
            for(int j=0;j<listasdeintereses.size();j++){
                if(listasdeintereses.get(j)==topicOfInterest){
                    resultado.add(preusuarios.get(i));
                }
            }
        }
        return resultado;
    }
    public List<String> obtenerUsuarios(){
        return usuarios.getUsers();
    }
    public List<TopicOfInterest> interesesComunes(String user1, String user2){
        List<TopicOfInterest> listauser1=usuarios.getInterestsUser(user1);
        List<TopicOfInterest> listauser2=usuarios.getInterestsUser(user2);
        List<TopicOfInterest> resultado= new ArrayList<>();
        for(int i=0;i< listauser1.size();i++){
            for(int j=0;j<listauser2.size();j++){
                if(listauser1.get(i)==listauser2.get(j)){
                    resultado.add(listauser2.get(j));
                }
            }
        }
        return resultado;
    }
    public String verInfo(){
        StringBuilder s= new StringBuilder();
        List<String> users=usuarios.getUsers();
        List<TopicOfInterest> topics;
        for(int i=0;i< users.size();i++){
            s.append(users.get(i)).append("(");
            topics=usuarios.getInterestsUser(users.get(i));
            for(int j=0;j< topics.size();j++){
                s.append(topics.get(j).toString()).append("/");
            }
            s.append(")\n");
        }
        System.out.printf(s.toString());
        return s.toString();
    }
    public List<TopicOfInterest> obtenerIntereses(){
        return usuarios.getInterest();

    }

}
