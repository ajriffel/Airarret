package com.example.as1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * UI activity that pulls in a list of the current user's friends to show on the screen.
 */
public class FriendListActivity extends AppCompatActivity {

    private String TAG = FriendListActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    Button button;
    String tag_json_arry = "json_array_req";
    String currUrl = "http://coms-309-032.class.las.iastate.edu:8080/allFriends/";
    String currUser = Userdata.username;

    TextView[] tvX = new TextView[50];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);

        button = findViewById(R.id.bkBtn);

        /*
        Oh boy
         */
        tvX[0] = findViewById(R.id.friend);
        tvX[1] = findViewById(R.id.friend1);
        tvX[2] = findViewById(R.id.friend2);
        tvX[3] = findViewById(R.id.friend3);
        tvX[4] = findViewById(R.id.friend4);
        tvX[5] = findViewById(R.id.friend5);
        tvX[6] = findViewById(R.id.friend6);
        tvX[7] = findViewById(R.id.friend7);
        tvX[8] = findViewById(R.id.friend8);
        tvX[9] = findViewById(R.id.friend9);
        tvX[10] = findViewById(R.id.friend10);
        tvX[11] = findViewById(R.id.friend11);
        tvX[12] = findViewById(R.id.friend12);
        tvX[13] = findViewById(R.id.friend13);
        tvX[14] = findViewById(R.id.friend14);
        tvX[15] = findViewById(R.id.friend15);
        tvX[16] = findViewById(R.id.friend16);
        tvX[17] = findViewById(R.id.friend17);
        tvX[18] = findViewById(R.id.friend18);
        tvX[19] = findViewById(R.id.friend19);
        tvX[20] = findViewById(R.id.friend20);
        tvX[21] = findViewById(R.id.friend21);
        tvX[22] = findViewById(R.id.friend22);
        tvX[23] = findViewById(R.id.friend23);
        tvX[24] = findViewById(R.id.friend24);
        tvX[25] = findViewById(R.id.friend25);
        tvX[26] = findViewById(R.id.friend26);
        tvX[27] = findViewById(R.id.friend27);
        tvX[28] = findViewById(R.id.friend28);
        tvX[29] = findViewById(R.id.friend29);
        tvX[30] = findViewById(R.id.friend30);
        tvX[31] = findViewById(R.id.friend31);
        tvX[32] = findViewById(R.id.friend32);
        tvX[33] = findViewById(R.id.friend33);
        tvX[34] = findViewById(R.id.friend34);
        tvX[35] = findViewById(R.id.friend35);
        tvX[36] = findViewById(R.id.friend36);
        tvX[37] = findViewById(R.id.friend37);
        tvX[38] = findViewById(R.id.friend38);
        tvX[39] = findViewById(R.id.friend39);
        tvX[40] = findViewById(R.id.friend40);
        tvX[41] = findViewById(R.id.friend41);
        tvX[42] = findViewById(R.id.friend42);
        tvX[43] = findViewById(R.id.friend43);
        tvX[44] = findViewById(R.id.friend44);
        tvX[45] = findViewById(R.id.friend45);
        tvX[46] = findViewById(R.id.friend46);
        tvX[47] = findViewById(R.id.friend47);
        tvX[48] = findViewById(R.id.friend48);
        tvX[49] = findViewById(R.id.friend49);

        System.out.println("Here");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FriendListActivity.this, FriendsActivity.class);
                startActivity(intent);
            }
        });

        makeJsonArryReq(currUrl + currUser);


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
     * Requests an array of friends of the current user.
     * @param url The URL of the request to be made. Relatively redundant.
     */
    private void makeJsonArryReq(String url){
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray resp){
                        Log.d(TAG, resp.toString());
                        hideProgressDialog();
                        for(int i = 0; i < 50 && i < resp.length(); i++){
                            System.out.println(resp.length());
                            try {
                                System.out.println(i + " this i");
                                tvX[i].setText(resp.getString(i));
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
            }
        });
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

}