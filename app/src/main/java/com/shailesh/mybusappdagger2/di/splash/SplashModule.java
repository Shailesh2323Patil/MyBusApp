package com.shailesh.mybusappdagger2.di.splash;

import android.app.ProgressDialog;

import com.shailesh.mybusappdagger2.network.SplashApi;
import com.shailesh.mybusappdagger2.ui.splash.Splash_Activity;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class SplashModule {

    @SplashScope
    @Provides
    static SplashApi provideSplashApi(Retrofit retrofit)
    {
        return retrofit.create(SplashApi.class);
    }

    @SplashScope
    @Provides
    static ProgressDialogCustom provideProgressDialogCustom(Splash_Activity splash_activity)
    {
        return new ProgressDialogCustom(splash_activity);
    }

}
