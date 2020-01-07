package com.example.smart_home_se2.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.smart_home_se2.R;
import com.example.smart_home_se2.Utility.Device;
import com.example.smart_home_se2.Utility.RequestSingleton;
import com.example.smart_home_se2.Utility.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    TextView d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15;
    String hostIP;
    String url;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications_1, container, false);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        hostIP = "192.168.1.232";
        url = "http://"+hostIP+":8080/SmartHouseApi/";

        initID(root);
        initCall(context);
        context = this.getContext();



        preferences = getActivity().getSharedPreferences("rememberme", Context.MODE_PRIVATE);
        user = new User(preferences.getString("firstname",null),preferences.getString("lastname",null),
                preferences.getString("username",null),preferences.getString("password",null));









        return root;

    }



    private void initializeInfo(){

        d1.setText(devices.get(0).prettyPrint());
        d2.setText(devices.get(1).prettyPrint());
        d3.setText(devices.get(2).prettyPrint());
        d4.setText(devices.get(3).prettyPrint());
        d5.setText(devices.get(4).prettyPrint());
        d6.setText(devices.get(5).prettyPrint());
        d7.setText(devices.get(6).prettyPrint());
        d8.setText(devices.get(7).prettyPrint());
        d9.setText(devices.get(8).prettyPrint());
        d11.setText(devices.get(10).prettyPrint());
        d12.setText(devices.get(11).prettyPrint());
        d13.setText(devices.get(12).prettyPrint());
        d14.setText(devices.get(13).prettyPrint());
        d15.setText(devices.get(14).prettyPrint());





    }


    private void initID(View root){

        d1 = root.findViewById(R.id.d1);
        d2 = root.findViewById(R.id.d2);
        d3 = root.findViewById(R.id.d3);
        d4 = root.findViewById(R.id.d4);
        d5 = root.findViewById(R.id.d5);
        d6 = root.findViewById(R.id.d6);
        d7 = root.findViewById(R.id.d7);
        d8 = root.findViewById(R.id.d8);
        d9 = root.findViewById(R.id.d9);
        d11 = root.findViewById(R.id.d11);
        d12 = root.findViewById(R.id.d12);
        d13 = root.findViewById(R.id.d13);
        d14 = root.findViewById(R.id.d14);
        d15 = root.findViewById(R.id.d15);

    }


    public void initCall(Context context){

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

                initializeInfo();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error calling api", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        RequestSingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);


    }


}