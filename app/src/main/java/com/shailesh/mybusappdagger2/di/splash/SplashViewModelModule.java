package com.shailesh.mybusappdagger2.di.splash;

import com.shailesh.mybusappdagger2.di.ViewModelKey;
import com.shailesh.mybusappdagger2.ui.splash.Splash_ViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SplashViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(Splash_ViewModel.class)
    public abstract ViewModel bindSplashViewModel(Splash_ViewModel splashViewModel);

}
