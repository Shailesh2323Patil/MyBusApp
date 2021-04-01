package com.shailesh.mybusappdagger2.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.shailesh.mybusappdagger2.ui.dashboard.Dashboard_Activity;

public class CallIntent
{
    public static void callDashboard(Context context,String flag)
    {
        Intent intent = new Intent(context, Dashboard_Activity.class);
        intent.putExtra("flag",flag);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}
