package com.shailesh.mybusappdagger2.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shailesh.mybusappdagger2.R;


public class DialogBox
{
    public static void dialogOkMessage(final Context context,
                                       final String title,
                                       final String message)
    {
        try
        {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_ok_message);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(false);

            final TextView id_txt_title = dialog.findViewById(R.id.id_txt_title);
            final TextView id_txt_message = dialog.findViewById(R.id.id_txt_message);
            final Button id_btn_ok = dialog.findViewById(R.id.id_btn_ok);

            id_txt_title.setText(title);
            id_txt_message.setText(message);

            id_btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void dialogOkMessage(final Context context,
                                       final String title,
                                       final String message,
                                       final Class cls)
    {
        try
        {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_ok_message);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(false);

            final TextView id_txt_title = dialog.findViewById(R.id.id_txt_title);
            final TextView id_txt_message = dialog.findViewById(R.id.id_txt_message);
            final Button id_btn_ok = dialog.findViewById(R.id.id_btn_ok);

            id_txt_title.setText(title);
            id_txt_message.setText(message);

            id_btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    Intent intent = new Intent(context,cls);
                    context.startActivity(intent);
                    ((Activity)context).finish();
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
