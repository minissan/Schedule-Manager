package com.example.nissan.schedulemanager.admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nissan.schedulemanager.R;
import com.example.nissan.schedulemanager.admin.fragments.AdminDashboardFragment;
import com.example.nissan.schedulemanager.expert.ExpertLoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class DashboardAdminUpdate extends AppCompatActivity {

    Context context = this;

    private String mexpertIDkey = null;

    //Calendar
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd.MMM.yyyy";
    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
    String dayDate1String, monthDate1String, yearDate1String;
    String dayDate2String, monthDate2String, yearDate2String;

    private EditText name;
    private EditText curr_loc;
    private EditText estm_stay;
    private TextView arrival;
    private TextView departure;
    private Button btnConfirm;

    //add Firebase Database stuff
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin_update);

        name = findViewById(R.id.name);
        curr_loc = findViewById(R.id.curr_loc);
        estm_stay = findViewById(R.id.estm_stay);
        arrival = findViewById(R.id.arrival);
        departure = findViewById(R.id.departure);
        btnConfirm = findViewById(R.id.btnConfirm);

        mexpertIDkey = getIntent().getExtras().getString("expertIDkey");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user");

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

        // set calendar date and update editDate
        date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //Converts into string
                dayDate1String = String.valueOf(dayOfMonth);
                monthDate1String = String.valueOf(monthOfYear);
                yearDate1String = String.valueOf(year);

                updateDate1();
            }
        };

        // set calendar date and update editDate
        date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view2, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //Converts into string
                dayDate2String = String.valueOf(dayOfMonth);
                monthDate2String = String.valueOf(monthOfYear);
                yearDate2String = String.valueOf(year);

                updateDate2();
            }
        };

        arrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = name.getText().toString();
                String farrival = arrival.getText().toString();
                String fdeparture = departure.getText().toString();
                String fcurr_loc = curr_loc.getText().toString();
                String festm_stay = estm_stay.getText().toString();

                mDatabase.child(mexpertIDkey).child("name").setValue(fname);
                mDatabase.child(mexpertIDkey).child("arrival").setValue(farrival);
                mDatabase.child(mexpertIDkey).child("departure").setValue(fdeparture);
                mDatabase.child(mexpertIDkey).child("curr_loc").setValue(fcurr_loc);
                mDatabase.child(mexpertIDkey).child("estm_stay").setValue(festm_stay);

                AlertBox();
            }
        });


        }
    private void updateDate1() {
        arrival.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateDate2() {
        departure.setText(sdf.format(myCalendar.getTime()));
    }

    // Alert Box
    public void AlertBox(){
        //Alert Box
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardAdminUpdate.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to update?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Updated successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashboardAdminUpdate.this, AdminDashBoard.class);
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
