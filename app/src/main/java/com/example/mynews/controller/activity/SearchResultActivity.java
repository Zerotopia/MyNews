package com.example.mynews.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.AlertDialogFragment;
import com.example.mynews.controller.fragment.ApiFragment;

import static com.example.mynews.controller.fragment.SearchFragment.ARGUMENTS;

public class SearchResultActivity extends AppCompatActivity implements ApiFragment.NumberOfResultsListener, AlertDialogFragment.AlertDialogClickEvent {

    private String[] mArguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        mArguments = intent.getStringArrayExtra(ARGUMENTS);
        System.out.println("AVANTGETFRAGMENT*********************************************");
        ApiFragment searchResultFragment = ApiFragment.newInstance(9, mArguments);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, searchResultFragment)
                .commit();
        System.out.println("APRESGETFRAGMENT*********************************************");
    }

    @Override
    public void onNumberOfResultsChange(int numberOfResults) {
        if (numberOfResults == 0) {
            AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(AlertDialogFragment.NO_RESULT);
            alertDialogFragment.show(getSupportFragmentManager(), "KEY");
        }
    }

    @Override
    public void doPositiveClick() {
        Log.d("TAG", "doPositiveClick: ");

        finish();
        //Intent intent = new Intent(view.getContext(), SearchActivity.class);
        //startActivity(intent);

    }

    @Override
    public void doNegativeClick() {
        Log.d("TAG", "doNegativeClick: ");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
