package com.shailesh.mybusappdagger2.di.login;

import com.shailesh.mybusappdagger2.network.LoginApi;
import com.shailesh.mybusappdagger2.ui.login.Login_Activity;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class LoginModule {

    @LoginScope
    @Provides
    static LoginApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(LoginApi.class);
    }

    @LoginScope
    @Provides
    static ProgressDialogCustom progressDialogCustom(Login_Activity activity)
    {
        return new ProgressDialogCustom(activity);
    }
}
