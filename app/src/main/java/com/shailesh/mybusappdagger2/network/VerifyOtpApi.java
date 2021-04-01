package com.shailesh.mybusappdagger2.network;

import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VerifyOtpApi {

    @POST("authentication/login_or_register")
    Single<JsonObject> apiVerifyOtp(@Body HashMap hashMap);

    @POST("authentication/generate_otp")
    Single<JsonObject> apiGenearateOtp(@Body HashMap hashMap);
}
