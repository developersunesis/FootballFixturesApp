/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.android.assessment.footballapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.assessment.footballapp.adapters.MyViewPagerAdapter;
import com.android.assessment.footballapp.models.ScrollState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    //Declare view variables
    //Appbar, ViewPager, 2 bottom ImageButton and its parent LinearLayout
    ViewPager viewPager;
    ImageButton fixtures, competitions;
    LinearLayout bottomNavigation;
    TextView appbar;

    //Register the EventBus to receive the Fragments ListView scroll event and hide the appbar and nav
    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    //Unregister the EventBus to receive the Fragments ListView scroll event and hide the appbar and nav
    @Override
    protected void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    //Subscribe the event tp hide and unhide the appbar and nav onScroll
    @Subscribe
    public void onScrollEvent(ScrollState state){
        if(state.isState()){
            bottomNavigation.setVisibility(View.GONE);
            appbar.setVisibility(View.GONE);
        } else {
            bottomNavigation.setVisibility(View.VISIBLE);
            appbar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the views
        viewPager = findViewById(R.id.viewPager);
        fixtures = findViewById(R.id.fixtures);
        competitions = findViewById(R.id.competitions);
        bottomNavigation = findViewById(R.id.bottomNavigationView);
        appbar = findViewById(R.id.toolbar);

        //set ViewPager adapter
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));

        //onClickListener on ImageButtons
        fixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        competitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        //register onPageChangeListener to swap appbar title and active ImageButton
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        fixtures.setImageDrawable(getResources().getDrawable(R.drawable.soccer));
                        competitions.setImageDrawable(getResources().getDrawable(R.drawable.soccer_field_selected));
                        appbar.setText(R.string.today_s_fixtures);
                        break;
                    case 1:
                        fixtures.setImageDrawable(getResources().getDrawable(R.drawable.soccer_selected));
                        competitions.setImageDrawable(getResources().getDrawable(R.drawable.soccer_field));
                        appbar.setText(R.string.competitions);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
