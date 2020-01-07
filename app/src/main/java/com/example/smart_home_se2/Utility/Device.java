package com.example.smart_home_se2.Utility;

import android.content.Context;
import android.widget.Toast;

public class Device {
    String deviceName, deviceStatus, deviceId,roomId;

    public Device(String deviceName, String deviceStatus, String deviceId,String roomId) {
        this.deviceName = deviceName;
        this.deviceStatus = deviceStatus;
        this.deviceId = deviceId;
        this.roomId = roomId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus, Context context) {

        if(!deviceName.toUpperCase().contains("window".toUpperCase()) && !deviceName.toUpperCase().contains("alarm".toUpperCase()) && !deviceName.toUpperCase().contains("leakage".toUpperCase()) && !deviceName.toUpperCase().contains("Sensor".toUpperCase())) {
            this.deviceStatus = deviceStatus;
        }
        else {
            Toast.makeText(context, "Not a changeable device", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    @Override
    public String toString() {
        String text = "";
        String name = getDeviceName().substring(0,1).toUpperCase() + getDeviceName().substring(1);


        if(deviceStatus.equals("1"))
            text = "ON";
        else if(deviceStatus.equals("0"))
            text = "OFF";
        else if(deviceName.contains("temp"))
            text = deviceStatus +"°";
        else if(deviceName.contains("fan"))
            text = deviceStatus + "";

        return name + "  :  " + text;
    }


    public String prettyPrint(){

        if(deviceStatus.equals("1"))
            return deviceName + " : " + "ON";
        else if(deviceStatus.equals("0"))
            return deviceName + " : " + "OFF";
        else if(deviceName.contains("temp"))
            return deviceName + " : " + deviceStatus +"°";




        return deviceName + " : " + deviceStatus;
    }

}
