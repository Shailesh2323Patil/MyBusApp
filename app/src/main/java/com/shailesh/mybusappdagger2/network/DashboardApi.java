package com.shailesh.mybusappdagger2.network;

import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DashboardApi {

    @POST("user/get_profile")
    Single<JsonObject> apiGetProfile();

    @GET("city")
    Single<JsonObject> apiGetCity();

    @GET("coupon/get_offers")
    Single<JsonObject> apiGetOffers();

    @POST("bus/my_bookings")
    Single<JsonObject> apiMyBookings();

    @POST("payment/refund_payment")
    Single<JsonObject> apiRefundPayment(@Body HashMap hashMap);

}
