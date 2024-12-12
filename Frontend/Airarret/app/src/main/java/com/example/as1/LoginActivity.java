package com.example.as1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;
import com.example.as1.utils.Userdata;

import android.app.Activity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * User is prompted to log in to use the core features of the app
 */
public class LoginActivity extends AppCompatActivity {

    private String TAG = LoginActivity.class.getSimpleName();
    private String response;
    private ProgressDialog pDialog;

    private String tag_string_req = "string_req";
    private String tag_json_obj = "jobj_req";

    static String user = null;
    Button loginGoBtn;
    Button loginCancelBtn;
    Button registerBtn;
    TextView alert;
    private AlertDialog.Builder builder;

    EditText username;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginGoBtn = findViewById(R.id.loginGoBtn);
        loginCancelBtn = findViewById(R.id.loginCancelBtn);
        registerBtn = findViewById(R.id.registerBtn);
        username = findViewById(R.id.usernameText);
        password = findViewById(R.id.passwordText);
        alert = findViewById(R.id.popup);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);



        //  Takes user input in the username & password field
        //  username is stored in user
        //  password is stored in pass
        //  On success, notify the user
        loginGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Login Success!");
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                String pass;
                String user;
                user = username.getText().toString();
                pass = password.getText().toString();

                if (user.equals("")) {
                    //Tell user they didn't enter a username
                    builder.setTitle("Login Error");
                    builder.setMessage("Username field is empty");
                    builder.show();
                }
                else if (pass.equals("")) {
                    //Tell user they didn't enter a password
                    builder.setTitle("Login Error");
                    builder.setMessage("Password field is empty");
                    builder.show();
                }
                else {
                    sendUserInfo(user, pass);
                    hideProgressDialog();
                }
            }
        });


        // Takes user to a new screen to register
        // This is pretty much a duplicate, but for registering
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        // Goes back to the menu
        loginCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
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

//    private void makeObjRequest(String username, String password) {
//        showProgressDialog();
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
//                Const.URL_LOGIN_GET, null,
//                new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject resp) {
//                Log.d(TAG, resp.toString());
//                hideProgressDialog();
//
//                try {
//                    if (resp.getInt("code") >= 400) {
//                        builder.setTitle("Login Error");
//                        response = "Invalid username/password";
//                    }
//                    else {
//                        response = resp.getString("username");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                builder.setMessage(response);
//                builder.show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hideProgressDialog();
//                builder.setTitle("Login Error");
//                builder.setMessage("There was an error with the connection to the server");
//                builder.show();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("username", username);
//                params.put("password", password);
//
//                return params;
//            }
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//    }

    /**
     * Sends the information in the text fields of username and password to the server to validate it
     * On success/fail, notify the user
     * @param username User's entered username
     * @param password User's entered password
     */
    private void sendUserInfo(String username, String password) {
        showProgressDialog();

        JSONObject object = new JSONObject();
        try {
            object.put("username", username);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
                Const.URL_POST_LOGIN, object,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject resp) {
                Log.d(TAG, resp.toString());
                hideProgressDialog();
                try {
                    if (resp == null) {
                        builder.setTitle("Login Error");
                        response = "Invalid username/password";
                    }
                    else {
                        response = "Successfully signed in as ";
                        response += resp.getString("username");
                        user = resp.getString("username");
                        Userdata.userid = resp.getInt("id");
                        loginCancelBtn.setText("Back to menu");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                builder.setMessage(response);
                builder.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
                builder.setTitle("Login Error");
                builder.setMessage("There was an error with the connection to the server");
                builder.show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    /**
     * Tell UserData.java in the utils directory the user's username
     * @return user
     */
    public static String getUser() {
        return user;
    }

}