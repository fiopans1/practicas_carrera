package e3;

import java.util.List;
import e3.TopicOfInterest;
/*
 * TITLE: B1 DS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander,varela@udc.es
 * GROUP: 2.3
 */
public interface NetworkManager {
    public abstract void addUser(String user, List<TopicOfInterest> topicssOfInterest);
    public abstract void removeUser(String user);
    public abstract void addInterest( String user, TopicOfInterest topicOfInterest);
    public abstract void removeInterest(String user, TopicOfInterest topicOfInterest);
    public abstract List<String> getUsers();
    public abstract List<TopicOfInterest> getInterest();
    List<TopicOfInterest> getInterestsUser(String user);


}
