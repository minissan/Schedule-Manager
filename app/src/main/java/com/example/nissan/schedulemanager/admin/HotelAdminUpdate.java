package com.example.nissan.schedulemanager.admin;
//Created by Nissan
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class HotelAdminUpdate extends AppCompatActivity {
    Context context = this;

    private String mexpertIDkey = null;
    private DatabaseReference mDatabase;
    private EditText hotel_name;
    private TextView check_in;
    private TextView check_out;
    private Button btnConfirm;

    //Calendar
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd.MMM.yyyy";
    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
    String dayDate1String, monthDate1String, yearDate1String;
    String dayDate2String, monthDate2String, yearDate2String;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_admin_update);
        hotel_name = findViewById(R.id.hotel_name);
        check_in = findViewById(R.id.check_in);
        check_out = findViewById(R.id.check_out);
        btnConfirm = findViewById(R.id.btnConfirm);

        mexpertIDkey = getIntent().getExtras().getString("expertIDkey");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user");

        mDatabase.child(mexpertIDkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Shotel_name = (String) dataSnapshot.child("hotel_name").getValue();
                String Scheck_in = (String) dataSnapshot.child("check_in").getValue();
                String Scheck_out = (String) dataSnapshot.child("check_out").getValue();

                hotel_name.setText(Shotel_name);
                check_in.setText(Scheck_in);
                check_out.setText(Scheck_out);
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

        check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        check_out.setOnClickListener(new View.OnClickListener() {
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
                String fhotel_name = hotel_name.getText().toString();
                String fcheck_in = check_in.getText().toString();
                String fcheck_out = check_out.getText().toString();

                mDatabase.child(mexpertIDkey).child("hotel_name").setValue(fhotel_name);
                mDatabase.child(mexpertIDkey).child("check_in").setValue(fcheck_in);
                mDatabase.child(mexpertIDkey).child("check_out").setValue(fcheck_out);

                AlertBox();
            }
        });


    }
    private void updateDate1() {
        check_in.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateDate2() {
        check_out.setText(sdf.format(myCalendar.getTime()));
    }

    // Alert Box
    public void AlertBox(){
        //Alert Box
        AlertDialog.Builder builder = new AlertDialog.Builder(HotelAdminUpdate.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to update?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Updated successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HotelAdminUpdate.this, AdminDashBoard.class);
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

