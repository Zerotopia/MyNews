package com.example.mynews.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.AlertDialogFragment;
import com.example.mynews.controller.fragment.ApiFragment;

import static com.example.mynews.controller.fragment.SearchFragment.ARGUMENTS;

/**
 * This activity display the result of the SearchActivity.
 */
public class SearchResultActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogClickEvent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String[] arguments = intent.getStringArrayExtra(ARGUMENTS);
        ApiFragment searchResultFragment = ApiFragment.newInstance(getResources().getStringArray(R.array.subjects).length, arguments);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, searchResultFragment)
                .commit();
    }

    @Override
    public void doPositiveClick() {
        finish();
    }

    @Override
    public void doNegativeClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
