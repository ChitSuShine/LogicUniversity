package com.example.team10ad.LogicUniversity.DepartmentHead;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.team10ad.LogicUniversity.Report1Fragment;
import com.example.team10ad.LogicUniversity.Report2Fragment;
import com.example.team10ad.LogicUniversity.Report3Fragment;

public class HodPagerAdapter extends FragmentPagerAdapter {

    int numOfTabs;

    public HodPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new Report1Fragment();
            case 1:
                return new Report2Fragment();
            case 2:
                return new Report3Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
