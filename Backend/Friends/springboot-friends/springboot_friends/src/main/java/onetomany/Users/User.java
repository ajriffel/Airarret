package onetomany.Users;

import java.util.ArrayList;
import java.util.List;

//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
//import javax.persistence.OneToOne;
import onetomany.Settings.Setting;
import onetomany.Friends.Friend;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;

    
    @OneToMany
    private List<Friend> test;

    @OneToOne
    private Setting settings;

    public User(String username) {
        this.username = username;
        test = new ArrayList<>();
        settings = null;
    }

    public User() {
        test = new ArrayList<>();
    }
    public static User findByName(List<User> uList, String name){
        for(User u : uList){
            if(name.equals(u.getName())){
                return u;
            }
        }
        return null;
    }
    public Setting getSettings(){
        return settings;
    }
    public void addSettings(Setting s){
        settings = s;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return username;
    }

    public void setName(String username){
        this.username = username;
    }



}
