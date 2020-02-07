package com.example.mynews.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.AlertDialogFragment;
import com.example.mynews.controller.fragment.ApiFragment;

import static com.example.mynews.controller.fragment.AlertDialogFragment.HTTP_ERROR_429;
import static com.example.mynews.controller.fragment.AlertDialogFragment.HTTP_ERROR_500;
import static com.example.mynews.controller.fragment.AlertDialogFragment.NO_RESULT_SEARCH;
import static com.example.mynews.controller.fragment.ApiFragment.POSITION_SEARCH;
import static com.example.mynews.controller.fragment.SearchFragment.ARGUMENTS;
import static com.example.mynews.network.NYService.NEWS_DESK;

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
        ApiFragment searchResultFragment = ApiFragment.newInstance(POSITION_SEARCH, arguments);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, searchResultFragment)
                .commit();
    }

    @Override
    public void doPositiveClick(int usage) {
        switch (usage) {
            case NO_RESULT_SEARCH:
                finish();
                break;
            case HTTP_ERROR_429:
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case HTTP_ERROR_500:
                break;
            default:
                //Send Report.
                break;
        }
    }

    @Override
    public void doNegativeClick(int usage) {
        switch (usage) {
            case HTTP_ERROR_429:
                break;
            case HTTP_ERROR_500:
                //quit
                break;
            default:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
