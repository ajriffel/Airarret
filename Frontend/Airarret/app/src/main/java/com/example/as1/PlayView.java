package com.example.as1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.SurfaceView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Userdata;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * The class that runs the logic of the game and facilitates rendering.
 */
public class PlayView extends SurfaceView implements Runnable {
    Thread thread;
    boolean bool = false;
    int x, y, holder, h2, h3, newx, newy;
    float xdir, ydir;
    Point pos, topLeft, lastPos, targetBreak, targetPlace;
    PointF actualPos;
    float ratioX, ratioY;
    Paint paint;
    Background bg1, bg2;
    int blocksizeX, blocksizeY;
    Block[][] block;
    private Bitmap jack, jackbackwards, red, gold;
    private Bitmap[] jacks;
    Point[] PlayerPos;
    String[] characters;
    private WebSocketClient cc;
    public String TAG = PlayActivity.class.getSimpleName();
    int reload = 0;
    int jump = 0;
    char nextBlock;
//    PlayViewHelper helper;

    World world;

    /**
     * Constructor for the game that sets up everything needed before running the thread.
     * @param context context
     * @param x The width in pixels of the current screen
     * @param y The height in pixels of the current screen.
     */
    public PlayView(Context context, int x, int y){
        super(context);
        paint = new Paint();


        pos = new Point(249, 499);
        actualPos = new PointF((float)249.0, (float)499.0);
        targetBreak = new Point(499, 999);
        targetPlace = new Point(499, 999);
        PlayerPos = new Point[3];
        characters = new String[3];
        jacks = new Bitmap[3];
        this.x = x;
        this.y = y;
        blocksizeX = (int)((double)x / 32.0) + 1;
        blocksizeY = (int)((double)y / 32.0) + 1;
        topLeft = new Point(249 - (int)((double)blocksizeX / 2.0), 499 - (int)((double)blocksizeY / 2.0));
        ratioX = 2340f / x;
        ratioY = 1080f / y;
        lastPos = new Point(249, 499);

        if(Userdata.character.shirtNum == 1){
            jack = BitmapFactory.decodeResource(getResources(), R.drawable.jack);
            jack = Bitmap.createScaledBitmap(jack, 80, 112, false);
            jackbackwards = BitmapFactory.decodeResource(getResources(), R.drawable.cooljackbackwards);
            jackbackwards = Bitmap.createScaledBitmap(jackbackwards, 80, 112, false);
        }
        else{
            jack = BitmapFactory.decodeResource(getResources(), R.drawable.jack2);
            jack = Bitmap.createScaledBitmap(jack, 80, 112, false);
            jackbackwards = BitmapFactory.decodeResource(getResources(), R.drawable.jackbackwards);
            jackbackwards = Bitmap.createScaledBitmap(jackbackwards, 80, 112, false);
        }

        jacks[0] = BitmapFactory.decodeResource(getResources(), R.drawable.jack);
        jacks[0] = Bitmap.createScaledBitmap(jacks[0], 80, 112, false);

        jacks[1] = BitmapFactory.decodeResource(getResources(), R.drawable.jack);
        jacks[1] = Bitmap.createScaledBitmap(jacks[1], 80, 112, false);

        jacks[2] = BitmapFactory.decodeResource(getResources(), R.drawable.jack);
        jacks[2] = Bitmap.createScaledBitmap(jacks[2], 80, 112, false);

        red = BitmapFactory.decodeResource(getResources(), R.drawable.reghighlight); //spelled red wrong
        red = Bitmap.createScaledBitmap(red, 32, 32, false);
        gold = BitmapFactory.decodeResource(getResources(), R.drawable.goldhighlight);
        gold = Bitmap.createScaledBitmap(gold, 32, 32, false);

        bg1 = new Background(x, y, getResources());
        bg2 = new Background(x, y, getResources());

        bg1.x = 0;
        block = new Block[blocksizeX][blocksizeY];

        world = new World(Userdata.worldDiff, 1, Userdata.worldName);
        world.Map[249][500] = 120;
        playerJoin();
        if(Userdata.worldJoin != 0) {
            for(int i = 0; i < 500; i++){
                getWorldState(i);
            }
        }
        for(int i = 0; i < blocksizeX; i++){
            for(int j = 0; j < blocksizeY; j++){
                block[i][j] = new Block(world.Map[topLeft.x + i][topLeft.y + j], topLeft.x + i, topLeft.y + j, getResources());
            }
        }

    }

    /**
     * An implied what-to-do for the thread once the class has been constructed.
     */
    @Override
    public void run(){
        while(bool){
//            try {
//                thread.sleep(17);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            update();
            draw();
        }
    }

    /**
     * Handles background movement, swapping in new blocks (only if the player has moved), and keeping track of the player positions each frame.
     */
    private void update(){
        if(bg1.x + bg1.fargrounds.getWidth() < 0){
            bg1.x = x;
        }
        if(bg2.x + bg2.fargrounds.getWidth() < 0){
            bg2.x = x;
        }
        bg1.x -= 10 * ratioX;
        bg2.x -= 10 * ratioX;
        if(pos.x > (int)((double)blocksizeX / 2.0) && pos.x < 499 - (int)((double)blocksizeX / 2.0)) {
            topLeft.x = lastPos.x - (int)((double)blocksizeX / 2.0);
            topLeft.y = lastPos.y - (int)((double)blocksizeY / 2.0);
        }

        if(pos.x > lastPos.x && pos.x < 499 - (int)((double)blocksizeX / 2.0) && pos.x > (int)((double)blocksizeX / 2.0)){
            for (int i = 0; i < blocksizeX - 1; i++) {
                if (blocksizeY >= 0)
                    System.arraycopy(block[i + 1], 0, block[i], 0, blocksizeY);
            }
            for(int j = 0; j < blocksizeY; j++){
                block[blocksizeX - 1][j] = new Block(world.Map[topLeft.x + blocksizeX][topLeft.y + j], topLeft.x + blocksizeX, topLeft.y + j, getResources());
            }

            posUpdate(pos.x, pos.y);
        }
        else if(pos.x < lastPos.x && pos.x < 499 - (int)((double)blocksizeX / 2.0) && pos.x > (int)((double)blocksizeX / 2.0)){
            for (int i = blocksizeX - 1; i > 0; i--) {
                if (blocksizeY >= 0)
                    System.arraycopy(block[i - 1], 0, block[i], 0, blocksizeY);
            }
            for(int j = 0; j < blocksizeY; j++){
                block[0][j] = new Block(world.Map[topLeft.x][topLeft.y + j], topLeft.x, topLeft.y + j, getResources());
            }
            posUpdate(pos.x, pos.y);
        }
        if(pos.y < lastPos.y && pos.y < 999 - (int)((double)blocksizeY / 2.0) && pos.y > (int)(double)blocksizeY / 2.0){
            for (int i = blocksizeY - 1; i > 0; i--) {
                for(int j = 0; j < blocksizeX; j++){
                    block[j][i] = block[j][i - 1];
                }
            }
            for(int i = 0; i < blocksizeX; i++){
                block[i][0] = new Block(world.Map[topLeft.x + i][topLeft.y], topLeft.x + i, topLeft.y, getResources());
            }
            posUpdate(pos.x, pos.y);
        }
        else if(pos.y > lastPos.y && pos.y < 999 - (int)((double)blocksizeY / 2.0) && pos.y > (int)(double)blocksizeY / 2.0){
            for (int i = 0; i < blocksizeY - 1; i++) {
                for(int j = 0; j < blocksizeX; j++){
                    block[j][i] = block[j][i + 1];
                }
            }
            for(int i = 0; i < blocksizeX; i++){
                block[i][blocksizeY - 1] = new Block(world.Map[topLeft.x + i][topLeft.y + blocksizeY], topLeft.x + i, topLeft.y + blocksizeY, getResources());
            }
            posUpdate(pos.x, pos.y);
        }

        lastPos.x = pos.x;
        lastPos.y = pos.y;
        if(reload == 0){
            reload();
            reload = 1;
        }

        //x position handling
        if(xdir > 0.1){
            xdir -= .05;
            move(xdir, 0);
        }
        else if(xdir < -0.1){
            xdir += .05;
            move(xdir, 0);
        }


        //y position handling
        if(ydir > -.8){
            ydir -= .1;
        }
        if(ydir < 0){
            move(0, ydir);
            jump = 0;
        }
        if(jump == 1){
            move(0, ydir);
        }

    }

    /**
     * Handles drawing the background, current block array, and playercharacter each frame.
     */
    private void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(bg1.sky, bg1.x, 0, paint);
            canvas.drawBitmap(bg1.clouds, bg1.x, (int)(1.0 * bg1.y / 4.0), paint);
            canvas.drawBitmap(bg1.sea, bg1.x, (int)(bg1.y / 2.0), paint);
            canvas.drawBitmap(bg1.fargrounds, bg1.x, (int)(bg1.y / 2.0), paint);
            canvas.drawBitmap(bg2.sky, bg2.x, 0, paint);
            canvas.drawBitmap(bg2.clouds, bg2.x, (int)(1.0 * bg2.y / 4.0), paint);
            canvas.drawBitmap(bg2.sea, bg2.x, (int)(bg2.y / 2.0), paint);
            canvas.drawBitmap(bg2.fargrounds, bg2.x, (int)(bg2.y / 2.0), paint);

            for(int i = blocksizeX - 1; i >= 0; i--){
                for(int j = blocksizeY - 1; j >= 0; j--){
                    canvas.drawBitmap(block[(blocksizeX - 1) - i][(blocksizeY - 1) - j].image, i * 32, (j + 1) * 32, paint);
                }
            }
            if(pos.x > 499 - (int)((double)blocksizeX / 2.0)){
                h2 = pos.x;
                h3 = (int)((double)blocksizeY / 2.0);
                if(xdir > 0.0){
                    canvas.drawBitmap(jackbackwards, ((499 - pos.x) * 32) - 44, (int)(y / 2.0) - 108, paint);

                    //Paint red highlights based on direction
                    if(block[h2][h3 + 3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3 + 3;
                    }
                    else if(block[h2 + 1][h3 + 2].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 2) * 32, (h3 - 2) * 32, paint);
                        targetBreak.x = h2 + 1;
                        targetBreak.y = h3 + 2;
                    }
                    else if(block[h2 + 1][h3 + 1].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 2) * 32, (h3 - 1) * 32, paint);
                        targetBreak.x = h2 + 1;
                        targetBreak.y = h3 + 1;
                    }
                    else if(block[h2][h3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, h3 * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3;
                    }

                    //Paint gold highlights based on direction
                    if(block[h2 + 1][h3 + 1].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 2) * 32, (h3 - 1) * 32, paint);
                        targetPlace.x = h2 + 1;
                        targetPlace.y = h3 + 1;
                    }
                    else if(block[h2 + 1][h3 + 2].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 2) * 32, (h3 - 2) * 32, paint);
                        targetPlace.x = h2 + 1;
                        targetPlace.y = h3 + 2;
                    }
                    else if(block[h2 + 1][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 2) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2 + 1;
                        targetPlace.y = h3 + 3;
                    }
                    else if(block[h2][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2;
                        targetPlace.y = h3 + 3;
                    }
                }
                else{
                    canvas.drawBitmap(jack, ((499 - pos.x) * 32) - 44, (int)(y / 2.0) - 108, paint);

                    //Paint red highlights based on direction
                    if(block[h2][h3 + 3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3 + 3;
                    }
                    else if(block[h2 - 1][h3 + 2].breakable < 999){
                        canvas.drawBitmap(red, (h2) * 32, (h3 - 2) * 32, paint);
                        targetBreak.x = h2 - 1;
                        targetBreak.y = h3 + 2;
                    }
                    else if(block[h2 - 1][h3 + 1].breakable < 999){
                        canvas.drawBitmap(red, (h2) * 32, (h3 - 1) * 32, paint);
                        targetBreak.x = h2 - 1;
                        targetBreak.y = h3 + 1;
                    }
                    else if(block[h2][h3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, h3 * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3;
                    }

                    //Paint gold highlights based on direction
                    if(block[h2 - 1][h3 + 1].block == 'z'){
                        canvas.drawBitmap(gold, (h2) * 32, (h3 - 1) * 32, paint);
                        targetPlace.x = h2 - 1;
                        targetPlace.y = h3 + 1;
                    }
                    else if(block[h2 - 1][h3 + 2].block == 'z'){
                        canvas.drawBitmap(gold, (h2) * 32, (h3 - 2) * 32, paint);
                        targetPlace.x = h2 - 1;
                        targetPlace.y = h3 + 2;
                    }
                    else if(block[h2 - 1][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2 - 1;
                        targetPlace.y = h3 + 3;
                    }
                    else if(block[h2][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2;
                        targetPlace.y = h3 + 3;
                    }
                }
            }
            else if (pos.x < (int) ((double) blocksizeX / 2.0)) {
                h2 = pos.x;
                h3 = (int)((double)blocksizeY / 2.0);
                if(xdir > 0.0){
                    canvas.drawBitmap(jackbackwards,  x - 44 - (pos.x * 32), (int)(y / 2.0) - 108, paint);

                    //Paint red highlights based on direction
                    if(block[h2][h3 + 3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3 + 3;
                    }
                    else if(block[h2 + 1][h3 + 2].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 2) * 32, (h3 - 2) * 32, paint);
                        targetBreak.x = h2 + 1;
                        targetBreak.y = h3 + 2;
                    }
                    else if(block[h2 + 1][h3 + 1].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 2) * 32, (h3 - 1) * 32, paint);
                        targetBreak.x = h2 + 1;
                        targetBreak.y = h3 + 1;
                    }
                    else if(block[h2][h3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, h3 * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3;
                    }

                    //Paint gold highlights based on direction
                    if(block[h2 + 1][h3 + 1].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 2) * 32, (h3 - 1) * 32, paint);
                        targetPlace.x = h2 + 1;
                        targetPlace.y = h3 + 1;
                    }
                    else if(block[h2 + 1][h3 + 2].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 2) * 32, (h3 - 2) * 32, paint);
                        targetPlace.x = h2 + 1;
                        targetPlace.y = h3 + 2;
                    }
                    else if(block[h2 + 1][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 2) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2 + 1;
                        targetPlace.y = h3 + 3;
                    }
                    else if(block[h2][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2;
                        targetPlace.y = h3 + 3;
                    }
                }
                else{
                    canvas.drawBitmap(jack, x- 44 - (pos.x * 32), (int)(y / 2.0) - 108, paint);

                    //Paint red highlights based on direction
                    if(block[h2][h3 + 3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3 + 3;
                    }
                    else if(block[h2 - 1][h3 + 2].breakable < 999){
                        canvas.drawBitmap(red, (h2) * 32, (h3 - 2) * 32, paint);
                        targetBreak.x = h2 - 1;
                        targetBreak.y = h3 + 2;
                    }
                    else if(block[h2 - 1][h3 + 1].breakable < 999){
                        canvas.drawBitmap(red, (h2) * 32, (h3 - 1) * 32, paint);
                        targetBreak.x = h2 - 1;
                        targetBreak.y = h3 + 1;
                    }
                    else if(block[h2][h3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, h3 * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3;
                    }

                    //Paint gold highlights based on direction
                    if(block[h2 - 1][h3 + 1].block == 'z'){
                        canvas.drawBitmap(gold, (h2) * 32, (h3 - 1) * 32, paint);
                        targetPlace.x = h2 - 1;
                        targetPlace.y = h3 + 1;
                    }
                    else if(block[h2 - 1][h3 + 2].block == 'z'){
                        canvas.drawBitmap(gold, (h2) * 32, (h3 - 2) * 32, paint);
                        targetPlace.x = h2 - 1;
                        targetPlace.y = h3 + 2;
                    }
                    else if(block[h2 - 1][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2 - 1;
                        targetPlace.y = h3 + 3;
                    }
                    else if(block[h2][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2;
                        targetPlace.y = h3 + 3;
                    }
                }
            }
            else {
                h2 = (int)((double)blocksizeX / 2.0);
                h3 = (int)((double)blocksizeY / 2.0);

                if (xdir > 0.0) {
                    canvas.drawBitmap(jackbackwards, (int) (x / 2.0) - 44, (int) (y / 2.0) - 108, paint);
                    //Paint red highlights based on direction
                    if(block[h2][h3 + 3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3 + 3;
                    }
                    else if(block[h2 + 1][h3 + 2].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 2) * 32, (h3 - 2) * 32, paint);
                        targetBreak.x = h2 + 1;
                        targetBreak.y = h3 + 2;
                    }
                    else if(block[h2 + 1][h3 + 1].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 2) * 32, (h3 - 1) * 32, paint);
                        targetBreak.x = h2 + 1;
                        targetBreak.y = h3 + 1;
                    }
                    else if(block[h2][h3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, h3 * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3;
                    }

                    //Paint gold highlights based on direction
                    if(block[h2 + 1][h3 + 1].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 2) * 32, (h3 - 1) * 32, paint);
                        targetPlace.x = h2 + 1;
                        targetPlace.y = h3 + 1;
                    }
                    else if(block[h2 + 1][h3 + 2].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 2) * 32, (h3 - 2) * 32, paint);
                        targetPlace.x = h2 + 1;
                        targetPlace.y = h3 + 2;
                    }
                    else if(block[h2 + 1][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 2) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2 + 1;
                        targetPlace.y = h3 + 3;
                    }
                    else if(block[h2][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2;
                        targetPlace.y = h3 + 3;
                    }

                } else {
                    canvas.drawBitmap(jack, (int) (x / 2.0) - 44, (int) (y / 2.0) - 108, paint);
                    //Paint red highlights based on direction
                    if(block[h2][h3 + 3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3 + 3;
                    }
                    else if(block[h2 - 1][h3 + 2].breakable < 999){
                        canvas.drawBitmap(red, (h2) * 32, (h3 - 2) * 32, paint);
                        targetBreak.x = h2 - 1;
                        targetBreak.y = h3 + 2;
                    }
                    else if(block[h2 - 1][h3 + 1].breakable < 999){
                        canvas.drawBitmap(red, (h2) * 32, (h3 - 1) * 32, paint);
                        targetBreak.x = h2 - 1;
                        targetBreak.y = h3 + 1;
                    }
                    else if(block[h2][h3].breakable < 999){
                        canvas.drawBitmap(red, (h2 - 1) * 32, h3 * 32, paint);
                        targetBreak.x = h2;
                        targetBreak.y = h3;
                    }

                    //Paint gold highlights based on direction
                    if(block[h2 - 1][h3 + 1].block == 'z'){
                        canvas.drawBitmap(gold, (h2) * 32, (h3 - 1) * 32, paint);
                        targetPlace.x = h2 - 1;
                        targetPlace.y = h3 + 1;
                    }
                    else if(block[h2 - 1][h3 + 2].block == 'z'){
                        canvas.drawBitmap(gold, (h2) * 32, (h3 - 2) * 32, paint);
                        targetPlace.x = h2 - 1;
                        targetPlace.y = h3 + 2;
                    }
                    else if(block[h2 - 1][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2 - 1;
                        targetPlace.y = h3 + 3;
                    }
                    else if(block[h2][h3 + 3].block == 'z'){
                        canvas.drawBitmap(gold, (h2 - 1) * 32, (h3 - 3) * 32, paint);
                        targetPlace.x = h2;
                        targetPlace.y = h3 + 3;
                    }
                }
            }

            //Oh boy, rendering in other players
            for(int i = 0; i < 3; i++){
                if(PlayerPos[i] != null){
                    //Checking if player should actually be rendered in
                    if(PlayerPos[i].x < pos.x + (blocksizeX / 2.0) && PlayerPos[i].x > pos.x - (blocksizeX / 2.0) && PlayerPos[i].y < pos.y + (blocksizeY / 2.0) && PlayerPos[i].y > pos.y - (blocksizeY / 2.0)){
                        //Getting relative positions
                        h2 = PlayerPos[i].x - pos.x;
                        h3 = PlayerPos[i].y - pos.y;

                        //Draw the bitmap with those positions relative to your own character
                        canvas.drawBitmap(jacks[i], (int) (x / 2.0) - 44 - (h2 * 32), (int) (y / 2.0) - 108 -  (h3 * 32), paint);
                    }
                }
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Reloads the current block array assuming the world's Map has changed.
     */
    private void reload(){
        for(int i = 0; i < blocksizeX; i++){
            for(int j = 0; j < blocksizeY; j++){
                block[i][j] = new Block(world.Map[topLeft.x + i][topLeft.y + j], topLeft.x + i, topLeft.y + j, getResources());
            }
        }
    }

    /**
     * Iterates the actual position in xdir, takes player position as a casted int to facilitate easier rendering for now.
     * @param xdir The xdirection and amount to move the character (negative: left, positive: right).
     */
    public void move(float xdir, float ydir){
        if(xdir != 0){
            if((xdir < 0 && pos.x > 0 && pos.x <= 499 && (!block[(int)((double)blocksizeX / 2.0) - 1][(int)((double)blocksizeY / 2.0) + 1].collidable && !block[(int)((double)blocksizeX / 2.0) - 1][(int)((double)blocksizeY / 2.0) + 2].collidable)) || (xdir >= 0 && pos.x >= 0 && pos.x < 499 && (!block[(int)((double)blocksizeX / 2.0) + 1][(int)((double)blocksizeY / 2.0) + 1].collidable && !block[(int)((double)blocksizeX / 2.0) + 1][(int)((double)blocksizeY / 2.0) + 2].collidable))){
                actualPos.x = actualPos.x + xdir;
                pos.x = (int)actualPos.x;
                this.xdir = xdir;
            }
        }
        if(ydir != 0){
            if((ydir < 0 && pos.y > 0 && pos.y <= 999 &&(!block[(int)((double)blocksizeX / 2.0)][(int)((double)blocksizeY / 2.0)].collidable)) || (ydir > 0 && pos.y >= 0 && pos.y < 999 && (!block[(int)((double)blocksizeX / 2.0)][(int)((double)blocksizeY / 2.0) + 2].collidable))) {
                actualPos.y += ydir;
                pos.y = (int) actualPos.y;
                this.ydir = ydir;
                if(ydir == 1){
                    jump = 1;
                }
            }
        }

    }

    /**
     * Pauses the thread.
     */
    public void pause(){
        try{
            bool = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resumes the thread.
     */
    public void resume(){
        bool = true;
        thread = new Thread(this);
        thread.start();
    }


    /**
     * Gets 1000 chars of the world state string from the backend starting from x.
     * @param x The index at which to start grabbing the substring of the world state from.
     */
    private void getWorldState(int x){
        String URL_POST_GET_WORLD = "http://coms-309-032.class.las.iastate.edu:8080/getWorld/";
        URL_POST_GET_WORLD += Userdata.worldid + "/" + (x * 1000) + "/" + ((x * 1000) + 1000);

        StringRequest objReq = new StringRequest(Request.Method.GET,
                URL_POST_GET_WORLD,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String resp) {
                        Log.d(TAG, resp);
                        for(int j = 0; j < 1000; j++){
                            world.Map[x][j] = resp.charAt(j);
                        }
//                        if(x == 499){
//                            getWorldUsers();
//                            getWorldPositions();
//                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
        };

        // Adding request to request queue
        String tag_string_req = "string_req";
        AppController.getInstance().addToRequestQueue(objReq, tag_string_req);
    }


    //WEBSOCKET WORK

    private void playerJoin() {

            Draft[] drafts = {
                    new Draft_6455()
            };
            String w = "ws://coms-309-032.class.las.iastate.edu:8080/worldsocket/"+ Userdata.username + "/" + Userdata.charname + "/" + Userdata.worldid;
            StringBuilder s = new StringBuilder(20);
            try {
                Log.d("Socket:", "Trying socket");
                cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                    @Override
                    public void onMessage(String message) {
                        Log.d("", "run() returned: " + message);
                        if(message.charAt(0) == 'P') {
                            holder = message.indexOf('.');
                            for(int i = holder + 1; i < message.length(); i++){
                                s.append(message.charAt(i));
                            }
                            h3 = characterSort(String.valueOf(s));
                            s.setLength(0);
                            h2 = message.indexOf(',');
                            for(int i = 1; i < h2; i++){
                                s.append(message.charAt(i));
                            }
                            newx = Integer.parseInt(String.valueOf(s));
                            s.setLength(0);
                            for(int i = h2 + 1; i < holder; i++){
                                s.append(message.charAt(i));
                            }
                            newy = Integer.parseInt(String.valueOf(s));
                            s.setLength(0);
                            if(PlayerPos[h3] == null){
                                PlayerPos[h3] = new Point(newx, newy);
                            }
                            else{
                                PlayerPos[h3].x = newx;
                                PlayerPos[h3].y = newy;
                            }
                        }
                        else if(message.charAt(0) == 'B') {
                            holder = message.indexOf(':');
                            h2 = message.indexOf(',');
                            for(int i = 1; i < h2; i++){
                                s.append(message.charAt(i));
                            }
                            newx = Integer.parseInt(String.valueOf(s));
                            s.setLength(0);
                            for(int i = h2 + 1; i < holder; i++){
                                s.append(message.charAt(i));
                            }
                            newy = Integer.parseInt(String.valueOf(s));
                            s.setLength(0);
                            world.Map[newx][newy] = message.charAt(holder + 1);
                            //render in new block if need be (NEEDS TO BE FIXED IF TIME ALLOWS)
                            if(newx < pos.x + (blocksizeX / 2.0) && newx > pos.x - (blocksizeX / 2.0) && newy < pos.y + (blocksizeY / 2.0) && newy > pos.y - (blocksizeY / 2.0)){
                                reload = 0;
                            }
                        }
                    }

                    @Override
                    public void onOpen(ServerHandshake handshake) {
                        Log.d("OPEN", "run() returned: " + "is connecting");
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        Log.d("CLOSE", "onClose() returned: " + reason);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("Exception:", e.toString());
                        s.setLength(0);
                    }
                };
            } catch (URISyntaxException e) {
                Log.d("Exception:", e.getMessage());
                e.printStackTrace();
            }
            cc.connect();

    }

    private void posUpdate(int x, int y) {
        try {
            cc.send("P" + x + "," + y + "." + Userdata.charname);
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", "Could not send pos");
        }
    }

    public void blockUpdate(int x, int y, char c) {
        try {
            cc.send("B" + x + "," + y + ":" + c);
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", "Could not send block update");
        }
    }

    private int characterSort(String charname){
        if(characters[0] == null) {
            characters[0] = charname;
        }
        else if(characters[1] == null){
            if(characters[0].equals(charname)){
                return 0;
            }
            characters[1] = characters[0];
            PlayerPos[1] = PlayerPos[0];
            characters[0] = charname;
        }
        else if(characters[2] == null){
            for (int i = 0; i < 2; i++) {
                if (characters[i].equals(charname)) {
                    return i;
                }
            }
            characters[2] = characters[1];
            PlayerPos[2] = PlayerPos[1];
            characters[1] = characters[0];
            PlayerPos[1] = PlayerPos[0];
            characters[0] = charname;
        }
        else {
            for (int i = 0; i < 3; i++) {
                if (characters[i].equals(charname)) {
                    return i;
                }
            }
            //Newest is first index, others are moved "down"
            characters[2] = characters[1];
            PlayerPos[2] = PlayerPos[1];
            characters[1] = characters[0];
            PlayerPos[1] = PlayerPos[0];
            characters[0] = charname;
        }
        return 0;
    }

}
