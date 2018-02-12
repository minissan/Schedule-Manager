package com.example.nissan.schedulemanager.admin.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nissan.schedulemanager.R;
import com.example.nissan.schedulemanager.admin.FlightAdminUpdate;

public class AdminFlightInfoFragment extends Fragment {

    /*public AdminFlightInfoFragment(){

    } */

    private String mexpertIDkey = null;
    private Button btnUpdate;
    private Button btnDelete;
    private TextView origin_airport_name;
    private TextView origin_flight_no;
    private TextView origin_flight_time;
    private TextView transit_airport_name;
    private TextView transit_flight_no;
    private TextView transit_flight_time;
    private TextView arrival_airport;
    private TextView arrival_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.activity_admin_flight_info_fragment, container, false);

      origin_airport_name = view.findViewById(R.id.origin_airport_name);
      origin_flight_no = view.findViewById(R.id.origin_flight_no);
      origin_flight_time = view.findViewById(R.id.origin_flight_time);
      transit_airport_name = view.findViewById(R.id.transit_airport_name);
      transit_flight_no = view.findViewById(R.id.transit_flight_no);
      transit_flight_time = view.findViewById(R.id.transit_flight_time);
      arrival_airport = view.findViewById(R.id.arrival_airport);
      arrival_time = view.findViewById(R.id.arrival_time);
      btnUpdate = view.findViewById(R.id.btnUpdate);
      btnDelete = view.findViewById(R.id.btnDelete);

      btnUpdate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(getContext(), FlightAdminUpdate.class);
              startActivity(intent);
          }
      });

      return view;
    }



}

