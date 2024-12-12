package com.example.as1;

import android.graphics.Point;

import java.lang.Object;
import java.util.Arrays;
import java.util.Random;

/**
 * A World class that holds the map, player info and world info.
 */
public class World {
    public char[][] Map;
    public int p2, p3, p4;
    public int activity, world_type;
    public String name;
    public char difficulty;

    /**
     * Takes a world and clones it to your current for use (ASSUMES ARRAY IS FULLY INITIALIZED)
     * @param world The state of the world.
     * @param pos The position of the world's owner.
     * @param owner The owner's characterid.
     * @param player2 Player2's characterid.
     * @param player3 Player3's characterid.
     * @param player4 Player4's characterid.
     * @param difficulty The world's difficulty.
     * @param name The name of the world.
     */
    public World(String world, Point pos, int owner, int player2, int player3, int player4, char difficulty, String name){
        for(int i = 0; i < 500; i++){
            for(int j = 0; j < 1000; j++){
                if(i == 0){
                    this.Map[i][j] = world.charAt(j);
                }
                else{
                    this.Map[i][j] = world.charAt((i * 500) + j);
                }
            }
        }

        this.difficulty = difficulty;
        activity = 1;
        this.name = name;
    }

    /**
     * Generates a random SUPERFLAT world.
     * @param difficulty The difficulty of the world.
     * @param world_type The type of the world (assumed to be 1).
     * @param name The name of the world.
     */
    public World(char difficulty, int world_type, String name){
        this.difficulty = difficulty;
        this.world_type = world_type;
        Map =  new char[500][1000];
        for(int i = 0; i < 500; i++){
            for(int j = 0; j < 1000; j++){
                Map[i][j] = 122; //122 will be the character value for Air, which has the breathable attribute
            }
        }

        this.name = name;
        if(world_type == 1) {
            SuperFlatWorld();
        }
    }

    /**
     * Generates a SUPERFLAT world procedurally.
     */
    private void SuperFlatWorld(){
        //Generate Grass blocks (grass blocks are only grass when exposed to "sun"
        for(int i = 0; i < 499; i++){
            Map[i][499] = 48; //Grass = 48
        }

        //Generate 6 layers of dirt blocks
        for(int i = 0; i < 499; i++){
            for(int j = 0; j < 6; j++){
                Map[i][498 - j] = 49; //Dirt = 49
            }
        }

        //Generate Stone to about the halfway point
        for(int i = 0; i < 499; i++){
            for(int j = 0; j < 243; j++){
                Map[i][492 - j] = 50; //Stone = 50
            }
        }

        //Generate Blackstone to near the bottom
        for(int i = 0; i < 499; i++){
            for(int j = 0; j < 250; j++){
                Map[i][249 - j] = 51; //Blackstone = 51
            }
        }

        //Generate Bedrock at the bottom
        for(int i = 0; i < 499; i++){
            Map[i][0] = 121; //Bedrock = 121
        }

        //Time for modular ores and whatnot. For now, we'll do coal, tin, copper, iron, silver, gold, and platinum.
        generateOres();

        generateTrees();

        generateShrubbery((char)97);
        generateShrubbery((char)98);
        generateShrubbery((char)99);

        //TODO: ADD MORE BLOCK GENERATION

    }

    /**
     * Method to generate all mineable ores in the form of large "deposits".
     */
    private void generateOres(){
        //Deposits for coal

        generateDeposits((char)65, 260, 492);

        //Deposits for tin
        generateDeposits((char)66, 100, 492);

        //Deposits for copper
        generateDeposits((char)67, 200, 442);

        //Deposits for iron
        generateDeposits((char)68, 240, 402);

        //Deposits for silver
        generateDeposits((char)69, 200, 342);

        //Deposits for gold
        generateDeposits((char)70, 200, 200);

        //Deposits for platinum
        generateDeposits((char)71, 100, 100);

    }

    //Method to procedurally generate an ore based on its number and range of available spots

    /**
     * Method to procedurally generate an ore based on its number and range of available spots
     * @param ore The character-value of the ore to be generated.
     * @param range The y-range of where the ore is available to be found.
     * @param start The starting y-value of the range.
     */
    private void generateDeposits(char ore, int range, int start){
        Random rand = new Random();
        int[][] deposits = new int[range / 20][2];
        int correct = 0;


        while(correct == 0) {
            //choose an x and y value to make the deposit at
            for (int i = 0; i < (range / 20); i++) {
                deposits[i][0] = (rand.nextInt(24) + 1) * 20; //x Value
                deposits[i][1] = start - ((rand.nextInt(range / 10) + 1) * 10); //y Value
            }

            //Make sure deposits aren't too close together
            for (int i = 0; i < (range / 20) - 2; i++) {
                if(magnitude(deposits[i][0], deposits[i][1]) -  magnitude(deposits[i + 1][0], deposits[i + 1][1]) > 20){
                    if(i == (range / 20) - 3){
                        correct = 1;
                    }
                }
            }
        }
        //Generate deposits from their random positions (might make double deposits but shouldn't be too bad)
        for(int i = 0; i < (range / 20) - 1; i++){
            generateDeposit(ore, deposits[i][0], deposits[i][1]);
        }

    }

    /**
     * Method to procedurally generate a deposit of ores with a given point
     * @param ore The character-value of the ore to be generated.
     * @param x The x-value of the deposit.
     * @param y The y-value of the deposit.
     */
    private void generateDeposit(char ore, int x, int y){
        Random rand = new Random();
        Map[x][y] = ore;
        //deposit has a 30 block radius surrounding it
        for(int i = 1; i < 31; i++){
            for(int j = 1; j < 31; j++){
                if(x + i < 499 && x - i >= 0 && y + j < 492 && y - j > 0){
                    //Will modify this later. For now, it's a guaranteed chance in the middle and less when it travels outwards.
                    //TODO: Make more natural
                    if((double)rand.nextInt(i) / 2.0 <= 2.0){
                        if((double)rand.nextInt(j) / 2.0 <= 2.0){
                            Map[x + i][y + j] = ore;
                            Map[x + i][y - j] = ore;
                            Map[x - i][y + j] = ore;
                            Map[x - i][y - j] = ore;
                        }
                    }
                }
            }
        }
    }

    /**
     * Randomly generates trees on layer 500. WARNING: Needs changes for different types of worlds.
     */
    private void generateTrees(){
        Random rand = new Random();
        for(int i = 0; i < 499; i++){
            if(rand.nextInt(10) == 0 && Map[i][500] == 122){
                switch(rand.nextInt(3)){
                    case 0:
                        for(int j = 500; j < 500 + rand.nextInt(4) + 5; j++){
                            Map[i][j] = 80;
                        }
                        break;
                    case 1:
                        for(int j = 500; j < 500 + rand.nextInt(4) + 4; j++){
                            Map[i][j] = 81;
                        }
                        break;
                    case 2:
                        for(int j = 500; j < 500 + rand.nextInt(4) + 8; j++){
                            Map[i][j] = 82;
                        }
                        break;
                }
                i += 5;
            }
        }
    }

    /**
     * Randomly generates flowers and grass on layer 500. WARNING: Needs changes for different types of worlds.
     * @param shrub The character-value of the "shrub" to be used.
     */
    private void generateShrubbery(char shrub){
        Random rand = new Random();
        for(int i = 0; i < 499; i++){
            if(rand.nextInt(9) == 0 && Map[i][500] == 122){
                Map[i][500] = shrub;
            }
        }
    }

    //Helper method

    /**
     * A helper method to get the magnitude of the distance between two blocks.
     * @param x The x-value for the equation (x2 - x1).
     * @param y The y-value for the equation (y2 - y1).
     * @return Returns the total distance between the two points assuming correct inputs.
     */
    private int magnitude(int x, int y){
        return (int)(Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0)));
    }

}
