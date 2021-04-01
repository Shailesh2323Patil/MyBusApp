package com.shailesh.mybusappdagger2.di.dashboard;
import com.shailesh.mybusappdagger2.ui.dashboard.home.Home_Fragment;
import com.shailesh.mybusappdagger2.ui.dashboard.my_account.MyAccount_Fragment;
import com.shailesh.mybusappdagger2.ui.dashboard.my_bookings.Bookings_Fragment;
import com.shailesh.mybusappdagger2.ui.dashboard.my_bookings.CancelBooking_Fragment;
import com.shailesh.mybusappdagger2.ui.dashboard.my_bookings.MyBooking_Fragment;
import com.shailesh.mybusappdagger2.ui.dashboard.offer.Offer_Fragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DashboardFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract Home_Fragment provideHomeFragment();

    @ContributesAndroidInjector
    abstract MyAccount_Fragment provideMyAccountFragment();

    @ContributesAndroidInjector
    abstract Bookings_Fragment provideBookingsFragment();

    @ContributesAndroidInjector
    abstract Offer_Fragment provideOfferFragment();

    @ContributesAndroidInjector
    abstract CancelBooking_Fragment provideCancelBookingFragment();

    @ContributesAndroidInjector
    abstract MyBooking_Fragment provideMyBookingFragment();
}
