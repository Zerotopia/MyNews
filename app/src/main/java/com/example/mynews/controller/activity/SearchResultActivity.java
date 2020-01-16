package com.example.mynews.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.ApiFragment;

import static com.example.mynews.controller.fragment.SearchFragment.ARGUMENTS;

public class SearchResultActivity extends AppCompatActivity {

    String[] mArguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        mArguments = intent.getStringArrayExtra(ARGUMENTS);
        ApiFragment searchResultFragment = ApiFragment.newInstance(9, mArguments);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,searchResultFragment)
                .commit();
    }
}
