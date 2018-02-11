package com.example.nissan.schedulemanager;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.nissan.schedulemanager.admin.fragments.AdminFlightInfoFragment;
import com.example.nissan.schedulemanager.admin.fragments.AdminHotelInfoFragment;
import com.example.nissan.schedulemanager.admin.fragments.AdminDashboardFragment;

public class AdminSingleExpertActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    BottomNavigationView navigationView;

    //This is viewPager
    private ViewPager viewPager;

    //Fragments

    AdminDashboardFragment overviewFragment;
    AdminFlightInfoFragment flightFragment;
    AdminHotelInfoFragment hotelFragment;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


    }
}
