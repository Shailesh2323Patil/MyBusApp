package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Booking implements Serializable
{
    @SerializedName("name")
    String name;

    @SerializedName("travelling_date")
    String travelling_date;

    @SerializedName("boardig_name")
    String boardig_name;

    @SerializedName("cancel_available")
    String cancel_available;

    @SerializedName("ticket_number")
    String ticket_number;

    @SerializedName("final_amout")
    String final_amout;

    @SerializedName("payment_status")
    String payment_status;

    @SerializedName("seats")
    ArrayList<Seats> seats;

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

    public ArrayList<Seats> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seats> seats) {
        this.seats = seats;
    }
}