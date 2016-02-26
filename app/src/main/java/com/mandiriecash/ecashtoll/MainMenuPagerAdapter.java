package com.mandiriecash.ecashtoll;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainMenuPagerAdapter extends FragmentPagerAdapter {
    public MainMenuPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public static final int PLAN = 0;
    public static final int HISTORY = 1;

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == PLAN){
            fragment = new PlanFragment();
        } else {
            fragment = new HistoryFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "woi" + position;
    }

}
