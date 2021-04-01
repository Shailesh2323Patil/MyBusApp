package com.shailesh.mybusappdagger2.di.my_booking_details;

import com.shailesh.mybusappdagger2.di.ViewModelKey;
import com.shailesh.mybusappdagger2.ui.dashboard.Dashboard_ViewModel;
import com.shailesh.mybusappdagger2.ui.dashboard.my_bookings.My_Booking_Details_ViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MyBookingViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(My_Booking_Details_ViewModel.class)
    public abstract ViewModel provideMyBookingDetailsViewModelModule(My_Booking_Details_ViewModel myBookingDetailsViewModel);
}
