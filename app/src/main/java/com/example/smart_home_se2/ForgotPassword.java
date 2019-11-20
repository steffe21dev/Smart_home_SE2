package com.example.smart_home_se2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smart_home_se2.Utility.APIHandler;

public class ForgotPassword extends AppCompatActivity {

    EditText editText;

    TextView textView;

    EditText editText1;

    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        button = findViewById(R.id.button2);

        editText = findViewById(R.id.editText2);

        editText1 = findViewById(R.id.editText3);

        textView = findViewById(R.id.textView3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                APIHandler.getInstance().forgottenPassword(editText1.getText().toString(),getApplicationContext());
            }
        });
    }
}
