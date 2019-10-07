package com.example.smart_home_se2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {


    // TODO: 2019-10-01 SHPAT, fixa inläsning av json i mainactivityn 

    //LIGHT ONE WIDGET
    Switch lightOne;
    Switch lightTwo;
    Switch lightThree;
    Switch lightFour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LIGHT ONE WIDGET
        lightOne = findViewById(R.id.switch1);
        lightTwo = findViewById(R.id.switch2);
        lightThree = findViewById(R.id.switch3);


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
}
