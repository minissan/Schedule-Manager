package com.example.nissan.schedulemanager.expert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.nissan.schedulemanager.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by nissan on 12/18/2017.
 */

public class Tab1MySchedule extends Fragment {
    private static final String TAG = "ViewDatabase";

    private TextView name;
    private TextView arrival_date;
    private TextView departure_date;
    private Button SelectButton;
    private Button DeleteButton;
    //Firebase Stuff
    //private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_my_schedule, container, false);

        //Database Access
        mAuth = FirebaseAuth.getInstance();
       // mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("nk_bsmia").child("user");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        name = view.findViewById(R.id.tvName);
        arrival_date = view.findViewById(R.id.tvArrivalDate);
        departure_date = view.findViewById(R.id.tvDepartureDate);
        SelectButton = view.findViewById(R.id.editBtn);
        DeleteButton = view.findViewById(R.id.btn_delete);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                    //Toast.makeText(getContext(),"Successfully signed in.",Toast.LENGTH_SHORT).show();
                }
            }
        };
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value &
                // again whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        SelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DateSelectActivity.class);
                startActivity(intent);
            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure you want to DELETE date?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //String arrival_date = mArrival_Date.getText().toString();
                        //String departure_date = mDeparture_Date.getText().toString();
                        myRef.child("i_expert").child(userID).child("arrival_date").setValue("NO DATE");
                        myRef.child("i_expert").child(userID).child("departure_date").setValue("NO DATE");
                        Toast.makeText(getContext(), "Date Deleted Successfully.", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(Tab1MySchedule.this,AdminDashBoardOld.class);
                        //startActivity(intent);
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

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            User user = new User();
            user.setName(ds.child(userID).getValue(User.class).getName());
            user.setArrival(ds.child(userID).getValue(User.class).getArrival());
            user.setDeparture(ds.child(userID).getValue(User.class).getDeparture());

            //display all the information
            Log.d(TAG, "showData: Name: " +user.getName());
            Log.d(TAG, "showData: Arrival Date: " +user.getArrival());
            Log.d(TAG, "showData: Departure Date: " +user.getDeparture());

            name.setText(user.getName());
            arrival_date.setText(user.getArrival());
            departure_date.setText(user.getDeparture());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

   /* private void toastMessage(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }
        */
}