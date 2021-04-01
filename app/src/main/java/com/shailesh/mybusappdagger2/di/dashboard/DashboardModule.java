package com.shailesh.mybusappdagger2.di.dashboard;

import com.shailesh.mybusappdagger2.adapter.Adapter_CancelBooking;
import com.shailesh.mybusappdagger2.adapter.Adapter_Coupon;
import com.shailesh.mybusappdagger2.adapter.Adapter_MyBooking;
import com.shailesh.mybusappdagger2.di.my_booking_details.MyBookingDetailsScope;
import com.shailesh.mybusappdagger2.network.DashboardApi;
import com.shailesh.mybusappdagger2.ui.dashboard.Dashboard_Activity;
import com.shailesh.mybusappdagger2.ui.dashboard.my_bookings.My_Booking_Details_Activity;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DashboardModule {

    @DashboardScope
    @Provides
    static DashboardApi provideDashboardApi(Retrofit retrofit)
    {
        return retrofit.create(DashboardApi.class);
    }

    @DashboardScope
    @Provides
    static ProgressDialogCustom progressDialogCustom(Dashboard_Activity activity)
    {
        return new ProgressDialogCustom(activity);
    }

    @DashboardScope
    @Provides
    static Adapter_Coupon provideAdapterCoupon(Dashboard_Activity activity)
    {
        return new Adapter_Coupon(activity);
    }

    @DashboardScope
    @Provides
    static Adapter_MyBooking provideAdapterMyBooking(Dashboard_Activity activity)
    {
        return new Adapter_MyBooking(activity);
    }

    @DashboardScope
    @Provides
    static Adapter_CancelBooking provideAdapterCancelBooking(Dashboard_Activity activity)
    {
        return new Adapter_CancelBooking(activity);
    }
}
