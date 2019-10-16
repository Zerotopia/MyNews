package com.example.mynews.controller;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.mynews.R;

public class WebActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");

        mWebView = findViewById(R.id.webview_art);
        mWebView.loadUrl(url);
    }
}
