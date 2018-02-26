package com.example.nissan.schedulemanager.admin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nissan.schedulemanager.R;
import com.example.nissan.schedulemanager.expert.BaseActivity;
import com.example.nissan.schedulemanager.expert.ExpertLoginActivity;
import com.example.nissan.schedulemanager.models.ExpertList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminExpertList extends BaseActivity {
    private static final String TAG = "ViewDatabase";

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private RecyclerView mExpertList;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_expert_list);
        mAuth = FirebaseAuth.getInstance();

        mExpertList = (RecyclerView)findViewById(R.id.expertlist);
        mExpertList.setHasFixedSize(true);
        mExpertList.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //toolbar not scrollable
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_menu_dash_board, menu);

        setIconInMenu(menu // The Menu Object
                , R.id.action_settings // The Id of the Menu
                , R.string.action_settings //The String to be displayed
                , R.mipmap.ic_exit_to_app_white_24dp);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.moveTaskToBack(true);

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminExpertList.this, ExpertLoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void setIconInMenu(Menu menu, int menuItemId, int labelId, int iconId) {
        MenuItem item = menu.findItem(menuItemId);
        SpannableStringBuilder builder =
                new SpannableStringBuilder(" " + getResources().getString(labelId));
        builder.setSpan(new ImageSpan(this, iconId), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        item.setTitle(builder);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
            FirebaseRecyclerAdapter<ExpertList, ExpertViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ExpertList, ExpertViewHolder>(

                    ExpertList.class,
                    R.layout.expert_row_admin,
                    AdminExpertList.ExpertViewHolder.class,
                    mDatabase
            ) {
                @Override
                protected void populateViewHolder(ExpertViewHolder viewHolder, ExpertList model, int position) {

                    final String expertIDkey = getRef(position).getKey();

                    viewHolder.setName(model.getName());
                    viewHolder.setImage(getApplicationContext(),model.getImage());
                    //viewHolder.setArrivalDate(model.getArrival());
                    //viewHolder.setDepartureDate(model.getDeparture());

                    //Click on profile
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(AdminExpertList.this, expertIDkey, Toast.LENGTH_LONG).show();
                            Intent singleExpertIntent = new Intent(AdminExpertList.this, AdminDashBoard.class);
                            singleExpertIntent.putExtra("expertIDkey", expertIDkey);
                            startActivity(singleExpertIntent);
                        }
                    });
                }
            };

            mExpertList.setAdapter(firebaseRecyclerAdapter);

    }
    public static class ExpertViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public ExpertViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String name){

            TextView lname = mView.findViewById(R.id.TvName);
            lname.setText(name);
        }

   /*     public void setArrivalDate(String arrival_date){

            TextView larrival_date = (TextView)itemView.findViewById(R.id.l_arrival_date);
            larrival_date.setText(arrival_date);
        }

        public void setDepartureDate(String departureDate){

            TextView ldeparture_date = (TextView)itemView.findViewById(R.id.l_departure_date);
            ldeparture_date.setText(departureDate);
        } */
        public void setImage(Context ctx, String image){
            ImageView profile_image = mView.findViewById(R.id.profile_img);
            Picasso.with(ctx).load(image).into(profile_image);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }


}


