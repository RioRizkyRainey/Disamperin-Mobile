package com.disamperin.mobile;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.disamperin.mobile.ui.TextProgressBar;


public class MainActivity extends ActionBarActivity {

    private WebView mWebView;
    private RelativeLayout mSplash;
    private TextProgressBar textProgressBar;

//    public static final String HOME_URL = "http://templatic.com/demos/directory/44";
    public static final String HOME_URL = "http://disamperin.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        mSplash = (RelativeLayout) findViewById(R.id.splash);
        textProgressBar = (TextProgressBar) findViewById(R.id.progressBar);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setBuiltInZoomControls(false);

        DisamperinWebViewClient webViewClient = new DisamperinWebViewClient(this);
        webViewClient.setSplashView(mSplash);
        mWebView.setWebChromeClient(new DisamperinWebChromeClient());
        mWebView.setWebViewClient(webViewClient);
        mWebView.loadUrl(HOME_URL);
    }

    @Override
    public void onBackPressed() {
        if (HOME_URL.equals(mWebView.getUrl())) {
            super.onBackPressed();
        } else {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DisamperinWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            textProgressBar.setProgress(newProgress);
            textProgressBar.setText(newProgress+"%");
//            if(newProgress == 100) {
//                mSplash.setVisibility(View.GONE);
//            }
        }
    }
}
