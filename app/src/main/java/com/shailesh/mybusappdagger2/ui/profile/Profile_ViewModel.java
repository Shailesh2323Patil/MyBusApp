package com.shailesh.mybusappdagger2.ui.profile;

import android.app.Application;
import android.view.View;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.model.Areas;
import com.shailesh.mybusappdagger2.model.Profile;
import com.shailesh.mybusappdagger2.model.Routes;
import com.shailesh.mybusappdagger2.model.Villages;
import com.shailesh.mybusappdagger2.network.ProfileAPi;
import com.shailesh.mybusappdagger2.util.error_response.ErrorResponse;
import com.shailesh.mybusappdagger2.util.shared_preference.SharedPreferenceStorage;
import org.apache.http.conn.ConnectTimeoutException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
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

public class Profile_ViewModel extends AndroidViewModel
{
    @Inject
    SharedPreferenceStorage sharedPreferenceStorage;

    @Inject
    ProfileAPi profileAPi;

    @Inject
    ErrorResponse errorResponse;

    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> mutableName = new MutableLiveData<String>();
    public MutableLiveData<String> mutableDOB = new MutableLiveData<String>();
    public MutableLiveData<String> mutableGender = new MutableLiveData<String>();
    public MutableLiveData<String> mutableEmail = new MutableLiveData<String>();
    public MutableLiveData<String> mutableMobile = new MutableLiveData<String>();
    public MutableLiveData<String> mutableUniqueCode = new MutableLiveData<String>();
    public MutableLiveData<String> mutableRouteId = new MutableLiveData<String>();
    public MutableLiveData<String> mutableVillageId = new MutableLiveData<String>();
    public MutableLiveData<String> mutableAreaId = new MutableLiveData<String>();
    public MutableLiveData<Profile> mutableProfile = new MutableLiveData<Profile>();

    public MutableLiveData<ApiResponceResource<String>> mutableMessageProfile = new MutableLiveData<ApiResponceResource<String>>();

    @Inject
    public Profile_ViewModel(Application application,
                             SharedPreferenceStorage sharedPreferenceStorage,
                             ProfileAPi profileAPi,
                             ErrorResponse errorResponse)
    {
        super(application);

        this.sharedPreferenceStorage = sharedPreferenceStorage;
        this.profileAPi = profileAPi;
        this.errorResponse = errorResponse;
    }

    public MutableLiveData<Profile> getProfile()
    {
        if(mutableProfile != null)
        {
            mutableProfile = new MutableLiveData<Profile>();
        }
        return mutableProfile;
    }

    public void onClickListner(View view)
    {
        Profile profile = new Profile();

        profile.setFull_name(mutableName.getValue());
        profile.setDate_of_birth(mutableDOB.getValue());
        profile.setGender(mutableGender.getValue());
        profile.setEmail_id(mutableEmail.getValue());
        profile.setMobile(mutableMobile.getValue());
        profile.setRoute_id(mutableRouteId.getValue());
        profile.setVillage_id(mutableVillageId.getValue());
        profile.setArea_id(mutableAreaId.getValue());

        mutableProfile.setValue(profile);
    }

    public void apiSaveProfile(Profile profile)
    {
        mutableMessageProfile.setValue(ApiResponceResource.loading((String) null));

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("user_id",sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userId));
        hashMap.put("full_name",profile.getFull_name());
        hashMap.put("email_id",profile.getEmail_id());
        hashMap.put("date_of_birth",profile.getDate_of_birth());
        hashMap.put("gender",profile.getGender());
        hashMap.put("village_id",profile.getVillage_id());
        hashMap.put("area_id",profile.getArea_id());
        hashMap.put("profile_pic",profile.getProfile_pic());

        disposable.add(
                profileAPi.apiSaveProfile(hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<JsonObject>() {
                            @Override
                            public void onSuccess(JsonObject jsonObject) {
                                try
                                {
                                    JsonElement root = new JsonParser().parse(jsonObject.toString());

                                    JsonObject jsonObject_1 = root.getAsJsonObject();

                                    JsonObject jsonObject_Data = jsonObject_1.get("data").getAsJsonObject();

                                    JsonObject jsonObject_User = jsonObject_Data.get("user").getAsJsonObject();

                                    sharedPreferenceStorage.saveProfile(jsonObject_User);

                                    mutableMessageProfile.setValue(ApiResponceResource.authenticated(getApplication().getString(R.string.profile_updated_successfully)));
                                }
                                catch (Exception exception)
                                {
                                    mutableMessageProfile.setValue(ApiResponceResource.error(getApplication().getString(R.string.something_went_wrong),(String) null));
                                }
                            }

                            @Override
                            public void onError(Throwable throwable)
                            {
                                mutableMessageProfile.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable),(String) null));
                            }
                        })
        );
    }
}
