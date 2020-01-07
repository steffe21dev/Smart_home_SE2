package com.example.smart_home_se2.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.smart_home_se2.R;
import com.example.smart_home_se2.Utility.APIHandler;
import com.example.smart_home_se2.Utility.Device;
import com.example.smart_home_se2.Utility.RequestSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    ListView listView;


    Context context;
    ArrayList<Device> devices;
    ArrayAdapter<Device> arrayAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });


        listView = root.findViewById(R.id.listview2);

        context = this.getContext();

        getDevices(context);


        //SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("rememberme", Context.MODE_PRIVATE);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Device device = (Device)listView.getItemAtPosition(position);


                if(!device.getDeviceName().contains("temp") && !device.getDeviceName().contains("fan")) {
                    switch (device.getDeviceStatus()) {
                        case "1":
                            device.setDeviceStatus("0");
                            break;
                        case "0":
                            device.setDeviceStatus("1");
                            break;

                        default:
                            Toast.makeText(context, "You can't do this", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(device.getDeviceName().contains("temp")) {
                    Toast.makeText(context, "The temp inside is " + device.getDeviceStatus() +"°", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Control the fan with the slider below", Toast.LENGTH_SHORT).show();
                }

                APIHandler.getInstance().changeStateDevice(device,context);

                getDevices(context);
            }
        });







        return root;
    }




    public void getDevices(final Context context){

        String new_url = APIHandler.getInstance().getUrl() + "rooms/2";

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


                    initList();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);


    }


    public void initList(){


        arrayAdapter = new ArrayAdapter<>(context,android.R.layout.simple_expandable_list_item_1

                ,devices);



        listView.setAdapter(arrayAdapter);
    }
}




