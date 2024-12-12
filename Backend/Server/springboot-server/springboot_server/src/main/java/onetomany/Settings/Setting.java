package onetomany.Settings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import onetomany.Users.User;

@Entity
public class Setting{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    

    private String color;
    private String resolution;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ApiModelProperty(notes = "The name of the user",name="name")
    private String name;

    public Setting(User u){
        user = u;
        color = "Purple";
        resolution = "1920x1080";
    }
    public Setting(String name, String color, String resolution){
        this.color = color;
        this.resolution = resolution;
        this.name = name;
    }
    public Setting(){

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public User getUser(){
        return user;
    }
    public int getId(){
        return id;
    }
    public void setColor(String s){
        color = s;
    }
    public void setResolution(String s){
        resolution = s;
    }
    public String getColor(){
        return color;
    }
    public String getResolution(){
        return resolution;
    }

}
