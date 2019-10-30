package com.example.smart_home_se2.Utility;

import android.content.Context;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class APIHandler {



    private static APIHandler apiHandler;

    //Enter Host ip adress of server.
    String hostIP = "192.168.1.232";
    String url = "https://"+hostIP+":8080/SmartHouseApi/";
    static User user_acc = null;
    static Device device = null;
    RequestQueue queue;
    ArrayList<Device> devices;




    public ArrayList<Device> devices(Context context){

        queue = Volley.newRequestQueue(context);
        String new_url = url + "devices";

        System.out.println(new_url);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, new_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("");
                    devices = new ArrayList<>();

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String deviceId = jsonObject.getString("deviceID");
                        String deviceName = jsonObject.getString("deviceName");
                        String deviceStatus = jsonObject.getString("deviceStatus");

                        System.out.println(deviceId);
                        System.out.println(deviceName);
                        System.out.println(deviceStatus);

                        devices.add(new Device(deviceName,deviceStatus,deviceId));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(request);

        return devices;

    }

    //
    public User login(String email,String pass){

        User user = null;


        String authString = email + ":" + pass;
        byte[] authStringEnc = Base64.encode(authString.getBytes(),Base64.DEFAULT);

        // TODO: 2019-10-21 Fix
        /**
        String name = email;
        String password = pass;
        String authString = name + ":" + password;
        byte[] authStringEnc = Base64.encode(authString.getBytes(),Base64.DEFAULT);
        System.out.println("Base64 encoded auth string: " + authStringEnc);

        Client client = ClientBuilder.newClient();
        WebTarget baseTarget = client.target("http://amirs-air-1405.lan:8080/SmartHouseApi/%22);



        new JsonTask().execute(url+"login/" +  email + "");

        return user_acc;

         */

        return user;
    }







    //Singleton.
    public static APIHandler getInstance(){
        if(apiHandler == null)
            apiHandler = new APIHandler();

        return apiHandler;
    }






    }





