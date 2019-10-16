package com.example.smart_home_se2.Utility;

public class Device {
    String deviceName, deviceStatus, deviceId;

    public Device(String deviceName, String deviceStatus, String deviceId) {
        this.deviceName = deviceName;
        this.deviceStatus = deviceStatus;
        this.deviceId = deviceId;
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

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    @Override
    public String toString() {
        return "Device{" +
                "deviceName='" + deviceName + '\'' +
                ", deviceStatus='" + deviceStatus + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }

}
