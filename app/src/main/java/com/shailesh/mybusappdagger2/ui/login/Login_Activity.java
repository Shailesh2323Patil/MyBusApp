package com.shailesh.mybusappdagger2.ui.login;


import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerAppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.databinding.ActivityLoginBinding;
import com.shailesh.mybusappdagger2.model.GenerateOtp;
import com.shailesh.mybusappdagger2.ui.verify_otp.VerifyOtp_Activity;
import com.shailesh.mybusappdagger2.util.FieldsValidation;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;
import com.shailesh.mybusappdagger2.util.shared_preference.SharedPreferenceStorage;
import com.shailesh.mybusappdagger2.view_model.ViewModelProviderFactory;

import java.util.Objects;

import javax.inject.Inject;

public class Login_Activity extends DaggerAppCompatActivity
{
    private static final String TAG = "Activity_Login";

    ActivityLoginBinding binding;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    SharedPreferenceStorage sharedPreferenceStorage;

    @Inject
    ProgressDialogCustom progressDialogCustom;

    private Login_ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        viewModel = new ViewModelProvider(this,providerFactory).get(Login_ViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        onSetObservables();
    }

    private void onSetObservables()
    {
        // Sign In
        viewModel.getMutableGenerateOtp().observe(this, new Observer<GenerateOtp>() {
            @Override
            public void onChanged(GenerateOtp generateOtp)
            {
                if (TextUtils.isEmpty(Objects.requireNonNull(generateOtp).getMobile_number()))
                {
                    PrintMessage.showErrorMessage(Login_Activity.this,getString(R.string.enter_mobile_number),binding.idLayRoot);
                }
                else if(!FieldsValidation.isPhoneNumberValid(generateOtp.getMobile_number()))
                {
                    PrintMessage.showErrorMessage(Login_Activity.this,getString(R.string.enter_valid_mobile_number),binding.idLayRoot);
                }
                else
                {
                    viewModel.apiGenerateOtp(generateOtp);
                }
            }
        });

        // Generate Api Response
        viewModel.apiGenerateApiResponse.observe(this, new Observer<ApiResponceResource<GenerateOtp>>() {
            @Override
            public void onChanged(ApiResponceResource<GenerateOtp> apiResponceResource) {
                switch (apiResponceResource.status)
                {
                    case LOADING :
                            progressDialogCustom.showProgressBar(true);
                        break;

                    case AUTHENTICATED :
                            progressDialogCustom.showProgressBar(false);

                            GenerateOtp generateOtp = apiResponceResource.data;

                            Intent intent = new Intent(Login_Activity.this, VerifyOtp_Activity.class);
                            intent.putExtra("GenerateOtp",generateOtp);
                            startActivity(intent);
                            finish();

                        break;

                    case ERROR :
                    case NOT_AUTHENTICATED :
                            progressDialogCustom.showProgressBar(false);
                            PrintMessage.showErrorMessage(Login_Activity.this,apiResponceResource.message,binding.idLayRoot);
                        break;
                }
            }
        });

    }


}