package com.example.nissan.schedulemanager.admin.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nissan.schedulemanager.R;
import com.example.nissan.schedulemanager.admin.DashboardAdminUpdate;


public class AdminDashboardFragment extends Fragment {

  /*  public AdminDashboardFragment(){

    } */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_admin_dashboard_fragment, container, false);

        Button btnUpdate = view.findViewById(R.id.btnUpdateDash);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DashboardAdminUpdate.class);
                startActivity(intent);
            }
        });

    return view;
    }

}
