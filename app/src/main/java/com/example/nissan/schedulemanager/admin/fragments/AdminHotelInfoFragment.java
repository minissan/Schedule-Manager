package com.example.nissan.schedulemanager.admin.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nissan.schedulemanager.R;

public class AdminHotelInfoFragment extends Fragment {

    public AdminHotelInfoFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_admin_hotel_info_fragment, container, false);
    }


}