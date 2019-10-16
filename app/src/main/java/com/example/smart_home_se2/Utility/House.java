package com.example.smart_home_se2.Utility;

public class House {
    private String lamp;
    private String fan;
    private String alarm;
    private String window;

    public House () {

    }
    public House(String lamp, String alarm, String window) {
        this.lamp = lamp;
        this.alarm = alarm;
        this.window = window;
    }

    public String getLamp() {
        return lamp;
    }

    public void setLamp(String lamp) {
        this.lamp = lamp;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setDoor(String alarm) {
        this.alarm = alarm;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }
}
