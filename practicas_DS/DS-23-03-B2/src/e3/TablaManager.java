package e3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public class TablaManager implements NetworkManager{
    private List<String> users= new ArrayList<>();
    private List<TopicOfInterest> interest= new ArrayList<>(List.of(TopicOfInterest.ENTRETENIMIENTO, TopicOfInterest.SOCIAL, TopicOfInterest.VIDEOJUEGO, TopicOfInterest.MODA, TopicOfInterest.BELLEZA,
            TopicOfInterest.EDUCACION, TopicOfInterest.ANIMALES, TopicOfInterest.PROGRAMACION));
    private static final int MAX=256;
    private int indices[][]=new int[MAX][MAX];
    @Override
    public void addUser(String user, List<TopicOfInterest> topicssOfInterest) {
        if(user!=null){
            int n;
            users.add(user);
            if(users.indexOf(user)<MAX) {
                for (int i = 0; i < topicssOfInterest.size(); i++) {
                    indices[users.indexOf(user)][interest.indexOf(topicssOfInterest.get(i))] = 1;
                }
            }else{
                users.remove(user);
                throw new IllegalArgumentException("Maximo numero de usuarios alcanzado");
            }
        }

    }

    @Override
    public void removeUser(String user) {

        for(int i=users.indexOf(user);i<(users.size()-1);i++){
            for(int j=0; j<interest.size();j++){
                indices[i][j]=indices[i+1][j];
            }
        }
        for(int j=0;j<users.size();j++){
            indices[(users.size()-1)][j]=0;
        }
        users.remove(user);

    }

    @Override
    public void addInterest(String user, TopicOfInterest topicOfInterest) {
        indices[users.indexOf(user)][interest.indexOf(topicOfInterest)] = 1;
    }

    @Override
    public void removeInterest(String user, TopicOfInterest topicOfInterest) {
        indices[users.indexOf(user)][interest.indexOf(topicOfInterest)] = 0;
    }

    @Override
    public List<String> getUsers() {
        return users;
    }

    @Override
    public List<TopicOfInterest> getInterest() {
        List<TopicOfInterest> resultado= new ArrayList<>();
        for(int i=0;i<users.size();i++)
        for(int j=0; j<interest.size();j++){
            if(indices[i][j]==1){
                resultado.add(interest.get(j));
            }
        }
        resultado= resultado.stream().distinct().collect(Collectors.toList());
        return resultado;
    }

    @Override
    public List<TopicOfInterest> getInterestsUser(String user) {
        List<TopicOfInterest> interesesUser= new ArrayList<>();
        int i=users.indexOf(user);
        for(int j=0; j<interest.size();j++){
            if(indices[i][j]==1){
                interesesUser.add(interest.get(j));
            }
        }
        return interesesUser;
    }
}
