package com.shailesh.mybusappdagger2.ui.dashboard.my_bookings;

import android.app.Application;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.network.MyBookingDetailsApi;
import com.shailesh.mybusappdagger2.util.error_response.ErrorResponse;
import com.shailesh.mybusappdagger2.util.shared_preference.SharedPreferenceStorage;
import java.util.HashMap;
import javax.inject.Inject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class My_Booking_Details_ViewModel extends AndroidViewModel
{
    @Inject
    SharedPreferenceStorage sharedPreferenceStorage;

    @Inject
    MyBookingDetailsApi myBookingDetailsApi;

    @Inject
    ErrorResponse errorResponse;

    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<ApiResponceResource<String>> mutableRefundId = new MutableLiveData<ApiResponceResource<String>>();

    @Inject
    public My_Booking_Details_ViewModel(Application application,
                                        MyBookingDetailsApi myBookingDetailsApi,
                                        SharedPreferenceStorage sharedPreferenceStorage,
                                        ErrorResponse errorResponse)
    {
        super(application);

        this.myBookingDetailsApi = myBookingDetailsApi;
        this.sharedPreferenceStorage = sharedPreferenceStorage;
        this.errorResponse = errorResponse;
    }

    public void apiRefundBooking(String receipt_number,String amount)
    {
        mutableRefundId.setValue(ApiResponceResource.loading((String) null));

        HashMap hashMap = new HashMap();
        hashMap.put("receipt_number",receipt_number);
        hashMap.put("amount",amount);

        disposable.add(
                myBookingDetailsApi
                        .apiRefundPayment(hashMap)
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
}
