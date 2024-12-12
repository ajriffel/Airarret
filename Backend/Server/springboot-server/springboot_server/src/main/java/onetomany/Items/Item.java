package onetomany.Items;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;



@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(notes = "Name of item",name="name")
    private String name;

    @ApiModelProperty(notes = "A brief description of the item",name="description")
    private String description;

    @ApiModelProperty(notes = "The type of item it is in game",name="itype")
    private String itype;

    public Item(String name, String description, String itype) {
        this.name = name;
        this.description = description;
        this.itype = itype;
    }
    public Item(){

    }

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String getIType(){
        return itype;
    }
    public int getiid(){
        return id;
    }
    public void setName(String n){
        name = n;
    }
    public void setDescription(String n){
        description = n;
    }
    public void setIType(String n){
        itype = n;
    }
}
