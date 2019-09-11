package com.example.smart_home_se2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LIGHT ONE WIDGET
        Switch lightOne = findViewById(R.id.switch2);


        //DETECT STATE CHANGE OF SWITCH
        lightOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2019-09-11 LOGIK FÖR DB UPPSKICK
            }
        });
    }

    private boolean initializeSwitch(){

        // TODO: 2019-09-11 Check db which state the button currently is in
        return Boolean.parseBoolean(null);
    }
}
