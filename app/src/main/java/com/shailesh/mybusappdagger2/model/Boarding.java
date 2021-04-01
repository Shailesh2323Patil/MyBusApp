package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Boarding implements Serializable
{
    @SerializedName("bus_boarding_id")
    String bus_boarding_id;

    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    @SerializedName("mobile_numbers")
    String mobile_numbers;

    @SerializedName("boarding_time")
    String boarding_time;

    @SerializedName("original_price")
    String original_price;

    @SerializedName("discount_price")
    String discount_price;

    public String getBus_boarding_id() {
        return bus_boarding_id;
    }

    public void setBus_boarding_id(String bus_boarding_id) {
        this.bus_boarding_id = bus_boarding_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile_numbers() {
        return mobile_numbers;
    }

    public void setMobile_numbers(String mobile_numbers) {
        this.mobile_numbers = mobile_numbers;
    }

    public String getBoarding_time() {
        return boarding_time;
    }

    public void setBoarding_time(String boarding_time) {
        this.boarding_time = boarding_time;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }
}
