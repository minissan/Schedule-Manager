package com.example.nissan.schedulemanager.admin;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.nissan.schedulemanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class FlightAdminUpdate extends AppCompatActivity {


    private String mexpertIDkey = null;
    private EditText origin_airport_name;
    private EditText origin_flight_no;
    private TextView origin_flight_time;
    private EditText transit_airport_name;
    private EditText transit_flight_no;
    private TextView transit_flight_time;
    private EditText arrival_airport;
    private TextView arrival_time;
    private DatabaseReference mDatabase;

    //TimePicker picker;
    DateFormat formatTime = DateFormat.getTimeInstance(DateFormat.SHORT);
    Calendar dateTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_admin_update);
        origin_airport_name = findViewById(R.id.origin_airport_name);
        origin_flight_no = findViewById(R.id.origin_flight_no);
        origin_flight_time = findViewById(R.id.origin_flight_time);
        transit_airport_name = findViewById(R.id.transit_airport_name);
        transit_flight_no = findViewById(R.id.transit_flight_no);
        transit_flight_time = findViewById(R.id.transit_flight_time);
        arrival_airport = findViewById(R.id.arrival_airport);
        arrival_time = findViewById(R.id.arrival_time);

        mexpertIDkey = getIntent().getExtras().getString("expertIDkey");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user");

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

        origin_flight_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTime();
            }
        });
        updateTextLabel();
    }

    private void updateTime(){
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), false).show();
    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel();
        }
    };

    private void updateTextLabel(){
        origin_flight_time.setText(formatTime.format(dateTime.getTime()));
    }
}




