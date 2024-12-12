package onetomany.Users;

import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import onetomany.Settings.Setting;
import onetomany.Friends.Friend;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;
    @ApiModelProperty(notes = "Name chosen by the user to login and connect characters to",name="username")
    private String username;
    
    @ApiModelProperty(notes = "password chosen by the user to login connected to the username",name="password")
    private String password;
    
    @ApiModelProperty(notes = "Boolean value that allows the front end to know if a user is logged in or not",name="loggedIn")
    private boolean loggedIn;
    
    @OneToMany
    private List<Friend> test;

    //@OneToOne
    //@JoinColumn(name = "setting_id", nullable = true)
    //@JsonIgnore
    //private Setting settings;

    public User(String username) {
        this.username = username;
        test = new ArrayList<>();
        //settings = null;
    }
    public User(String username, 
                String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = false;
        //settings = null;
    }
    public User() {
        test = new ArrayList<>();
    }
    public static User findByName(List<User> uList, String username){
        for(User u : uList){
            if(username.equals(u.getUsername())){
                return u;
            }
        }
        return null;
    }
    //public Setting getSettings(){
    //    return settings;
    //}
    //public void addSettings(Setting s){
    //    settings = s;
    //}
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isLoggedIn() {
        return loggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, 
                            loggedIn);
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }


}
