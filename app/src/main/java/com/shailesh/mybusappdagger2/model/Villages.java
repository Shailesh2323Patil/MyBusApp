package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Villages implements Serializable
{
    @SerializedName("village_id")
    String village_id;

    @SerializedName("route_id")
    String route_id;

    @SerializedName("name")
    String name;

    @SerializedName("code")
    String code;

    public String getVillage_id() {
        return village_id;
    }

    public void setVillage_id(String village_id) {
        this.village_id = village_id;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
