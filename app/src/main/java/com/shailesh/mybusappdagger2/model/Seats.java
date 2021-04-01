package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Seats implements Serializable
{
    @SerializedName("bus_seat_transaction_id")
    String bus_seat_transaction_id;

    @SerializedName("status")
    String status;

    @SerializedName("number")
    String number;

    public String getBus_seat_transaction_id() {
        return bus_seat_transaction_id;
    }

    public void setBus_seat_transaction_id(String bus_seat_transaction_id) {
        this.bus_seat_transaction_id = bus_seat_transaction_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
