package com.example.nissan.schedulemanager.admin;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.nissan.schedulemanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdminSingleExpert extends AppCompatActivity {
    Context context = this;
    private static final String TAG = "AddToDatabase";
    private String mexpertIDkey = null;
    private TextView mName;
    private TextView mArrival_Date;
    private TextView mDeparture_Date;

    //Calendar
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd.MMM.yyyy";
    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
    Button BtnDelete;
    Button BtnUpdate;

    String dayDate1String, monthDate1String, yearDate1String;
    String dayDate2String, monthDate2String, yearDate2String;
    String tag;
    String name;
    String arrival_date;
    String departure_date;

    //add Firebase Database stuff
    private DatabaseReference mDatabase;
    //  private FirebaseDatabase mFirebaseDatabase;
    //  private FirebaseAuth mAuth;
    //  private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_single_expert);
        mexpertIDkey = getIntent().getExtras().getString("expertIDkey");

        mName = findViewById(R.id.TvName);
        mArrival_Date = findViewById(R.id.l_arrival_date);
        mDeparture_Date = findViewById(R.id.l_departure_date);
       // BtnUpdate = findViewById(R.id.ButtonAdminUpdate);
       // BtnDelete = findViewById(R.id.ButtonAdminDelete);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("nk_bsmia").child("user").child("i_expert");
        //Toast.makeText(AdminSingleExpert.this, mexpertIDkey,Toast.LENGTH_LONG).show();

        //TextView Text
        mArrival_Date.setText("SELECT DATE");
        mDeparture_Date.setText("SELECT DATE");
        mDatabase.child(mexpertIDkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    name = (String) dataSnapshot.child("name").getValue();
                    arrival_date = (String) dataSnapshot.child("arrival_date").getValue();
                    departure_date = (String) dataSnapshot.child("departure_date").getValue();

                  mName.setText(name);
                  mArrival_Date.setText(arrival_date);
                  mDeparture_Date.setText(departure_date);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //set calendar date and update editDate
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

        //set calendar date and update editDate
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

        //onclick - popup datepicker
        mArrival_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //onclick - popup datepicker
        mDeparture_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Date Comparison
                try {

                        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                        Date date3 = sdf3.parse(yearDate1String + "-" + monthDate1String + "-" + dayDate1String);
                        Date date4 = sdf3.parse(yearDate2String + "-" + monthDate2String + "-" + dayDate2String);

                        if (date3.equals(date4)) {
                            // Log.i(tag, "Arrival & departure date are same!");
                            // tag2 = "Arrival & departure date are same!";
                            Toast.makeText(AdminSingleExpert.this, "Arrival & departure date are same!", Toast.LENGTH_LONG).show();
                        } else if (date3.after(date4)) {
                            Toast.makeText(AdminSingleExpert.this, "Please select different departure date!", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d(TAG, "onClick: Attemting to add object to database.");
                            //FirebaseUser user2 = mAuth.getCurrentUser();
                            //String userID = user2.getUid();
                            String arrival_date = mArrival_Date.getText().toString();
                            String departure_date = mDeparture_Date.getText().toString();
                            mDatabase.child(mexpertIDkey).child("arrival_date").setValue(arrival_date);
                            mDatabase.child(mexpertIDkey).child("departure_date").setValue(departure_date);
                            Toast.makeText(getApplicationContext(), "Date Updated Successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminSingleExpert.this,AdminExpertList.class);
                            startActivity(intent);
                        }

                }
                catch (ParseException ex) {
                    Log.i(tag, "parse exception");
                   // Toast.makeText(AdminSingleExpert.this,"Please select Arrival/Departure date again!",Toast.LENGTH_LONG).show();
                    //Popup message

                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminSingleExpert.this, R.style.AlertDialogStyle);
                    builder.setTitle("Confirm Arrival/Departure");
                    builder.setMessage("Please confirm arrival/departure date again!");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }

            }
        });

        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminSingleExpert.this, R.style.AlertDialogStyle);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure to DELETE date?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //String arrival_date = mArrival_Date.getText().toString();
                        //String departure_date = mDeparture_Date.getText().toString();
                        mDatabase.child(mexpertIDkey).child("arrival_date").setValue("NO DATE");
                        mDatabase.child(mexpertIDkey).child("departure_date").setValue("NO DATE");
                        Toast.makeText(getApplicationContext(), "Date Deleted Successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminSingleExpert.this,AdminExpertList.class);
                        startActivity(intent);
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


    }
    @Override
    public void onStart(){
        super.onStart();
       // mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
      /*  if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        } */
    }
    private void updateDate1() {
        mArrival_Date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateDate2() {
        mDeparture_Date.setText(sdf.format(myCalendar.getTime()));
    }
}
