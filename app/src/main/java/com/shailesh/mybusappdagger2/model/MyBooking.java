package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MyBooking implements Serializable
{
    @SerializedName("name")
    String name;

    @SerializedName("travelling_date")
    String travelling_date;

    @SerializedName("boarding_name")
    String boardig_name;

    @SerializedName("dropping_name")
    String dropping_name;

    @SerializedName("cancel_available")
    String cancel_available;

    @SerializedName("ticket_number")
    String ticket_number;

    @SerializedName("final_amout")
    String final_amout;

    @SerializedName("payment_status")
    String payment_status;

    @SerializedName("refund_receipt_number")
    String refund_id;

    @SerializedName("refund_receipt_date")
    String refund_receipt_date;

    @SerializedName("boarding_time")
    String boarding_time;

    @SerializedName("dropping_time")
    String dropping_time;

    @SerializedName("req_return_coupon")
    String req_return_coupon;

    @SerializedName("seats")
    ArrayList<MySeats> seats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTravelling_date() {
        return travelling_date;
    }

    public void setTravelling_date(String travelling_date) {
        this.travelling_date = travelling_date;
    }

    public String getBoardig_name() {
        return boardig_name;
    }

    public void setBoardig_name(String boardig_name) {
        this.boardig_name = boardig_name;
    }

    public String getCancel_available() {
        return cancel_available;
    }

    public void setCancel_available(String cancel_available) {
        this.cancel_available = cancel_available;
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
    }

    public String getFinal_amout() {
        return final_amout;
    }

    public void setFinal_amout(String final_amout) {
        this.final_amout = final_amout;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public ArrayList<MySeats> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<MySeats> seats) {
        this.seats = seats;
    }

    public String getDropping_name() {
        return dropping_name;
    }

    public void setDropping_name(String dropping_name) {
        this.dropping_name = dropping_name;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public String getRefund_receipt_date() {
        return refund_receipt_date;
    }

    public void setRefund_receipt_date(String refund_receipt_date) {
        this.refund_receipt_date = refund_receipt_date;
    }

    public String getBoarding_time() {
        return boarding_time;
    }

    public void setBoarding_time(String boarding_time) {
        this.boarding_time = boarding_time;
    }

    public String getDropping_time() {
        return dropping_time;
    }

    public void setDropping_time(String dropping_time) {
        this.dropping_time = dropping_time;
    }

    public String getReq_return_coupon() {
        return req_return_coupon;
    }

    public void setReq_return_coupon(String req_return_coupon) {
        this.req_return_coupon = req_return_coupon;
    }
}