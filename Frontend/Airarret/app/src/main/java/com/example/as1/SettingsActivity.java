package com.example.as1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.example.as1.utils.Userdata;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.ProgressDialog;
import android.widget.TextView;

/**
 * The screen for customizable settings within the menu
 */
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String TAG = SettingsActivity.class.getSimpleName();
    private String response;
    private ProgressDialog pDialog;

    private String tag_string_req = "string_req";
    private String tag_json_obj = "jobj_req";

    Button prevBtn;
    Button apply;
    Spinner colors;
    Spinner res;
    Slider volume;
    String colorCurItem;
    String resCurItem;

    TextView alert;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prevBtn = findViewById(R.id.prevBtn);
        colors = findViewById(R.id.colors);
        res = findViewById(R.id.resolutions);
        volume = findViewById(R.id.volume);
        apply = findViewById(R.id.applybtn);
        alert = findViewById(R.id.popupThree);

        String name = Userdata.getUser();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        colorCurItem = "Purple";
        resCurItem = "1920x1080";
        ArrayAdapter colAdapter = ArrayAdapter.createFromResource(this, R.array.colors, R.layout.spinner_header);
        colAdapter.setDropDownViewResource(R.layout.spinner_list);

        ArrayAdapter resAdapter = ArrayAdapter.createFromResource(this, R.array.resolutions, R.layout.spinner_header);
        resAdapter.setDropDownViewResource(R.layout.spinner_list);

        colors.setAdapter(colAdapter);
        res.setAdapter(resAdapter);

        SharedPreferences sharedPrefs = getSharedPreferences("com.example.as1", MODE_PRIVATE);

        /*
         * The listener for the currently selected color
         */
        colors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                colorCurItem = parent.getItemAtPosition(pos).toString();
        }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

        }
        });

        /*
         * The listener for the currently selected resolution
         */
        res.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                resCurItem = parent.getItemAtPosition(pos).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
         * The listener for the current volume
         */
        volume.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return null;
            }
        });

//        volume.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
//            @Override
//            public void onStartTrackingTouch(slider: Slider) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(slider: Slider) {
//
//            }
//        });

        /*
         * Sends user back to the main menu
         */
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingsActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        /*
         * Applies all customizable settings and sends them to the server using sendUserInfo()
         */
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Settings Applied!");

                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                sendUserInfo(name, colorCurItem, resCurItem);
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

//    private void makeObjRequest(String color, String res) {
//        showProgressDialog();
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                Const.URL_LOGIN_GET, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject resp) {
//                        Log.d(TAG, resp.toString());
//                        hideProgressDialog();
//                        try {
//
//                            response = resp.getString("color");
//                            response += " " + resp.getString("resolution");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        builder.setMessage(response);
//                        builder.show();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hideProgressDialog();
//                builder.setTitle("Settings Error:");
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
//                params.put("color", color);
//                params.put("resolution", res);
//
//                return params;
//            }
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//    }

    /**
     * Sends name, color, and res to the server
     * Server updates these variables on the back-end
     * Notify the user on fail/success
     * On success, show what they changed
     * @param name Currently signed-in username
     * @param color Currently selected color
     * @param res Currently selected Resolution
     */
    private void sendUserInfo(String name, String color, String res) {
        showProgressDialog();

        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
            object.put("color", color);
            object.put("resolution", res);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.URL_POST_SETTINGS, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        Log.d(TAG, resp.toString());
                        hideProgressDialog();
                        try {
                            response = "applied color ";
                            response += resp.getString("color");
                            response += " and resolution ";
                            response += resp.getString("resolution");
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
                builder.setTitle("Settings Error:");
                builder.setMessage("There was an error with the connection to the server");
                builder.show();
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

}