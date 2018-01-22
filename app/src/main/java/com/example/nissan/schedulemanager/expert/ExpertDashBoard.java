package com.example.nissan.schedulemanager.expert;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;


import com.example.nissan.schedulemanager.R;
import com.google.firebase.auth.FirebaseAuth;

public class ExpertDashBoard extends BaseActivity {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expert_activity_dash_board);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //toolbar not scrollable
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        //tab color
        tabLayout.setTabTextColors(
                ContextCompat.getColor(getApplication(), R.color.tab_selected_text_color),
                ContextCompat.getColor(getApplicationContext(), R.color.tab_unselected_text_color)
        );

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //Floating Bar
      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.expert_menu_dash_board, menu);

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
            startActivity(new Intent(ExpertDashBoard.this, ExpertLoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    //deleted PlaceholderFragment class from here
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    Tab1MySchedule tab1 = new Tab1MySchedule();
                    return tab1;
                case 1:
                    Tab2OtherSchedule tab2 = new Tab2OtherSchedule();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "My Schedule";
                case 1:
                    return "Other's Schedule";
            }
            return null;
        }
    }

    private void setIconInMenu(Menu menu, int menuItemId, int labelId, int iconId) {
        MenuItem item = menu.findItem(menuItemId);
        SpannableStringBuilder builder =
                new SpannableStringBuilder(" " + getResources().getString(labelId));
        builder.setSpan(new ImageSpan(this, iconId), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        item.setTitle(builder);
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
