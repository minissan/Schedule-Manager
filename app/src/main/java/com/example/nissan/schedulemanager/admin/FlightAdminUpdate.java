package com.example.nissan.schedulemanager.admin;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    private Button btnConfirm;
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
        btnConfirm = findViewById(R.id.btnConfirm);

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
                updateOriginFlightTime();
            }
        });

        transit_flight_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTransitFlightTime();
            }
        });

        arrival_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateArrivalFlightTime();
            }
        });
        //updateTextLabel();
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String forigin_airport_name = origin_airport_name.getText().toString();
                String forigin_flight_no = origin_flight_no.getText().toString();
                String forigin_flight_time = origin_flight_time.getText().toString();
                String ftransit_airport_name = transit_airport_name.getText().toString();
                String ftransit_flight_no = transit_flight_no.getText().toString();
                String ftransit_flight_time = transit_flight_time.getText().toString();
                String farrival_airport = arrival_airport.getText().toString();
                String farrival_time = arrival_time.getText().toString();

                mDatabase.child(mexpertIDkey).child("origin_airport").setValue(forigin_airport_name);
                mDatabase.child(mexpertIDkey).child("origin_flight_no").setValue(forigin_flight_no);
                mDatabase.child(mexpertIDkey).child("origin_flight_time").setValue(forigin_flight_time);
                mDatabase.child(mexpertIDkey).child("transit_airport").setValue(ftransit_airport_name);
                mDatabase.child(mexpertIDkey).child("transit_flight_no").setValue(ftransit_flight_no);
                mDatabase.child(mexpertIDkey).child("transit_flight_time").setValue(ftransit_flight_time);
                mDatabase.child(mexpertIDkey).child("arrival_airport").setValue(farrival_airport);
                mDatabase.child(mexpertIDkey).child("arrival_time").setValue(farrival_time);

                AlertBox();
            }
        });

    }
    //Origin Flight Time Starts
    private void updateOriginFlightTime(){
        new TimePickerDialog(this, originFlightTimePicker, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), false).show();
    }
    TimePickerDialog.OnTimeSetListener originFlightTimePicker = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateOriginFlightTimeLabel();
        }
    };
    private void updateOriginFlightTimeLabel(){
        origin_flight_time.setText(formatTime.format(dateTime.getTime())+" (JPT)");

    }
    //Origin Flight Time Finish

    //Transit Flight Time Starts
    private void updateTransitFlightTime(){
        new TimePickerDialog(this, transitFlightTimePicker, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), false).show();
    }
    TimePickerDialog.OnTimeSetListener transitFlightTimePicker = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTransitFlightTimeLabel();
        }
    };
    private void updateTransitFlightTimeLabel(){
        transit_flight_time.setText(formatTime.format(dateTime.getTime())+" (JPT)");

    }
    //Transit Flight Time Finish

    //Arrival Flight Time Starts
    private void updateArrivalFlightTime(){
        new TimePickerDialog(this, arrivalFlightTimePicker, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), false).show();
    }
    TimePickerDialog.OnTimeSetListener arrivalFlightTimePicker = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateArrivalFlightTimeLabel();
        }
    };
    private void updateArrivalFlightTimeLabel(){
        arrival_time.setText(formatTime.format(dateTime.getTime())+" (JPT)");

    }
    //Arrival Flight Time Finish

    //Alert Box
    public void AlertBox(){
        //Alert Box
        AlertDialog.Builder builder = new AlertDialog.Builder(FlightAdminUpdate.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to update?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(FlightAdminUpdate.this, "Updated successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FlightAdminUpdate.this, AdminDashBoard.class);
                intent.putExtra("expertIDkey", mexpertIDkey);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}




