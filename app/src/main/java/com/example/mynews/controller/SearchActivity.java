package com.example.mynews.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.mynews.R;

import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        SearchFragment searchFragment = SearchFragment.newInstance(false);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,searchFragment)
                .commit();
    }
}
