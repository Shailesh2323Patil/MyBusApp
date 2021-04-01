package com.shailesh.mybusappdagger2.ui.splash;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerAppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.BuildConfig;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.databinding.ActivitySplashBinding;
import com.shailesh.mybusappdagger2.model.AppVersion;
import com.shailesh.mybusappdagger2.ui.login.Login_Activity;
import com.shailesh.mybusappdagger2.util.CallIntent;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;
import com.shailesh.mybusappdagger2.util.shared_preference.SharedPreferenceStorage;
import com.shailesh.mybusappdagger2.view_model.ViewModelProviderFactory;

import javax.inject.Inject;

public class Splash_Activity extends DaggerAppCompatActivity
{
    private static final Integer internetConnectivity = 181;

    @Inject
    SharedPreferenceStorage sharedPreferenceStorage;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    ProgressDialogCustom progressDialogCustom;

    Splash_ViewModel viewModel;

    ActivitySplashBinding binding;
    private static int SPLASH_SCREEN_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        //This method is used so that your splash activity
//        //can cover the entire screen.

        viewModel = new ViewModelProvider(this,providerFactory).get(Splash_ViewModel.class);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        binding = DataBindingUtil.setContentView(Splash_Activity.this, R.layout.activity_splash);

        changeStatusBarColor();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                String version_code = BuildConfig.VERSION_NAME;
                viewModel.apiSplash(version_code);
            }
        }, SPLASH_SCREEN_TIME_OUT);

        onSetObservables();
        dialogNoInternet();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    private void dialogNoInternet()
    {
        if(CheckInternetConnection.isConnectionToInternet(this) == false)
        {
            DialogBox_NoInternetConnection.dialogOkMessage(this);
        }
    }

    private void onSetObservables()
    {
        // App Version
        viewModel.mutableAppVersion.observe(this, new Observer<ApiResponceResource<AppVersion>>() {
            @Override
            public void onChanged(ApiResponceResource<AppVersion> appVersionApiResponceResource)
            {
                switch (appVersionApiResponceResource.status)
                {
                    case LOADING:
                        progressDialogCustom.showProgressBar(true);
                        break;

                    case AUTHENTICATED:
                        {
                            progressDialogCustom.showProgressBar(false);

                            AppVersion appVersion = appVersionApiResponceResource.data;

                            Boolean stable_version = appVersion.getStable_version();

                            if(stable_version)
                            {
                                String userId = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userId);
                                String authToken = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.authToken);

                                if(userId.equals("") && authToken.equals(""))
                                {
                                    Intent intent = new Intent(Splash_Activity.this, Login_Activity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    CallIntent.callDashboard(Splash_Activity.this,"1");
                                }
                            }
                            else
                            {
                                dialogAppUpdate(Splash_Activity.this,getString(R.string.app_update),appVersion.getDescription(),appVersion.getUrl());
                            }
                        }
                        break;

                    case ERROR:
                            progressDialogCustom.showProgressBar(false);
                            PrintMessage.showErrorMessage(Splash_Activity.this,appVersionApiResponceResource.message,binding.idLayRoot);
                        break;
                }

            }
        });
    }

    public static void dialogAppUpdate(final Context context,
                                       final String title,
                                       final String message,
                                       final String url)
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
                public void onClick(View view)
                {
                    try
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        intent.setPackage("com.android.vending");
                        context.startActivity(intent);
                    }
                    catch (Exception e)
                    {
                        String packageName = BuildConfig.APPLICATION_ID;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                        context.startActivity(intent);
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
