package com.shailesh.mybusappdagger2.network;

public class Constant {

    public static final String Base_Url = "https://raigadshree-uat-api.herokuapp.com/";
    public static final String Base_Url_WebView = "https://raigadshree-uat.natureinfotech.com/";

    public static final String aboutUs = Base_Url_WebView+"webview/about-us";
    public static final String referToEarn = Base_Url_WebView+"webview/refer-to-earn";
    public static final String help = Base_Url_WebView+"webview/help";
    public static final String offers = Base_Url_WebView+"webview/offers";
    public static final String callSupport = Base_Url_WebView+"webview/call-support";
    public static final String privacyPolicy = Base_Url_WebView+"auth/privacy-policy";

    public static final String invoiceLink = Base_Url_WebView+"webview/generate-invoice?receipt_number=";

    public static final String smartCardLink = Base_Url_WebView+"webview/generate-smart-card?mobile=";
}
