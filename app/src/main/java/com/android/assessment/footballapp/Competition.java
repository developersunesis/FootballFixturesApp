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

    private TextView title;
    private int competition_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        title = findViewById(R.id.title);

        if(getIntent() != null){
            title.setText(getIntent().getStringExtra(TITLE));
            competition_id = getIntent().getIntExtra(COMPETITIONS, -1);
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), competition_id);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
}