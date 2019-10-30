package com.example.smart_home_se2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.smart_home_se2.Utility.APIHandler;
import com.example.smart_home_se2.Utility.Device;
import com.example.smart_home_se2.Utility.RequestSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("rememberme",0);
        final SharedPreferences.Editor editor = pref.edit();
        ArrayList<Device> list = new ArrayList<>();



        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://api.myjson.com/bins/kp9wz", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("employees");

                    System.out.println(response.toString());
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String deviceId = jsonObject.getString("firstname");
                        String deviceName = jsonObject.getString("age");
                        String deviceStatus = jsonObject.getString("mail");


                        System.out.println(jsonObject.getString("firstname"));
                        System.out.println(jsonObject.getString("age"));
                        System.out.println(jsonObject.getString("mail"));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("Gay");
            }
        });

        RequestSingleton.getInstance(this).addToRequestQueue(request);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(pref.getBoolean("rem",false) == true){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        final EditText username = findViewById(R.id.editText);

        final EditText password = findViewById(R.id.editText2);

        Button button = findViewById(R.id.button);

        final CheckBox rememberMe = findViewById(R.id.checkBox);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hashedPass = md5Hash(password.getText().toString());

                if (username.getText().toString().equals("admin") && hashedPass.equals("21232f297a57a5a743894a0e4a801fc3")){


                    APIHandler.getInstance().login(username.getText().toString(),password.getText().toString());


                    if(rememberMe.isChecked()){
                        editor.putBoolean("rem",true);
                    }


                    Toast.makeText(LoginActivity.this, "Sucessful login!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    System.out.println(username.getText() + "" + password.getText());


                }else{
                    System.out.println(username.getText() + " " + password.getText());

                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String md5Hash(String password){
        String passwordToHash = password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
            Log.d("Hash",generatedPassword);

            return generatedPassword;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return null;
    }


}


