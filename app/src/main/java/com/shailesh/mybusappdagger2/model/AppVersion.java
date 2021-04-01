package com.shailesh.mybusappdagger2.model;

import com.google.gson.annotations.SerializedName;

public class AppVersion
{
    @SerializedName("version")
    String version;

    @SerializedName("url")
    String url;

    @SerializedName("description")
    String description;

    @SerializedName("stable_version")
    Boolean stable_version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStable_version() {
        return stable_version;
    }

    public void setStable_version(Boolean stable_version) {
        this.stable_version = stable_version;
    }
}
