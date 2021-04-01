package com.shailesh.mybusappdagger2.ui.splash;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.model.AppVersion;
import com.shailesh.mybusappdagger2.network.SplashApi;
import com.shailesh.mybusappdagger2.util.error_response.ErrorResponse;
import com.shailesh.mybusappdagger2.util.shared_preference.SharedPreferenceStorage;
import org.apache.http.conn.ConnectTimeoutException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import javax.inject.Inject;
import javax.net.ssl.SSLHandshakeException;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Splash_ViewModel extends AndroidViewModel
{
    SharedPreferenceStorage sharedPreferenceStorage;
    SplashApi splashApi;
    ErrorResponse errorResponse;

    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<ApiResponceResource<AppVersion>> mutableAppVersion = new MutableLiveData<ApiResponceResource<AppVersion>>();

    @Inject
    public Splash_ViewModel(Application application,
                            SharedPreferenceStorage sharedPreferenceStorage,
                            SplashApi splashApi,
                            ErrorResponse errorResponse)
    {
        super(application);

        this.sharedPreferenceStorage = sharedPreferenceStorage;
        this.splashApi = splashApi;
        this.errorResponse = errorResponse;
    }

    public void apiSplash(String buildVersion)
    {

        mutableAppVersion.setValue(ApiResponceResource.loading((AppVersion) null));

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("build_version",buildVersion);

        disposable.add(
                splashApi.apiCheckVersion(hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<JsonObject>(){
                            @Override
                            public void onSuccess(@NonNull JsonObject jsonObject) {

                                JsonElement root = new JsonParser().parse(jsonObject.toString());
                                JsonObject jsonObject_1 = root.getAsJsonObject();

                                JsonObject jsonObject_2 = jsonObject_1.get("data").getAsJsonObject();

                                Gson gson = new Gson();
                                Type type = new TypeToken<AppVersion>(){}.getType();

                                AppVersion appVersion = gson.fromJson(jsonObject_2,type);

                                mutableAppVersion.setValue(ApiResponceResource.authenticated(appVersion));
                            }

                            @Override
                            public void onError(@NonNull Throwable throwable) {
                                mutableAppVersion.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable), (AppVersion) null));
                            }
                        }));
    }
}
