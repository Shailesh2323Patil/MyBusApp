package com.shailesh.mybusappdagger2.di.verify_otp;

import com.shailesh.mybusappdagger2.model.VerifyOtp;
import com.shailesh.mybusappdagger2.network.VerifyOtpApi;
import com.shailesh.mybusappdagger2.ui.verify_otp.VerifyOtp_Activity;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class VerifyOtpModule {

    @VerifyOtpScope
    @Provides
    static VerifyOtpApi provideVerifyOtp(Retrofit retrofit)
    {
        return retrofit.create(VerifyOtpApi.class);
    }

    @VerifyOtpScope
    @Provides
    static ProgressDialogCustom provideProgressDialogCustom(VerifyOtp_Activity verifyOtp_activity)
    {
        return new ProgressDialogCustom(verifyOtp_activity);
    }
}
