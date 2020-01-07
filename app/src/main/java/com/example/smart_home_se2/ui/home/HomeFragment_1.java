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
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.smart_home_se2.R;
import com.example.smart_home_se2.Utility.APIHandler;
import com.example.smart_home_se2.Utility.Device;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment_1 extends Fragment {

    private HomeViewModel_1 homeViewModel1;
    Switch deviceOne;
    Switch deviceTwo;
    Switch deviceThree;
    Switch deviceFour;
    Switch deviceFive;
    Switch deviceSix;

    SeekBar seekBar;


    TextView textView;
    Context context;
    ArrayList<Device> devices;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel1 =
                ViewModelProviders.of(this).get(HomeViewModel_1.class);
        View root = inflater.inflate(R.layout.fragment_home_1, container, false);





        context = this.getContext();

        devices = APIHandler.getInstance().devices(context);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("rememberme", Context.MODE_PRIVATE);


        ///Initialize the id of device switches
        TextView textView = root.findViewById(R.id.textView);
        deviceOne = root.findViewById(R.id.switch1);
        deviceTwo = root.findViewById(R.id.switch2);
        deviceThree = root.findViewById(R.id.switch4);
        seekBar = root.findViewById(R.id.seekBar2);
        deviceFive = root.findViewById(R.id.switch6);
        deviceSix = root.findViewById(R.id.switch7);





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
            if (devices.get(0).getDeviceStatus().equals("0")) {
                deviceOne.setChecked(false);
            } else {
                deviceOne.setChecked(true);
            }


            if (devices.get(1).getDeviceStatus().equals("0")) {
                deviceTwo.setChecked(false);
            } else {
                deviceTwo.setChecked(true);
            }


            if (devices.get(4).getDeviceStatus().equals("0")) {
                deviceThree.setChecked(false);
            } else {
                deviceThree.setChecked(true);
            }


            seekBar.setProgress(Integer.parseInt(devices.get(8).getDeviceStatus()));


            if (devices.get(12).getDeviceStatus().equals("0")) {
                deviceFive.setChecked(false);
            } else {
                deviceFive.setChecked(true);
            }

            if (devices.get(13).getDeviceStatus().equals("0")) {
                deviceSix.setChecked(false);
            } else {
                deviceSix.setChecked(true);
            }


        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }


    }


    private void initializeListeners(final Context context){

        deviceOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!deviceOne.isChecked()){
                    devices.get(0).setDeviceStatus("0");
                    devices.get(0).toString();
                    APIHandler.getInstance().changeStateDevice(devices.get(0),context);
                }
                else{
                    devices.get(0).setDeviceStatus("1");
                    APIHandler.getInstance().changeStateDevice(devices.get(0),context);
                }
            }
        });

        deviceTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!deviceTwo.isChecked()){
                    devices.get(1).setDeviceStatus("0");
                    devices.get(1).toString();
                    APIHandler.getInstance().changeStateDevice(devices.get(1),context);
                }
                else{
                    devices.get(1).setDeviceStatus("1");
                    APIHandler.getInstance().changeStateDevice(devices.get(1),context);
                }
            }
        });

        deviceThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!deviceThree.isChecked()){
                    devices.get(4).setDeviceStatus("0");
                    devices.get(4).toString();
                    APIHandler.getInstance().changeStateDevice(devices.get(4),context);
                }
                else{
                    devices.get(4).setDeviceStatus("1");
                    APIHandler.getInstance().changeStateDevice(devices.get(4),context);
                }
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                devices.get(8).setDeviceStatus(String.valueOf(seekBar.getProgress()));

                APIHandler.getInstance().changeStateDevice(devices.get(8),context);
            }
        });


        deviceFive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!deviceFive.isChecked()){
                    devices.get(12).setDeviceStatus("0");
                    devices.get(12).toString();
                    APIHandler.getInstance().changeStateDevice(devices.get(12),context);
                }
                else{
                    devices.get(12).setDeviceStatus("1");
                    APIHandler.getInstance().changeStateDevice(devices.get(12),context);
                }
            }
        });

        deviceSix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!deviceSix.isChecked()){
                    devices.get(13).setDeviceStatus("0");
                    devices.get(13).toString();
                    APIHandler.getInstance().changeStateDevice(devices.get(13),context);
                }
                else{
                    devices.get(13).setDeviceStatus("1");
                    APIHandler.getInstance().changeStateDevice(devices.get(13),context);
                }
            }
        });
    }
}