package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CouponVerify implements Serializable
{
    @SerializedName("coupon_id")
    String coupon_id;

    @SerializedName("is_available")
    String is_available;

    @SerializedName("is_percentage")
    String is_percentage;

    @SerializedName("off_value")
    String off_value;

    @SerializedName("is_return")
    String is_return;

    String isReturnCouponCode;

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getIs_available() {
        return is_available;
    }

    public void setIs_available(String is_available) {
        this.is_available = is_available;
    }

    public String getIs_percentage() {
        return is_percentage;
    }

    public void setIs_percentage(String is_percentage) {
        this.is_percentage = is_percentage;
    }

    public String getOff_value() {
        return off_value;
    }

    public void setOff_value(String off_value) {
        this.off_value = off_value;
    }

    public String getIs_return() {
        return is_return;
    }

    public void setIs_return(String is_return) {
        this.is_return = is_return;
    }

    public String getIsReturnCouponCode() {
        return isReturnCouponCode;
    }

    public void setIsReturnCouponCode(String isReturnCouponCode) {
        this.isReturnCouponCode = isReturnCouponCode;
    }
}
