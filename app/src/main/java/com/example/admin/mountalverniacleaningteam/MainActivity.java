package com.example.admin.mountalverniacleaningteam;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

//OnfragmentInteractionListener to communicate with fragments
public class MainActivity extends AppCompatActivity implements CleaningTeam.OnFragmentInteractionListener{



    ViewPager viewPager;
    PagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fire base notification by topic
        FirebaseMessaging.getInstance().subscribeToTopic("SGWard");

        //Internet Access
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        //Tab layout
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("SG Ward Cleaning Request"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //Viewpager allow swipe
        viewPager = (ViewPager)findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }


    @Override
    protected void onResume()
    {

        super.onResume();
        viewPager.setAdapter(adapter);


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
