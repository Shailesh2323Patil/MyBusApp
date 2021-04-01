package com.shailesh.mybusappdagger2.di;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.shailesh.mybusappdagger2.network.Constant;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.error_response.ErrorResponse;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;
import com.shailesh.mybusappdagger2.util.shared_preference.SharedPreferenceStorage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(OkHttpClient okHttpClient)
    {
        return new Retrofit.Builder()
                .baseUrl(Constant.Base_Url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(final SharedPreferenceStorage sharedPreferences)
    {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");

                // Adding Authorization token (API Key)
                // Requests will be denied without API key

                if (!TextUtils.isEmpty(sharedPreferences.getInformation("authToken")))
                {
                    requestBuilder.addHeader("Authorization","Bearer "+sharedPreferences.getInformation("authToken"));
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return httpClient.build();
    }

    @Singleton
    @Provides
    static SharedPreferenceStorage provideSharedPreferenceStorage(Application application)
    {
        return new SharedPreferenceStorage(application);
    }

    @Singleton
    @Provides
    static ErrorResponse provideErrorResponse(Application application)
    {
        return new ErrorResponse(application);
    }
}
