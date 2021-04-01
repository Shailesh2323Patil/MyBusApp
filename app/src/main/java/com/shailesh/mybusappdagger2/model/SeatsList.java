package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SeatsList implements Serializable
{
    @SerializedName("seats")
    public ArrayList<Seats> seats;
}
