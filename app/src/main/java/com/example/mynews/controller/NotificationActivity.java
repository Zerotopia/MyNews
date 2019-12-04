package com.example.mynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mynews.R;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        SearchFragment searchFragment = SearchFragment.newInstance(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,searchFragment)
                .commit();
    }
}
