package com.disamperin.mobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Rio Rizky Rainey on 26/03/2015.
 * rizkyrainey@gmail.com
 */
public class DisamperinWebViewClient extends WebViewClient {

    private View splashView;
    private Context context;

    private ProgressDialog progressDialog;

    private static final String host = "disamperin.com";
    private static final String shortHost = "dsmpr.in";

    public DisamperinWebViewClient(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        String host = Uri.parse(url).getHost();
        if(host !=null) {
            if (host.endsWith("disamperin.com") || host.endsWith("dsmpr.in")) {
                return false;
            }
        }
        if(url.startsWith("mailto:")) {
            MailTo mailTo = MailTo.parse(url);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { mailTo.getTo() });
            intent.putExtra(Intent.EXTRA_TEXT, mailTo.getBody());
            intent.putExtra(Intent.EXTRA_SUBJECT, mailTo.getSubject());
            intent.putExtra(Intent.EXTRA_CC, mailTo.getCc());
            intent.setType("message/rfc822");
            context.startActivity(intent);
        }  else {
            if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
        }
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        System.out.println("onPageStarted: "+url);
        if(!url.equalsIgnoreCase(MainActivity.HOME_URL) && progressDialog!=null) progressDialog.show();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        System.out.println("onPageFinished: " + url);
        if(url.equalsIgnoreCase(MainActivity.HOME_URL)) {
            if(splashView != null) {
                splashView.setVisibility(View.GONE);
            }
        } else if(progressDialog!=null && progressDialog.isShowing()) progressDialog.dismiss();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if(progressDialog!=null && progressDialog.isShowing()) progressDialog.dismiss();
    }

    public void setSplashView(View splashView) {
        this.splashView = splashView;
    }
}
