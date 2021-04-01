package com.shailesh.mybusappdagger2.network;

import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SplashApi {

    @POST("authentication/check_version")
    Single<JsonObject> apiCheckVersion(@Body HashMap hashMap);

}
