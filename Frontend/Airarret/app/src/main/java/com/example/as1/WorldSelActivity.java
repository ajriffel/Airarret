package com.example.as1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;
import com.example.as1.utils.Userdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Screen for selecting a world to play on
 */
public class WorldSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String TAG = WorldSelActivity.class.getSimpleName();
    private String response;
    private ProgressDialog pDialog;

    private String tag_string_req = "string_req";
    private String tag_json_obj = "jobj_req";
    private String tag_json_arry = "json_array_req";


    TextView alert;
    private AlertDialog.Builder builder;

    RadioGroup yourWorldsList;
    RadioButton yworld1;
    RadioButton yworld2;
    RadioButton yworld3;
    RadioButton yworld4;
    RadioButton yworld5;

    RadioGroup friendsWorldsList;
    RadioButton fworld1;
    RadioButton fworld2;
    RadioButton fworld3;
    RadioButton fworld4;
    RadioButton fworld5;
    RadioButton fworld6;
    RadioButton fworld7;
    RadioButton fworld8;
    RadioButton fworld9;
    RadioButton fworld10;
    RadioButton fworld11;
    RadioButton fworld12;
    RadioButton fworld13;
    RadioButton fworld14;
    RadioButton fworld15;
    RadioButton fworld16;
    RadioButton fworld17;
    RadioButton fworld18;
    RadioButton fworld19;
    RadioButton fworld20;

    Button worldSelYSel;
    Button worldSelFSel;
    Button worldSelCreateNew;
    Button worldSelDelete;
    Button worldSelBack;

    String user;

    String[] yworlds = new String[5];
    int[] yworldsID = new int[5];
    String[] fworlds = new String[20];
    int[] fworldsID = new int[5];
    String selectedYWorld;
    int yWorldSelected, fWorldSelected;
    String selectedFWorld;

    /*
     * Refresh the world list whenever loading up the screen
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        getUserWorldList(user);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!(yworlds[0] == null) && !(fworlds[0] == null)) {
                    setUserWorldList();
                }
                else {
                    yworld1.setText("Server Error");
                    yworld2.setText("");
                    yworld3.setText("");
                    yworld4.setText("");
                    yworld5.setText("");
                    fworld1.setText("Server Error");
                    fworld2.setText("");
                    fworld3.setText("");
                    fworld4.setText("");
                    fworld5.setText("");
                }
                //Set user's character names to the radio buttons after using getUserCharList()

            }
        }, 200);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worldsel);
        SharedPreferences sharedPrefs = getSharedPreferences("com.example.as1", MODE_PRIVATE);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        //retrieve username from Users table
        user = Userdata.getUser();

        yourWorldsList = findViewById(R.id.yourWorldsList);
        yworld1 = findViewById(R.id.yworldSel1);
        yworld2 = findViewById(R.id.yworldSel2);
        yworld3 = findViewById(R.id.yworldSel3);
        yworld4 = findViewById(R.id.yworldSel4);
        yworld5 = findViewById(R.id.yworldSel5);
        yourWorldsList.clearCheck();

        friendsWorldsList = findViewById(R.id.friendsWorldsList);
        fworld1 = findViewById(R.id.fWorldSel1);
        fworld2 = findViewById(R.id.fWorldSel2);
        fworld3 = findViewById(R.id.fWorldSel3);
        fworld4 = findViewById(R.id.fWorldSel4);
        fworld5 = findViewById(R.id.fWorldSel5);
        fworld6 = findViewById(R.id.fWorldSel6);
        fworld7 = findViewById(R.id.fWorldSel7);
        fworld8 = findViewById(R.id.fWorldSel8);
        fworld9 = findViewById(R.id.fWorldSel9);
        fworld10 = findViewById(R.id.fWorldSel10);
        fworld11 = findViewById(R.id.fWorldSel11);
        fworld12 = findViewById(R.id.fWorldSel12);
        fworld13 = findViewById(R.id.fWorldSel13);
        fworld14 = findViewById(R.id.fWorldSel14);
        fworld15 = findViewById(R.id.fWorldSel15);
        fworld16 = findViewById(R.id.fWorldSel16);
        fworld17 = findViewById(R.id.fWorldSel17);
        fworld18 = findViewById(R.id.fWorldSel18);
        fworld19 = findViewById(R.id.fWorldSel19);
        fworld20 = findViewById(R.id.fWorldSel20);
        friendsWorldsList.clearCheck();

        worldSelYSel = findViewById(R.id.worldSelYourSel);
        worldSelFSel = findViewById(R.id.worldSelFriendsSel);
        worldSelCreateNew = findViewById(R.id.worldSelCreateNew);
        worldSelDelete = findViewById(R.id.worldSelDelete);
        worldSelBack = findViewById(R.id.worldSelBackBtn);

        alert = findViewById(R.id.worldSelPopup);



        builder = new AlertDialog.Builder(WorldSelActivity.this);
        builder.setCancelable(true);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        //Get user's worlds from the worlds table
        getUserWorldList(user);

        //Checks to see if there was a problem with the server
        //Otherwise, wait 400 milliseconds to let the server respond with the list
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!(yworlds[0] == null) && !(fworlds[0] == null)) {
                    setUserWorldList();
                }
                else {
                    builder.setTitle("World list:");
                    builder.setMessage("User has no worlds");
                    yworld1.setText("Server Error");
                    yworld2.setText("");
                    yworld3.setText("");
                    yworld4.setText("");
                    yworld5.setText("");
                    fworld1.setText("Server Error");
                    fworld2.setText("");
                    fworld3.setText("");
                    fworld4.setText("");
                    fworld5.setText("");
                    builder.show();
                }

            }
        }, 200);

        /*
         * Button for selecting your own world
         * Uses the currently selected radio button
         * If an invalid world is selected, i.e. a world slot, warn user and force to pick an actual world
         * Otherwise, call getWorldInfo() to get world info from the server
         */
        worldSelYSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                builder.setTitle("World selection error");
                if (selectedYWorld == null) {
                    builder.setMessage("Didn't select a world");
                    builder.show();
                }
                else if (selectedYWorld.equals("")) {
                    builder.setMessage("Selected a non-existent world!");
                    builder.show();
                }
                else {
                    Userdata.worldid = yworldsID[yWorldSelected];
                    Userdata.worldJoin = 1;
                    Intent intent = new Intent(WorldSelActivity.this, PlayActivity.class);
                    startActivity(intent);
                    WorldSelActivity.this.finish();
                }
                
            }
        });

        /*
         * Button for selecting friend's world
         * Uses the currently selected radio button
         * If an invalid world is selected, i.e. a world slot, warn user and force to pick an actual world
         * Otherwise, call getWorldInfo() to get world info from the server
         */
        worldSelFSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                builder.setTitle("World selection error");
                if (selectedFWorld == null) {
                    builder.setMessage("Didn't select a world");
                    builder.show();
                }
                else if (selectedFWorld.equals("")) {
                    builder.setMessage("Selected a non-existent world!");
                    builder.show();
                }
                else {
                    Userdata.worldid = fworldsID[fWorldSelected];
                    Userdata.worldJoin = 2;
                    Intent intent = new Intent(WorldSelActivity.this, PlayActivity.class);
                    startActivity(intent);
                    WorldSelActivity.this.finish();
                }
            }
        });
        

        /*
         * Listener for user's worlds
         * Changes selectedYWorld to currently selected radio button
         */
        yourWorldsList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                        String yWorldName = (String) radioButton.getText();
                        if (yWorldName.equals(yworlds[0])) {
                            selectedYWorld = yworlds[0];
                            yWorldSelected = 0;
                        }
                        else if (yWorldName.equals(yworlds[1])) {
                            selectedYWorld = yworlds[1];
                            yWorldSelected = 1;
                        }
                        else if (yWorldName.equals(yworlds[2])) {
                            selectedYWorld = yworlds[2];
                            yWorldSelected = 2;
                        }
                        else if (yWorldName.equals(yworlds[3])) {
                            selectedYWorld = yworlds[3];
                            yWorldSelected = 3;
                        }
                        else if (yWorldName.equals(yworlds[4])) {
                            selectedYWorld = yworlds[4];
                            yWorldSelected = 4;
                        }
                        else {
                            selectedYWorld = "";
                        }
                    }
                });

        /*
         * Listener for friend's worlds
         * Changes selectedFWorld to currently selected radio button
         */
        friendsWorldsList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                String fWorldName = (String) radioButton.getText();
                if (fWorldName.equals(fworlds[0])) {
                    selectedFWorld = fworlds[0];
                    fWorldSelected = 0;
                }
                else if (fWorldName.equals(fworlds[1])) {
                    selectedFWorld = fworlds[1];
                    fWorldSelected = 1;
                }
                else if (fWorldName.equals(fworlds[2])) {
                    selectedFWorld = fworlds[2];
                    fWorldSelected = 2;
                }
                else if (fWorldName.equals(fworlds[3])) {
                    selectedFWorld = fworlds[3];
                    fWorldSelected = 3;
                }
                else if (fWorldName.equals(fworlds[4])) {
                    selectedFWorld = fworlds[4];
                    fWorldSelected = 4;
                }
                else if (fWorldName.equals(fworlds[5])) {
                    selectedFWorld = fworlds[5];
                    fWorldSelected = 5;
                }
                else if (fWorldName.equals(fworlds[6])) {
                    selectedFWorld = fworlds[6];
                    fWorldSelected = 6;
                }
                else if (fWorldName.equals(fworlds[7])) {
                    selectedFWorld = fworlds[7];
                    fWorldSelected = 7;
                }
                else if (fWorldName.equals(fworlds[8])) {
                    selectedFWorld = fworlds[8];
                    fWorldSelected = 8;
                }
                else if (fWorldName.equals(fworlds[9])) {
                    selectedFWorld = fworlds[9];
                    fWorldSelected = 9;
                }
                else if (fWorldName.equals(fworlds[10])) {
                    selectedFWorld = fworlds[10];
                    fWorldSelected = 10;
                }
                else if (fWorldName.equals(fworlds[11])) {
                    selectedFWorld = fworlds[11];
                    fWorldSelected = 11;
                }
                else if (fWorldName.equals(fworlds[12])) {
                    selectedFWorld = fworlds[12];
                    fWorldSelected = 12;
                }
                else if (fWorldName.equals(fworlds[13])) {
                    selectedFWorld = fworlds[13];
                    fWorldSelected = 13;
                }
                else if (fWorldName.equals(fworlds[14])) {
                    selectedFWorld = fworlds[14];
                    fWorldSelected = 14;
                }
                else if (fWorldName.equals(fworlds[15])) {
                    selectedFWorld = fworlds[15];
                    fWorldSelected = 15;
                }
                else if (fWorldName.equals(fworlds[16])) {
                    selectedFWorld = fworlds[16];
                    fWorldSelected = 16;
                }
                else if (fWorldName.equals(fworlds[17])) {
                    selectedFWorld = fworlds[17];
                    fWorldSelected = 17;
                }
                else if (fWorldName.equals(fworlds[18])) {
                    selectedFWorld = fworlds[18];
                    fWorldSelected = 18;
                }
                else if (fWorldName.equals(fworlds[19])) {
                    selectedFWorld = fworlds[19];
                    fWorldSelected = 19;
                }
                else {
                    selectedFWorld = "";
                }

            }
        });

        /*
         * Takes user to create a new world screen
         */
        worldSelCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Userdata.worldJoin = 0;
                Intent intent = new Intent(WorldSelActivity.this, WorldCreationActivity.class);
                startActivity(intent);
                WorldSelActivity.this.finish();
            }
        });

        /*
         * Deletes the hovered world
         * Note: only works for your own world, not a friend's
         */
        worldSelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (selectedYWorld.equals("")) {
                    builder.setTitle("World Deletion Error");
                    builder.setMessage("Can't delete a world slot!");
                    builder.show();
                }
                else if (selectedYWorld == null) {
                    builder.setTitle("World Deletion Error");
                    builder.setMessage("Didn't select a world");
                    builder.show();
                }
                else if (selectedYWorld.equals(yworlds[0])) {
                    deleteWorld(yworldsID[0]);
                    yworld1.setText("Empty World Slot");
                }
                else if (selectedYWorld.equals(yworlds[1])) {
                    deleteWorld(yworldsID[1]);
                    yworld2.setText("Empty World Slot");
                }
                else if (selectedYWorld.equals(yworlds[2])) {
                    deleteWorld(yworldsID[2]);
                    yworld3.setText("Empty World Slot");
                }
                else if (selectedYWorld.equals(yworlds[3])) {
                    deleteWorld(yworldsID[3]);
                    yworld4.setText("Empty World Slot");
                }
                else if (selectedYWorld.equals(yworlds[4])) {
                    deleteWorld(yworldsID[4]);
                    yworld5.setText("Empty World Slot");
                }

            }
        });
        /*
         * Takes user back to the character selection screen
         */
        worldSelBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WorldSelActivity.this, CharSelActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    /**
     * Deletes a user's world visually on the front-end and on in the tables in the back-end
     * @param worldID ID of a world in the worlds table
     */
    private void deleteWorld(int worldID) {
        showProgressDialog();

        String url = Const.URL_DELETE_WORLDSELDELWORLD + "/" + worldID;
        StringRequest delReq = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {
                        Log.d(TAG, resp.toString());
                        hideProgressDialog();
                        builder.setTitle("World Deleted");
                        builder.setMessage("World " + resp + " successfully deleted");
                        for (int i = 0;i < 5; i++) {
                            if (yworlds[i].equals(resp)) {
                                yworlds[i] = "";
                            }
                        }
                        selectedYWorld = "";
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                builder.setTitle("Character Deletion Error:");
                builder.setMessage("There was an error with the connection to the server");
                builder.show();
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(delReq, tag_string_req);
    }



    /**
     * Retrieve an object array consisting of 50 strings from the server
     * The first 5 strings are different user's world's names
     * The second 5 strings are user's world's IDs
     * The third 20 are friend's world's names
     * The last 20 are friend's world's IDs
     *
     * Set String yworlds[] and fworlds[] to the different names sent by this string array
     * If world doesn't exist, server should set yworlds[] or fworlds[] = ""
     *
     * @param user Currently signed-in username
     */
    private void getUserWorldList(String user) {

        String url = Const.URL_POST_WORLDSELGETLIST + "/" + user;
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray resp){
                        Log.d(TAG, resp.toString());
                        for(int i = 0; i < 50; i++) {
                            try {
                                if (i < 5) {
                                    yworlds[i] = resp.getString(i);
                                } else if (i < 10) {
                                    if (!resp.getString(i).equals("")) {
                                        yworldsID[i-5] = Integer.valueOf(resp.getString(i));
                                    }
                                } else if (i < 30) {
                                    fworlds[i-10] = resp.getString(i);
                                } else {
                                    if (!resp.getString(i).equals("")) {
                                        fworldsID[i-30] = Integer.valueOf(resp.getString(i));
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                builder.setTitle("World Selection Error:");
                builder.setMessage("There was an error with the connection to the server");
                builder.show();
            }
        });
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    /**
     * Sets radio buttons to user's world's names
     * If yworlds[i] or fworlds[i] = "", make the radio button text "Empty World Slot"
     * Otherwise, set it to the world's name
     */
    private void setUserWorldList() {
        //YOUR WORLDS SECTION
            if (!yworlds[0].equals("")) {
                yworld1.setText(yworlds[0]);
            }
            else {
                yworld1.setText("Empty World Slot");
            }
            if (!yworlds[1].equals("")) {
                yworld2.setText(yworlds[1]);
            }
            else {
                yworld2.setText("Empty World Slot");
            }
            if (!yworlds[2].equals("")) {
                yworld3.setText(yworlds[2]);
            }
            else {
                yworld3.setText("Empty World Slot");
            }
            if (!yworlds[3].equals("")) {
                yworld4.setText(yworlds[3]);
            }
            else {
                yworld4.setText("Empty World Slot");
            }

            if (!yworlds[4].equals("")) {
                yworld5.setText(yworlds[4]);
            }
        else {
            yworld5.setText("Empty World Slot");
        }
        //FRIENDS WORLDS SECTION
        //1-5
            if (!fworlds[0].equals("")) {
                fworld1.setText(fworlds[0]);
            }
        else {
            fworld1.setText("Empty World Slot");
        }
            if (!fworlds[1].equals("")) {
                fworld2.setText(fworlds[1]);
            }
        else {
            fworld2.setText("Empty World Slot");
        }
            if (!fworlds[2].equals("")) {
                fworld3.setText(fworlds[2]);
            }
        else {
            fworld3.setText("Empty World Slot");
        }
            if (!fworlds[3].equals("")) {
                fworld4.setText(fworlds[3]);
            }
        else {
            fworld4.setText("Empty World Slot");
        }
            if (!fworlds[4].equals("")) {
                fworld5.setText(fworlds[4]);
            }
        else {
            fworld5.setText("Empty World Slot");
        }
        //5-10
            if (!fworlds[5].equals("")) {
                fworld6.setText(fworlds[5]);
            }
        else {
            fworld6.setText("Empty World Slot");
        }
            if (!fworlds[6].equals("")) {
                fworld7.setText(fworlds[6]);
            }
        else {
            fworld7.setText("Empty World Slot");
        }
            if (!fworlds[7].equals("")) {
                fworld8.setText(fworlds[7]);
            }
        else {
            fworld8.setText("Empty World Slot");
        }
            if (!fworlds[8].equals("")) {
                fworld9.setText(fworlds[8]);
            }
        else {
            fworld9.setText("Empty World Slot");
        }
            if (!fworlds[9].equals("")) {
                fworld10.setText(fworlds[9]);
            }
        else {
            fworld10.setText("Empty World Slot");
        }
        //10-15
            if (!fworlds[10].equals("")) {
                fworld11.setText(fworlds[10]);
            }
        else {
            fworld11.setText("Empty World Slot");
        }
            if (!fworlds[11].equals("")) {
                fworld12.setText(fworlds[11]);
            }
        else {
            fworld12.setText("Empty World Slot");
        }
            if (!fworlds[12].equals("")) {
                fworld13.setText(fworlds[12]);
        }
        else {
            fworld13.setText("Empty World Slot");
        }
            if (!fworlds[13].equals("")) {
                fworld14.setText(fworlds[13]);
        }
        else {
            fworld14.setText("Empty World Slot");
        }
            if (!fworlds[14].equals("")) {
                fworld15.setText(fworlds[14]);
            }
        else {
            fworld15.setText("Empty World Slot");
        }
        //15-20
            if (!fworlds[15].equals("")) {
                fworld16.setText(fworlds[15]);
            }
        else {
            fworld16.setText("Empty World Slot");
        }
            if (!fworlds[16].equals("")) {
                fworld17.setText(fworlds[16]);
        }
        else {
            fworld17.setText("Empty World Slot");
        }
            if (!fworlds[17].equals("")) {
                fworld18.setText(fworlds[17]);
            }
        else {
            fworld18.setText("Empty World Slot");
        }
            if (!fworlds[18].equals("")) {
                fworld19.setText(fworlds[18]);
        }
        else {
            fworld19.setText("Empty World Slot");
        }
            if (!fworlds[19].equals("")) {
                fworld20.setText(fworlds[19]);
        }
        else {
            fworld20.setText("Empty World Slot");
        }


    }

}