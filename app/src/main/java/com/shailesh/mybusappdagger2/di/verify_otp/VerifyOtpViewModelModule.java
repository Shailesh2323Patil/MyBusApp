package com.shailesh.mybusappdagger2.di.verify_otp;

import com.shailesh.mybusappdagger2.di.ViewModelKey;
import com.shailesh.mybusappdagger2.ui.verify_otp.VerifyOtp_ViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class VerifyOtpViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(VerifyOtp_ViewModel.class)
    public abstract ViewModel bindVerifyOtpViewModel(VerifyOtp_ViewModel verifyOtp_viewModel);
}
