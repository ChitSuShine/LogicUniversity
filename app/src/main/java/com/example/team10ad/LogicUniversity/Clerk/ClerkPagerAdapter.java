package com.example.team10ad.LogicUniversity.Clerk;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.team10ad.LogicUniversity.Clerk.TrendAnalysis;
import com.example.team10ad.LogicUniversity.Clerk.ItemUsageAnalysis;

public class ClerkPagerAdapter extends FragmentPagerAdapter {

    int numOfTabs;

    public ClerkPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new TrendAnalysis();
            case 1:
                return new ItemUsageAnalysis();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
