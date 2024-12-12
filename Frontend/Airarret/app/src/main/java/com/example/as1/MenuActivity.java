package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.String;

import com.example.as1.utils.Userdata;

/**
 * The main menu of the game
 */
public class MenuActivity extends AppCompatActivity {

    Button playBtn;
    Button backBtn;
    Button settingsBtn;
    Button friendsBtn;
    Button loginBtn;
    TextView alert;
    private AlertDialog.Builder builder;
    static String loggedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        loggedUser = Userdata.getUser();
        playBtn = findViewById(R.id.playBtn);
        settingsBtn = findViewById(R.id.setBtn);
        backBtn = findViewById(R.id.backBtn);
        friendsBtn = findViewById(R.id.friendsBtn);
        loginBtn = findViewById(R.id.loginBtn);
        alert = findViewById(R.id.popupMenu);

        builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Error");
        builder.setMessage("You shouldn't be seeing this!");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        /*
         * Checks if user is logged in
         * If so, send them to select a character to play
         * Otherwise, tell them they need to log in
         */
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (loggedUser == null) {
                    builder.setTitle("Unable to start playing");
                    builder.setMessage("User not logged in!");
                    builder.show();
                }
                else {
                    Intent intent = new Intent(MenuActivity.this, CharSelActivity.class);
                    startActivity(intent);
                }
            }
        });

        /*
         * Checks if user is logged in
         * If so, send them to settings screen
         * Otherwise, tell them they need to log in
         */
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggedUser == null) {
                    builder.setTitle("Unable to change settings");
                    builder.setMessage("User not logged in!");
                    builder.show();
                }
                else {
                    Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
            }
        });

        /*
         * Sends user to login screen
         */
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        /*
         * Checks if user is logged in
         * If so, send them to friends screen
         * Otherwise, tell them they need to log in
         */
        friendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggedUser == null) {
                    builder.setTitle("Unable to view friends page");
                    builder.setMessage("User not logged in!");
                    builder.show();
                }
                else {
                    Intent intent = new Intent(MenuActivity.this, FriendsActivity.class);
                    startActivity(intent);
                }
            }
        });

        /*
         * Sends user back to the title screen
         */
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}