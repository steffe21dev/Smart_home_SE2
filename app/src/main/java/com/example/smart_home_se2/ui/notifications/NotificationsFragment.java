package com.example.smart_home_se2.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smart_home_se2.R;
import com.example.smart_home_se2.Utility.APIHandler;
import com.example.smart_home_se2.Utility.Device;
import com.example.smart_home_se2.Utility.User;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;


    User user;
    SharedPreferences preferences;
    Context context;
    ArrayList<Device> devices;

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

        devices = APIHandler.getInstance().devices(context);


        preferences = getActivity().getSharedPreferences("rememberme", Context.MODE_PRIVATE);
        user = new User(preferences.getString("firstname",null),preferences.getString("lastname",null),
                preferences.getString("username",null),preferences.getString("password",null));









        return root;

    }



    private void initializeInfo(){

    }


}