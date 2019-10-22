package com.example.smart_home_se2.Utility;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.smart_home_se2.MainActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIHandler {



    private static APIHandler apiHandler = null;

    //Enter Host ip adress of server.
    String hostIP = "localhost";
    String url = "http://"+hostIP+":8080/SmartHouseApi/";
    static User user_acc = null;
    static Device device = null;



    //
    public User login(String email,String pass){

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

        return null;
    }



    //Singleton.
    public static APIHandler getInstance(){
        if(apiHandler == null)
            apiHandler = new APIHandler();

        return apiHandler;
    }




    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result, String type) {
            super.onPostExecute(result);

            Gson gson = new Gson();


            switch (type){
                case "user":
                    JsonParser parser = new JsonParser();
                    JsonObject object = (JsonObject) parser.parse(result);
                    user_acc = gson.fromJson(object,User.class);
                    break;

                case "device":
                    JsonObject obj = new JsonObject(result);
                    device = gson.fromJson(obj,Device.class);
                    break;

                default:
                    System.out.println("Error wrong type!");
                    break;
            }
            user_acc = gson.fromJson(object,User.class);

        }


    }

}



