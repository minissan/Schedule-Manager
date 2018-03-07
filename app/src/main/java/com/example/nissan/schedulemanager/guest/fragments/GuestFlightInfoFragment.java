package com.example.nissan.schedulemanager.guest.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nissan.schedulemanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class GuestFlightInfoFragment extends Fragment {

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
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_guest_flight_info_fragment, container, false);

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

        mexpertIDkey = getActivity().getIntent().getExtras().getString("expertIDkey");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user");


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FlightGuestUpdate.class);
                intent.putExtra("expertIDkey", mexpertIDkey);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure you want to DELETE data?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDatabase.child(mexpertIDkey).child("origin_airport").setValue("Not Available");
                        mDatabase.child(mexpertIDkey).child("origin_flight_no").setValue("Not Available");
                        mDatabase.child(mexpertIDkey).child("origin_flight_time").setValue("Not Available");
                        mDatabase.child(mexpertIDkey).child("transit_airport").setValue("Not Available");
                        mDatabase.child(mexpertIDkey).child("transit_flight_no").setValue("Not Available");
                        mDatabase.child(mexpertIDkey).child("transit_flight_time").setValue("Not Available");
                        mDatabase.child(mexpertIDkey).child("arrival_airport").setValue("Not Available");
                        mDatabase.child(mexpertIDkey).child("arrival_time").setValue("Not Available");
                        Toast.makeText(getContext(), "Data Deleted Successfully.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

            }
        });


        mDatabase.child(mexpertIDkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Sorigin_airport_name = (String) dataSnapshot.child("origin_airport").getValue();
                String Sorigin_flight_no = (String) dataSnapshot.child("origin_flight_no").getValue();
                String Sorigin_flight_time = (String) dataSnapshot.child("origin_flight_time").getValue();
                String Stransit_airport_name = (String) dataSnapshot.child("transit_airport").getValue();
                String Stransit_flight_no = (String) dataSnapshot.child("transit_flight_no").getValue();
                String Stransit_flight_time = (String) dataSnapshot.child("transit_flight_time").getValue();
                String Sarrival_airport = (String) dataSnapshot.child("arrival_airport").getValue();
                String Sarrival_time = (String) dataSnapshot.child("arrival_time").getValue();

                origin_airport_name.setText(Sorigin_airport_name);
                origin_flight_no.setText(Sorigin_flight_no);
                origin_flight_time.setText(Sorigin_flight_time);
                transit_airport_name.setText(Stransit_airport_name);
                transit_flight_no.setText(Stransit_flight_no);
                transit_flight_time.setText(Stransit_flight_time);
                arrival_airport.setText(Sarrival_airport);
                arrival_time.setText(Sarrival_time);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return view;
    }



}

