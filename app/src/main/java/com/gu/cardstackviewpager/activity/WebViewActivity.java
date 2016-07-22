package com.gu.cardstackviewpager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.gu.cardstackviewpager.R;

/**
 * Created by Nate on 2016/7/22.
 */
public class WebViewActivity extends AppCompatActivity {

    public static final String URL_KEY = "url_key";

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String url = "https://github.com/NateRobinson";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置切换动画
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString(WebViewActivity.URL_KEY);
        }

        mWebView= (WebView) findViewById(R.id.web_view);
        mProgressBar= (ProgressBar) findViewById(R.id.web_view_progress_bar);

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.loadUrl(url);
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                if (mProgressBar.getVisibility() == View.GONE) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    class WebViewClient extends android.webkit.WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void finish() {
        super.finish();
        //设置切换动画
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
