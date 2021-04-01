package com.shailesh.mybusappdagger2.di.profile;

import com.shailesh.mybusappdagger2.di.ViewModelKey;
import com.shailesh.mybusappdagger2.ui.profile.Profile_ViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ProfileViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(Profile_ViewModel.class)
    abstract ViewModel provideProfileViewModelModule(Profile_ViewModel profile_viewModel);

}
