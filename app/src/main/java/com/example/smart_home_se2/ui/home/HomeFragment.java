package com.example.smart_home_se2.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smart_home_se2.R;
import com.example.smart_home_se2.Utility.APIHandler;
import com.example.smart_home_se2.Utility.Device;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Switch lightOne;
    Switch lightTwo;
    Switch lightThree;
    Switch lightFour;
    TextView textView;
    Context context;
    ArrayList<Device> devices;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        context = this.getContext();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("rememberme", Context.MODE_PRIVATE);

        TextView textView = root.findViewById(R.id.textView);
        lightOne = root.findViewById(R.id.switch1);

        textView.setText("Welcome home " + sharedPreferences.getString("firstname","Error"));
        textView.setGravity(Gravity.CENTER);


        initializeDevices();
        initializeListeners(context);



        return root;


    }


    private void initializeDevices(){
        devices = APIHandler.getInstance().devices(this.getContext());

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
            Toast.makeText(this.getContext(), "Nullpointer", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void initializeListeners(final Context context){
        lightOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(lightOne.isChecked()){
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
    }
}