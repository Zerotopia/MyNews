package com.example.mynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mynews.R;

public class SearchResultActivity extends AppCompatActivity {

    String[] mArguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        mArguments = intent.getStringArrayExtra("ARGS");
        ApiFragment searchResultFragment = ApiFragment.newInstance(9, mArguments);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,searchResultFragment)
                .commit();
    }
}
