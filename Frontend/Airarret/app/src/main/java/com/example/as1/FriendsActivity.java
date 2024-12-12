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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;
import com.example.as1.utils.Userdata;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

/**
 * UI Activity that allows the current user to add friends via an EditText object.
 */
public class FriendsActivity extends AppCompatActivity {

    private String TAG = FriendsActivity.class.getSimpleName();
    private ProgressDialog pDialog;

    private String tag_string_req = "string_req";


    Button button;
    Button addFriendButton;
    Button friendListButton;

    TextView[] tvX = new TextView[50];
    EditText u2;

    TextView alert;
    private AlertDialog.Builder builder;

    String URL_POST_ADD_FRIEND = "http://coms-309-032.class.las.iastate.edu:8080/addFriend?u1=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        builder = new AlertDialog.Builder(FriendsActivity.this);
        builder.setCancelable(true);

        button = findViewById(R.id.bkBtn);
        addFriendButton = findViewById(R.id.addFrBtn);
        friendListButton = findViewById(R.id.fListBtn);
        alert = findViewById(R.id.popupThree);
        u2 = findViewById(R.id.friendText);


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

//        String testing;
//        testing = Userdata.getUser();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FriendsActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String user1 = Userdata.getUser();
                String user2;
                user2 = u2.getText().toString();

                URL_POST_ADD_FRIEND += user1 + "&u2=" + user2;
                sendUserInfo(user2);

            }
        });

        friendListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FriendsActivity.this, FriendListActivity.class);
                startActivity(intent);
            }
        });


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
     * Sends the current user as well as another to the backend. Response value determines whether or not it worked.
     * @param user2 The user being added.
     */
    private void sendUserInfo(String user2) {
        showProgressDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_POST_ADD_FRIEND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {
                        Log.d(TAG, resp.toString());
                        hideProgressDialog();
                        if(resp.toString().equals("Success")) {
                            builder.setTitle("Friend added!");
                            builder.setMessage("You have added " + user2 + " as a friend!");
                            builder.show();
                        }
                        else {
                            builder.setTitle("Friends Error:");
                            builder.setMessage("There was an error with the connection to the server");
                            builder.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                builder.setTitle("Friends Error:");
                builder.setMessage("There was an error with the connection to the server");
                builder.show();
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}