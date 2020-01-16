package com.example.mynews.controller.fragment;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.controller.activity.SearchResultActivity;
import com.example.mynews.controller.broadcastreciever.Reciever;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.mynews.controller.broadcastreciever.Reciever.CHANNEL;
import static com.example.mynews.model.FormatMaker.d8DateFormat;
import static com.example.mynews.model.FormatMaker.filterQueryFormat;
import static com.example.mynews.model.FormatMaker.stringDateToMillis;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String BOOLEAN = "activity";

    private Context mContext;

    private EditText mBeginDate;
    private EditText mEndDate;
    private EditText mSearchText;
    private Boolean mBegin;
    private Boolean mNotification;
    private LinearLayout mNotificationLayout;
    private LinearLayout mDateLayout;
    private TextView mNotificationTextView;

    private Button mSearchButton;
    private Switch mNotificationSwitch;

    private CheckBox[] mTopics;
    private boolean mCheckbox;
    private boolean mEditText;

    // private FormatMaker mFormatMaker = new FormatMaker();

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
        mDateLayout = inflate_search_xml.findViewById(R.id.fragment_search_dates);
        mNotificationLayout = inflate_search_xml.findViewById(R.id.fragment_search_notification_layout);
        mNotificationTextView = inflate_search_xml.findViewById(R.id.fragment_search_notification_text);
        mSearchText = inflate_search_xml.findViewById(R.id.fragment_search_editText);
        mSearchButton = inflate_search_xml.findViewById(R.id.fragment_search_button);
        mNotificationSwitch = inflate_search_xml.findViewById(R.id.fragment_search_notification_switch);
        mTopicsFindViewById(inflate_search_xml);

        setVisibilityContent(inflate_search_xml);

        mContext = inflate_search_xml.getContext();
        changeStatusOfSearch(false);

        setSearchTextChangeListener();
        setCheckBoxesOnClickListener();
        setDateOnClickListener();
        setSearchButtonOnClickListener();
        setNotificationSwitchListener();

        Log.d("TAG", "onCreateView: ");
        return inflate_search_xml;
    }

    private void setNotificationSwitchListener() {
        mNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                createNotificationChannel(mContext);
                Log.d("TAG", "onViewCreated: Abc ");
                Intent intent = new Intent(mContext, Reciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        mContext,
                        42,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
                if (alarmManager != null)
                    alarmManager.setRepeating(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + 15000,
                            AlarmManager.INTERVAL_DAY,
                            pendingIntent);

                mNotificationTextView.setText(R.string.disable_notification);
            } else {
                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
                if (notificationManager != null) notificationManager.cancel(42);

                mNotificationTextView.setText(R.string.enable_notification);
            }
        });
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "New Results";
            String description = "New article published";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);

        }
    }

    private void setSearchButtonOnClickListener() {
        mSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SearchResultActivity.class);
            String[] intentExtra = new String[4];
            intentExtra[0] = mSearchText.getText().toString();
            intentExtra[1] = d8DateFormat(mBeginDate.getText().toString());
            intentExtra[2] = d8DateFormat(mEndDate.getText().toString());
            intentExtra[3] = filterQueryFormat(topicsToStringSet());
            intent.putExtra("ARGS", intentExtra);
            startActivity(intent);
        });
    }

    private void setDateOnClickListener() {
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
                if (mBegin)
                    datePickerDialog.getDatePicker().setMaxDate(stringDateToMillis(date));
                else
                    datePickerDialog.getDatePicker().setMinDate(stringDateToMillis(date));
            }
            datePickerDialog.show();
        }
    }

    private void setCheckBoxesOnClickListener() {
        for (CheckBox checkBox : mTopics)
            checkBox.setOnClickListener(v -> {
                refreshCheckBox();
                changeStatusOfSearch(mEditText && mCheckbox);
            });
    }

    private void refreshCheckBox() {
        boolean newClickable = false;
        for (CheckBox checkBox : mTopics)
            newClickable = newClickable || checkBox.isChecked();
        mCheckbox = newClickable;
    }

    private void setSearchTextChangeListener() {
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
    }

    private void setVisibilityContent(View view) {
        if (mNotification) {
            mDateLayout.setVisibility(View.GONE);
            mSearchButton.setVisibility(View.GONE);
        } else {
            mBeginDate = view.findViewById(R.id.fragment_search_begin_date);
            mEndDate = view.findViewById(R.id.fragment_search_end_date);
            mNotificationLayout.setVisibility(View.GONE);
        }
    }

    private void mTopicsFindViewById(View view) {
        TypedArray idCheckbox = getResources().obtainTypedArray(R.array.res_checkbox);

        mTopics = new CheckBox[idCheckbox.length()];
        for (int i = 0; i < mTopics.length; i++)
            mTopics[i] = view.findViewById(idCheckbox.getResourceId(i, -1));

        idCheckbox.recycle();
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

    @Override
    public void onStop() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("search_params", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();

        preferencesEditor
                .putString("KEYWORD", mSearchText.getText().toString())
                .putString("BEGINDATE", mBeginDate.getText().toString())
                .putString("ENDDATE", mEndDate.getText().toString())
                .putStringSet("IOPICS", topicsToStringSet())
                .apply();
        // preferencesEditor.
        /*
        if ((mBeginDate != null) && (mEndDate != null)) {
            mBeginDate.setText("");
            mEndDate.setText("");
        }*/
        super.onStop();
    }

    private Set<String> topicsToStringSet() {
        Set<String> result = new HashSet<>();
        for (CheckBox checkBox : mTopics)
            if (checkBox.isChecked()) result.add(checkBox.getText().toString());
        return result;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (!mNotification) {
            String date = getString(R.string.date_format, dayOfMonth, (month + 1), year);
            if (mBegin) mBeginDate.setText(date);
            else mEndDate.setText(date);
        }
    }
}
