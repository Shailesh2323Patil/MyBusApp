package com.shailesh.mybusappdagger2.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;

public class DialogBox_NoInternetConnection
{
    public static void dialogOkMessage(final Context context)
    {
        try
        {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_no_internet_connection);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(false);

            final Button id_btn_ok = dialog.findViewById(R.id.id_btn_ok);

            id_btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if(CheckInternetConnection.isConnectionToInternet(context) == true)
                    {
                        dialog.dismiss();
                    }
                    else
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
                }
            });

            dialog.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
