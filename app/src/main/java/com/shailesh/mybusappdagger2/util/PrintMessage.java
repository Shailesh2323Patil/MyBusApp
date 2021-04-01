package com.shailesh.mybusappdagger2.util;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class PrintMessage
{
    public static void toastMsg(Context context,String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void logE(String TAG,String message)
    {
        Log.e(TAG,message);
    }

    public static void logD(String TAG,String message)
    {
        Log.d(TAG,message);
    }

    public static void snackBarNoInternetMsg(final Context context, String message,String action, View view, int textColor,int ok_or_setting)
    {
        // 1 Means No Internet Connection
        if(ok_or_setting == 1)
        {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .setAction(action, new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            try
                            {
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                context.startActivity(intent);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setActionTextColor(textColor)
                    .show();
        }
        else
        {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                    .setAction(action, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(textColor)
                    .show();
        }

    }

    public static void showErrorMessage(Context context,String message,View view)
    {
        try
        {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
