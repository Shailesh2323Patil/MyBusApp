package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GenerateOrderData implements Serializable
{
    @SerializedName("id")
    String id;

    @SerializedName("entity")
    String entity;

    @SerializedName("amount")
    String amount;

    @SerializedName("amount_paid")
    String amount_paid;

    @SerializedName("amount_due")
    String amount_due;

    @SerializedName("currency")
    String currency;

    @SerializedName("receipt")
    String receipt;

    @SerializedName("status")
    String status;

    @SerializedName("attempts")
    String attempts;

    @SerializedName("created_at")
    String created_at;

    @SerializedName("razorpay_key")
    String razorpay_key;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getAmount_due() {
        return amount_due;
    }

    public void setAmount_due(String amount_due) {
        this.amount_due = amount_due;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttempts() {
        return attempts;
    }

    public void setAttempts(String attempts) {
        this.attempts = attempts;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getRazorpay_key() {
        return razorpay_key;
    }

    public void setRazorpay_key(String razorpay_key) {
        this.razorpay_key = razorpay_key;
    }
}
