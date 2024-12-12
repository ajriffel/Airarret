package com.example.as1;

/**
 * Contains all information about a character's
 * location
 * difficulty
 * appearance
 * name
 */
public class Character {
    double x;
    double y;
    String difficulty;
    int hairNum, skinNum, shirtNum, pantsNum, shoesNum;
    String characterName;

    /**
     * Constructor
     * @param x Location on x-axis
     * @param y Location on y-axis
     * @param difficulty Character difficulty
     * @param hairNum Hair-type
     * @param skinNum Skin-type
     * @param shirtNum Shirt-type
     * @param pantsNum Pants-type
     * @param shoesNum Shoes-type
     * @param CharacterName Character's name
     */
    public Character(double x, double y, String difficulty, int hairNum, int skinNum, int shirtNum, int pantsNum, int shoesNum, String CharacterName){
        this.x = x;
        this.y = y;
        this.difficulty = difficulty;
        this.hairNum = hairNum;
        this.skinNum = skinNum;
        this.shirtNum = shirtNum;
        this.pantsNum = pantsNum;
        this.shoesNum = shoesNum;
        this.characterName = CharacterName;
    }

    /**
     * Returns where the character is located on the x-axis
     * @return x
     */
    public double getX() {
        return this.x;
    }
    /**
     * Returns where the character is located on the y-axis
     * @return y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Returns the character's difficulty
     * @return difficulty
     */
    public String getCharDifficulty() {
        return this.difficulty;
    }

    /**
     * Returns the character's hair
     * @return hairNum
     */
    public int getHairNum() {
        return this.hairNum;
    }
    /**
     * Returns the character's skin
     * @return skinNum
     */
    public int getSkinNum() {
        return this.skinNum;
    }
    /**
     * Returns the character's shirt
     * @return shirtNum
     */
    public int getShirtNum() {
        return this.shirtNum;
    }
    /**
     * Returns the character's pants
     * @return pantsNum
     */
    public int getPantsNum() {
        return this.pantsNum;
    }
    /**
     * Returns the character's shoes
     * @return shoesNum
     */
    public int getShoesNum() {
        return this.shoesNum;
    }
    /**
     * Returns the character's name
     * @return characterName
     */
    public String getCharName() {
        return this.characterName;
    }

    /**
     * Sets a character's position on the x-axis
     * @param input Location on x-axis
     */
    public void setX(double input) {
        this.x = input;
    }
    /**
     * Sets a character's position on the y-axis
     * @param input Location on y-axis
     */
    public void setY(double input) {
        this.y = input;
    }
}
