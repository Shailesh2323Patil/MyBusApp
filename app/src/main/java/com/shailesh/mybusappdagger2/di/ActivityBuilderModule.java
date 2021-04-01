package com.shailesh.mybusappdagger2.di;

import com.shailesh.mybusappdagger2.di.dashboard.DashboardFragmentBuilderModule;
import com.shailesh.mybusappdagger2.di.dashboard.DashboardModule;
import com.shailesh.mybusappdagger2.di.dashboard.DashboardScope;
import com.shailesh.mybusappdagger2.di.dashboard.DashboardViewModelModule;
import com.shailesh.mybusappdagger2.di.login.LoginModule;
import com.shailesh.mybusappdagger2.di.login.LoginScope;
import com.shailesh.mybusappdagger2.di.login.LoginViewModelModule;
import com.shailesh.mybusappdagger2.di.my_booking_details.MyBookingDetailsModule;
import com.shailesh.mybusappdagger2.di.my_booking_details.MyBookingDetailsScope;
import com.shailesh.mybusappdagger2.di.my_booking_details.MyBookingViewModelModule;
import com.shailesh.mybusappdagger2.di.profile.ProfileModule;
import com.shailesh.mybusappdagger2.di.profile.ProfileScope;
import com.shailesh.mybusappdagger2.di.profile.ProfileViewModelModule;
import com.shailesh.mybusappdagger2.di.splash.SplashModule;
import com.shailesh.mybusappdagger2.di.splash.SplashScope;
import com.shailesh.mybusappdagger2.di.splash.SplashViewModelModule;
import com.shailesh.mybusappdagger2.di.verify_otp.VerifyOtpModule;
import com.shailesh.mybusappdagger2.di.verify_otp.VerifyOtpScope;
import com.shailesh.mybusappdagger2.di.verify_otp.VerifyOtpViewModelModule;
import com.shailesh.mybusappdagger2.di.webview.WebViewScope;
import com.shailesh.mybusappdagger2.di.webview.WebviewModule;
import com.shailesh.mybusappdagger2.ui.dashboard.Dashboard_Activity;
import com.shailesh.mybusappdagger2.ui.dashboard.my_bookings.My_Booking_Details_Activity;
import com.shailesh.mybusappdagger2.ui.login.Login_Activity;
import com.shailesh.mybusappdagger2.ui.profile.Profile_Activity;
import com.shailesh.mybusappdagger2.ui.splash.Splash_Activity;
import com.shailesh.mybusappdagger2.ui.verify_otp.VerifyOtp_Activity;
import com.shailesh.mybusappdagger2.ui.webview.WebViewClientImpl;
import com.shailesh.mybusappdagger2.ui.webview.WebView_Activity;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @LoginScope
    @ContributesAndroidInjector(
        modules = {LoginViewModelModule.class, LoginModule.class}
    )
    abstract Login_Activity contributeLoginActivity();

    @SplashScope
    @ContributesAndroidInjector(
            modules =  {SplashViewModelModule.class, SplashModule.class}
    )
    abstract Splash_Activity contributeSplashActivity();

    @VerifyOtpScope
    @ContributesAndroidInjector(
            modules = {VerifyOtpViewModelModule.class, VerifyOtpModule.class}
    )
    abstract VerifyOtp_Activity contributeVerifyOtpActivity();

    @DashboardScope
    @ContributesAndroidInjector(
            modules = {DashboardViewModelModule.class, DashboardModule.class, DashboardFragmentBuilderModule.class}
    )
    abstract Dashboard_Activity contributeDashboardActivity();

    @MyBookingDetailsScope
    @ContributesAndroidInjector(
            modules = {MyBookingViewModelModule.class,MyBookingDetailsModule.class}
    )
    abstract My_Booking_Details_Activity contributeMyBookingDetailsActivity();

    @ProfileScope
    @ContributesAndroidInjector(
            modules = {ProfileViewModelModule.class, ProfileModule.class}
    )
    abstract Profile_Activity contributeProfileActivity();

//    @WebViewScope
//    @ContributesAndroidInjector(
//            modules = {WebviewModule.class}
//    )
//    abstract Activity_WebView contributeActivityWebView();
}
