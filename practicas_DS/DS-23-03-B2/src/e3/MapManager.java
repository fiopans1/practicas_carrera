package e3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class MapManager implements NetworkManager{
    private Map<String, List<TopicOfInterest>> users= new HashMap<>();
    @Override
    public void addUser(String user, List<TopicOfInterest> topicsOfInterest) {
        topicsOfInterest= topicsOfInterest.stream().distinct().collect(Collectors.toList());//para sacar repetidos
        users.put(user,topicsOfInterest);
    }

    @Override
    public void removeUser(String user) {
        users.remove(user);
    }

    @Override
    public void addInterest(String user, TopicOfInterest topicOfInterest) {
        List<TopicOfInterest> aux= users.get(user);
        if(!aux.contains(topicOfInterest)) {
            aux.add(topicOfInterest);
            users.replace(user, aux);
        }
    }

    @Override
    public void removeInterest(String user, TopicOfInterest topicOfInterest) {
        List<TopicOfInterest> aux= users.get(user);
        aux.remove(topicOfInterest);
        users.replace(user,aux);
    }

    @Override
    public List<String> getUsers() {
        List<String> userList= new ArrayList<>(users.keySet());//obtenemos lista de keys
        return userList;
    }

    @Override
    public List<TopicOfInterest> getInterest() {
        List<TopicOfInterest> interesestotales= new ArrayList<>();
        ArrayList<String> keys= new ArrayList<>(users.keySet());
            for(int i=0; i< keys.size();i++){
                interesestotales.addAll(users.get(keys.get(i)));
            }
            interesestotales= interesestotales.stream().distinct().collect(Collectors.toList());
            return interesestotales;

    }

    @Override
    public List<TopicOfInterest> getInterestsUser(String user) {
        return users.get(user);
    }

}
