package com.example.nissan.schedulemanager.expert;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nissan.schedulemanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;

public class DateSelectActivity extends AppCompatActivity {
    private static final String TAG = "AddToDatabase";
    Context context = this;
    TextView aEditDate;
    TextView dEditDate;

    //Calendar
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd.MMM.yyyy";
    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
    Button DoneBtn;

    String dayDate1String, monthDate1String, yearDate1String;
    String dayDate2String, monthDate2String, yearDate2String;
    String tag;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select);

        aEditDate = (TextView) findViewById(R.id.as_date_editText);
        dEditDate = (TextView) findViewById(R.id.ds_date_editText);
        DoneBtn = (Button) findViewById(R.id.DoneBtn);

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be usable.

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if(user1!=null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged: signed_in: "+user1.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out ");
                }
            }
        };

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

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

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



        //onclick - popup datepicker
        aEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //onclick - popup datepicker
        dEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {

                //Date Comparison
                try {
                    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                    Date date3 = sdf3.parse(yearDate1String + "-" + monthDate1String + "-" + dayDate1String);
                    Date date4 = sdf3.parse(yearDate2String + "-" + monthDate2String + "-" + dayDate2String);

                    if(date3.equals(date4)){
                      //  Log.i(tag, "Arrival & departure date are same!");
                       // tag2 = "Arrival & departure date are same!";
                        Toast.makeText(DateSelectActivity.this, "Arrival & departure date are same!",Toast.LENGTH_LONG).show();
                    }

                    else if(date3.after(date4)){
                        Toast.makeText(DateSelectActivity.this, "Please select different departure date!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Log.d(TAG, "onClick: Attemting to add object to database.");
                        FirebaseUser user2 = mAuth.getCurrentUser();
                        String userID = user2.getUid();
                        String arrival_date = aEditDate.getText().toString();
                        String departure_date = dEditDate.getText().toString();

                        myRef.child("nk_bsmia").child("user").child("i_expert").child(userID).child("arrival_date").setValue(arrival_date);
                        myRef.child("nk_bsmia").child("user").child("i_expert").child(userID).child("departure_date").setValue(departure_date);
                        Toast.makeText(getApplicationContext(),"Date Updated Successfully.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DateSelectActivity.this,ExpertDashBoard.class);
                        startActivity(intent);
                    }
                }
                catch (ParseException ex) {
                    Log.i(tag, "parse exception");
                    Toast.makeText(DateSelectActivity.this,"Please Select Date!",Toast.LENGTH_LONG).show();
                }

                //Firebase data transfer
               // Firebase mRefChild = mRef.child("Name");

            }
        });


    }
    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
        private void updateDate1() {
           aEditDate.setText(sdf.format(myCalendar.getTime()));
         }

        private void updateDate2() {
            dEditDate.setText(sdf.format(myCalendar.getTime()));
    }



}

