package com.shailesh.mybusappdagger2.ui.verify_otp;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerAppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.databinding.ActivityVerifyOtpBinding;
import com.shailesh.mybusappdagger2.model.GenerateOtp;
import com.shailesh.mybusappdagger2.model.VerifyOtp;
import com.shailesh.mybusappdagger2.ui.login.Login_Activity;
import com.shailesh.mybusappdagger2.util.CallIntent;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.UtilsHC;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;
import com.shailesh.mybusappdagger2.util.sms_retriver.SMSReceiver;
import com.shailesh.mybusappdagger2.view_model.ViewModelProviderFactory;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class VerifyOtp_Activity extends DaggerAppCompatActivity implements SMSReceiver.OTPReceiveListener, View.OnClickListener
{
    public static final String TAG = VerifyOtp_Activity.class.getSimpleName();
    private SMSReceiver smsReceiver;

    VerifyOtp_ViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    ProgressDialogCustom mProgressDialog;

    ActivityVerifyOtpBinding binding;

    GenerateOtp generateOtp;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this,providerFactory).get(VerifyOtp_ViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_verify_otp);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        try
        {
            generateOtp =  (GenerateOtp) getIntent().getSerializableExtra("GenerateOtp");
            viewModel.mutableMobileNumber.setValue(generateOtp.getMobile_number());
            viewModel.mutableEmail.setValue(generateOtp.getEmail());
            viewModel.mutableIsSocial.setValue(generateOtp.getIs_social());
            viewModel.mutableFullName.setValue(generateOtp.getName());
            viewModel.mutableDOB.setValue("");
            viewModel.mutableGender.setValue("");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        startTimer(30000);
        onSetObservables();
        startSMSListener();
        dialogNoInternet();
        onClickListner();
    }

    private void onClickListner()
    {
        binding.idBtnResendOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == binding.idBtnResendOtp)
        {
            startTimer(30000);

            String mobileNumber = generateOtp.getMobile_number();
            String smsKey = generateOtp.getSmsKey();

            viewModel.apiResendOtp(mobileNumber,smsKey);
        }
    }

    private void onSetObservables()
    {
        // getOtp
        viewModel.getMutableVerifyOtp().observe(this, new Observer<VerifyOtp>()
        {
            @Override
            public void onChanged(VerifyOtp verifyOtp)
            {
                if (TextUtils.isEmpty(Objects.requireNonNull(verifyOtp).getOtp()))
                {
                    PrintMessage.showErrorMessage(VerifyOtp_Activity.this,getString(R.string.enter_otp),binding.idLayRoot);
                }
                else
                {
                    viewModel.apiVerifyOtp(verifyOtp);
                }
            }
        });

        // Verify Otp Complete
        viewModel.mutableVerifyOtpComplete.observe(this, new Observer<ApiResponceResource<Boolean>>() {
            @Override
            public void onChanged(ApiResponceResource<Boolean> responce) {
                 switch (responce.status)
                 {
                     case LOADING:
                            mProgressDialog.showProgressBar(true);
                         break;

                     case AUTHENTICATED:
                            mProgressDialog.showProgressBar(false);
                            CallIntent.callDashboard(VerifyOtp_Activity.this,"1");
                         break;

                     case ERROR :
                     case NOT_AUTHENTICATED :
                             mProgressDialog.showProgressBar(false);
                             PrintMessage.showErrorMessage(VerifyOtp_Activity.this,responce.message,binding.idLayRoot);
                         break;
                 }
            }
        });

        // Resend Otp
        viewModel.mutableResendOtp.observe(this, new Observer<ApiResponceResource<Boolean>>() {
            @Override
            public void onChanged(ApiResponceResource<Boolean> responce) {
                switch (responce.status)
                {
                    case LOADING:
                        mProgressDialog.showProgressBar(true);
                        break;

                    case AUTHENTICATED:
                        mProgressDialog.showProgressBar(false);
                        break;

                    case ERROR:
                        mProgressDialog.showProgressBar(false);
                        break;
                }
            }
        });

    }

    /* SMS Retriver API Start*/
    private void startSMSListener()
    {
        try
        {
            //AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(this);

            // This code requires one time to get Hash keys do comment and share key
            //PrintMessage.logE(TAG, "HashKey: " + appSignatureHashHelper.getAppSignatures().get(0));

            smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener(this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            this.registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(this);

            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // API successfully started
                    PrintMessage.logE(TAG,"SMS Retriver API Started");
                }
            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Fail to start API
                    PrintMessage.logE(TAG,"SMS Retriver API Failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOTPReceived(String str) {

        String otp = str.substring(0,str.indexOf(":"));

        binding.etOtpVerify.setText(UtilsHC.fetchNumber(otp));

        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }

        binding.btnOtpSubmit.performClick();
    }

    @Override
    public void onOTPTimeOut() {
        PrintMessage.showErrorMessage(this,getString(R.string.otp_timeout),binding.idLayRoot);
    }

    @Override
    public void onOTPReceivedError(String error) {
        PrintMessage.showErrorMessage(this,error,binding.idLayRoot);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(countDownTimer != null)
        {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    /* SMS Retriver API End*/
    private void dialogNoInternet()
    {
        if(CheckInternetConnection.isConnectionToInternet(this) == false)
        {
            DialogBox_NoInternetConnection.dialogOkMessage(this);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(VerifyOtp_Activity.this, Login_Activity.class);
        startActivity(intent);
        finish();
    }

    private void startTimer(int noOfMilliSecond)
    {
        binding.idBtnResendOtp.setVisibility(View.GONE);
        binding.idTxtResendOtpMessage.setVisibility(View.VISIBLE);

        countDownTimer = new CountDownTimer(noOfMilliSecond, 1000) {
            public void onTick(long millisUntilFinished)
            {
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d : %02d ",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                binding.idTxtResendOtpMessage.setText(getString(R.string.resend_in)+" "+hms);//set text
            }
            public void onFinish()
            {
                binding.idTxtResendOtpMessage.setVisibility(View.GONE);
                binding.idBtnResendOtp.setVisibility(View.VISIBLE);
            }
        }.start();
    }

}