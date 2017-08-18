package com.teemukurki.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by teemu on 18.8.2017.
 */

public class PagerTabsAdapter extends FragmentStatePagerAdapter {

    public PagerTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return com.teemukurki.myapplication.fragments.ListFragment.newInstance("curiosity");
        /*if(position == 0){
            //Ensimmäinen tabi
            return com.teemukurki.myapplication.fragments.ListFragment.newInstance("curiosity");

        }else if(position == 1){
            //Toinen tabi
            return com.teemukurki.myapplication.fragments.ListFragment.newInstance("opportunity");

        }else {
            //Kolmas tabi
            return com.teemukurki.myapplication.fragments.ListFragment.newInstance("spirit");
        }*/
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "curiosity";
            case 1:
                return "opportunity";
            default:
                return "spirit";
        }
    }

}
