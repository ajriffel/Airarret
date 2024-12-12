package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;
import android.app.Activity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The screen for user registration to play the game
 */
public class RegisterActivity extends AppCompatActivity {

    private static String user;
    private String TAG = LoginActivity.class.getSimpleName();
    private String response;
    private ProgressDialog pDialog;

    private String tag_string_req = "string_req";
    private String tag_json_obj = "jobj_req";

    Button createAccBtn;
    Button registerCancelBtn;

    TextView alert;
    private AlertDialog.Builder builder;

    EditText username;
    EditText password1;
    EditText password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccBtn = findViewById(R.id.createAccBtn);
        registerCancelBtn = findViewById(R.id.registerCancelBtn);
        username = findViewById(R.id.regUsernameText);
        password1 = findViewById(R.id.passwordText1);
        password2 = findViewById(R.id.passwordText2);
        alert = findViewById(R.id.popupTwo);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        /*
         * Takes user input in the username & password fields
         * username is stored in user
         * password 1 is stored in pass1
         * password 2 is stored in pass2
         * Checks there is an input and that both passwords match
         * Finally, sends info to sendUserInfo()
         */
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Account Creation Success!");
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alert.setVisibility(View.VISIBLE);
                    }
                });

                String pass1;
                String pass2;
                String user;
                user = username.getText().toString();
                pass1 = password1.getText().toString();
                pass2 = password2.getText().toString();

                if (user.equals("")) {
                    //Tell user they didn't enter a username
                    builder.setTitle("Account Creation Error");
                    builder.setMessage("Username field is empty");
                    builder.show();
                }

                else if (pass1.equals("") || pass2.equals("")) {
                    //Tell user they didn't enter a password in one of the fields
                    builder.setTitle("Account Creation Error");
                    builder.setMessage("A password field is empty");
                    builder.show();
                }

                else if (!pass1.equals(pass2)) {
                    //Tell user the passwords don't match
                    builder.setTitle("Account Creation Error");
                    builder.setMessage("Passwords do not match");
                    builder.show();
                }
                else {
                    sendUserInfo(user, pass1);
                }


            }
        });

        /*
         * Sends user back to the main menu
         */
        registerCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
     * Sends username and password to the server to validate it
     * If the account is already taken, notify the user
     * Otherwise, on success, notify the user the account was created
     * @param user User's entered username
     * @param pass User's entered password
     */
    private void sendUserInfo(String user, String pass) {
        showProgressDialog();

        JSONObject object = new JSONObject();
        try {
            object.put("username", user);
            object.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.URL_POST_REGISTER, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        Log.d(TAG, resp.toString());
                        hideProgressDialog();
                        try {
                            if (resp == null) {
                                builder.setTitle("Account Creation Error");
                                response = "Username already taken";
                            }
                            else {
                                response = "Successfully registered and logged in as " + resp.getString("username") + "!";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        builder.setMessage(response);
                        builder.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                builder.setTitle("Account Creation Error");
                builder.setMessage("There was an error with the connection to the server");
                builder.show();
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}