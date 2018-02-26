package com.example.nissan.schedulemanager.admin.fragments;

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
import com.example.nissan.schedulemanager.admin.FlightAdminUpdate;
import com.example.nissan.schedulemanager.admin.HotelAdminUpdate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class AdminHotelInfoFragment extends Fragment {

    /*public AdminHotelInfoFragment(){
    }*/
    private String mexpertIDkey = null;
    private TextView hotel_name;
    private TextView hotel_check_in;
    private TextView hotel_check_out;
    private Button btnDelete;
    private Button btnUpdate;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_admin_hotel_info_fragment, container, false);
        hotel_name = view.findViewById(R.id.hotel_name);
        hotel_check_in = view.findViewById(R.id.hotel_check_in);
        hotel_check_out = view.findViewById(R.id.hotel_check_out);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnUpdate = view.findViewById(R.id.btnUpdate);

        mexpertIDkey = getActivity().getIntent().getExtras().getString("expertIDkey");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getContext(), HoteAdminUpdate.class);
                //startActivity(intent);
            }
        });
        mDatabase.child(mexpertIDkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Shotel_name = (String) dataSnapshot.child("hotel_name").getValue();
                String Shotel_check_in = (String) dataSnapshot.child("check_in").getValue();
                String Shotel_check_out = (String) dataSnapshot.child("check_out").getValue();

                hotel_name.setText(Shotel_name);
                hotel_check_in.setText(Shotel_check_in);
                hotel_check_out.setText(Shotel_check_out);
                
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HotelAdminUpdate.class);
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
                        mDatabase.child(mexpertIDkey).child("hotel_name").setValue("Not Available");
                        mDatabase.child(mexpertIDkey).child("check_in").setValue("Not Available");
                        mDatabase.child(mexpertIDkey).child("check_out").setValue("Not Available");
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

        return view;
    }


}
