package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Dropping implements Serializable
{
    @SerializedName("bus_dropping_id")
    String bus_dropping_id;

    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    @SerializedName("dropping_time")
    String dropping_time;

    @SerializedName("original_price")
    String original_price;

    @SerializedName("discount_price")
    String discount_price;

    public String getBus_dropping_id() {
        return bus_dropping_id;
    }

    public void setBus_dropping_id(String bus_dropping_id) {
        this.bus_dropping_id = bus_dropping_id;
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

    public String getDropping_time() {
        return dropping_time;
    }

    public void setDropping_time(String dropping_time) {
        this.dropping_time = dropping_time;
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
