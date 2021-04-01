package com.shailesh.mybusappdagger2.di.webview;

import android.app.Activity;

import com.shailesh.mybusappdagger2.ui.webview.WebView_Activity;
import com.shailesh.mybusappdagger2.ui.webview.WebViewClientImpl;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class WebviewModule {

    @Provides
    static ProgressDialogCustom provideProgressDialogCustom(WebView_Activity webViewActivity)
    {
        return new ProgressDialogCustom(webViewActivity);
    }

    @Provides
    static WebViewClientImpl provideWebViewClientImpl(WebView_Activity activity,
                                                      @Named("aString") String aString,
                                                      @Named("bString") String bString)
    {
        return new WebViewClientImpl(activity,new ProgressDialogCustom(activity),aString,bString);
    }

}
