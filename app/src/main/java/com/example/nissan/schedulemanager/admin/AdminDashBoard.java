package com.example.nissan.schedulemanager.admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.nissan.schedulemanager.R;
import com.example.nissan.schedulemanager.admin.fragments.AdminFlightInfoFragment;
import com.example.nissan.schedulemanager.admin.fragments.AdminHotelInfoFragment;
import com.example.nissan.schedulemanager.admin.fragments.ViewPagerAdapter;
import com.example.nissan.schedulemanager.admin.fragments.AdminDashboardFragment;

/**
 * Created by nissan on 2/4/2018.
 */

public class AdminDashBoard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    //viewPager
    private ViewPager viewPager;

    //Fragments

    AdminDashboardFragment dashboardFragment;
    AdminFlightInfoFragment flightFragment;
    AdminHotelInfoFragment hotelFragment;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        //Initializing viewPager
        viewPager = findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_dashboard:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_flight:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_hotel:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(prevMenuItem !=null){
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        dashboardFragment=new AdminDashboardFragment();
        flightFragment=new AdminFlightInfoFragment();
        hotelFragment=new AdminHotelInfoFragment();
        adapter.addFragment(dashboardFragment);
        adapter.addFragment(flightFragment);
        adapter.addFragment(hotelFragment);
        viewPager.setAdapter(adapter);
    }
}
