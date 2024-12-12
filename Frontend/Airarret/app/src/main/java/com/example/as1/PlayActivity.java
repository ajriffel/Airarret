package com.example.as1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.RepeatListener;
import com.example.as1.utils.Userdata;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity that holds the "PlayView" which is basically the game to be run. Utilized mostly so that UI can be more easily added, as well as for thread management.
 */
public class PlayActivity extends AppCompatActivity {

    private PlayView gameView;

    private ProgressDialog pDialog;

    private String tag_string_req = "string_req";
    private String tag_json_obj = "jobj_req";
    public String TAG = PlayActivity.class.getSimpleName();
    private AlertDialog.Builder builder;

    /**
     * Method that runs whenever this PlayActivity is called - instantiates dynamic classes for use.
     * @param savedInstanceState The state which the activity is being added to.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        builder = new AlertDialog.Builder(PlayActivity.this);
        builder.setCancelable(true);

        FrameLayout game = new FrameLayout(this);
        gameView = new PlayView(this, point.x, point.y);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        if(Userdata.worldJoin == 0){
            createWorld();
        }
        else{
            //playerJoin();
        }

        //Layout across the screen
        LinearLayout screen = new LinearLayout(this);
        screen.setOrientation(LinearLayout.HORIZONTAL);
        //Column 1
        LinearLayout col1 = new LinearLayout(this);
        col1.setOrientation(LinearLayout.VERTICAL);
        //Column 2
        LinearLayout col2 = new LinearLayout(this);
        col2.setOrientation(LinearLayout.VERTICAL);
        //Column 3
        LinearLayout col3 = new LinearLayout(this);
        col3.setOrientation(LinearLayout.VERTICAL);
        //Column 4
        LinearLayout col4 = new LinearLayout(this);
        col4.setOrientation(LinearLayout.VERTICAL);
        //Column 5
        LinearLayout col5 = new LinearLayout(this);
        col5.setOrientation(LinearLayout.VERTICAL);
        //Column 6
        LinearLayout col6 = new LinearLayout(this);
        col6.setOrientation(LinearLayout.VERTICAL);
        //Column 7
        LinearLayout col7 = new LinearLayout(this);
        col7.setOrientation(LinearLayout.VERTICAL);
        //Column 8


        //padding
        TextView col1pad = new TextView(this);
        col1pad.setText("\n\n\n\n\n\n\n\n\n\n");
        TextView col2pad = new TextView(this);
        col2pad.setText("\n\n\n\n\n\n\n\n\n\n");
        TextView col3pad = new TextView(this);
        col3pad.setText("\n\n\n\n\n\n\n\n\n\n"); //27 spaces
        TextView col4pad = new TextView(this);
        col4pad.setText("                           ");
        TextView col5pad = new TextView(this);
        col5pad.setText("                           ");
        TextView col6pad = new TextView(this);
        col6pad.setText("                                  ");//34 spaces
        TextView col7pad = new TextView(this);
        col7pad.setText("\n\n\n\n\n\n");

        //buttons
        Button right = new Button(this);
        right.setText("right");
        Button jump = new Button(this);
        jump.setText("jump");
        Button left = new Button(this);
        left.setText("left");
        Button placeBlock = new Button(this);
        placeBlock.setText("place\nblock");
        Button changeBlock = new Button(this);
        changeBlock.setText("break\nblock");
        Button chest = new Button(this);
        chest.setText("add chest");
        Button removeChest = new Button(this);
        removeChest.setText("rmv chest");
        Button inventory = new Button(this);
        inventory.setText("Inventory");
        Button leave = new Button(this);
        leave.setText("leave");


        col1.addView(chest);
        col1.addView(col1pad);
        col1.addView(left);

        col2.addView(removeChest);
        col2.addView(col2pad);
        col2.addView(jump);

        col3.addView(inventory);
        col3.addView(col3pad);
        col3.addView(right);

        col4.addView(col4pad);
        col5.addView(col5pad);
        col6.addView(col6pad);

        col7.addView(leave);
        col7.addView(col7pad);
        col7.addView(placeBlock);
        col7.addView(changeBlock);

        screen.addView(col1);
        screen.addView(col2);
        screen.addView(col3);
        screen.addView(col4);
        screen.addView(col5);
        screen.addView(col6);
        screen.addView(col7);

        game.addView(gameView);
        game.addView(screen);



        setContentView(game);

        right.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameView.xdir > -.8){
                    gameView.xdir -= .2;
                }
            }
        }));

        left.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameView.xdir < .8){
                    gameView.xdir += .2;
                }
            }
        }));

        changeBlock.setOnClickListener(this::changeBlock);
        placeBlock.setOnClickListener(this::placeBlock);
        jump.setOnClickListener(this::jump);
        chest.setOnClickListener(this::chest);
        removeChest.setOnClickListener(this::removeChest);
        leave.setOnClickListener(this::leave);
    }

    private void placeBlock(View view) {
        int x = gameView.topLeft.x + gameView.targetPlace.x;
        int y = gameView.topLeft.y + gameView.targetPlace.y;
        gameView.world.Map[x][y] = gameView.nextBlock;
        gameView.block[gameView.targetPlace.x][gameView.targetPlace.y] = new Block(gameView.nextBlock, x, y, getResources());
        gameView.blockUpdate(x, y, gameView.nextBlock);
    }


    /**
     * Calls playerLeave with a button in the UI view of the game.
     * @param view Takes an implied view of the button being sent in.
     */
    private void leave(View view) {
        //playerLeave();
        this.finish();
    }


    /**
     * Changes a single block one below and to the right of the player to Bedrock.
     * @param v Takes an implied view of the button being sent in.
     */
    private void changeBlock(View v) {
        int x = gameView.topLeft.x + gameView.targetBreak.x;
        int y = gameView.topLeft.y + gameView.targetBreak.y;
        gameView.world.Map[x][y] = 'z';
        gameView.nextBlock = gameView.block[gameView.targetBreak.x][gameView.targetBreak.y].block;
        gameView.block[gameView.targetBreak.x][gameView.targetBreak.y] = new Block('z', x, y, getResources());
        gameView.blockUpdate(x, y, 'z');

    }

    /**
     * Adds a chest to the backend with 'A' as a proof of concept
     * @param v Takes an implied view of the button being sent in.
     */
    private void chest(View v){
        addChest(249, 500);
    }

    private void jump(View v){
        if(gameView.block[(int)((double)gameView.blocksizeX / 2.0)][(int)((double)gameView.blocksizeY / 2.0)].collidable){
            gameView.ydir = 1;
            gameView.jump = 1;
        }
    }

    /**
     * Attempts to remove an added chest from the backend. WARNING: Will not work if you have not created one.
     * @param view Takes an implied view of the button being sent in.
     */
    private void removeChest(View view) {
        deleteChest(249, 500);
    }

    /**
     * Pauses the thread.
     */
    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }

    /**
     * Resumes the thread.
     */
    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();
    }

    /**
     * Creates a world with worldname, difficulty, and username on backend, calling sendWorld onResponse to fill in the world's state.
     */
    private void createWorld() {
        showProgressDialog();

        String URL_POST_CREATE_WORLD = "http://coms-309-032.class.las.iastate.edu:8080/createWorld?name=";
        URL_POST_CREATE_WORLD += gameView.world.name + "&difficulty=" +(char)gameView.world.difficulty + "&username=" + Userdata.getUser();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_POST_CREATE_WORLD,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String resp) {
                        Log.d(TAG, resp);
                        hideProgressDialog();
                        Userdata.worldid = Integer.parseInt(resp);
                        for(int i = 0; i < 500; i++){
                            sendWorld(i);
                        }
                        //playerJoin();
                        builder.setMessage("Done");
                        builder.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                builder.setTitle("World Creation Error:");
                builder.setMessage("There was an error with the connection to the server");
                builder.show();
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Sends the state of a world to the server, 1000 chars at a time.
     * @param x the x-value to start sending at.
     */
    private void sendWorld(int x) {
        showProgressDialog();
        StringBuilder s = new StringBuilder(1000);
        for(int j = 0; j < 1000; j++) {
            s.append(gameView.world.Map[x][j]);
        }

        String URL_POST_ADD_WORLD = "http://coms-309-032.class.las.iastate.edu:8080/addWorldState?worldstate=";
        URL_POST_ADD_WORLD += String.valueOf(s) + "&wid=" + Userdata.worldid;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_POST_ADD_WORLD,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String resp) {
                        Log.d(TAG, resp);
                        hideProgressDialog();
                        if(resp.equals("True")){
                            builder.setMessage("Done");
                        }
                        else{
                            builder.setMessage("There was a problem");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                builder.setTitle("World Creation Error:");
                builder.setMessage("There was an error with the connection to the server");
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Adds a chest with 'A' in the first slot and ' ' in the rest at position x, y in a world distinguished by worldID.
     * @param x The x-position of the chest within the world.
     * @param y The y-position of the chest within the world.
     */
    private void addChest(int x, int y){
        String URL_POST_ADD_CHEST = "http://coms-309-032.class.las.iastate.edu:8080/chests/addChest";

        JSONObject object = new JSONObject();
        try {
            object.put("worldID", Userdata.worldid);
            object.put("x", x);
            object.put("y", y);
            object.put("item1", "A");
            String s;
            for(int i = 2; i < 31; i++){
                s = "item" + i;
                object.put(s, " ");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objReq = new JsonObjectRequest(Request.Method.POST,
                URL_POST_ADD_CHEST, object,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject resp) {
                        Log.d(TAG, resp.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(objReq, tag_json_obj);
    }

    /**
     * Deletes a chest at an (x, y) in current world that has 'A' in its first slot and ' ' in the rest.
     * @param x
     * @param y
     */
    private void deleteChest(int x, int y){
        String URL_POST_REMOVE_CHEST = "http://coms-309-032.class.las.iastate.edu:8080/chests/removeChest";

        JSONObject object = new JSONObject();
        try {
            object.put("worldID", Userdata.worldid);
            object.put("x", x);
            object.put("y", y);
            object.put("item1", "A");
            String s;
            for(int i = 1; i < 30; i++){
                s = "item" + i;
                object.put(s, " ");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objReq = new JsonObjectRequest(Request.Method.POST,
                URL_POST_REMOVE_CHEST, object,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject resp) {
                        Log.d(TAG, resp.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(objReq, tag_json_obj);
    }

    /**
     * Simple progress dialog turn-on for user-friendly UI.
     */
    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Simple progress dialog turn-off for user-friendly UI.
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

}