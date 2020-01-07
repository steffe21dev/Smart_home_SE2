package com.example.smart_home_se2.ui.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smart_home_se2.R;
import com.example.smart_home_se2.Utility.User;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    EditText oldPass, newPass;
    User user;
    SharedPreferences preferences;
    Button button;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_share, container, false);

        context = getActivity().getApplicationContext();

        preferences = getActivity().getSharedPreferences("rememberme", Context.MODE_PRIVATE);
        user = new User(preferences.getString("firstname", null), preferences.getString("lastname", null),
                preferences.getString("username", null), null);

        oldPass = root.findViewById(R.id.oldPass);
        newPass = root.findViewById(R.id.newPass);
        button = root.findViewById(R.id.changePassButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });


        return root;
    }

    private void changePass() {

        if (!oldPass.getText().toString().isEmpty() && !newPass.getText().toString().isEmpty()) {
            Toast.makeText(context, "Sucess!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "One or more fields are empty", Toast.LENGTH_SHORT).show();
        }
    }

}