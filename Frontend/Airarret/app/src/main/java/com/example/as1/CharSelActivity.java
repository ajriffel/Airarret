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
 * User is prompted to select a character to play the game with.
 */
public class CharSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private String TAG = CharSelActivity.class.getSimpleName();
    private String response;
    private ProgressDialog pDialog;

    private String tag_string_req = "string_req";
    private String tag_json_obj = "jobj_req";
    private String tag_json_arry = "json_array_req";


    TextView alert;
    private AlertDialog.Builder builder;

    RadioGroup radioCharList;
    RadioButton char1;
    RadioButton char2;
    RadioButton char3;
    RadioButton char4;
    RadioButton char5;

    Button charSelSel;
    Button charSelNewChar;
    Button charSelDelChar;
    Button charSelBack;

    String user;

    String[] characters = new String[5];
    String selectedChar;
    static Character userSelectedChar;

    @Override
    protected void onRestart() {
        super.onRestart();
        getUserCharList(user);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!(characters[0] == null)) {
                    setUserCharList();
                }
                else {
                    char1.setText("Server Error");
                    char2.setText("");
                    char3.setText("");
                    char4.setText("");
                    char5.setText("");
                }
                //Set user's character names to the radio buttons after using getUserCharList()
            }
        }, 200);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charsel);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        radioCharList = findViewById(R.id.radioCharList);
        char1 = findViewById(R.id.charSel1);
        char2 = findViewById(R.id.charSel2);
        char3 = findViewById(R.id.charSel3);
        char4 = findViewById(R.id.charSel4);
        char5 = findViewById(R.id.charSel5);
        radioCharList.clearCheck();

        charSelSel = findViewById(R.id.charSelBtn);
        charSelNewChar = findViewById(R.id.charSelNewCharBtn);
        charSelDelChar = findViewById(R.id.charSelDeleteBtn);
        charSelBack = findViewById(R.id.charSelGoBackBtn);

        alert = findViewById(R.id.charSelPopup);

        //retrieve username from Users table
        user = Userdata.getUser();

        builder = new AlertDialog.Builder(CharSelActivity.this);
        builder.setCancelable(true);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        //Get user's characters from the characters table
        getUserCharList(user);


         //Checks to see if there was a problem with the server
         //Otherwise, wait 400 milliseconds to let the server respond with the list
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!(characters[0] == null)) {
                    //Set user's character names to the radio buttons after using getUserCharList()
                    setUserCharList();
                }
                else {
                    builder.setTitle("Character Selection Error");
                    builder.setMessage("Problem with connection to server");
                    builder.show();
                    char1.setText("Server Error");
                    char2.setText("");
                    char3.setText("");
                    char4.setText("");
                    char5.setText("");
                }

            }
        }, 400);



        //  Button for selecting a character
        //  Uses the currently selected radio button
        //  If an invalid character is selected, i.e. a character slot, warn user and force to pick an actual character
        //  Otherwise, call getCharInfo() to get character info from the server
        charSelSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                builder.setTitle("Character selection error");
                if (selectedChar == null) {
                    builder.setMessage("Didn't select a character");
                    builder.show();
                }
                else if (selectedChar.equals("")) {
                    builder.setMessage("Selected a non-existent character!");
                    builder.show();
                }
                else {
                    //Get character info and set userSelectedChar to the character
                    Userdata.charname = selectedChar;
                    getCharInfo(selectedChar, user);
                }
            }
        });


        // Changes selectedChar to currently selected radio button
        radioCharList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                        String charName = (String) radioButton.getText();
                        if (charName.equals(characters[0])) {
                            selectedChar = characters[0];
                        }
                        else if (charName.equals(characters[1])) {
                            selectedChar = characters[1];
                        }
                        else if (charName.equals(characters[2])) {
                            selectedChar = characters[2];
                        }
                        else if (charName.equals(characters[3])) {
                            selectedChar = characters[3];
                        }
                        else if (charName.equals(characters[4])) {
                            selectedChar = characters[4];
                        }
                        else {
                            selectedChar = "";
                        }
                    }
                });

        // Takes user to create a new character screen
        charSelNewChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CharSelActivity.this, CharCreationActivity.class);
                startActivity(intent);
                CharSelActivity.this.finish();
            }
        });

        // Deletes the selected character
        charSelDelChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (selectedChar.equals("")) {
                    builder.setTitle("Character Deletion Error");
                    builder.setMessage("Can't delete a character slot!");
                    builder.show();
                }
                else if (selectedChar == null) {
                    builder.setTitle("Character Deletion Error");
                    builder.setMessage("Didn't select a character");
                    builder.show();
                }
                else if (selectedChar.equals(characters[0])) {
                    deleteUser(user, selectedChar);
                    char1.setText("Empty Character Slot");
                }
                else if (selectedChar.equals(characters[1])) {
                    deleteUser(user, selectedChar);
                    char2.setText("Empty Character Slot");
                }
                else if (selectedChar.equals(characters[2])) {
                    deleteUser(user, selectedChar);
                    char3.setText("Empty Character Slot");
                }
                else if (selectedChar.equals(characters[3])) {
                    deleteUser(user, selectedChar);
                    char4.setText("Empty Character Slot");
                }
                else if (selectedChar.equals(characters[4])) {
                    deleteUser(user, selectedChar);
                    char5.setText("Empty Character Slot");
                }
            }
        });


        // Takes user back to the menu
        charSelBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CharSelActivity.this, MenuActivity.class);
                startActivity(intent);
                CharSelActivity.this.finish();
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
     * Deletes a user's character both visually on the front-end and on the tables in the back-end
     *
     * @param user Currently signed-in user
     * @param characterName Name of the character selected
     */
    private void deleteUser(String user, String characterName) {

        String url = Const.URL_DELETE_CHARSELDELCHAR + "/" + characterName + "/" + user;
        StringRequest delReq = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {
                        Log.d(TAG, resp);
                        hideProgressDialog();
                        builder.setTitle("Character deleted");
                        builder.setMessage("Character" + resp + "successfully deleted");
                        for (int i = 0;i < 5; i++) {
                            if (characters[i].equals(characterName)) {
                                characters[i] = "";
                            }
                        }
                        selectedChar = "";
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
     * Retrieve an object array consisting of 5 strings from the server
     * The 5 strings are different character's names
     * Set String characters[] to the different names
     * If character doesn't exist, server should send back ""
     *
     * @param user Currently signed-in user
     */
    public void getUserCharList(String user) {

        String url = Const.URL_POST_CHARSELGETLIST + "/" + user;
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray resp) {
                        Log.d(TAG, resp.toString());
                        hideProgressDialog();
                        for(int i = 0; i < 5; i++) {
                            try {
                                if (resp.getString(i).equals("null")) {
                                    characters[i] = "";
                                }
                                else {
                                    characters[i] = resp.getString(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                builder.setTitle("Character Selection Error:");
                builder.setMessage("There was an error with the connection to the server");
                builder.show();
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    /**
     * Send in character name and username to find the customization settings of a character
     * @param selectedChar Name of hovered character
     * @param user Currently signed-in user
     */
    private void getCharInfo(String selectedChar ,String user) {


        String url = Const.URL_POST_GETCHARINFO + "/" + selectedChar + "/" + user;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        Log.d(TAG, resp.toString());
                        hideProgressDialog();
                        try {
                            userSelectedChar = new Character(0.0, 0.0,
                                    resp.getString("difficulty"), resp.getInt("hair"),
                                    resp.getInt("skin"), resp.getInt("shirt"),
                                    resp.getInt("pants"), resp.getInt("shoes"), resp.getString("characterName"));
                            Userdata.charname = userSelectedChar.characterName;
                            Userdata.character = userSelectedChar;
                            Intent intent = new Intent(CharSelActivity.this, WorldSelActivity.class);
                            startActivity(intent);
                            CharSelActivity.this.finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                builder.setTitle("Character Selection Error:");
                builder.setMessage("There was an error with the connection to the server");
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    /**
     * Sets radio buttons to user's character's names
     * If characters[i] = "", make the radio button text "Empty Character Slot"
     * Otherwise, set it to the character's name
     */
    private void setUserCharList() {
        if (!characters[0].equals("")) {
            char1.setText(characters[0]);
        }
        else {
            char1.setText("Empty Character Slot");
        }
        if (!characters[1].equals("")) {
            char2.setText(characters[1]);
        }
        else {
            char2.setText("Empty Character Slot");
        }
        if (!characters[2].equals("")) {
            char3.setText(characters[2]);
        }
        else {
            char3.setText("Empty Character Slot");
        }
        if (!characters[3].equals("")) {
            char4.setText(characters[3]);
        }
        else {
            char4.setText("Empty Character Slot");
        }
        if (!characters[4].equals("")) {
            char5.setText(characters[4]);
        }
        else {
            char5.setText("Empty Character Slot");
        }
    }

    /**
     * Tell UserData.java in the utils directory our selected character and data associated with it
     * @return userSelectedChar
     */
    public static Character getCharacter() {
        return userSelectedChar;
    }


}