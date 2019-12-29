package com.example.mynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.mynews.R;

import static com.example.mynews.model.Article.NYT_HOME_URL;

public class WebActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        String url = (extra != null) ? extra.getString("url") : NYT_HOME_URL;

        mWebView = findViewById(R.id.web_activity_web_view);
        mWebView.loadUrl(url);
    }
}
