package com.example.mynews.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mynews.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.main_activity_viewpager);
        mTabLayout = findViewById(R.id.main_activity_tablayout);

        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),getResources()));

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
