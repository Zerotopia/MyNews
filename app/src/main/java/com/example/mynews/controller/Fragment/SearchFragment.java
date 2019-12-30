package com.example.mynews.controller.Fragment;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.ResultReceiver;
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
import android.widget.Switch;

import com.example.mynews.R;
import com.example.mynews.controller.Activity.SearchResultActivity;
import com.example.mynews.model.Results;
import com.example.mynews.network.NYService;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import io.reactivex.Observable;

import static android.content.Context.ALARM_SERVICE;

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
    private LinearLayout mNotificationLayout;

    private Button mSearchButton;
    private Switch mNotificationSwitch;

    private CheckBox[] mTopics;
    private boolean mCheckbox;
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
        mCheckbox = false;
        mEditText = false;
        Log.d("TAG", "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate_search_xml = inflater.inflate(R.layout.fragment_search, container, false);
        LinearLayout dateLayout = inflate_search_xml.findViewById(R.id.fragment_search_dates);
        mNotificationLayout = inflate_search_xml.findViewById(R.id.fragment_search_notification_layout);
        mSearchText = inflate_search_xml.findViewById(R.id.fragment_search_editText);
        mSearchButton = inflate_search_xml.findViewById(R.id.fragment_search_button);
        mNotificationSwitch = inflate_search_xml.findViewById(R.id.fragment_search_notification_switch);

        TypedArray idCheckbox = getResources().obtainTypedArray(R.array.res_checkbox);
        mTopicsFindViewById(inflate_search_xml, idCheckbox);
        idCheckbox.recycle();

        if (mNotification) {
            dateLayout.setVisibility(View.GONE);
            mSearchButton.setVisibility(View.GONE);
        } else {
            mBeginDate = inflate_search_xml.findViewById(R.id.fragment_search_begin_date);
            mEndDate = inflate_search_xml.findViewById(R.id.fragment_search_end_date);
            mNotificationLayout.setVisibility(View.GONE);
        }
        Log.d("TAG", "onCreateView: ");
        return inflate_search_xml;
    }

    @Override
    public void onStop() {
        if ((mBeginDate != null) && (mEndDate != null)) {
            mBeginDate.setText("");
            mEndDate.setText("");
        }
        super.onStop();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("TAG", "onViewCreated: ");
        changeStatusOfSearch(false);
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
                changeStatusOfSearch(mEditText && mCheckbox);

            }
        });

        for (CheckBox checkBox : mTopics)
            checkBox.setOnClickListener(v -> {
                refreshCheckBox();
                changeStatusOfSearch(mEditText && mCheckbox);
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


//        mNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                if (isChecked) {
//                    String[] intentExtra = new String[4];
//                    intentExtra[0] = mSearchText.getText().toString();
//                    int year = Calendar.getInstance().get(Calendar.YEAR);
//                    int month = Calendar.getInstance().get(Calendar.MONTH);
//                    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//                    String date = day + "/" + month + "/" + year;
//                    intentExtra[1] = d8DateFormat(date);
//                    intentExtra[2] = d8DateFormat(date);
//                    intentExtra[3] = filterQueryFormat();
//                    ApiFragment apiFragment = ApiFragment.newInstance(9,intentExtra);
//                    int nbr = apiFragment.getNbResults();
//
//                } else {}
//        });
    }

    private void displayPickerDialog(String date) {
        Context context = getActivity();
        if (context != null) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    context,
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(stringDateToMillis("18/09/1851"));
            datePickerDialog.getDatePicker().setMaxDate(stringDateToMillis(""));

            if (!date.isEmpty()) {
                if (mBegin) datePickerDialog.getDatePicker().setMaxDate(stringDateToMillis(date));
                else datePickerDialog.getDatePicker().setMinDate(stringDateToMillis(date));
            }
            datePickerDialog.show();
        }
    }

    private long stringDateToMillis(String date) {
        String[] arrayDate = date.split("/");

        Calendar calendar = new GregorianCalendar();
        if (arrayDate.length == 3) {
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrayDate[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(arrayDate[1]) - 1);
            calendar.set(Calendar.YEAR, Integer.parseInt(arrayDate[2]));
            return calendar.getTimeInMillis();
        } else return System.currentTimeMillis();
    }

    private void setDateOnClickListener(View view) {
        if (!mNotification) {
            mBeginDate.setOnClickListener(v -> {
                mBegin = true;
                String endDate = mEndDate.getText().toString();
                displayPickerDialog(endDate);
            });

            mEndDate.setOnClickListener(v -> {
                mBegin = false;
                String beginDate = mBeginDate.getText().toString();
                displayPickerDialog(beginDate);
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
        mCheckbox = newClickable;
    }

    private void changeStatusOfSearch(boolean enable) {
        if (enable) {
            mSearchButton.setAlpha(0.9f);
            mNotificationLayout.setAlpha(1.0f);
        } else {
            mSearchButton.setAlpha(0.5f);
            mNotificationLayout.setAlpha(0.5f);
        }
        mSearchButton.setEnabled(enable);
        mNotificationLayout.setEnabled(enable);

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
        for (CheckBox checkBox : mTopics) {
            if (checkBox.isChecked()) {
                result.append("\"");
                result.append(checkBox.getText());
                result.append("\" ");
            }
        }
        return result.append(")").toString();
    }
}
