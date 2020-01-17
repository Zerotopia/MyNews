package com.example.mynews.controller.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.ApiFragment;

import static com.example.mynews.controller.fragment.SearchFragment.ARGUMENTS;

public class SearchResultActivity extends AppCompatActivity implements ApiFragment.NumberOfResultsListener {

    String[] mArguments;

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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder
                    .setIcon(R.drawable.ic_mood_bad_black_24dp)
                    .setCancelable(false)
                    .setMessage("Rien trouvé")
                    .setTitle("Pas de Résultat")
                    .setPositiveButton("Home", (dialog, which) -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    })
                    .setNegativeButton("New Search", (dialog, which) -> {
                        finish();
                        //Intent intent = new Intent(view.getContext(), SearchActivity.class);
                        //startActivity(intent);
                    })
                    .create()
                    .show();

        }
    }
}
