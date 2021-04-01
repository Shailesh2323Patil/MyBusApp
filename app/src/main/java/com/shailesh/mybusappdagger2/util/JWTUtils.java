package com.shailesh.mybusappdagger2.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class JWTUtils
{

    public static String decodedHeader(String JWTEncoded) throws Exception
    {
        try
        {
            String[] split = JWTEncoded.split("\\.");
//            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
//            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
            return getJson(split[0]);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String decodedBody(String JWTEncoded) throws Exception
    {
        try
        {
            String[] split = JWTEncoded.split("\\.");
//            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
//            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
            return getJson(split[1]);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException
    {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}
