package com.shailesh.mybusappdagger2.di.login;

import com.shailesh.mybusappdagger2.di.ViewModelKey;
import com.shailesh.mybusappdagger2.ui.login.Login_ViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LoginViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(Login_ViewModel.class)
    public abstract ViewModel bindLoginViewModule(Login_ViewModel loginViewModel);
}
