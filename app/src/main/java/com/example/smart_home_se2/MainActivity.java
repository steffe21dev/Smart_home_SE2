package com.example.smart_home_se2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.smart_home_se2.Utility.APIHandler;
import com.example.smart_home_se2.Utility.Device;
import com.example.smart_home_se2.ui.dashboard.DashboardFragment;
import com.example.smart_home_se2.ui.home.HomeFragment;
import com.example.smart_home_se2.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {



    //LIGHT ONE WIDGET
    Switch lightOne;
    Switch lightTwo;
    Switch lightThree;
    Switch lightFour;
    ArrayList<Device> devices;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RequestQueue queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LIGHT ONE WIDGET
        lightOne = findViewById(R.id.switch1);
        lightTwo = findViewById(R.id.switch2);
        lightThree = findViewById(R.id.switch3);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        //Logic for listeners and onclick actions.

        initializeDevices();
        initializeListeners();



    }


    private void initializeDevices(){
        ArrayList<Device> devices = APIHandler.getInstance().devices(this);

        try {
            for (int i = 0; i < devices.size(); i++){
                if(devices.get(i).getDeviceId().equals("1")){
                    if(devices.get(i).getDeviceStatus().equals("OFF")){
                        lightOne.setChecked(false);
                    }
                    else {
                        lightOne.setChecked(true);
                    }
                }
            }
        }catch (NullPointerException e){
            Toast.makeText(this, "Nullpointer", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void initializeListeners(){
        lightOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lightOne.isChecked()){
                    devices.get(1).setDeviceStatus("OFF");
                    APIHandler.getInstance().changeStateDevice(devices.get(1),getApplicationContext());
                }
                else{
                    devices.get(1).setDeviceStatus("ON");
                    APIHandler.getInstance().changeStateDevice(devices.get(1),getApplicationContext());
                }
            }
        });
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
