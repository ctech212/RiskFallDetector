package com.riskfall.detector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Result extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String url = "http://muntako.esy.es/history.php";
        WebView view = (WebView) this.findViewById(R.id.web_view);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}