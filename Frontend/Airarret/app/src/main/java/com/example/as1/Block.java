package com.example.as1;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Block class that holds a block and all of its possible attributes.
 */
public class Block {
    char block;
    boolean breathable, collidable, liquid, air;
    int itemDropped, breakable;
    Point pos;
    Bitmap image;

    /**
     * Block constructor that sets attributes and bitmaps based on the character-value given.
     * @param block The given character-value of the block.
     * @param posX The x-position of the block to be stored.
     * @param posY The y-position of the block to be stored.
     * @param res The location of the resources folder within the project.
     */
    public Block(char block, int posX, int posY, Resources res){
        this.block = block;
        pos = new Point(posX, posY);

        switch(block){

            //Grass
            case 48:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 1002;
                breakable = 1;
                image = BitmapFactory.decodeResource(res, R.drawable.grass);
                break;

            //Dirt
            case 49:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 1002;
                breakable = 1;
                image = BitmapFactory.decodeResource(res, R.drawable.dirt);
                break;

            //Stone
            case 50:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 1003;
                breakable = 1;
                image = BitmapFactory.decodeResource(res, R.drawable.stone);
                break;

            //Blackstone
            case 51:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 1004;
                breakable = 1;
                image = BitmapFactory.decodeResource(res, R.drawable.blackstone);
                break;

            //Coal
            case 65:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 2901;
                breakable = 2;
                image = BitmapFactory.decodeResource(res, R.drawable.coal);
                break;

            //Tin
            case 66:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 2902;
                breakable = 2;
                image = BitmapFactory.decodeResource(res, R.drawable.tin);
                break;

            //Copper
            case 67:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 2903;
                breakable = 2;
                image = BitmapFactory.decodeResource(res, R.drawable.copper);
                break;

            //Iron
            case 68:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 2904;
                breakable = 3;
                image = BitmapFactory.decodeResource(res, R.drawable.iron);
                break;

            //Silver
            case 69:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 2905;
                breakable = 4;
                image = BitmapFactory.decodeResource(res, R.drawable.silver);
                break;

            //Gold
            case 70:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 2906;
                breakable = 5;
                image = BitmapFactory.decodeResource(res, R.drawable.gold);
                break;

            //Platinum
            case 71:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 2907;
                breakable = 6;
                image = BitmapFactory.decodeResource(res, R.drawable.platinum);
                break;

            //Oakwood
            case 80:
                breathable = true;
                collidable = false;
                liquid = false;
                itemDropped = 3901;
                breakable = 0;
                image = BitmapFactory.decodeResource(res, R.drawable.wood);
                break;

            //Birchwood
            case 81:
                breathable = true;
                collidable = false;
                liquid = false;
                itemDropped = 3902;
                breakable = 0;
                image = BitmapFactory.decodeResource(res, R.drawable.birchwood);
                break;

            //Sprucewood
            case 82:
                breathable = true;
                collidable = false;
                liquid = false;
                itemDropped = 3903;
                breakable = 0;
                image = BitmapFactory.decodeResource(res, R.drawable.sprucewood);
                break;

            //Tallgrass
            case 97:
                breathable = true;
                collidable = false;
                liquid = false;
                itemDropped = 0;
                breakable = 0;
                image = BitmapFactory.decodeResource(res, R.drawable.tallgrass);
                break;

            //Redflower
            case 98:
                breathable = true;
                collidable = false;
                liquid = false;
                itemDropped = 0;
                breakable = 0;
                image = BitmapFactory.decodeResource(res, R.drawable.redflower);
                break;

            //Pinkflower
            case 99:
                breathable = true;
                collidable = false;
                liquid = false;
                itemDropped = 0;
                breakable = 0;
                image = BitmapFactory.decodeResource(res, R.drawable.pinkflower);
                break;

            //Chest
            case 120:
                breathable = true;
                collidable = false;
                liquid = false;
                itemDropped = 0;
                breakable = 0;
                image = BitmapFactory.decodeResource(res, R.drawable.chest);
                break;

            //Bedrock
            case 121:
                breathable = false;
                collidable = true;
                liquid = false;
                itemDropped = 0;
                breakable = 999;
                image = BitmapFactory.decodeResource(res, R.drawable.bedrock);
                break;


            default:
                breathable = true;
                collidable = false;
                liquid = false;
                itemDropped = 0;
                breakable = 999;
                image = BitmapFactory.decodeResource(res, R.drawable.air);
                break;
        }

    //Scale the bitmap
    image = Bitmap.createScaledBitmap(image, 32, 32, false);
    }
}
