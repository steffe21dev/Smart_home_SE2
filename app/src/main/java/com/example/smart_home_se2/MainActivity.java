package com.example.smart_home_se2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;

import com.example.smart_home_se2.ui.dashboard.DashboardFragment;
import com.example.smart_home_se2.ui.home.HomeFragment;
import com.example.smart_home_se2.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    // TODO: 2019-10-01 SHPAT, fixa inläsning av json i mainactivityn 

    //LIGHT ONE WIDGET
    Switch lightOne;
    Switch lightTwo;
    Switch lightThree;
    Switch lightFour;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LIGHT ONE WIDGET
        lightOne = findViewById(R.id.switch1);
        lightTwo = findViewById(R.id.switch2);
        lightThree = findViewById(R.id.switch3);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        //Logic for listeners and onclick actions.
        initializeListeners();



    }


    private void initializeListeners(){
        //DETECT STATE CHANGE OF SWITCH
        lightOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2019-09-11 LOGIK FÖR DB UPPSKICK
            }
        });

        lightTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lightThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initializeSwitches(){

        // TODO: 2019-09-11 Check db which state the button currently is in

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =

            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment fragment = null;

                    switch(menuItem.getItemId()){

                        case R.id.navigation_home:

                            fragment = new HomeFragment();

                            break;

                        case R.id.navigation_dashboard:

                            fragment = new DashboardFragment();

                            break;

                        case R.id.navigation_notifications:

                            fragment = new NotificationsFragment();

                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.conatainer, fragment).commit();
                    return true;
                }
            };

}
