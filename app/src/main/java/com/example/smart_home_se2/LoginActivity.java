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


    User user = null;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = this;

        SharedPreferences pref = getApplicationContext().getSharedPreferences("rememberme",0);
        final SharedPreferences.Editor editor = pref.edit();

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                user = APIHandler.getInstance().login(username.getText().toString(),password.getText().toString(),context);


                try{
                if(user.getEmail().equals(username.getText().toString())){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                }}catch (NullPointerException e){
                    Toast.makeText(context, "Failed Login", Toast.LENGTH_SHORT).show();
                }




            }
        });

    }




}


