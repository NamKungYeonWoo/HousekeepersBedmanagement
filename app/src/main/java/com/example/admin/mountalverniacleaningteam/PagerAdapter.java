package com.example.admin.mountalverniacleaningteam;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by admin on 10/28/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int NoOfTabs;

    public PagerAdapter(FragmentManager fm, int NoOfTabs){
        super(fm);
        this.NoOfTabs = NoOfTabs;

    }
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            //Only 1 tab for now
            //Future enchance for different tab, eg. view by ready to clean,
            // cleaning in process, cleaned rooms
            case 0 :
                CleaningTeam tab1 = new CleaningTeam();
                return tab1;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return NoOfTabs;
    }
}
