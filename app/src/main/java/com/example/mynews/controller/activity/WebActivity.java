package com.example.mynews.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.AlertDialogFragment;

import static com.example.mynews.model.Article.NYT_HOME_URL;
import static com.example.mynews.model.Article.UNDEFINED;

public class WebActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogClickEvent {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        String url = (extra != null) ? extra.getString("url") : NYT_HOME_URL;
        if (url.equals(UNDEFINED)) {
            AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(AlertDialogFragment.NO_URL);
            alertDialogFragment.show(getSupportFragmentManager(),"KEY");
        }

        mWebView = findViewById(R.id.web_activity_web_view);
        mWebView.loadUrl(url);
    }

    @Override
    public void doPositiveClick() {
        mWebView.loadUrl(NYT_HOME_URL);
    }

    @Override
    public void doNegativeClick() {
        finish();
    }
}
