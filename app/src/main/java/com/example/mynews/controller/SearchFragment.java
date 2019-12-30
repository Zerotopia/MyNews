package com.example.mynews.controller;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mynews.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String BOOLEAN = "activity";

    private EditText mBeginDate;
    private EditText mEndDate;
    private EditText mSearchText;
    private Boolean mBegin;
    private Boolean mNotification;

    private Button mSearchButton;

    private CheckBox[] mTopics;
    private boolean mChekbox;
    private boolean mEditText;

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
        mChekbox = false;
        mEditText = false;
        Log.d("TAG", "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate_search_xml = inflater.inflate(R.layout.fragment_search, container, false);
        LinearLayout dateLayout = inflate_search_xml.findViewById(R.id.fragment_search_dates);
        LinearLayout notification_layout = inflate_search_xml.findViewById(R.id.fragment_search_notification_layout);
        mSearchText = inflate_search_xml.findViewById(R.id.fragment_search_editText);
        mSearchButton = inflate_search_xml.findViewById(R.id.fragment_search_button);

        TypedArray idCheckbox = getResources().obtainTypedArray(R.array.res_checkbox);
        mTopicsFindViewById(inflate_search_xml, idCheckbox);
        idCheckbox.recycle();

        if (mNotification) {
            dateLayout.setVisibility(View.GONE);
            mSearchButton.setVisibility(View.GONE);
        } else {
            mBeginDate = inflate_search_xml.findViewById(R.id.fragment_search_begin_date);
            mEndDate = inflate_search_xml.findViewById(R.id.fragment_search_end_date);
            notification_layout.setVisibility(View.GONE);
        }
        Log.d("TAG", "onCreateView: ");
        return inflate_search_xml;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("TAG", "onViewCreated: ");
        changeStatusOfSearchButton(false);
        mSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mEditText = (s != null) && !s.toString().trim().isEmpty();
                changeStatusOfSearchButton(mEditText && mChekbox);

            }
        });

        // changeStatusButton(mSearchButton, false);
        for (CheckBox checkBox : mTopics)
            checkBox.setOnClickListener(v -> {
                refreshCheckBox();
                changeStatusOfSearchButton(mEditText && mChekbox);
            });

        setDateOnClickListener(view);

        mSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), SearchResultActivity.class);
            String[] intentExtra = new String[4];
            intentExtra[0] = mSearchText.getText().toString();
            intentExtra[1] = d8DateFormat(mBeginDate.getText().toString());
            intentExtra[2] = d8DateFormat(mEndDate.getText().toString());
            intentExtra[3] = filterQueryFormat();
            intent.putExtra("ARGS", intentExtra);
            startActivity(intent);
        });
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

    private void setDateOnClickListener(View view) {
        if (!mNotification) {
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (!mNotification) {
            String date = getString(R.string.date_format, dayOfMonth, (month + 1), year);
            if (mBegin) mBeginDate.setText(date);
            else mEndDate.setText(date);
        }
    }

    private void mTopicsFindViewById(View view, TypedArray idArray) {
        mTopics = new CheckBox[idArray.length()];
        for (int i = 0; i < mTopics.length; i++)
            mTopics[i] = view.findViewById(idArray.getResourceId(i, -1));
    }

    private void refreshCheckBox() {
        boolean newClickable = false;
        for (CheckBox checkBox : mTopics)
            newClickable = newClickable || checkBox.isChecked();
        mChekbox = newClickable;
    }

    private void changeStatusOfSearchButton(boolean enable) {
        if (enable) mSearchButton.setAlpha(0.9f);
        else mSearchButton.setAlpha(0.5f);
        mSearchButton.setEnabled(enable);
    }

    private String d8DateFormat(String date) {
        String[] arrayDate = date.split("/");
        if (arrayDate.length == 3) {
            String month = twoDigitFormat(arrayDate[1]);
            String day = twoDigitFormat(arrayDate[0]);
            return arrayDate[2] + month + day;
        } else return "";
    }

    private String twoDigitFormat(String number) {
        if (number.length() == 1) return "0" + number;
        else return number;
    }

    private String filterQueryFormat() {
        StringBuilder result = new StringBuilder("news_desk:(");
        for (int i = 0; i < mTopics.length; i++) {
            if (mTopics[i].isChecked()) {
                result.append("\"");
                result.append(mTopics[i].getText());
                result.append("\" ");
            }
        }
        return result.append(")").toString();
    }
}
