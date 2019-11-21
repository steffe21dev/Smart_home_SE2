package com.example.smart_home_se2.ui.dashboard;

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
import com.example.smart_home_se2.Utility.APIHandler;
import com.example.smart_home_se2.Utility.User;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;


    EditText firstName;

    EditText lastName;

    EditText email;

    Button addUser;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        firstName = root.findViewById(R.id.editText4);
        lastName = root.findViewById(R.id.editText5);
        email = root.findViewById(R.id.editText6);
        addUser = root.findViewById(R.id.button3);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName1 = firstName.getText().toString();
                String lastName1 = lastName.getText().toString();
                String email2 = email.getText().toString();

                User user = new User(firstName1,lastName1,email2,"");
                boolean bFalse= isValid(email2);
                if (bFalse==false) {
                    Toast.makeText(getContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                }
                APIHandler.getInstance().addUser(user,getContext());

            }
        });
        return root;


    }

    static boolean isValid(String email2){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email2.matches(regex);

    }
}