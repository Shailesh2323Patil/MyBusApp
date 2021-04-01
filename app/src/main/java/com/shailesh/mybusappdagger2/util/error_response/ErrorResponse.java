package com.shailesh.mybusappdagger2.util.error_response;

import android.app.Application;
import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shailesh.mybusappdagger2.R;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

public class ErrorResponse {

    Context context;

    public ErrorResponse(Context context)
    {
        this.context = context;
    }

    public String errorResponse(Throwable throwable)
    {
        try
        {
            if(throwable instanceof HttpException)
            {
                try
                {
                    HttpException httpException = (HttpException) throwable;

                    int errorCode = httpException.code();

                    String errorBody = httpException.response().errorBody().string();

                    JsonElement jsonElement = new JsonParser().parse(errorBody);

                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    String error_type = jsonObject.get("error_type").getAsString();
                    String error_description = jsonObject.get("error_description").getAsString();

                    return error_description;
                }
                catch (Exception e)
                {
                    return context.getString(R.string.something_went_wrong);
                }
            }
            else if(throwable instanceof SSLHandshakeException)
            {
                return context.getString(R.string.error_in_connection_please_try_again_letter)+" SSLHandshakeException";
            }
            else if(throwable instanceof ConnectTimeoutException)
            {
                return context.getString(R.string.error_in_connection_please_try_again_letter)+" ConnectTimeoutException";
            }
            else if(throwable instanceof SocketTimeoutException)
            {
                return context.getString(R.string.error_in_connection_please_try_again_letter)+" SocketTimeoutException";
            }
            else
            {
                return context.getString(R.string.error_in_connection_please_try_again_letter);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return context.getString(R.string.something_went_wrong);
        }
    }

}
