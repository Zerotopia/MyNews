package com.example.mynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mynews.R;

public class SearchActivity extends AppCompatActivity {

    boolean mNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        mNotification = intent.getBooleanExtra("ACTIVITY",false);
        SearchFragment searchFragment = SearchFragment.newInstance(mNotification);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,searchFragment)
                .commit();
    }
}
