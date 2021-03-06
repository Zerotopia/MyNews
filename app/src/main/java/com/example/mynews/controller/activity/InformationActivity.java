package com.example.mynews.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mynews.R;

import static com.example.mynews.controller.activity.MainActivity.ACTIVITY;

/**
 * Activity launch when the user click on "About" or "Help".
 */
public class InformationActivity extends AppCompatActivity {

    private boolean mHelp;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Intent intent = getIntent();
        mHelp = intent.getBooleanExtra(ACTIVITY, true);

        mTextView = findViewById(R.id.information_activity_text);

        if (mHelp) mTextView.setText(R.string.help_text);
        else mTextView.setText(R.string.about_text);
    }
}
