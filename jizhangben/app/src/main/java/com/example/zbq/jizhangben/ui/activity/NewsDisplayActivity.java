package com.example.zbq.jizhangben.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.zbq.jizhangben.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zbq on 18-3-19.
 */

public class NewsDisplayActivity extends AppCompatActivity {

    @BindView(R.id.web_news)
    WebView webNews;
    private String newsUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        ButterKnife.bind(this);
        newsUrl = getIntent().getStringExtra("news_url");
        webNews.getSettings().setJavaScriptEnabled(true);
        webNews.setWebViewClient(new WebViewClient());
        webNews.loadUrl(newsUrl);
        Log.v("Tag",newsUrl);
    }
}
