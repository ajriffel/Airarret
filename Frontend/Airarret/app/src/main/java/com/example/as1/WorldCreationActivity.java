package com.example.as1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
 * Screen for creating a world
 */
public class WorldCreationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String TAG = WorldCreationActivity.class.getSimpleName();
    private String response;
    private ProgressDialog pDialog;

    private String tag_string_req = "string_req";
    private String tag_json_obj = "jobj_req";


    TextView alert;
    private AlertDialog.Builder builder;

    EditText worldNameTxt;
    String worldName = "";

    RadioGroup worldDiffGroup;
    RadioButton easy;
    RadioButton normal;
    RadioButton hard;
    char difficulty = '.';

    RadioGroup worldTypeGroup;
    RadioButton normalType;
    RadioButton superflatType;
    RadioButton crazyType;
    String type = "";


    Button worldCreationCancel;
    Button worldCreationCreateWorld;

    String user;

    int worldID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worldcreation);
        SharedPreferences sharedPrefs = getSharedPreferences("com.example.as1", MODE_PRIVATE);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        user = Userdata.getUser();

        worldNameTxt = findViewById(R.id.worldNameTxt);

        worldDiffGroup = findViewById(R.id.worldDiffGroup);
        easy = findViewById(R.id.easyWorldBtn);
        normal = findViewById(R.id.normalWorldBtn);
        hard = findViewById(R.id.hardWorldBtn);
        worldDiffGroup.clearCheck();

        worldTypeGroup = findViewById(R.id.worldTypeGroup);
        normalType = findViewById(R.id.worldTypeNormal);
        superflatType = findViewById(R.id.worldTypeSuperflat);
        crazyType = findViewById(R.id.worldTypeCrazy);
        worldTypeGroup.clearCheck();

        worldCreationCancel = findViewById(R.id.worldCreationCancel);
        worldCreationCreateWorld = findViewById(R.id.worldCreationCreateWorld);

        alert = findViewById(R.id.worldCreationPopup);

        builder = new AlertDialog.Builder(WorldCreationActivity.this);
        builder.setCancelable(true);
        builder.setTitle("World creation error");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        /*
         * Checks to see all the inputs are valid, then sends the user to the game
         */
        worldCreationCreateWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                worldName = worldNameTxt.getText().toString();
                if (worldName.equals("")) {
                    builder.setMessage("Did not name your world!");
                    builder.show();
                }
                else if (difficulty == '.') {
                    builder.setMessage("Did not set a difficulty for your character!");
                    builder.show();
                }
                else if (type.equals("")) {
                    builder.setMessage("Did not set a type for your world!");
                    builder.show();
                }

                else {
                    Userdata.worldDiff = difficulty;
                    Userdata.worldType = type;
                    Userdata.worldName = worldName;
                    Intent intent = new Intent(WorldCreationActivity.this, PlayActivity.class);
                    startActivity(intent);
                    WorldCreationActivity.this.finish();
                }
            }
        });

        /*
         * Listener for the world difficulty
         */
        worldDiffGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                        String diff = (String) radioButton.getText();
                        if (diff.equals("Hard")) {
                            difficulty ='h';
                        }
                        else if (diff.equals("Normal")) {
                            difficulty = 'n';
                        }
                        else if (diff.equals("Easy")) {
                            difficulty = 'e';
                        }
                    }
                });

        /*
         * Listener for the world type
         */
        worldTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                String worldType = (String) radioButton.getText();
                if (worldType.equals("Normal")) {
                    type = "Normal";
                }
                else if (worldType.equals("Superflat")) {
                    type = "Superflat";
                }
                else if (worldType.equals("Crazy")) {
                    type = "Crazy";
                }
            }
        });

        /*
         * Sends user back to the world selection screen
         */
        worldCreationCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WorldCreationActivity.this, WorldSelActivity.class);
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
}