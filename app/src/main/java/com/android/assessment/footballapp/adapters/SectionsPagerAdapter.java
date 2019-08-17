package com.android.assessment.footballapp.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.assessment.footballapp.R;
import com.android.assessment.footballapp.fragments.FixtureFragment;
import com.android.assessment.footballapp.fragments.TableFragment;
import com.android.assessment.footballapp.fragments.TeamsFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private int id;

    public SectionsPagerAdapter(Context context, FragmentManager fm, int id) {
        super(fm);
        mContext = context;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a TableFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return TableFragment.newInstance(id);
            case 1:
                return new FixtureFragment();
            case 2:
                return new TeamsFragment();
        }
        return TableFragment.newInstance(id);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}