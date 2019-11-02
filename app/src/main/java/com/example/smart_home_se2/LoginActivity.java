package com.example.smart_home_se2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smart_home_se2.Utility.APIHandler;
import com.example.smart_home_se2.Utility.User;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("rememberme",Context.MODE_PRIVATE);
        editor = pref.edit();

        username = findViewById(R.id.editText);

        password = findViewById(R.id.editText2);

        Button button = findViewById(R.id.button);

        rememberMe = findViewById(R.id.checkBox);


        if(pref.getBoolean("rem",false) == true){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                user = APIHandler.getInstance().login(username.getText().toString(),password.getText().toString(),context);



                login();




            }
        });

    }


private void login(){

    try{
        if(user.getEmail().equals(username.getText().toString())){

            if(rememberMe.isChecked()) {
                editor.putBoolean("rem", true).commit();
            }


            editor.putString("username",user.getEmail()).commit();
            editor.putString("firstname",user.getFirstName()).commit();
            editor.putString("lastname",user.getLastName()).commit();
            editor.putString("password",password.getText().toString()).commit();
            editor.apply();

            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }}catch (NullPointerException e){
        Toast.makeText(context, "Failed Login", Toast.LENGTH_SHORT).show();
    }

}

}


