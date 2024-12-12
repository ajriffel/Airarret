package onetomany.Player;

import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Customization")
public class PlayerCusomization {
	 private @Id @GeneratedValue int id;
	 
	 @ApiModelProperty(notes = "user chosen characterName for one of the players characters",name="characterName")
	 private String characterName;
	 
	 @ApiModelProperty(notes = "user chosen hair",name="hair")
	 private int hair;
	 
	 @ApiModelProperty(notes = "user chosen skin",name="skin")
	 private int skin;
	 
	 @ApiModelProperty(notes = "user chosen shirt",name="shirt")
	 private int shirt;
	 
	 @ApiModelProperty(notes = "user chosen pants",name="pants")
	 private int pants;
	 
	 @ApiModelProperty(notes = "user chosen shoes",name="shoes")
	 private int shoes;
	 
	 @ApiModelProperty(notes = "user chosen difficulty",name="difficulty")
	 private String difficulty;
	 
	 @ApiModelProperty(notes = "users given username that the user has logged in with",name="username")
	 private String username;
	 
	 public PlayerCusomization() {
		 
	 }
	 
	 public PlayerCusomization(int hair,
			  int skin,
			  int shirt,
			  int pants,
			  int shoes,
			  String difficulty,
			  String username,
			  String characterName) {
		 this.hair = hair;
		 this.shirt = shirt;
		 this.pants = pants;
		 this.shoes = shoes;
		 this.difficulty = difficulty;
		 this.username = username;
		 this.characterName = characterName;

}
	 
	 public int getHair() {
		 return hair;
	 }
	 public int getSkin() {
		 return skin;
	 }
	 public int getShirt() {
		 return shirt;
	 }
	 public int getPants() {
		 return pants;
	 }
	 public int getShoes() {
		 return shoes;
	 }
	 public String getDifficulty() {
		 return difficulty;
	 }
	 public String getUsername() {
		 return username;
	 }
	 public String getCharacterName() {
		 return characterName;
	 }
	 public void setCharacterName(String characterName) {
		  this.characterName = characterName;
	 }
	
	 public void setHair(int hair) {
		 this.hair = hair;
	 }
	 public void setSkin(int skin) {
		 this.skin = skin;
	 }
	 public void setShirt(int shirt) {
		this.shirt = shirt;
	 }
	 public void setPants(int pants) {
		 this.pants = pants;
	 }
	 public void setShoes(int shoes) {
		 this.shoes = shoes;
	 }
	 public void setDifficulty(String difficulty) {
		 this.difficulty = difficulty;
	 }
	 public void setUsername(String username) {
		 this.username = username;
	 }

	public int getId() {
		return id;
	}
	 
	 }

	
