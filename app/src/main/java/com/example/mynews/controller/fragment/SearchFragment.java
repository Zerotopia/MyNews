package com.example.mynews.controller.fragment;


import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.mynews.controller.workermanager.NotificationWorker;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;
import static com.example.mynews.model.FormatMaker.d8DateFormat;
import static com.example.mynews.model.FormatMaker.filterQueryFormat;
import static com.example.mynews.model.FormatMaker.stringDateToMillis;
import static com.example.mynews.network.NYService.NEWS_DESK;

/**
 * SearchFragment is the fragment used for research articles, and for notification.
 */
public class SearchFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    /**
     * Key to pass some arguments to the intent of SearchResultActivity.
     */
    public static final String ARGUMENTS = "ARGS";

    /**
     * Tags used for the creation of the Notification and the Notification channel.
     */
    public static final int REQUEST_CODE = 42;
    public static final String CHANNEL = "NotificationChannel";
    private static final String NOTIFICATION_CHANNEL_NAME = "New Results";
    private static final String CHANNEL_DESCRIPTION = "New article published";

    /**
     * Tags used to save the SharedPreferences. We distinguish the SharedPreferences
     * of the search activity from the notification activity by the two constants
     * SEARCH_PARAM and NOTIFICATION_PARAM.
     */
    public static final String SEARCH_PARAM = "SEARCH PARAMETERS";
    public static final String NOTIFICATION_PARAM = "NOTIFICATION PARAMETERS";
    public static final String KEYWORD = "KEYWORD";
    public static final String TOPICS = "TOPICS";
    private static final String BEGIN_DATE = "BEGIN DATE";
    private static final String END_DATE = "END DATE";

    /**
     * Key use in newInstance to bundle the boolean passed in argument of the constructor.
     */
    private static final String BOOLEAN = "ACTIVITY";

    /**
     * Constant that contains the date of the oldest accessible article
     * on the NewYork Times website.
     */
    private static final String FIRST_ARTICLE_DATE = "18/09/1851";

    private SharedPreferences mSharedPreferences;

    /**
     * Widgets
     */
    private EditText mBeginDate;
    private EditText mEndDate;
    private EditText mSearchText;
    private LinearLayout mNotificationLayout;
    private LinearLayout mDateLayout;
    private TextView mNotificationTextView;
    private Button mSearchButton;
    private Switch mNotificationSwitch;
    private CheckBox[] mTopics;

    /**
     * This activity is both for Search articles and Notification.
     * This boolean is to know in which situation we are.
     * true : Notification
     * false : Search article.
     */
    private boolean mNotification;

    /**
     * Boolean to know if the user has clicked on the EditText mBeginDate or mEndDate.
     * It is useful to configure the DatePickerDialog.
     * true : mBeginDate.
     * false : mEndDate.
     */
    private boolean mBegin;

    /**
     * mCheckbox is true if at least one topic (checkbox) is selected.
     * And mEditText is true if mSearchText is not empty.
     * So the search button is enable if these booleans are both true.
     */
    private boolean mCheckbox;
    private boolean mEditText;

    public static SearchFragment newInstance(boolean activity) {
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
        mEditText = false;
        mCheckbox = false;
    }

    /**
     * 1 - Binding Views
     * 2 - We determine what is visible according to the boolean mNotification
     * 3 - Setting Context
     * 4 - In the creation of the view, all field are empty so we disable the Search Button or the
     * Notification switcher.
     * 5 - We set all Listener that we need to the smooth operation of the activity.
     * 6 - If necessary, we restore the previous research elements saved in the Shared Preferences.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflate_search_xml = inflater.inflate(R.layout.fragment_search, container, false);
        bindView(inflate_search_xml);
        setVisibilityContent(inflate_search_xml);

        changeStatusOfSearch(false);

        setSearchTextChangeListener();
        setCheckBoxesOnClickListener();
        setDateOnClickListener();
        setSearchButtonOnClickListener();
        setNotificationSwitchListener();

        restorePreferences();

        return inflate_search_xml;
    }

    /**
     * Method that pre-filled Notification form or Search Article form with
     * saved data in SharedPreferences.
     * We are two space name for the Shared Preferences :
     * - NOTIFICATION_PARAM for the notification part
     * - SEARCH_PARAM for the search article part
     * This allows saved state of the search article activity without overwrite
     * the saved state of the notification part.
     */
    private void restorePreferences() {
        Set<String> topics;

        if (mNotification) {
            mSharedPreferences = requireContext().getSharedPreferences(NOTIFICATION_PARAM, MODE_PRIVATE);
            mSearchText.setText(mSharedPreferences.getString(KEYWORD, ""));
            topics = mSharedPreferences.getStringSet(TOPICS, new HashSet<>());
        } else {
            mSharedPreferences = requireContext().getSharedPreferences(SEARCH_PARAM, MODE_PRIVATE);
            mSearchText.setText(mSharedPreferences.getString(KEYWORD, ""));
            mBeginDate.setText(mSharedPreferences.getString(BEGIN_DATE, ""));
            mEndDate.setText(mSharedPreferences.getString(END_DATE, ""));
            topics = mSharedPreferences.getStringSet(TOPICS, new HashSet<>());
        }
        for (int i = 0; i < mTopics.length; i++)
            if (topics.contains(NEWS_DESK[i + 1])) mTopics[i].setChecked(true);
    }

    /**
     * Listener for the Switch button that enable or disable notification.
     * We use a PeriodicWorkRequest to send a request every day.
     */
    private void setNotificationSwitchListener() {
        mNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            PeriodicWorkRequest notificationRequest =
                    new PeriodicWorkRequest.Builder(NotificationWorker.class, 1, TimeUnit.DAYS)
                            .setInitialDelay(1, TimeUnit.DAYS)
                            .build();
            if (isChecked) {
                createNotificationChannel();
                WorkManager.getInstance(requireContext()).enqueue(notificationRequest);
                mNotificationTextView.setText(R.string.disable_notification);
            } else {
                mNotificationTextView.setText(R.string.enable_notification);
                WorkManager.getInstance(requireContext()).cancelWorkById(notificationRequest.getId());
            }
        });
    }

    /**
     * Creation of the Notification channel for devices with
     * API level greater or equals to 26.
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESCRIPTION);

            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
    }

    /**
     * Listener for the Search button. Launch an activity that
     * display the articles found. The NewYork Times API request
     * need that some arguments are in the good format. See the
     * FormatMaker class to have more details about d8DateFormat
     * and filterQueryFormat.
     */
    private void setSearchButtonOnClickListener() {
        mSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SearchResultActivity.class);

            String[] intentExtra = {
                    mSearchText.getText().toString(),
                    d8DateFormat(mBeginDate.getText().toString()),
                    d8DateFormat(mEndDate.getText().toString()),
                    filterQueryFormat(topicsToStringSet())};
            intent.putExtra(ARGUMENTS, intentExtra);
            startActivity(intent);
        });
    }

    /**
     * @return The Set<String> that contains all topics "checked" by the user.
     */
    private Set<String> topicsToStringSet() {
        Set<String> result = new HashSet<>();
        for (int i = 0; i < mTopics.length; i++)
            if (mTopics[i].isChecked()) result.add(NEWS_DESK[i + 1]);
        return result;
    }

    /**
     * Listener for date selection.
     * <p>
     * The boolean mBegin allows us to know if we are clicked on mBeginDate or
     * on mEndDate TextView for selecting a date.
     * <p>
     * Since mBeginDate and mEndDate are not focusable, before display the PickerDialog
     * we set an empty text to allows the user to clear the date if he cancel the PickerDialog.
     */
    private void setDateOnClickListener() {
        if (!mNotification) {
            mBeginDate.setOnClickListener(v -> {
                mBegin = true;
                mBeginDate.setText("");
                displayPickerDialog(mEndDate.getText().toString());
            });

            mEndDate.setOnClickListener(v -> {
                mBegin = false;
                mEndDate.setText("");
                displayPickerDialog(mBeginDate.getText().toString());
            });
        }
    }

    /**
     * method that displays the PickerDialog.
     * PickerDialog appears with the "today" date, and by default
     * we fix the minDate to the date of the oldest article of the NewYork Times website,
     * and the maxDate to "today".
     * <p>
     * After according to the date passed in parameter and if we selected a BeginDate or a EndDate
     * (mBegin boolean) we set a MinDate or a MaxDate.
     * This ensures that beginDate will never be after the endDate.
     *
     * @param date to fix minDate or maxDate of the PickerDialog.
     */
    private void displayPickerDialog(String date) {
        Context context = getActivity();
        if (context != null) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    context,
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(stringDateToMillis(FIRST_ARTICLE_DATE));
            datePickerDialog.getDatePicker().setMaxDate(stringDateToMillis(""));

            if (!date.isEmpty()) {
                if (mBegin) datePickerDialog.getDatePicker().setMaxDate(stringDateToMillis(date));
                else datePickerDialog.getDatePicker().setMinDate(stringDateToMillis(date));
            }
            datePickerDialog.show();
        }
    }

    /**
     * Override method to implements the DatePickerDialog.OnDateSetListener.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (!mNotification) {
            String date = getString(R.string.date_format, dayOfMonth, (month + 1), year);
            if (mBegin) mBeginDate.setText(date);
            else mEndDate.setText(date);
        }
    }

    /**
     * Listener for CheckBoxes selection.
     * When the user click on a checkBox :
     * 1 - we update the boolean mCheckbox
     * 2 - we enable or disable the Search Button or the notification switch button
     * according to the new value of mCheckbox.
     */
    private void setCheckBoxesOnClickListener() {
        for (CheckBox checkBox : mTopics)
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                updateCheckBox();
                changeStatusOfSearch(mEditText && mCheckbox);
            });
    }

    /**
     * Method that update the value of th boolean mCheckbox.
     */
    private void updateCheckBox() {
        boolean newCheckbox = false;
        for (CheckBox checkBox : mTopics)
            newCheckbox = newCheckbox || checkBox.isChecked();
        mCheckbox = newCheckbox;
    }

    /**
     * Listener for the Search Text.
     * Every time that the text of the textView mSearchText change,
     * the boolean mEditText is update and we change the status of
     * the Search button (or notification switch) according to the new
     * value of mEditText.
     */
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

    /**
     * Method that enable or disable the Search Button or
     * the Notification switch button :
     * enable if the argument enable is true,
     * disable if the argument enable is false.
     * To have a visual indication that the button is disable,
     * we use the method setAlpha.
     */
    private void changeStatusOfSearch(boolean enable) {
        if (enable) {
            mSearchButton.setAlpha(0.9f);
            mNotificationLayout.setAlpha(1.0f);
        } else {
            mSearchButton.setAlpha(0.5f);
            mNotificationLayout.setAlpha(0.5f);
            mNotificationSwitch.setChecked(false);
        }
        mSearchButton.setEnabled(enable);
        mNotificationLayout.setEnabled(enable);
    }

    /**
     * The interface of the activity is not the same,
     * according to whether the user search articles or
     * the user set notification parameters.
     * mNotification is the boolean hat determines in which case we are.
     * So this method determines what is visible or not
     * according to the boolean mNotification.
     */
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

    /**
     * Method to bind views of all ChexkBoxes.
     */
    private void mTopicsFindViewById(View view) {
        TypedArray idCheckbox = getResources().obtainTypedArray(R.array.res_checkbox);

        mTopics = new CheckBox[idCheckbox.length()];
        for (int i = 0; i < mTopics.length; i++)
            mTopics[i] = view.findViewById(idCheckbox.getResourceId(i, -1));
        idCheckbox.recycle();
    }

    private void bindView(View view) {
        mDateLayout = view.findViewById(R.id.fragment_search_dates);
        mNotificationLayout = view.findViewById(R.id.fragment_search_notification_layout);
        mNotificationTextView = view.findViewById(R.id.fragment_search_notification_text);
        mSearchText = view.findViewById(R.id.fragment_search_editText);
        mSearchButton = view.findViewById(R.id.fragment_search_button);
        mNotificationSwitch = view.findViewById(R.id.fragment_search_notification_switch);
        mTopicsFindViewById(view);
    }

    /**
     * In onPause method we save the current state in SharedPreferences.
     */
    @Override
    public void onPause() {
        SharedPreferences.Editor preferencesEditor = mSharedPreferences.edit();

        preferencesEditor
                .putString(KEYWORD, mSearchText.getText().toString())
                .putStringSet(TOPICS, topicsToStringSet())
                .apply();
        if (!mNotification) {
            preferencesEditor
                    .putString(BEGIN_DATE, mBeginDate.getText().toString())
                    .putString(END_DATE, mEndDate.getText().toString())
                    .apply();
        }
        super.onPause();
    }
}
