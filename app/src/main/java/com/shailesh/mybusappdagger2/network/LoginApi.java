package com.shailesh.mybusappdagger2.network;

import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi
{
    @POST("authentication/generate_otp")
    Single<JsonObject> apiGenearateOtp(@Body HashMap hashMap);

}
