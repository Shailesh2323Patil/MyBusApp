package com.shailesh.mybusappdagger2.di.webview;

import com.shailesh.mybusappdagger2.ui.webview.WebViewClientImpl;
import com.shailesh.mybusappdagger2.ui.webview.WebView_Activity;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Subcomponent;

@Subcomponent(modules = WebviewModule.class)
@WebViewScope
public interface WebView_ActivitySubcomponent {
    WebViewClientImpl getWebClientImpl();

    ProgressDialogCustom getProgressDialogCustom();

    void inject(WebView_Activity webView_activity);

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder setActivity(WebView_Activity activity);

        @BindsInstance
        Builder setAString(@Named("aString") String aString);

        @BindsInstance
        Builder setBString(@Named("bString") String bString);

        WebView_ActivitySubcomponent build();
    }
}