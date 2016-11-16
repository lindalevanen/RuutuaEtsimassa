package com.example.linda.ruutuaetsimassa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Map;
import java.util.Set;

//TODO: login and register fragments

public class LoginActivity extends AppCompatActivity {

    private boolean appOpenedBefore;
    public SharedPreferences boolPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Editointi:
        /*
        SharedPreferences.Editor editor = favPrefs.edit();
                String json = convertToJson(landmarklist);
                editor.putString("appOpenedBefore", json);
                editor.apply();
        */

        // Set up Shared Preferences
        boolPrefs = getSharedPreferences("boolPrefs", Context.MODE_PRIVATE);

        if(boolPrefs.contains("appOpenedBefore")) {

            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(intent);
            finish();

        } else {
            //TODO: tee kaikki jutut mitä tarvii loginissa ja rekkaamisessa
            //TODO: muista lisää boolPrefseihin appopenedbefore muuttuja kun valmista
            initButtons();
        }
    }

    private void initButtons() {
        Button loginB = (Button) findViewById(R.id.logB);
        Button registerB = (Button) findViewById(R.id.regB);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: open registerFragment
            }
        });
    }

    public void setAppAsOpenedBefore() {
        appOpenedBefore = true;
    }
}
