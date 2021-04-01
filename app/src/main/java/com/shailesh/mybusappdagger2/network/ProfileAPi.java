package com.shailesh.mybusappdagger2.network;

import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProfileAPi {
    @POST("user/save_profile")
    Single<JsonObject> apiSaveProfile(@Body HashMap hashMap);
}
