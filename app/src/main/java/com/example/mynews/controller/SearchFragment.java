package com.example.mynews.controller;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mynews.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String BOOLEAN = "activity";

    private EditText mBeginDate;
    private EditText mEndDate;
    private Boolean mBegin;
    private Boolean mNotification;

    public static SearchFragment newInstance(Boolean activity) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putBoolean(BOOLEAN, activity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotification = (getArguments() == null) || getArguments().getBoolean(BOOLEAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate_search_xml = inflater.inflate(R.layout.fragment_search, container, false);
        LinearLayout dateLayout = inflate_search_xml.findViewById(R.id.fragment_search_dates);
        Button button = inflate_search_xml.findViewById(R.id.fragment_search_button);
        LinearLayout notification_layout = inflate_search_xml.findViewById(R.id.fragment_search_notification_layout);

        if (mNotification) {
            dateLayout.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        } else notification_layout.setVisibility(View.GONE);

        return inflate_search_xml;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!mNotification) {
            mBeginDate = view.findViewById(R.id.fragment_search_begin_date);
            mEndDate = view.findViewById(R.id.fragment_search_end_date);

            mBeginDate.setOnClickListener(v -> {
                mBegin = true;
                displayPickerDialog();
            });

            mEndDate.setOnClickListener(v -> {
                mBegin = false;
                displayPickerDialog();
            });
        }
    }

    private void displayPickerDialog() {
        Context context = getActivity();
        if (context != null) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    context,
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (!mNotification) {
            String date = getString(R.string.date_format,  dayOfMonth,(month + 1),year);
            if (mBegin) mBeginDate.setText(date);
            else mEndDate.setText(date);
        }
    }
}
