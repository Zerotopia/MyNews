package com.example.mynews.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.mynews.R;

import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {
    boolean notif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        Intent intent = getIntent();
        notif= intent.getBooleanExtra("NOTIF",false);
        SearchFragment searchFragment = SearchFragment.newInstance(notif);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,searchFragment)
                .commit();
    }
}
