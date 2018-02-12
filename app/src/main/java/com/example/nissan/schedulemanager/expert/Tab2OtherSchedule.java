package com.example.nissan.schedulemanager.expert;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nissan.schedulemanager.R;
import com.example.nissan.schedulemanager.models.ExpertList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by nissan on 12/18/2017.
 */

public class Tab2OtherSchedule extends Fragment {

    private RecyclerView mExpertList;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("nk_bsmia").child("user").child("i_expert");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_other_schedule, container, false);

        mExpertList = (RecyclerView) rootView.findViewById(R.id.expertlist);
        mExpertList.setHasFixedSize(true);
        mExpertList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ExpertList, ExpertViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ExpertList, ExpertViewHolder>(

                ExpertList.class,
                R.layout.expert_row,
                ExpertViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ExpertViewHolder viewHolder, ExpertList model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setArrivalDate(model.getArrival());
                viewHolder.setDepartureDate(model.getDeparture());

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

            TextView lname = (TextView)itemView.findViewById(R.id.TvName);
            lname.setText(name);
        }

        public void setArrivalDate(String arrival_date){

            TextView larrival_date = (TextView)itemView.findViewById(R.id.l_arrival_date);
            larrival_date.setText(arrival_date);
        }

        public void setDepartureDate(String departureDate){

            TextView ldeparture_date = (TextView)itemView.findViewById(R.id.l_departure_date);
            ldeparture_date.setText(departureDate);
        }
    }
}
