package com.example.smart_home_se2.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.smart_home_se2.R;
import com.example.smart_home_se2.Utility.APIHandler;
import com.example.smart_home_se2.Utility.Device;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Switch lightOne;
    Switch lightTwo;
    Switch lightThree;
    TextView textView;
    Context context;
    ArrayList<Device> devices;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);





        context = this.getContext();

        devices = APIHandler.getInstance().devices(context);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("rememberme", Context.MODE_PRIVATE);

        TextView textView = root.findViewById(R.id.textView);
        lightOne = root.findViewById(R.id.switch1);
        lightTwo = root.findViewById(R.id.switch2);
        lightThree = root.findViewById(R.id.switch3);


        textView.setText("Welcome home " + sharedPreferences.getString("firstname","Error"));
        textView.setGravity(Gravity.CENTER);


        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,500);
                initializeDevices();
            }
        };
        runnable.run();


        initializeListeners(context);





        return root;


    }


    private void initializeDevices(){

        try {
            if (devices.get(0).getDeviceStatus().equals("OFF")) {
                lightOne.setChecked(false);
            } else {
                lightOne.setChecked(true);
            }


            if (devices.get(1).getDeviceStatus().equals("OFF")) {
                lightTwo.setChecked(false);
            } else {
                lightTwo.setChecked(true);
            }


            if (devices.get(2).getDeviceStatus().equals("OFF")) {
                lightThree.setChecked(false);
            } else {
                lightThree.setChecked(true);
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }


    }


    private void initializeListeners(final Context context){

        lightOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!lightOne.isChecked()){
                    devices.get(0).setDeviceStatus("OFF");
                    devices.get(0).toString();
                    APIHandler.getInstance().changeStateDevice(devices.get(0),context);
                }
                else{
                    devices.get(0).setDeviceStatus("ON");
                    APIHandler.getInstance().changeStateDevice(devices.get(0),context);
                }
            }
        });

        lightTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!lightTwo.isChecked()){
                    devices.get(1).setDeviceStatus("OFF");
                    devices.get(1).toString();
                    APIHandler.getInstance().changeStateDevice(devices.get(1),context);
                }
                else{
                    devices.get(1).setDeviceStatus("ON");
                    APIHandler.getInstance().changeStateDevice(devices.get(1),context);
                }
            }
        });

        lightThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!lightThree.isChecked()){
                    devices.get(2).setDeviceStatus("OFF");
                    devices.get(2).toString();
                    APIHandler.getInstance().changeStateDevice(devices.get(2),context);
                }
                else{
                    devices.get(2).setDeviceStatus("ON");
                    APIHandler.getInstance().changeStateDevice(devices.get(2),context);
                }
            }
        });
    }
}