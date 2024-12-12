package onetomany.Friends;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import onetomany.Users.User;

@Entity
public class Friend {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_id2")
    @JsonIgnore
    private User user2;

    public Friend(){

    }
    public Friend(User u1, User u2) {
        user1 = u1;
        user2 = u2;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public User getUser1() {
        return user1;
    }
    public User getUser2(){
        return user2;
    }
    public void setUser1(User user) {
        this.user1 = user;
    }
    public void setUser2(User user) {
        this.user2 = user;
    }

}
