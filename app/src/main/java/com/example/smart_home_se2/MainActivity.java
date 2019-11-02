package com.example.smart_home_se2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.smart_home_se2.Utility.User;
import com.example.smart_home_se2.ui.dashboard.DashboardFragment;
import com.example.smart_home_se2.ui.home.HomeFragment;
import com.example.smart_home_se2.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {



    //LIGHT ONE WIDGET

    SharedPreferences result;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RequestQueue queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        User user = LoginActivity.user;


        //LIGHT ONE WIDGET

        //textView.setText("Welcome " + user.getFirstName());


        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        //Logic for listeners and onclick actions.

        //initializeDevices();
        //initializeListeners();



    }






    private BottomNavigationView.OnNavigationItemSelectedListener navListener =

            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment fragment = null;

                    switch(menuItem.getItemId()){

                        case R.id.navigation_home:

                            fragment = new HomeFragment();

                            break;

                        case R.id.navigation_dashboard:

                            fragment = new DashboardFragment();

                            break;

                        case R.id.navigation_notifications:

                            fragment = new NotificationsFragment();

                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.conatainer, fragment).commit();
                    return true;
                }
            };





}
