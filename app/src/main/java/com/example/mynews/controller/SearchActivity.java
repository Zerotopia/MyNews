package com.example.mynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.mynews.R;

import java.util.Calendar;

public class SearchActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText mBeginDate;
    private EditText mEndDate;
    private Boolean date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        mBeginDate = findViewById(R.id.start_date);
        mEndDate = findViewById(R.id.end_date);


        mBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = true;
                displayPictureDialog();
            }
        });

        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = false;
                displayPictureDialog();
            }
        });
    }

    private void displayPictureDialog() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (date) mBeginDate.setText(dayOfMonth + "/" + month + "/" + year);
        else mEndDate.setText(dayOfMonth + "/" + month + "/" + year);
    }
}
