package com.shailesh.mybusappdagger2.ui.verify_otp;

import android.app.Application;
import android.view.View;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.model.VerifyOtp;
import com.shailesh.mybusappdagger2.network.VerifyOtpApi;
import com.shailesh.mybusappdagger2.util.JWTUtils;
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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class VerifyOtp_ViewModel extends AndroidViewModel
{
    SharedPreferenceStorage sharedPreferenceStorage;
    VerifyOtpApi verifyOtpApi;
    ErrorResponse errorResponse;

    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<ApiResponceResource<Boolean>> mutableVerifyOtpComplete = new MutableLiveData<ApiResponceResource<Boolean>>();
    public MutableLiveData<ApiResponceResource<Boolean>> mutableResendOtp = new MutableLiveData<ApiResponceResource<Boolean>>();

    public MutableLiveData<String> mutableMobileNumber = new MutableLiveData<String>();
    public MutableLiveData<String> mutableEmail = new MutableLiveData<String>();
    public MutableLiveData<String> mutableIsSocial = new MutableLiveData<String>();
    public MutableLiveData<String> mutableOtp = new MutableLiveData<String>();

    public MutableLiveData<String> mutableFullName = new MutableLiveData<String>();
    public MutableLiveData<String> mutableDOB = new MutableLiveData<String>();
    public MutableLiveData<String> mutableGender = new MutableLiveData<String>();

    public MutableLiveData<VerifyOtp> mutableVerifyOtp = new MutableLiveData<VerifyOtp>();

    @Inject
    public VerifyOtp_ViewModel(Application application,
                               SharedPreferenceStorage sharedPreferenceStorage,
                               VerifyOtpApi verifyOtpApi,
                               ErrorResponse errorResponse)
    {
        super(application);

        this.sharedPreferenceStorage = sharedPreferenceStorage;
        this.verifyOtpApi = verifyOtpApi;
        this.errorResponse = errorResponse;
    }

    public MutableLiveData<VerifyOtp> getMutableVerifyOtp()
    {
        if(mutableVerifyOtp == null)
        {
            mutableVerifyOtp = new MutableLiveData<VerifyOtp>();
        }
        return mutableVerifyOtp;
    }

    public void clickContinue(View view)
    {
        VerifyOtp verifyOtp = new VerifyOtp();
        verifyOtp.setMobile_number(mutableMobileNumber.getValue());
        verifyOtp.setEmail(mutableEmail.getValue());
        verifyOtp.setIs_social(mutableIsSocial.getValue());
        verifyOtp.setOtp(mutableOtp.getValue());
        verifyOtp.setFullName(mutableFullName.getValue());
        verifyOtp.setDob(mutableDOB.getValue());
        verifyOtp.setGender(mutableGender.getValue());
        verifyOtp.setRole_id("4");

        mutableVerifyOtp.setValue(verifyOtp);
    }

    public void apiVerifyOtp(VerifyOtp verifyOtp)
    {
        mutableVerifyOtpComplete.setValue(ApiResponceResource.loading((Boolean) null));

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("full_name",verifyOtp.getFullName());
        hashMap.put("mobile_number",verifyOtp.getMobile_number());
        hashMap.put("email_id",verifyOtp.getEmail());
        hashMap.put("date_of_birth",verifyOtp.getDob());
        hashMap.put("gender",verifyOtp.getGender());
        hashMap.put("is_social",verifyOtp.getIs_social());
        hashMap.put("role_id",verifyOtp.getRole_id());
        hashMap.put("otp",verifyOtp.getOtp());

        disposable.add(
                verifyOtpApi.apiVerifyOtp(hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<JsonObject>() {
                            @Override
                            public void onSuccess(JsonObject jsonObject) {
                                try
                                {
                                    JsonElement root = new JsonParser().parse(jsonObject.toString());
                                    JsonObject jsonObject_1 = root.getAsJsonObject();

                                    JsonObject jsonObjectData = jsonObject_1.get("data").getAsJsonObject();
                                    String token = jsonObjectData.get("token").getAsString();

                                    String body = JWTUtils.decodedBody(token);
                                    JsonObject bodyJson = new JsonParser().parse(body).getAsJsonObject();

                                    sharedPreferenceStorage.addInformation(sharedPreferenceStorage.userId,bodyJson.get("user_id").getAsString());
                                    sharedPreferenceStorage.addInformation(sharedPreferenceStorage.userMobile,bodyJson.get("mobile_number").getAsString());
                                    sharedPreferenceStorage.addInformation(sharedPreferenceStorage.userRoleId,bodyJson.get("role_id").getAsString());
                                    sharedPreferenceStorage.addInformation(sharedPreferenceStorage.authToken,token);

                                    mutableVerifyOtpComplete.setValue(ApiResponceResource.authenticated(true));
                                }
                                catch (Exception exception)
                                {
                                    mutableVerifyOtpComplete.setValue(ApiResponceResource.error(getApplication().getString(R.string.something_went_wrong),(Boolean) null));
                                }
                            }

                            @Override
                            public void onError(Throwable throwable)
                            {
                                mutableVerifyOtpComplete.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable),(Boolean) null));
                            }
                        })
        );
    }

    public void apiResendOtp(String mobileNumber,String smsKey)
    {
        mutableResendOtp.setValue(ApiResponceResource.loading((Boolean) null));

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("mobile_number",mobileNumber);
        hashMap.put("sms_key",smsKey);

        disposable.add(
                verifyOtpApi.apiGenearateOtp(hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<JsonObject>() {
                            @Override
                            public void onSuccess(JsonObject jsonObject) {
                                try
                                {
                                    JsonElement root = new JsonParser().parse(jsonObject.toString());
                                    mutableResendOtp.setValue(ApiResponceResource.authenticated(true));
                                }
                                catch (Exception exception)
                                {
                                    mutableResendOtp.setValue(ApiResponceResource.error(getApplication().getString(R.string.something_went_wrong),(Boolean) null));
                                }
                            }

                            @Override
                            public void onError(Throwable throwable)
                            {
                                mutableResendOtp.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable),(Boolean) null));
                            }
                        })
        );
    }
}
