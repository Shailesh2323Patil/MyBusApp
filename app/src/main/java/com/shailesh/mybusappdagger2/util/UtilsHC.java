package com.shailesh.mybusappdagger2.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.shailesh.mybusappdagger2.R;

import static android.content.Context.CLIPBOARD_SERVICE;

public class UtilsHC
{
    public static String fetchNumber(String textWithNumber)
    {
        return textWithNumber.replaceAll("[^0-9]", "");
    }

    public static String fetchMobileNumber(String phoneNumber)
    {
        if(phoneNumber.startsWith("+"))
        {
            if(phoneNumber.length() == 13)
            {
                return phoneNumber.substring(3);
            }
            else if(phoneNumber.length() == 14)
            {
                return phoneNumber.substring(4);
            }
        }
            return phoneNumber;
    }

    public static void callDialog(Context context,String phone_number)
    {
        try
        {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone_number, null));
            context.startActivity(phoneIntent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void shareAPKLink(Context context,String subject,String message)
    {
        try
        {
            /*Create an ACTION_SEND Intent*/
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            /*The type of the content is text, obviously.*/
            intent.setType("text/plain");
            /*Applying information Subject and Body.*/
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            /*Fire!*/
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.app_name)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void copyValue(Context context,String value)
    {
        try
        {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", value);
            clipboard.setPrimaryClip(clip);

            PrintMessage.toastMsg(context,context.getString(R.string.coupon_copied));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
