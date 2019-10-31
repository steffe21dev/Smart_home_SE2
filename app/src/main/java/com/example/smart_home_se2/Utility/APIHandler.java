package com.example.smart_home_se2.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class APIHandler {



    private static APIHandler apiHandler;

    //Enter Host ip adress of server.
    String hostIP = "192.168.1.232";
    String url = "http://"+hostIP+":8080/SmartHouseApi/";
    static User user_acc = null;
    static Device device = null;
    RequestQueue queue;
    ArrayList<Device> devices = new ArrayList<>();
    static User user = null;

    ProgressDialog mProgressDialog;







    public void changeStateDevice(Device device, final Context context){
        String new_url = url + "devices/";

        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("deviceId",String.valueOf(device.getDeviceId()));
            jsonObject.put("deviceName",device.deviceName);
            jsonObject.put("deviceStatus",device.getDeviceStatus());
        }
        catch (Exception e){
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, new_url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                Log.d("Response",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                Log.d("Response",error.toString());
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }


    public ArrayList<Device> devices(Context context){

        queue = Volley.newRequestQueue(context);
        String new_url = url + "devices";

        System.out.println(new_url);


        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, new_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);


                        String deviceId = jsonObject.getString("deviceId");
                        String deviceName = jsonObject.getString("deviceName");
                        String deviceStatus = jsonObject.getString("deviceStatus");

                        System.out.println(deviceId);
                        System.out.println(deviceName);
                        System.out.println(deviceStatus);

                        devices.add(new Device(deviceName,deviceStatus,deviceId));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

        return devices;

    }

    //
    @RequiresApi(api = Build.VERSION_CODES.O)
    public User login(String email, String pass, Context context){


        mProgressDialog = new ProgressDialog(context);

        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        String new_url = url + "login/" + email;


        final String authString = email + ":" + pass;
        final String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());

        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, new_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    user = new User(response.getString("firstName"),response.getString("lastName"),response.getString("emailAddress"),null);
                    System.out.println(user.toString());
                    mProgressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.toString());
                System.out.println(authStringEnc);
            }
        }){

            @Override
            public Map getHeaders()throws AuthFailureError {

                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic " + authStringEnc);

                return headers;

            }


        };

        RequestSingleton.getInstance(context).addToRequestQueue(objectRequest,"headerRequest");

        return user;

    }








    //Singleton.
    public static APIHandler getInstance(){
        if(apiHandler == null)
            apiHandler = new APIHandler();

        return apiHandler;
    }






    }





