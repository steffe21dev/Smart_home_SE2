package com.example.smart_home_se2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.smart_home_se2.Utility.APIHandler;
import com.example.smart_home_se2.Utility.RequestSingleton;
import com.example.smart_home_se2.Utility.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    static User user = null;
    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    CheckBox rememberMe;
    EditText username;
    EditText password;
    Boolean legit;
    ProgressDialog mProgressDialog;

    TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        legit = false;
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("rememberme",Context.MODE_PRIVATE);
        editor = pref.edit();

        username = findViewById(R.id.editText);

        password = findViewById(R.id.editText2);

        Button button = findViewById(R.id.button);

        rememberMe = findViewById(R.id.checkBox);

        forgotPassword = findViewById(R.id.password);


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));

            }
        });


        if(pref.getBoolean("rem",false) == true){
            startActivity(new Intent(LoginActivity.this,ShowRoomActivity.class));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                login(username.getText().toString(),password.getText().toString());

            }
        });


    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void login(String email, String pass){


        mProgressDialog = new ProgressDialog(context);

        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        String new_url = APIHandler.getInstance().getUrl() + "login/" + email;
        System.out.println(new_url);


        final String authString = email + ":" + pass;
        final String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());

        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, new_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    user = new User(response.getString("firstName"),response.getString("lastName"),response.getString("emailAddress"),null);
                    System.out.println(user.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try{
                    if(user.getEmailAddress().equals(username.getText().toString())){

                        mProgressDialog.dismiss();

                        if(rememberMe.isChecked()) {
                            editor.putBoolean("rem", true).commit();
                        }


                        editor.putString("username",user.getEmailAddress()).commit();
                        editor.putString("firstname",user.getFirstName()).commit();
                        editor.putString("lastname",user.getLastName()).commit();
                        editor.putString("password",password.getText().toString()).commit();
                        editor.apply();

                        startActivity(new Intent(LoginActivity.this,ShowRoomActivity.class));
                        Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }}catch (NullPointerException e){
                    Toast.makeText(LoginActivity.this, "Failed Login", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mProgressDialog.dismiss();
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


    }


}


