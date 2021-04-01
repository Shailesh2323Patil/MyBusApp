package com.shailesh.mybusappdagger2.ui.webview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;


public class WebViewClientImpl extends WebViewClient
{
    private static final String TAG = "WebViewClientImpl";

    private Activity activity = null;

    ProgressDialogCustom mProgressDialog;

    public WebViewClientImpl(Activity activity,
                             ProgressDialogCustom mProgressDialog,
                             String aString,
                             String bString)
    {
        this.activity = activity;
        this.mProgressDialog = mProgressDialog;

        Log.e(TAG,aString+" "+bString);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url)
    {
//        if(url.indexOf("journaldev.com") > -1 ) return false;
//
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        activity.startActivity(intent);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon)
    {
        super.onPageStarted(view, url, favicon);
        mProgressDialog.showProgressBar(true);
    }

    @Override
    public void onPageFinished(WebView view, String url)
    {
        super.onPageFinished(view, url);
        mProgressDialog.showProgressBar(false);
    }
}
