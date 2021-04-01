package com.shailesh.mybusappdagger2.di.my_booking_details;

import com.shailesh.mybusappdagger2.adapter.Adapter_Booking_Seat_Arrangement;
import com.shailesh.mybusappdagger2.adapter.Adapter_CancelBooking;
import com.shailesh.mybusappdagger2.adapter.Adapter_Coupon;
import com.shailesh.mybusappdagger2.adapter.Adapter_MyBooking;
import com.shailesh.mybusappdagger2.di.dashboard.DashboardScope;
import com.shailesh.mybusappdagger2.network.DashboardApi;
import com.shailesh.mybusappdagger2.network.MyBookingDetailsApi;
import com.shailesh.mybusappdagger2.ui.dashboard.Dashboard_Activity;
import com.shailesh.mybusappdagger2.ui.dashboard.my_bookings.My_Booking_Details_Activity;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MyBookingDetailsModule {

    @MyBookingDetailsScope
    @Provides
    static MyBookingDetailsApi provideMyBookingDetailsApi(Retrofit retrofit)
    {
        return retrofit.create(MyBookingDetailsApi.class);
    }

    @MyBookingDetailsScope
    @Provides
    static ProgressDialogCustom progressDialogCustom(My_Booking_Details_Activity activity)
    {
        return new ProgressDialogCustom(activity);
    }

    @MyBookingDetailsScope
    @Provides
    static Adapter_Booking_Seat_Arrangement provideAdapterBookingSeatArrangment(My_Booking_Details_Activity activity)
    {
        return new Adapter_Booking_Seat_Arrangement(activity);
    }
}
