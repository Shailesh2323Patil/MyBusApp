package com.shailesh.mybusappdagger2.di.dashboard;

import com.shailesh.mybusappdagger2.di.ViewModelKey;
import com.shailesh.mybusappdagger2.ui.dashboard.Dashboard_ViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class DashboardViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(Dashboard_ViewModel.class)
    public abstract ViewModel provideDashboardViewModelModule(Dashboard_ViewModel dashboard_viewModel);
}
