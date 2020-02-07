package com.example.mynews.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.AlertDialogFragment;

import static com.example.mynews.controller.adapteur.ArticleAdapter.ArticlesViewHolder.URL_ARTICLE;
import static com.example.mynews.controller.fragment.AlertDialogFragment.ALERT_DIALOG_TAG;
import static com.example.mynews.controller.fragment.AlertDialogFragment.HTTP_ERROR_429;
import static com.example.mynews.controller.fragment.AlertDialogFragment.HTTP_ERROR_500;
import static com.example.mynews.controller.fragment.AlertDialogFragment.NO_URL;
import static com.example.mynews.model.Article.NYT_HOME_URL;
import static com.example.mynews.model.Article.UNDEFINED;

/**
 * This activity show in a Webview the content of the selected article.
 * If we not found a valid url then we show an AlertDialog.
 */
public class WebActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogClickEvent {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        String url = (extra != null) ? extra.getString(URL_ARTICLE, UNDEFINED) : UNDEFINED;
        if (url.equals(UNDEFINED)) {
            AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(AlertDialogFragment.NO_URL);
            alertDialogFragment.show(getSupportFragmentManager(), ALERT_DIALOG_TAG);
        }

        mWebView = findViewById(R.id.web_activity_web_view);
        mWebView.loadUrl(url);
    }

    @Override
    public void doPositiveClick(int usage) {
        switch (usage) {
            case NO_URL:
                mWebView.loadUrl(NYT_HOME_URL);
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
            case NO_URL:
                finish();
                break;
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
