package com.shailesh.mybusappdagger2.util.shared_preference;

import android.content.Context;

import com.google.gson.JsonObject;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.util.DateFormatCls;
import com.shailesh.mybusappdagger2.util.PrintMessage;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

public class SharedPreferenceStorage
{
    private Context context;

    public String TAG = "SharedPreferenceStorage";

    public String NoInfo = "";

    public String SharedPreference = "Storage";

    /* Referesh Time */
    public long sharedPreferenceTime = 5 * 60 * 1000 * 1000 * 1000L;
    public String refreshTimeChooseDisease = "refreshTimeChooseDisease";
    /* Referesh Time */

    public String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public String authToken = "authToken";
    public String firebaseToken = "firebaseToken";
    public String userId = "userId";
    public String userName = "userName";
    public String userMobile = "userMobile";
    public String userEmail = "userEmail";
    public String userRoleId = "userRoleId";
    public String userUniqueCode = "userUniqueCode";
    public String userDOB = "userDOB";
    public String userAge = "userAge";
    public String userGender = "userGender";
    public String userRouteId = "userRouteId";
    public String userRouteName = "userRouteName";
    public String userVillageId = "userVillageId";
    public String userVillageName = "userVillageName";
    public String userAreaId = "userAreaId";
    public String userAreaName = "userAreaName";
    public String userProfilePic = "userProfilePic";

    public String returnJourneyMessage = "returnJourneyMessage";

    public String selectUserBoarding = "selectUserBoarding";
    public String selectUserDropping = "selectUserDropping";
    public String selectPassengerInfo = "selectPassengerInfo";
    public String selectContactEmail = "selectContactEmail";
    public String selectContactMobile = "selectContactMobile";
    public String selectTotalDiscountAmt = "selectTotalDiscountAmt";
    public String selectTotalOriginalAmt = "selectTotalOriginalAmt";
    public String selectFinalAmt = "selectFinalAmt";
    public String selectBusName = "selectBusName";
    public String selectBusId = "selectBusId";
    public String selectBusArrivalTime = "selectBusArrivalTime";
    public String selectBusDepartTime = "selectBusDepartTime";
    public String selectBusTravelingDate = "selectBusTravelingDate";
    public String selectBusTravelingEndDate = "selectBusTravelingEndDate";
    public String selectBusFromCity = "selectBusFromCity";
    public String selectBusToCity = "selectBusToCity";
    public String selectReceiptNumber = "selectReceiptNumber";
    public String selectCouponId = "selectCouponId";
    public String selectCouponAmount = "selectCouponAmount";
    public String selectIsCouponApplied = "selectIsCouponApplied";
    public String selectIsPercentage = "selectIsPercentage";
    public String selectIsReturn = "selectIsReturn";
    public String selectIsReturnCouponCode = "selectIsReturnCouponCode";
    public String selectIsReturnCouponCodeAmt = "selectIsReturnCouponCodeAmt";


    public SharedPreferenceStorage(Context context)
    {
        this.context = context;
    }

    public void addInformation(String key, String value)
    {
        try
        {
            context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE)
                    .edit().putString(key,value).commit();
        }
        catch (Exception e)
        {
            PrintMessage.logE(TAG,"Error "+e.getMessage());
        }
    }

    public void removeInformation(String key)
    {
        try
        {
            context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(key).commit();
        }
        catch (Exception e)
        {
            PrintMessage.logE(TAG,"Error "+e.getMessage());
        }
    }

    public String getInformation(String key)
    {
        String value = new String();

        try
        {
            value = context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE)
                    .getString(key,NoInfo);
        }
        catch (Exception e)
        {
            PrintMessage.logE(TAG,"Error "+e.getMessage());
        }

        return value;
    }

    public void clearStorage()
    {
        /* This Line to Clear all Storage */
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().clear().commit();
    }

    public void saveProfile(JsonObject jsonObject)
    {
        try
        {
            String userId = jsonObject.get("user_id").getAsString();
            String fullName = jsonObject.get("full_name").getAsString();
            String mobileNumber = jsonObject.get("mobile_number").getAsString();
            String emailId = jsonObject.get("email_id").getAsString();
            String DOB = jsonObject.get("date_of_birth").getAsString();
            String gender = jsonObject.get("gender").getAsString();
            String roleId = jsonObject.get("role_id").getAsString();
            String unicode = jsonObject.get("unicode").getAsString();
            String route_id = jsonObject.get("route_id").getAsString();
            String route_name = jsonObject.get("route_name").getAsString();
            String village_id = jsonObject.get("village_id").getAsString();
            String village_name = jsonObject.get("village_name").getAsString();
            String area_id = jsonObject.get("area_id").getAsString();
            String area_name = jsonObject.get("area_name").getAsString();
            String return_journey_message = jsonObject.get("return_message").getAsString();
            String profilePic = jsonObject.get("profile_pic").getAsString();

            try
            {
                if(DateFormatCls.isDateValid(DOB) && !DOB.equals("0000-00-00"))
                {
                    Date date = DateFormatCls.inputFormat.parse(DOB);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    Integer day = calendar.get(Calendar.DAY_OF_MONTH);
                    Integer month = calendar.get(Calendar.MONTH);
                    Integer year = calendar.get(Calendar.YEAR);

                    Integer age = DateFormatCls.getAge(year,month,day);

                    this.addInformation(this.userAge,String.valueOf(age));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            if(gender.equals("1"))
            {
                this.addInformation(this.userGender,context.getString(R.string.male));
            }
            else if(gender.equals("2"))
            {
                this.addInformation(this.userGender,context.getString(R.string.female));
            }

            this.addInformation(this.userId,userId);
            this.addInformation(this.userName,fullName);
            this.addInformation(this.userMobile,mobileNumber);
            this.addInformation(this.userEmail,emailId);
            this.addInformation(this.userDOB,DOB);
            this.addInformation(this.userRoleId,roleId);
            this.addInformation(this.userUniqueCode,unicode);

            this.addInformation(this.userRouteId,route_id);
            this.addInformation(this.userRouteName,route_name);
            this.addInformation(this.userVillageId,village_id);
            this.addInformation(this.userVillageName,village_name);
            this.addInformation(this.userAreaId,area_id);
            this.addInformation(this.userAreaName,area_name);
            this.addInformation(this.returnJourneyMessage,return_journey_message);
            this.addInformation(this.userProfilePic,profilePic);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void clearBusSelectData()
    {
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectUserBoarding).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectUserDropping).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectPassengerInfo).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectContactEmail).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectContactMobile).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectTotalDiscountAmt).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectTotalOriginalAmt).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectFinalAmt).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectBusName).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectBusId).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectBusArrivalTime).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectBusDepartTime).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectBusTravelingDate).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectBusTravelingEndDate).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectBusFromCity).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectBusToCity).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectReceiptNumber).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectCouponId).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectCouponAmount).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectIsCouponApplied).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectIsPercentage).commit();
        context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE).edit().remove(selectIsReturn).commit();
    }
}
