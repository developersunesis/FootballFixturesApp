package com.android.assessment.footballapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.assessment.footballapp.fragments.CompetitionsFragment;
import com.android.assessment.footballapp.fragments.FixturesFragment;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    public MyViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FixturesFragment();
            case 1:
                return new CompetitionsFragment();
        }

        return new FixturesFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
