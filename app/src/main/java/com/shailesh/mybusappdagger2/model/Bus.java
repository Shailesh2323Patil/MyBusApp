package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Bus implements Serializable
{
    @SerializedName("bus_id")
    String bus_id;

    @SerializedName("bus_transaction_id")
    String bus_transaction_id;

    @SerializedName("name")
    String name;

    @SerializedName("brand")
    String brand;

    @SerializedName("number")
    String number;

    @SerializedName("default_price")
    String default_price;

    @SerializedName("original_price")
    String original_price;

    @SerializedName("discount_price")
    String discount_price;

    @SerializedName("original_group_price")
    String original_group_price;

    @SerializedName("discount_group_price")
    String discount_group_price;

    @SerializedName("travelling_date")
    String travelling_date;

    @SerializedName("type")
    String type;

    @SerializedName("start_time")
    String start_time;

    @SerializedName("end_time")
    String end_time;

    @SerializedName("seats")
    ArrayList<Seats> seats[];

    @SerializedName("boardings")
    ArrayList<Boarding> boardings;

    @SerializedName("droppings")
    ArrayList<Dropping> droppings;

    @SerializedName("available_seat")
    String available_seat;

    String fromCity;

    String toCity;

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getBus_transaction_id() {
        return bus_transaction_id;
    }

    public void setBus_transaction_id(String bus_transaction_id) {
        this.bus_transaction_id = bus_transaction_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getOriginal_group_price() {
        return original_group_price;
    }

    public void setOriginal_group_price(String original_group_price) {
        this.original_group_price = original_group_price;
    }

    public String getDiscount_group_price() {
        return discount_group_price;
    }

    public void setDiscount_group_price(String discount_group_price) {
        this.discount_group_price = discount_group_price;
    }

    public String getTravelling_date() {
        return travelling_date;
    }

    public void setTravelling_date(String travelling_date) {
        this.travelling_date = travelling_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Boarding> getBoardings() {
        return boardings;
    }

    public void setBoardings(ArrayList<Boarding> boardings) {
        this.boardings = boardings;
    }

    public ArrayList<Dropping> getDroppings() {
        return droppings;
    }

    public void setDroppings(ArrayList<Dropping> droppings) {
        this.droppings = droppings;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public ArrayList<Seats>[] getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seats>[] seats) {
        this.seats = seats;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getDefault_price() {
        return default_price;
    }

    public void setDefault_price(String default_price) {
        this.default_price = default_price;
    }

    public String getAvailable_seat() {
        return available_seat;
    }

    public void setAvailable_seat(String available_seat) {
        this.available_seat = available_seat;
    }
}
