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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;
import com.example.as1.utils.Userdata;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Screen for creating a character
 */
public class CharCreationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String TAG = CharCreationActivity.class.getSimpleName();
    private String response;
    private ProgressDialog pDialog;

    private String tag_string_req = "string_req";
    private String tag_json_obj = "jobj_req";


    TextView alert;
    private AlertDialog.Builder builder;

//    Button hairPrev;
//    Button hairNext;
//    Button skinPrev;
//    Button skinNext;
//    Button shirtPrev;
//    Button shirtNext;
//    Button pantsPrev;
//    Button pantsNext;
//    Button shoesPrev;
//    Button shoesNext;
//
//    ImageView hairImg;
//    ImageView shirtImg;
//    ImageView skinImg;
//    ImageView pantsImg;
//    ImageView shoesImg;

    //START: FOR DEMO
    Button charNext;
    Button charPrev;
    ImageView charImg;

    //END: FOR DEMO, CHANGE SHIRTNUM FUNCTIONS BACK.
    int hairNum = 1;
    int skinNum = 1;
    int shirtNum = 1; //will be using shirtNum for character num
    int pantsNum = 1;
    int shoesNum = 1;

    RadioGroup diffGroup;
    RadioButton easy;
    RadioButton normal;
    RadioButton hard;
    String difficulty = "";

    EditText charName;
    String characterName = "";

    Button charCreationApply;
    Button charCreationCancel;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charcreation);
        SharedPreferences sharedPrefs = getSharedPreferences("com.example.as1", MODE_PRIVATE);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        user = Userdata.getUser();

//        hairPrev = findViewById(R.id.hairPrev);
//        hairNext = findViewById(R.id.hairNext);
//        skinPrev = findViewById(R.id.skinPrev);
//        skinNext = findViewById(R.id.skinNext);
//        shirtPrev = findViewById(R.id.shirtPrev);
//        shirtNext = findViewById(R.id.shirtNext);
//        pantsPrev = findViewById(R.id.pantsPrev);
//        pantsNext = findViewById(R.id.pantsNext);
//        shoesPrev = findViewById(R.id.shoesPrev);
//        shoesNext = findViewById(R.id.shoesNext);
//        hairImg = findViewById(R.id.hairIMG);
//        shirtImg = findViewById(R.id.shirtIMG);
//        skinImg = findViewById(R.id.skinIMG);
//        pantsImg = findViewById(R.id.pantsIMG);
//        shoesImg = findViewById(R.id.shoesIMG);
        //FOR DEMO
        charNext = findViewById(R.id.charNext);
        charPrev = findViewById(R.id.charPrev);
        charImg = findViewById(R.id.shirtIMG);
        //END FOR DEMO

        diffGroup = findViewById(R.id.charDiffGrp);
        easy = findViewById(R.id.easyRdBtn);
        normal = findViewById(R.id.normalRdBtn);
        hard = findViewById(R.id.hardRdBtn);
        diffGroup.clearCheck();

        charName = findViewById(R.id.charNameTxt);

        charCreationApply = findViewById(R.id.charCreationApply);
        charCreationCancel = findViewById(R.id.charCreationCancel);

        alert = findViewById(R.id.charCreationPopup);


        // Checks to see if all inputs are valid, then calls sendCharInfo()
        charCreationApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                builder = new AlertDialog.Builder(CharCreationActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Character creation error");
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                characterName = charName.getText().toString();
                if (characterName.equals("")) {
                    builder.setMessage("Did not name your character!");
                    builder.show();
                }
                else if (difficulty.equals("")) {
                    builder.setMessage("Did not set a difficulty for your character!");
                    builder.show();
                }
                else {
                    sendCharInfo(hairNum, skinNum, shirtNum, pantsNum, shoesNum, difficulty, characterName, user);
                }
            }
        });


        // Listener for character difficulty
        diffGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                        String diff = (String) radioButton.getText();
                        if (diff.equals("Hard")) {
                            difficulty = "Hard";
                        }
                        else if (diff.equals("Normal")) {
                            difficulty = "Normal";
                        }
                        else if (diff.equals("Easy")) {
                            difficulty = "Easy";
                        }
                    }
                });


        // Button to go back to the character select screen
        charCreationCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CharCreationActivity.this, CharSelActivity.class);
                startActivity(intent);
            }
        });


            //FOR DEMO
        charPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(shirtNum == 1) {
                    shirtNum = 2;
                }
                else {
                    shirtNum--;
                }
                switchShirt();
            }
        });
        charNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(shirtNum == 2) {
                    shirtNum = 1;
                }
                else {
                    shirtNum++;
                }
                switchShirt();
            }
        });
        //END FOR DEMO

        //  Adds/subtracts from xNum where x is hair, skin, shirt, pants, and shoes
        //  Loops from 1 to 5
//        hairPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                if(hairNum == 1) {
//                    hairNum = 5;
//                }
//                else {
//                    hairNum--;
//                }
//                switchHair();
//            }
//        });
//        hairNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                if(hairNum == 5) {
//                    hairNum = 1;
//                }
//                else {
//                    hairNum++;
//                }
//                switchHair();
//            }
//        });
//        skinPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                if(skinNum == 1) {
//                    skinNum = 5;
//                }
//                else {
//                    skinNum--;
//                }
//                switchSkin();
//            }
//        });
//        skinNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                if(skinNum == 5) {
//                    skinNum = 1;
//                }
//                else {
//                    skinNum++;
//                }
//                switchSkin();
//            }
//        });
//        shirtPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                if(shirtNum == 1) {
//                    shirtNum = 5;
//                }
//                else {
//                    shirtNum--;
//                }
//                switchShirt();
//            }
//        });
//        shirtNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                if(shirtNum == 5) {
//                    shirtNum = 1;
//                }
//                else {
//                    shirtNum++;
//                }
//                switchShirt();
//            }
//        });
//        pantsPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                if(pantsNum == 1) {
//                    pantsNum = 5;
//                }
//                else {
//                    pantsNum--;
//                }
//                switchPants();
//            }
//        });
//        pantsNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                if(pantsNum == 5) {
//                    pantsNum = 1;
//                }
//                else {
//                    pantsNum++;
//                }
//                switchPants();
//            }
//        });
//        shoesPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                if(shoesNum == 1) {
//                    shoesNum = 5;
//                }
//                else {
//                    shoesNum--;
//                }
//                switchShoes();
//            }
//        });
//        shoesNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                if(shoesNum == 5) {
//                    shoesNum = 1;
//                }
//                else {
//                    shoesNum++;
//                }
//                switchShoes();
//            }
//        });
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
     * Sends a Character object to the server and adds it as a character
     * Gives feedback on success/fail
     * @param hairNum Hair-type
     * @param skinNum Skin-type
     * @param shirtNum Shirt-type
     * @param pantsNum Pants-type
     * @param shoesNum Shoes-type
     * @param difficulty Character difficulty
     * @param characterName Character name
     * @param user Currently signed-in username
     */
    private void sendCharInfo(int hairNum, int skinNum, int shirtNum, int pantsNum, int shoesNum, String difficulty, String characterName, String user) {
        showProgressDialog();

        JSONObject object = new JSONObject();
        try {
            object.put("hair", hairNum);
            object.put("skin", skinNum);
            object.put("shirt", shirtNum);
            object.put("pants", pantsNum);
            object.put("shoes", shoesNum);
            object.put("difficulty", difficulty);
            object.put("characterName", characterName);
            object.put("username", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.URL_POST_CHARCREATE, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        Log.d(TAG, resp.toString());
                        hideProgressDialog();
                        response = "Character successfully created!";
                        builder.setMessage(response);
                        builder.setTitle("Character creation success!");
                        charCreationCancel.setText("Back to select character");
                        builder.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                builder.setTitle("Character Creation Error:");
                builder.setMessage("There was an error with the connection to the server");
                builder.show();
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

//    /**
//     * Switches currently displayed hair based on user input
//     */
//    public void switchHair() {
//        switch(hairNum) {
//            case 1:
//                hairImg.setImageResource(R.drawable.hairone);
//                break;
//            case 2:
//                hairImg.setImageResource(R.drawable.hairtwo);
//                break;
//            case 3:
//                hairImg.setImageResource(R.drawable.hairthree);
//                break;
//            case 4:
//                hairImg.setImageResource(R.drawable.hairfour);
//                break;
//            case 5:
//                hairImg.setImageResource(R.drawable.hairfive);
//                break;
//        }
//    }
//    /**
//     * Switches currently displayed skin tone based on user input
//     */
//    public void switchSkin() {
//        switch(skinNum) {
//            case 1:
//                skinImg.setImageResource(R.drawable.skinone);
//                break;
//            case 2:
//                skinImg.setImageResource(R.drawable.skintwo);
//                break;
//            case 3:
//                skinImg.setImageResource(R.drawable.skinthree);
//                break;
//            case 4:
//                skinImg.setImageResource(R.drawable.skinfour);
//                break;
//            case 5:
//                skinImg.setImageResource(R.drawable.skinfive);
//                break;
//        }
//    }
    /**
     * Switches currently displayed shirt based on user input
     */
    public void switchShirt() {
        switch(shirtNum) {
            //FOR DEMO
            case 1:
                charImg.setImageResource(R.drawable.jack);
                break;
            case 2:
                charImg.setImageResource(R.drawable.jack2);
                break;
            //END FOR DEMO

//            case 1:
//                shirtImg.setImageResource(R.drawable.shirtone);
//                break;
//            case 2:
//                shirtImg.setImageResource(R.drawable.shirttwo);
//                break;
//            case 3:
//                shirtImg.setImageResource(R.drawable.shirtthree);
//                break;
//            case 4:
//                shirtImg.setImageResource(R.drawable.shirtfour);
//                break;
//            case 5:
//                shirtImg.setImageResource(R.drawable.shirtfive);
//                break;
        }
    }
//    /**
//     * Switches currently displayed pants based on user input
//     */
//    public void switchPants() {
//        switch(pantsNum) {
//            case 1:
//                pantsImg.setImageResource(R.drawable.pantsone);
//                break;
//            case 2:
//                pantsImg.setImageResource(R.drawable.pantstwo);
//                break;
//            case 3:
//                pantsImg.setImageResource(R.drawable.pantsthree);
//                break;
//            case 4:
//                pantsImg.setImageResource(R.drawable.pantsfour);
//                break;
//            case 5:
//                pantsImg.setImageResource(R.drawable.pantsfive);
//                break;
//        }
//    }
//    /**
//     * Switches currently displayed shoes based on user input
//     */
//    public void switchShoes() {
//        switch(shoesNum) {
//            case 1:
//                shoesImg.setImageResource(R.drawable.shoesone);
//                break;
//            case 2:
//                shoesImg.setImageResource(R.drawable.shoestwo);
//                break;
//            case 3:
//                shoesImg.setImageResource(R.drawable.shoesthree);
//                break;
//            case 4:
//                shoesImg.setImageResource(R.drawable.shoesfour);
//                break;
//            case 5:
//                shoesImg.setImageResource(R.drawable.shoesfive);
//                break;
//        }
//    }

}