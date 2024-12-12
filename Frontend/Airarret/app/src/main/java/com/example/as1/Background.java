package com.example.as1;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * A Background class that holds a background to be used in the PlayView.
 */
public class Background {
    int x, y;
    Bitmap clouds;
    Bitmap sky;
    Bitmap sea;
    Bitmap fargrounds;

    /**
     * Background constructor that instantiates the bitmaps of the background to be used.
     * @param x The width of the screen in pixels.
     * @param y The height of the screen in pixels.
     * @param res The location of the resources folder of the project.
     */
    Background (int x, int y, Resources res){
        this.x = x;
        this.y = y;
        fargrounds = BitmapFactory.decodeResource(res, R.drawable.fargrounds);
        fargrounds = Bitmap.createScaledBitmap(fargrounds, x, (int)(y / 2.0), false);

        sea = BitmapFactory.decodeResource(res, R.drawable.sea);
        sea = Bitmap.createScaledBitmap(sea, x, (int)(y / 2.0), false);

        clouds = BitmapFactory.decodeResource(res, R.drawable.clouds);
        clouds = Bitmap.createScaledBitmap(clouds, x, (int)(3 * y / 4.0), false);

        sky = BitmapFactory.decodeResource(res, R.drawable.sky);
        sky = Bitmap.createScaledBitmap(sky, x, y, false);

    }
}
