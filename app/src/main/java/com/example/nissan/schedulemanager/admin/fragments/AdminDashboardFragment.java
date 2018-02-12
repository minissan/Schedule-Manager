package com.example.nissan.schedulemanager.admin.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nissan.schedulemanager.R;
import com.example.nissan.schedulemanager.admin.DashboardAdminUpdate;
import com.example.nissan.schedulemanager.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;


public class AdminDashboardFragment extends Fragment {

  /*  public AdminDashboardFragment(){

    } */
    private String mexpertIDkey = null;
    private TextView arrival;
    private TextView departure;
    private TextView name;
    private TextView curr_loc;
    private TextView estm_stay;
    private Button btnUpdate;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_admin_dashboard_fragment, container, false);

        btnUpdate = view.findViewById(R.id.btnUpdateDash);
        name = view.findViewById(R.id.ExpertName);
        arrival = view.findViewById(R.id.ArrivalDate);
        departure = view.findViewById(R.id.DepartureDate);
        curr_loc = view.findViewById(R.id.CurrentLocation);
        estm_stay = view.findViewById(R.id.EstimateStay);

        mexpertIDkey = getActivity().getIntent().getExtras().getString("expertIDkey");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user");
        //name.setText(mexpertIDkey);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DashboardAdminUpdate.class);
                startActivity(intent);
            }
        });

        mDatabase.child(mexpertIDkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               String Sname = (String) dataSnapshot.child("name").getValue();
               String Sarrival = (String) dataSnapshot.child("arrival").getValue();
               String Sdeparture = (String) dataSnapshot.child("departure").getValue();
               String Scurr_loc = (String) dataSnapshot.child("curr_loc").getValue();
               String Sestm_stay = (String) dataSnapshot.child("estm_stay").getValue();

                name.setText(Sname);
                arrival.setText(Sarrival);
                departure.setText(Sdeparture);
                curr_loc.setText(Scurr_loc);
                estm_stay.setText(Sestm_stay);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    return view;
    }
}
