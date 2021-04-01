package com.shailesh.mybusappdagger2.ui.dashboard;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.model.City;
import com.shailesh.mybusappdagger2.model.Coupons;
import com.shailesh.mybusappdagger2.model.MyBooking;
import com.shailesh.mybusappdagger2.network.DashboardApi;
import com.shailesh.mybusappdagger2.util.error_response.ErrorResponse;
import com.shailesh.mybusappdagger2.util.shared_preference.SharedPreferenceStorage;
import org.apache.http.conn.ConnectTimeoutException;

import java.lang.reflect.Type;
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

public class Dashboard_ViewModel extends AndroidViewModel
{
    DashboardApi dashboardApi;
    SharedPreferenceStorage sharedPreferenceStorage;
    ErrorResponse errorResponse;

    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<ApiResponceResource<Boolean>> mutableShowProfileBox = new MutableLiveData<ApiResponceResource<Boolean>>();

    public MutableLiveData<ApiResponceResource<ArrayList<City>>> mutableArrayListCity = new MutableLiveData<ApiResponceResource<ArrayList<City>>>();
    public MutableLiveData<ApiResponceResource<ArrayList<Coupons>>> mutableCouponsList = new MutableLiveData<ApiResponceResource<ArrayList<Coupons>>>();

    public MutableLiveData<ApiResponceResource<Boolean>> mutableCompleteMyAccount = new MutableLiveData<ApiResponceResource<Boolean>>();

    public MutableLiveData<ApiResponceResource<ArrayList<MyBooking>>> mutableBookingList = new MutableLiveData<ApiResponceResource<ArrayList<MyBooking>>>();

    public MutableLiveData<ApiResponceResource<String>> mutableRefundId = new MutableLiveData<ApiResponceResource<String>>();

    public MutableLiveData<ApiResponceResource<ArrayList<Coupons>>> mutableCouponsListOffers = new MutableLiveData<ApiResponceResource<ArrayList<Coupons>>>();

    @Inject
    public Dashboard_ViewModel(Application application,
                               DashboardApi dashboardApi,
                               SharedPreferenceStorage sharedPreferenceStorage,
                               ErrorResponse errorResponse)
    {
        super(application);

        this.dashboardApi = dashboardApi;
        this.sharedPreferenceStorage = sharedPreferenceStorage;
        this.errorResponse = errorResponse;
    }

    public void apiGetProfile()
    {
        mutableShowProfileBox.setValue(ApiResponceResource.loading((Boolean) null));

        disposable.add(
                dashboardApi.apiGetProfile()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<JsonObject>() {
                            @Override
                            public void onSuccess(JsonObject jsonObject) {
                                try {
                                    JsonElement root = new JsonParser().parse(jsonObject.toString());

                                    JsonObject jsonObject_1 = root.getAsJsonObject();

                                    JsonObject jsonObject_Data = jsonObject_1.get("data").getAsJsonObject();

                                    JsonObject jsonObject_User = jsonObject_Data.get("user").getAsJsonObject();

                                    sharedPreferenceStorage.saveProfile(jsonObject_User);

                                    String fullName = jsonObject_User.get("full_name").getAsString();
                                    String unicode = jsonObject_User.get("unicode").getAsString();

                                    if(fullName.equals("") || unicode.equals(""))
                                    {
                                        mutableShowProfileBox.setValue(ApiResponceResource.loading(true));
                                    }
                                }
                                catch (Exception exception)
                                {
                                    mutableShowProfileBox.setValue(ApiResponceResource.error(getApplication().getString(R.string.something_went_wrong),(Boolean) null));
                                }
                            }

                            @Override
                            public void onError(Throwable throwable)
                            {
                                mutableShowProfileBox.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable),(Boolean) null));
                            }
                        })
        );
    }

    public void apiGetCity()
    {
        mutableArrayListCity.setValue(ApiResponceResource.loading((ArrayList<City>) null));

        disposable.add(
                dashboardApi.apiGetCity()
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

                                    JsonArray jsonArray_Cities = jsonObject_Data.get("cities").getAsJsonArray();

                                    Gson gson = new Gson();
                                    Type type = new TypeToken<ArrayList<City>>(){}.getType();

                                    ArrayList<City> arrayList = gson.fromJson(jsonArray_Cities,type);

                                    mutableArrayListCity.setValue(ApiResponceResource.authenticated(arrayList));
                                }
                                catch (Exception exception)
                                {
                                    mutableArrayListCity.setValue(ApiResponceResource.error(getApplication()
                                            .getString(R.string.something_went_wrong),(ArrayList<City>) null));
                                }
                            }

                            @Override
                            public void onError(Throwable throwable)
                            {
                                mutableArrayListCity.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable),
                                        (ArrayList<City>) null));
                            }
                        })
        );
    }

    public void apiGetOffers()
    {
        mutableCouponsList.setValue(ApiResponceResource.loading((ArrayList<Coupons>) null));

        String token = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.authToken);

        disposable.add(
                dashboardApi.apiGetOffers()
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

                                    JsonArray jsonArray_Coupons = jsonObject_Data.get("coupons").getAsJsonArray();

                                    Gson gson = new Gson();
                                    Type type_coupons = new TypeToken<ArrayList<Coupons>>(){}.getType();

                                    ArrayList<Coupons> arrayList = gson.fromJson(jsonArray_Coupons,type_coupons);

                                    mutableCouponsList.setValue(ApiResponceResource.authenticated(arrayList));
                                }
                                catch (Exception exception)
                                {
                                    mutableCouponsList.setValue(ApiResponceResource.error(getApplication().getString(R.string.something_went_wrong),(ArrayList<Coupons>) null));
                                }
                            }

                            @Override
                            public void onError(Throwable throwable)
                            {
                                mutableCouponsList.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable),(ArrayList<Coupons>) null));
                            }
                        })
        );
    }

    public void apiGetProfile_MyAccount()
    {
        mutableCompleteMyAccount.setValue(ApiResponceResource.loading((Boolean) null));

        disposable.add(
                dashboardApi.apiGetProfile()
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

                                    mutableCompleteMyAccount.setValue(ApiResponceResource.authenticated(true));
                                }
                                catch (Exception exception)
                                {
                                    mutableCompleteMyAccount.setValue(ApiResponceResource.error(getApplication().getString(R.string.something_went_wrong),(Boolean) null));
                                }
                            }

                            @Override
                            public void onError(Throwable throwable)
                            {
                                mutableCompleteMyAccount.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable),(Boolean) null));
                            }
                        })
        );
    }

    public void apiGetMyBookings()
    {
        mutableBookingList.setValue(ApiResponceResource.loading((ArrayList<MyBooking>) null));

        String token = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.authToken);

        disposable.add(
                dashboardApi.apiMyBookings()
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

                                    JsonArray jsonArray_Booking = jsonObject_Data.get("booking").getAsJsonArray();

                                    String cancellation_message = jsonObject_Data.get("cancellation_message").getAsString();

                                    Gson gson = new Gson();
                                    Type type_booking = new TypeToken<ArrayList<MyBooking>>(){}.getType();

                                    ArrayList<MyBooking> arrayList = gson.fromJson(jsonArray_Booking,type_booking);

                                    mutableBookingList.setValue(ApiResponceResource.authenticated(arrayList));
                                }
                                catch (Exception exception)
                                {
                                    mutableCompleteMyAccount.setValue(ApiResponceResource.error(getApplication().getString(R.string.something_went_wrong),(Boolean) null));
                                }
                            }

                            @Override
                            public void onError(Throwable throwable)
                            {
                                mutableCompleteMyAccount.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable),(Boolean) null));
                            }
                        })
        );
    }

    public void apiRefundBooking(String receipt_number,String amount)
    {
        mutableRefundId.setValue(ApiResponceResource.loading((String) null));

        HashMap hashMap = new HashMap();
        hashMap.put("receipt_number",receipt_number);
        hashMap.put("amount",amount);

        disposable.add(
                dashboardApi.apiRefundPayment(hashMap)
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

                                    mutableRefundId.setValue(ApiResponceResource.authenticated(jsonObject_Data.get("refund_receipt_number").getAsString()));

                                }
                                catch (Exception exception)
                                {
                                    mutableRefundId.setValue(ApiResponceResource.error(getApplication().getString(R.string.something_went_wrong),(String) null));
                                }
                            }

                            @Override
                            public void onError(Throwable throwable)
                            {
                                mutableRefundId.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable),(String) null));
                            }
                        })
        );
    }

    public void apiGetOffers_Offers()
    {
        mutableCouponsListOffers.setValue(ApiResponceResource.loading((ArrayList<Coupons>) null));

        String token = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.authToken);

        disposable.add(
                dashboardApi.apiGetOffers()
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

                                    JsonArray jsonArray_Coupons = jsonObject_Data.get("coupons").getAsJsonArray();

                                    Gson gson = new Gson();
                                    Type type_coupons = new TypeToken<ArrayList<Coupons>>(){}.getType();

                                    ArrayList<Coupons> arrayList = gson.fromJson(jsonArray_Coupons,type_coupons);

                                    mutableCouponsListOffers.setValue(ApiResponceResource.authenticated(arrayList));
                                }
                                catch (Exception exception)
                                {
                                    mutableCouponsListOffers.setValue(ApiResponceResource.error(getApplication().getString(R.string.something_went_wrong),(ArrayList<Coupons>) null));
                                }
                            }

                            @Override
                            public void onError(Throwable throwable)
                            {
                                mutableCouponsListOffers.setValue(ApiResponceResource.error(errorResponse.errorResponse(throwable),(ArrayList<Coupons>) null));
                            }
                        })
        );
    }
}
