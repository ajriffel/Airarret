package onetomany.chests;

import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chests")
public class Chests {

	private @Id @GeneratedValue int id;
	 @ApiModelProperty(notes = "Connects to the worldID from world class",name="worldID")
	 private int worldID;
	 
	 @ApiModelProperty(notes = "X value of the chest location",name="x")
	 private int x;
	 
	 @ApiModelProperty(notes = "Y value of the chest location",name="y")
	 private int y;
	 
	 @ApiModelProperty(notes = "item in the 1st slot in the chest",name="item1")
	 private String item1;
	 
	 @ApiModelProperty(notes = "item in the 2nd slot in the chest",name="item2")
     private String item2;
	 
	 @ApiModelProperty(notes = "item in the 3rd slot in the chest",name="item3")
     private String item3;
	 
	 @ApiModelProperty(notes = "item in the 4th slot in the chest",name="item4")
     private String item4;
	 
	 @ApiModelProperty(notes = "item in the 5th slot in the chest",name="item5")
     private String item5;
	 
	 @ApiModelProperty(notes = "item in the 6th slot in the chest",name="item6")
     private String item6;
	 
	 @ApiModelProperty(notes = "item in the 7th slot in the chest",name="item7")
     private String item7;
	 
	 @ApiModelProperty(notes = "item in the 8th slot in the chest",name="item8")
     private String item8;
	 
	 @ApiModelProperty(notes = "item in the 9th slot in the chest",name="item9")
     private String item9;
	 
	 @ApiModelProperty(notes = "item in the 10th slot in the chest",name="item10")
     private String item10;
	 
	 @ApiModelProperty(notes = "item in the 11th slot in the chest",name="item11")
     private String item11;
	 
	 @ApiModelProperty(notes = "item in the 12th slot in the chest",name="item12")
     private String item12;
	 
	 @ApiModelProperty(notes = "item in the 13th slot in the chest",name="item13")
     private String item13;
	 
	 @ApiModelProperty(notes = "item in the 14th slot in the chest",name="item14")
     private String item14;
	 
	 @ApiModelProperty(notes = "item in the 15th slot in the chest",name="item15")
     private String item15;
	 
	 @ApiModelProperty(notes = "item in the 16th slot in the chest",name="item16")
     private String item16;
	 
	 @ApiModelProperty(notes = "item in the 17th slot in the chest",name="item17")
     private String item17;
	 
	 @ApiModelProperty(notes = "item in the 18th slot in the chest",name="item18")
     private String item18;
	 
	 @ApiModelProperty(notes = "item in the 19th slot in the chest",name="item19")
     private String item19;
	 
	 @ApiModelProperty(notes = "item in the 20th slot in the chest",name="item20")
     private String item20;
	 
	 @ApiModelProperty(notes = "item in the 21st slot in the chest",name="item21")
     private String item21;
	 
	 @ApiModelProperty(notes = "item in the 22nd slot in the chest",name="item22")
     private String item22;
	 
	 @ApiModelProperty(notes = "item in the 23rd slot in the chest",name="item23")
     private String item23;
	 
	 @ApiModelProperty(notes = "item in the 24th slot in the chest",name="item24")
     private String item24;
	 
	 @ApiModelProperty(notes = "item in the 25th slot in the chest",name="item25")
     private String item25;
	 
	 @ApiModelProperty(notes = "item in the 26th slot in the chest",name="item26")
     private String item26;
	 
	 @ApiModelProperty(notes = "item in the 27th slot in the chest",name="item27")
     private String item27;
	 
	 @ApiModelProperty(notes = "item in the 28th slot in the chest",name="item28")
     private String item28;
	 
	 @ApiModelProperty(notes = "item in the 29th slot in the chest",name="item29")
     private String item29;
	 
	 @ApiModelProperty(notes = "item in the 30th slot in the chest",name="item30")
     private String item30;
	 
	 public Chests () {
		 
	 }
	 public Chests (int worldID,
			 int x,
			 int y,
			 String item1,
             String item2,
             String item3,
             String item4,
             String item5,
             String item6,
             String item7,
             String item8,
             String item9,
             String item10,
             String item11,
             String item12,
             String item13,
             String item14,
             String item15,
             String item16,
             String item17,
             String item18,
             String item19,
             String item20,
             String item21,
             String item22,
             String item23,
             String item24,
             String item25,
             String item26,
             String item27,
             String item28,
             String item29,
             String item30) {
		 
		 this.setWorldID(worldID);
		 this.setItem1(item1);
		 this.setItem2(item2);
		 this.setItem3(item3);
		 this.setItem4(item4);
		 this.setItem5(item5);
		 this.setItem6(item6);
		 this.setItem7(item7);
		 this.setItem8(item8);
		 this.setItem9(item9);
		 this.setItem10(item10);
		 this.setItem11(item11);
		 this.setItem12(item12);
		 this.setItem13(item13);
		 this.setItem14(item14);
		 this.setItem15(item15);
		 this.setItem16(item16);
		 this.setItem17(item17);
		 this.setItem18(item18);
		 this.setItem19(item19);
		 this.setItem20(item20);
		 this.setItem21(item21);
		 this.setItem22(item22);
		 this.setItem23(item23);
		 this.setItem24(item24);
		 this.setItem25(item25);
		 this.setItem26(item26);
		 this.setItem27(item27);
		 this.setItem28(item28);
		 this.setItem29(item29);
		 this.setItem30(item30);
	 }
	 public int getId() {
		 return id;
	 }
	 public void setId(int id) {
		 this.id = id;
	 }
	public int getWorldID() {
		return worldID;
	}
	public void setWorldID(int worldID) {
		this.worldID = worldID;
	}
	public String getItem1() {
		return item1;
	}
	public void setItem1(String item1) {
		this.item1 = item1;
	}
	public String getItem2() {
		return item2;
	}
	public void setItem2(String item2) {
		this.item2 = item2;
	}
	public String getItem3() {
		return item3;
	}
	public void setItem3(String item3) {
		this.item3 = item3;
	}
	public String getItem4() {
		return item4;
	}
	public void setItem4(String item4) {
		this.item4 = item4;
	}
	public String getItem6() {
		return item6;
	}
	public void setItem6(String item6) {
		this.item6 = item6;
	}
	public String getItem5() {
		return item5;
	}
	public void setItem5(String item5) {
		this.item5 = item5;
	}
	public String getItem8() {
		return item8;
	}
	public void setItem8(String item8) {
		this.item8 = item8;
	}
	public String getItem7() {
		return item7;
	}
	public void setItem7(String item7) {
		this.item7 = item7;
	}
	public String getItem9() {
		return item9;
	}
	public void setItem9(String item9) {
		this.item9 = item9;
	}
	public String getItem10() {
		return item10;
	}
	public void setItem10(String item10) {
		this.item10 = item10;
	}
	public String getItem11() {
		return item11;
	}
	public void setItem11(String item11) {
		this.item11 = item11;
	}
	public String getItem12() {
		return item12;
	}
	public void setItem12(String item12) {
		this.item12 = item12;
	}
	public String getItem13() {
		return item13;
	}
	public void setItem13(String item13) {
		this.item13 = item13;
	}
	public String getItem14() {
		return item14;
	}
	public void setItem14(String item14) {
		this.item14 = item14;
	}
	public String getItem15() {
		return item15;
	}
	public void setItem15(String item15) {
		this.item15 = item15;
	}
	public String getItem16() {
		return item16;
	}
	public void setItem16(String item16) {
		this.item16 = item16;
	}
	public String getItem17() {
		return item17;
	}
	public void setItem17(String item17) {
		this.item17 = item17;
	}
	public String getItem18() {
		return item18;
	}
	public void setItem18(String item18) {
		this.item18 = item18;
	}
	public String getItem19() {
		return item19;
	}
	public void setItem19(String item19) {
		this.item19 = item19;
	}
	public String getItem20() {
		return item20;
	}
	public void setItem20(String item20) {
		this.item20 = item20;
	}
	public String getItem21() {
		return item21;
	}
	public void setItem21(String item21) {
		this.item21 = item21;
	}
	public String getItem22() {
		return item22;
	}
	public void setItem22(String item22) {
		this.item22 = item22;
	}
	public String getItem23() {
		return item23;
	}
	public void setItem23(String item23) {
		this.item23 = item23;
	}
	public String getItem24() {
		return item24;
	}
	public void setItem24(String item24) {
		this.item24 = item24;
	}
	public String getItem25() {
		return item25;
	}
	public void setItem25(String item25) {
		this.item25 = item25;
	}
	public String getItem26() {
		return item26;
	}
	public void setItem26(String item26) {
		this.item26 = item26;
	}
	public String getItem27() {
		return item27;
	}
	public void setItem27(String item27) {
		this.item27 = item27;
	}
	public String getItem28() {
		return item28;
	}
	public void setItem28(String item28) {
		this.item28 = item28;
	}
	public String getItem29() {
		return item29;
	}
	public void setItem29(String item29) {
		this.item29 = item29;
	}
	public String getItem30() {
		return item30;
	}
	public void setItem30(String item30) {
		this.item30 = item30;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	 
	 
}

