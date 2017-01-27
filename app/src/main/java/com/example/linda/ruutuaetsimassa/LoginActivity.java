package com.example.linda.ruutuaetsimassa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.linda.ruutuaetsimassa.Entities.Car;
import com.example.linda.ruutuaetsimassa.Entities.PoleType;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import static com.example.linda.ruutuaetsimassa.HelperMethods.changeStatusBarColor;

public class LoginActivity extends AppCompatActivity
        implements LoginFragment.OnFragmentInteractionListener, OwnInfoFragment.OnFragmentInteractionListener,
        CreditCardFragment.OnFragmentInteractionListener, AddCarFragment.OnFragmentInteractionListener {

    private boolean appOpenedBefore;
    public SharedPreferences boolPrefs;
    SlidingUpPanelLayout sliderLO;

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

        changeStatusBarColor(this, "#22735C");

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
        //Button ohitaB = (Button) findViewById(R.id.ohita);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();

                LoginFragment loginFrag =
                        LoginFragment.newInstance();
                trans.add(R.id.loginFrag, loginFrag, "loginFrag");
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();

                OwnInfoFragment regFrag =
                        OwnInfoFragment.newInstance();
                trans.add(R.id.regFrag, regFrag, "regFrag");
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        /*ohitaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PoleType[] types = {PoleType.TYPE1, PoleType.CHADEMO};
                MapActivity.ownCar = new Car("Nissan Leaf", "ABC - 123", types);
                startMapActivity();
            }
        });*/
    }

    public void onLoginPressed() {
        PoleType[] types = {PoleType.TYPE1, PoleType.CHADEMO};
        MapActivity.ownCar = new Car("Nissan Leaf", "ABC - 123", types);
        startMapActivity();
    }

    public void setUserCar(Car userCar) {
        MapActivity.ownCar = userCar;
    }

    /**
     * Defines what happenes when user moves to the next part of registeration.
     * @param fromFragment the fragment the user comes from
     */

    public void onNextPressed(String fromFragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();

        switch (fromFragment) {
            case "ownInfoFrag":
                AddCarFragment addCarFrag =
                        AddCarFragment.newInstance();
                trans.add(R.id.addCarFrag, addCarFrag, "addCarFrag");
                break;
            case "addCarFrag":
                CreditCardFragment ccFrag =
                        CreditCardFragment.newInstance();
                trans.add(R.id.ccFrag, ccFrag, "ccFrag");
                break;
            case "ccFrag":
                startMapActivity();
                break;
        }

        trans.addToBackStack(null);
        trans.commit();
    }

    public void startMapActivity() {
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(intent);
        finish();
    }

    public void setAppAsOpenedBefore() {
        //TODO: muokkaa sharedpreferenssiä
        appOpenedBefore = true;
    }
}
