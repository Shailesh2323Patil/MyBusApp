package com.shailesh.mybusappdagger2.ui.login;

import android.app.Application;
import android.view.View;
import com.google.gson.JsonObject;
import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.model.GenerateOtp;
import com.shailesh.mybusappdagger2.network.LoginApi;
import com.shailesh.mybusappdagger2.util.error_response.ErrorResponse;
import com.shailesh.mybusappdagger2.util.shared_preference.SharedPreferenceStorage;
import java.util.HashMap;
import javax.inject.Inject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Login_ViewModel extends AndroidViewModel
{
    private static final String TAG = "Login_ViewModel";

    SharedPreferenceStorage sharedPreferenceStorage;
    LoginApi loginApi;
    ErrorResponse errorResponse;

    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<GenerateOtp> mutableGenerateOtp = new MutableLiveData<GenerateOtp>();

    public MutableLiveData<String> mutableSmsKey = new MutableLiveData<String>();
    public MutableLiveData<String> mutableMobileNumber = new MutableLiveData<String>();

    public MutableLiveData<ApiResponceResource<GenerateOtp>> apiGenerateApiResponse = new MutableLiveData<ApiResponceResource<GenerateOtp>>();

    @Inject
    public Login_ViewModel(Application application,
                           LoginApi loginApi,
                           SharedPreferenceStorage sharedPreferenceStorage,
                           ErrorResponse errorResponse)
    {
        super(application);

        this.sharedPreferenceStorage = sharedPreferenceStorage;
        this.loginApi = loginApi;
        this.errorResponse = errorResponse;
    }

    public MutableLiveData<GenerateOtp> getMutableGenerateOtp()
    {
        if(mutableGenerateOtp == null)
        {
            mutableGenerateOtp = new MutableLiveData<GenerateOtp>();
        }
        return mutableGenerateOtp;
    }

    public void clickContinue(View view)
    {
        GenerateOtp generateOtp = new GenerateOtp(mutableMobileNumber.getValue(),"","0","",mutableSmsKey.getValue());
        mutableGenerateOtp.setValue(generateOtp);
    }

    public void apiGenerateOtp(final GenerateOtp generateOtp)
    {
        apiGenerateApiResponse.setValue(ApiResponceResource.loading((GenerateOtp) null));

        HashMap hashMap = new HashMap<String, Object>();
        hashMap.put("mobile_number",generateOtp.getMobile_number());
        hashMap.put("sms_key",generateOtp.getSmsKey());

        disposable.add(
                loginApi.apiGenearateOtp(hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<JsonObject>() {
                            @Override
                            public void onSuccess(@NonNull JsonObject jsonObject) {
                                apiGenerateApiResponse.setValue(ApiResponceResource.authenticated(generateOtp));
                            }

                            @Override
                            public void onError(@NonNull Throwable throwable) {
                                apiGenerateApiResponse.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable),(GenerateOtp) null));
                            }
                        })
        );
    }
}
