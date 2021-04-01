package com.shailesh.mybusappdagger2.util.progress_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.shailesh.mybusappdagger2.R;

public class ProgressDialogCustom {

    Dialog dialog;
    Activity activity;
    int loadImageGif;
    Boolean cancelable = true;

    public ProgressDialogCustom(Activity activity)
    {
        this.activity = activity;
    }

    //set drawable of loading gif
    public void setLoadImage(int img)
    {
        this.loadImageGif = img;
    }

    //(optional)if user can cancel(click out of layout)
    public void setCancelable(Boolean state)
    {
        this.cancelable = state;
    }

    private void show()
    {
        try
        {
            dialog = new Dialog(activity, R.style.mydialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //inflate the layout
            dialog.setContentView(R.layout.progress_bar);
            //setup cancelable, default=false
            dialog.setCancelable(cancelable);
            // Set Background Transparent
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            //get imageview to use in Glide
            ImageView imageView = dialog.findViewById(R.id.custom_loading_imageView);

            //load gif and callback to imageview
            Glide.with(activity)
                    .load(R.raw.progress_loader_gif) // loadImageGif
                    .placeholder(R.drawable.logo) // loadImageGif
                    .error(R.drawable.logo) // loadImageGif
                    .centerCrop()
                    .into(imageView);

            dialog.show();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    //Dismiss the dialog
    private void dismiss()
    {
        try
        {
            dialog.dismiss();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void showProgressBar(boolean isVisible)
    {
        try
        {
            if(isVisible)
            {
                show();
            }
            else
            {
                dismiss();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
