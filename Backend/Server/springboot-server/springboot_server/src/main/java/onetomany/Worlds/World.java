package onetomany.Worlds;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import onetomany.Player.PlayerCusomization;
import onetomany.Users.User;
import javax.persistence.Table;

@Entity
@Table(name = "worldtest")
public class World {
    private static String def ="(250, 500)";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ApiModelProperty(notes = "Name given to world by user",name="worldName")
    private String worldName;

    @ApiModelProperty(notes = "The state of the world as stored in the database",name="worldState")
    @Column(name="state", columnDefinition = "mediumtext")
    private String worldState;


    private char difficulty;

    @ApiModelProperty(notes = "World is in use if 1, inactive if 0",name="active")
    private int active;
    
    @ApiModelProperty(notes = "The user whom the world belongs to",name="ownerUser")
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User ownerUser;

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = true)
    @JsonIgnore
    private PlayerCusomization owner;
    private String owner_pos;

    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = true)
    @JsonIgnore
    private PlayerCusomization user2;
    private String user2_pos;

    @ManyToOne
    @JoinColumn(name = "player3_id", nullable = true)
    @JsonIgnore
    private PlayerCusomization user3;
    private String user3_pos;

    @ManyToOne
    @JoinColumn(name = "player4_id", nullable = true)
    @JsonIgnore
    private PlayerCusomization user4;
    private String user4_pos;

    public World(){}
    public World(String name, String state, char difficulty, User own, PlayerCusomization player){
        worldName = name;
        worldState = state;
        active = 0;
        this.difficulty = difficulty;
        owner_pos = def;
        ownerUser = own;
        owner = player;
        user2 = null;
        user3 = null;
        user4 = null;
    }
    public World(String name, char difficulty, User own){
        worldName = name;
        active = 0;
        this.difficulty = difficulty;
        worldState = "";
        owner_pos = def;
        ownerUser = own;
        //owner = player;
        user2 = null;
        user3 = null;
        user4 = null;
    }

    public boolean addWorldState(String s){
        String test = worldState + s;
        if(test.length() <= 500000){
            worldState = test;
        }
        if (worldState.length() == 500000){
            return true;
        }
        else{
            return false;
        }
    }
    public String getName(){
        return worldName;
    }
    public char getDifficulty(){
        return difficulty;
    }
    public void updateState(int x, char replace){
        String pre = worldState.substring(0, x);
        String post = worldState.substring(x + 1);
        worldState = pre + replace + post;
    }
    public int getActive(){
        return active;
    }
    public User getOwner() {
        return ownerUser;
    }
    public int getPlayers(int i){
        if(owner != null && i == 0){
            return owner.getId();
        }
        else if(user2 != null && i == 1){
            return user2.getId();
        }
        else if(user3 != null && i == 2){
            return user3.getId();
        }
        else if(user4 != null && i == 3){
            return user4.getId();
        }
        return -1;
    }
    public boolean addPlayer(PlayerCusomization u){
        if((owner != null && owner.getId() == u.getId()) || 
        (user2 != null && user2.getId() == u.getId()) ||
        (user3 != null && user3.getId() == u.getId())||
        (user4 != null && user4.getId() == u.getId())){
            return false;
        }
        if(owner == null){
            active = 1;
            owner = u;
            owner_pos = def;
            return true;
        }
        else if(user2 == null){
            user2 = u;
            user2_pos = def;
            return true;
        }
        else if(user3 == null){
            user3 = u;
            user3_pos = def;
            return true;
        }
        else if(user4 == null){
            user4 = u;
            user4_pos = def;
            return true;
        }
        else{
            return false;
        }
    }
    public void removePlayer(int id){
        if(owner != null && owner.getId() == id){
            active = 0;
            owner = null;
            owner_pos = null;
        }
        else if(user2 != null && user2.getId() == id){
            user2_pos = null;
            user2 = null;
        }
        else if(user3 != null && user3.getId() == id){
            user3_pos = null;
            user3 = null;
            
        }
        else if(user4 != null && user4.getId() == id){
            user4_pos = null;
            user4 = null;
            
        }
    }
    public boolean updatePlayer(int id, String val){
        if(user2 != null && user2.getId() == id){
            user2_pos = val;
            return true;
        }
        else if(user3 != null && user3.getId() == id){
            user3_pos = val;
            return true;
            
        }
        else if(user4 != null && user4.getId() == id){
            user4_pos = val;
            return true;
            
        }
        else if(owner != null && owner.getId() == id){
            owner_pos = val;
            return true;
        }
        return false;
    }
    public void nullOut(){
        ownerUser = null;
        owner = null;
        user2 = null;
        user3 = null;
        user4 = null;
    }
    public int getId(){
        return id;
    }
    public void clearState(){
        worldState = "";
    }
    public String getFrom(int start, int end){
        return worldState.substring(start, end);
    }
    public String getUsers(){
        String r = "";
        if(owner != null){
            r += owner.getCharacterName();
        }
        else if(user2 != null){
            r += ", " + user2.getCharacterName();
        }
        else if(user3 != null){
            r += ", " + user3.getCharacterName();
        }
        else if(user4 != null){
            r += ", " + user4.getCharacterName();
        }
        return r;
    }
    public String getPostitions(){
        String r = "";
        if(owner != null){
            r += owner_pos;
        }
        else if(user2 != null){
            r += ", " + user2_pos;
        }
        else if(user3 != null){
            r += ", " + user3_pos;
        }
        else if(user4 != null){
            r += ", " + user4_pos;
        }
        return r;
    }
}
