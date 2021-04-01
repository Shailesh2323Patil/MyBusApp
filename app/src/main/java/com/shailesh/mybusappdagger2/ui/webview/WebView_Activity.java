package com.shailesh.mybusappdagger2.ui.webview;

import androidx.appcompat.app.AppCompatActivity;
import dagger.android.support.DaggerAppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.shailesh.mybusappdagger2.BaseApplication;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.di.ActivityBuilderModule;
import com.shailesh.mybusappdagger2.di.AppComponent;
import com.shailesh.mybusappdagger2.di.DaggerAppComponent;
import com.shailesh.mybusappdagger2.di.webview.WebView_ActivitySubcomponent;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;

import javax.inject.Inject;

public class WebView_Activity extends AppCompatActivity
{
    private static final String TAG = "WebView_Activity";

    private WebView webView = null;

    AppComponent appComponent;

    ProgressDialogCustom progressDialogCustom;

    WebView_ActivitySubcomponent activitySubcomponent;

    WebViewClientImpl webViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.id_web_view);

        appComponent = DaggerAppComponent.builder().application((BaseApplication) getApplication()).build();

        activitySubcomponent = appComponent.getWebViewSubComponent()
                                            .setActivity(this)
                                            .setAString("A")
                                            .setBString("B")
                                            .build();

        String url = getIntent().getStringExtra("url");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webViewClient = activitySubcomponent.getWebClientImpl();

        progressDialogCustom = activitySubcomponent.getProgressDialogCustom();

        //WebViewClientImpl webViewClient = new WebViewClientImpl(this,mProgressDialog);
        webView.setWebViewClient(webViewClient);

        webView.loadUrl(url);

        dialogNoInternet();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    private void dialogNoInternet()
    {
        if(CheckInternetConnection.isConnectionToInternet(this) == false)
        {
            DialogBox_NoInternetConnection.dialogOkMessage(this);
        }
    }
}