package com.example.smart_home_se2.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smart_home_se2.R;
import com.example.smart_home_se2.Utility.APIHandler;
import com.example.smart_home_se2.Utility.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;


    EditText oldPass,newPass;
    User user;
    SharedPreferences preferences;
    Button button;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        context = this.getContext();

        preferences = getActivity().getSharedPreferences("rememberme", Context.MODE_PRIVATE);
        user = new User(preferences.getString("firstname",null),preferences.getString("lastname",null),
                preferences.getString("username",null),preferences.getString("password",null));

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


    private void changePass(){

        if(!oldPass.getText().toString().isEmpty() && !newPass.getText().toString().isEmpty()){
            APIHandler.getInstance().updatePassword(user,newPass.getText().toString(),getContext());
        }
        else {
            Toast.makeText(context, "One or more fields are empty", Toast.LENGTH_SHORT).show();
        }

    }
}