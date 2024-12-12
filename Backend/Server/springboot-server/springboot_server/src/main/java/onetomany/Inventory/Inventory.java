package onetomany.Inventory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory {

	 private @Id @GeneratedValue int id;
	    private String username;
	    private String characterName;
	    private String slot1;
	    private String slot2;
	    private String slot3;
	    private String slot4;
	    private String slot5;
	    private String slot6;
	    private String slot7;
	    private String slot8;
	    private String slot9;
	    private String slot10;
	    private String slot11;
	    private String slot12;
	    private String slot13;
	    private String slot14;
	    private String slot15;
	    private String slot16;
	    private String slot17;
	    private String slot18;
	    private String slot19;
	    private String slot20;
	    private String slot21;
	    private String slot22;
	    private String slot23;
	    private String slot24;
	    private String slot25;
	    private String slot26;
	    private String slot27;
	    private int selected;
	    public Inventory() {
	    }
	    public Inventory(String username, 
	                String characterName,
	                String slot1,
	                String slot2,
	                String slot3,
	                String slot4,
	                String slot5,
	                String slot6,
	                String slot7,
	                String slot8,
	                 String slot9,
	                 String slot10,
	                 String slot11,
	                 String slot12,
	                 String slot13,
	                 String slot14,
	                 String slot15,
	                 String slot16,
	                 String slot17,
	                 String slot18,
	                 String slot19,
	                 String slot20,
	                 String slot21,
	                 String slot22,
	                 String slot23,
	                 String slot24,
	     			String slot25,
	     			String slot26,
	     			String slot27,
	     			int selected) {
	    	this.username = username;
	    	this.characterName = characterName;
	        this.slot1 = slot1;
	        this.slot2 = slot2;
	        this.slot3 = slot3;
	        this.slot4 = slot4;
	        this.slot5 = slot5;
	        this.slot6 = slot6;
	        this.slot7 = slot7;
	        this.slot8 = slot8;
	        this.slot9 = slot9;
		    this.slot10 = slot10;
		    this.slot11 = slot11;
		    this.slot12 = slot12;
		    this.slot13 = slot13;
		    this.slot14 = slot14;
		    this.slot15 = slot15;
		    this.slot16 = slot16;
		    this.slot17 = slot17;
		    this.slot18 = slot18;
		    this.slot19 = slot19;
		    this.slot20 = slot20;
		    this.slot21 = slot21;
		    this.slot22 = slot22;
		    this.slot23 = slot23;
		    this.slot24 = slot24;
		    this.slot25 = slot25;
		    this.slot26 = slot26;
		    this.slot27 = slot27;
	        this.selected = selected;
	    }
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getCharacterName() {
			return characterName;
		}
		public void setCharacterName(String characterName) {
			this.characterName = characterName;
		}
		public String getSlot1() {
			return slot1;
		}
		public void setSlot1(String slot1) {
			this.slot1 = slot1;
		}
		public String getSlot2() {
			return slot2;
		}
		public void setSlot2(String slot2) {
			this.slot2 = slot2;
		}
		public String getSlot3() {
			return slot3;
		}
		public void setSlot3(String slot3) {
			this.slot3 = slot3;
		}
		public String getSlot4() {
			return slot4;
		}
		public void setSlot4(String slot4) {
			this.slot4 = slot4;
		}
		public String getSlot5() {
			return slot5;
		}
		public void setSlot5(String slot5) {
			this.slot5 = slot5;
		}
		public String getSlot6() {
			return slot6;
		}
		public void setSlot6(String slot6) {
			this.slot6 = slot6;
		}
		public String getSlot7() {
			return slot7;
		}
		public void setSlot7(String slot7) {
			this.slot7 = slot7;
		}
		public String getSlot8() {
			return slot8;
		}
		public void setSlot8(String slot8) {
			this.slot8 = slot8;
		}
		public String getSlot9() {
			return slot9;
		}
		public void setSlot9(String slot9) {
			this.slot9 = slot9;
		}
		public String getSlot10() {
			return slot10;
		}
		public void setSlot10(String slot10) {
			this.slot10 = slot10;
		}
		public String getSlot11() {
			return slot11;
		}
		public void setSlot11(String slot11) {
			this.slot11 = slot11;
		}
		public String getSlot12() {
			return slot12;
		}
		public void setSlot12(String slot12) {
			this.slot12 = slot12;
		}
		public String getSlot13() {
			return slot13;
		}
		public void setSlot13(String slot13) {
			this.slot13 = slot13;
		}
		public String getSlot14() {
			return slot14;
		}
		public void setSlot14(String slot14) {
			this.slot14 = slot14;
		}
		public String getSlot15() {
			return slot15;
		}
		public void setSlot15(String slot15) {
			this.slot15 = slot15;
		}
		public String getSlot16() {
			return slot16;
		}
		public void setSlot16(String slot16) {
			this.slot16 = slot16;
		}
		public String getSlot17() {
			return slot17;
		}
		public void setSlot17(String slot17) {
			this.slot17 = slot17;
		}
		public String getSlot18() {
			return slot18;
		}
		public void setSlot18(String slot18) {
			this.slot18 = slot18;
		}
		public String getSlot19() {
			return slot19;
		}
		public void setSlot19(String slot19) {
			this.slot19 = slot19;
		}
		public String getSlot20() {
			return slot20;
		}
		public void setSlot20(String slot20) {
			this.slot20 = slot20;
		}
		public String getSlot21() {
			return slot21;
		}
		public void setSlot21(String slot21) {
			this.slot21 = slot21;
		}
		public String getSlot22() {
			return slot22;
		}
		public void setSlot22(String slot22) {
			this.slot22 = slot22;
		}
		public String getSlot23() {
			return slot23;
		}
		public void setSlot23(String slot23) {
			this.slot23 = slot23;
		}
		public String getSlot24() {
			return slot24;
		}
		public void setSlot24(String slot24) {
			this.slot24 = slot24;
		}
		public String getSlot25() {
			return slot25;
		}
		public void setSlot25(String slot25) {
			this.slot25 = slot25;
		}
		public String getSlot26() {
			return slot26;
		}
		public void setSlot26(String slot26) {
			this.slot26 = slot26;
		}
		public String getSlot27() {
			return slot27;
		}
		public void setSlot27(String slot27) {
			this.slot27 = slot27;
		}
		public int getSelected() {
			return selected;
		}
		public void setSelected(int selected) {
			this.selected = selected;
		}
		
	    
	    
}
