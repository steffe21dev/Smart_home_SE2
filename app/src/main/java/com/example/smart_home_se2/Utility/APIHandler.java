package com.example.smart_home_se2.Utility;

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
    ArrayList<Device> devices;
    static User user = null;


    public String getUrl() {
        return url;
    }





    public void addUser(User usern, Context context){
        String new_url = url + "users";

        JSONObject object = new JSONObject();

        try {
            object.put("firstName",usern.getFirstName());
            object.put("lastName",usern.getLastName());
            object.put("emailAddress",usern.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, new_url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }


    // FIXME: 2019-11-02 Implementera byte av lösenord
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updatePassword(User user1, String newPass, final Context context){
        String new_url = url + "login/"+user1.getEmail();

        final String authString = user1.getEmail() + ":" + user1.getPassword();

        final String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());

        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("firstName",user1.getFirstName());
            jsonObject.put("lastName",user1.getLastName());
            jsonObject.put("emailAddress",user1.getEmail());
            jsonObject.put("password",newPass);

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
        }){

            @Override
            public Map getHeaders()throws AuthFailureError {

                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic " + authStringEnc);

                return headers;

            }


        };


        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }



    public void changeStateDevice(Device device, final Context context){
        String new_url = url + "houseId/rooms/1/"+device.getDeviceId();

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
        String new_url = url + "houseId/rooms/1";

        System.out.println(new_url);

        devices = new ArrayList<>();


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





    //Singleton.
    public static APIHandler getInstance(){
        if(apiHandler == null)
            apiHandler = new APIHandler();

        return apiHandler;
    }


    public void forgottenPassword(String mail , final Context context){

        String new_url = url + "users/"+mail + "/forgotPassword";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, new_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, "Bra joba", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "inte pra Sine...", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(request);
    }

    }





