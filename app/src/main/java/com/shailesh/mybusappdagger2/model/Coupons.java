package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

public class Coupons
{
    @SerializedName("coupon_id")
    private Integer couponId;

    @SerializedName("coupon_type_id")
    private Integer couponTypeId;

    @SerializedName("coupon_code")
    private String couponCode;

    @SerializedName("coupon_type_name")
    private String couponTypeName;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("off_value")
    private Integer offValue;

    @SerializedName("is_percentage")
    private Integer isPercentage;

    @SerializedName("created_on")
    private String createdOn;

    @SerializedName("expired_on")
    private String expiredOn;

    @SerializedName("min_value")
    private Integer minValue;

    @SerializedName("max_used")
    private Integer maxUsed;

    @SerializedName("is_active")
    private Integer isActive;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getCouponTypeId() {
        return couponTypeId;
    }

    public void setCouponTypeId(Integer couponTypeId) {
        this.couponTypeId = couponTypeId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponTypeName() {
        return couponTypeName;
    }

    public void setCouponTypeName(String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOffValue() {
        return offValue;
    }

    public void setOffValue(Integer offValue) {
        this.offValue = offValue;
    }

    public Integer getIsPercentage() {
        return isPercentage;
    }

    public void setIsPercentage(Integer isPercentage) {
        this.isPercentage = isPercentage;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getExpiredOn() {
        return expiredOn;
    }

    public void setExpiredOn(String expiredOn) {
        this.expiredOn = expiredOn;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxUsed() {
        return maxUsed;
    }

    public void setMaxUsed(Integer maxUsed) {
        this.maxUsed = maxUsed;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

}