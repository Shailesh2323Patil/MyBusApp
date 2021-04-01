package com.shailesh.mybusappdagger2.model;

import java.io.Serializable;

public class GenerateOtp implements Serializable
{
    String mobile_number;
    String email;
    String is_social;
    String name;
    String smsKey;

    public GenerateOtp(String mobile_number, String email, String is_social, String name,String smsKey)
    {
        this.mobile_number = mobile_number;
        this.email = email;
        this.is_social = is_social;
        this.name = name;
        this.smsKey = smsKey;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIs_social() {
        return is_social;
    }

    public void setIs_social(String is_social) {
        this.is_social = is_social;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmsKey() {
        return smsKey;
    }

    public void setSmsKey(String smsKey) {
        this.smsKey = smsKey;
    }
}
