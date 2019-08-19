/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.android.assessment.footballapp;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import com.android.assessment.footballapp.adapters.SectionsPagerAdapter;

import static com.android.assessment.footballapp.workers.Constants.COMPETITIONS;
import static com.android.assessment.footballapp.workers.Constants.TITLE;

public class Competition extends AppCompatActivity {

    //This variable is to assign the competition id
    //before loading API calls and populating the fragment views
    private int competition_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        TextView title = findViewById(R.id.title);

        if(getIntent() != null){
            title.setText(getIntent().getStringExtra(TITLE));
            competition_id = getIntent().getIntExtra(COMPETITIONS, -1);
        }

        //Initialize and assign ViewPager for tabs
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), competition_id);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
}