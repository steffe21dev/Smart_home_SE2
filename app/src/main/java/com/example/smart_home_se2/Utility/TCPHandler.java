package com.example.smart_home_se2.Utility;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

class TCPHandler {

    Socket socket = null;
    ObjectOutputStream out = null;
    BufferedReader in = null;

    private static final TCPHandler ourInstance = new TCPHandler();

    static TCPHandler getInstance() {
        return ourInstance;
    }

    private TCPHandler() {
        JSONObject object = new JSONObject();
        House house = new House();
    }




    public void send(String json){
        try {
            // TODO: 2019-10-13 Fixa anslutning 
            socket = new Socket("192.168.1.232",12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(json);

            String messageBack = in.readLine();
            System.out.println(messageBack);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
