package com.shailesh.mybusappdagger2.ui.dashboard.my_account;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.databinding.FragmentMyAccountBinding;
import com.shailesh.mybusappdagger2.network.Constant;
import com.shailesh.mybusappdagger2.ui.dashboard.Dashboard_ViewModel;
import com.shailesh.mybusappdagger2.ui.login.Login_Activity;
import com.shailesh.mybusappdagger2.ui.profile.Profile_Activity;
import com.shailesh.mybusappdagger2.ui.webview.WebView_Activity;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.UtilsHC;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.file_selection.FileSelection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;
import com.shailesh.mybusappdagger2.util.shared_preference.SharedPreferenceStorage;
import com.shailesh.mybusappdagger2.view_model.ViewModelProviderFactory;

import javax.inject.Inject;

public class MyAccount_Fragment extends DaggerFragment implements View.OnClickListener
{
    FragmentMyAccountBinding binding;

    @Inject
    SharedPreferenceStorage sharedPreferenceStorage;

    @Inject
    ProgressDialogCustom mProgressDialog;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Dashboard_ViewModel viewModel;

    public MyAccount_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(getActivity(),providerFactory).get(Dashboard_ViewModel.class);
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_account, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setClickListner();
        dialogNoInternet();
        onSetObservables();

        viewModel.apiGetProfile_MyAccount();
    }

    private void setClickListner()
    {
        binding.cardProfile.setOnClickListener(this);
        binding.rlLogout.setOnClickListener(this);
        binding.rlRefer.setOnClickListener(this);
        binding.rlOffers.setOnClickListener(this);
        binding.rlHelp.setOnClickListener(this);
        binding.rlCall.setOnClickListener(this);
        binding.rlAbout.setOnClickListener(this);
        binding.rlPrivacyPolicy.setOnClickListener(this);
        binding.rlShareApp.setOnClickListener(this);
        binding.rlSmartCard.setOnClickListener(this);
    }

    private void setTheContent()
    {
        String userName = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userName);
        String userGender = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userGender);
        String userMobile = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userMobile);
        String userEmail = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userEmail);
        String userDOB = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userDOB);
        String userAge = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userAge);
        String profileImage = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userProfilePic);

        binding.tvName.setText(userName.equals("") ? getActivity().getString(R.string.enter_name) : userName);
        binding.tvAge.setText(userAge.equals("") ? getString(R.string.enter_dob) : getString(R.string.age)+" "+userAge);
        binding.tvMobile.setText(userMobile.equals("") ? getActivity().getString(R.string.enter_mobile_number) : userMobile);
        binding.tvEmail.setText(userEmail.equals("") ? getActivity().getString(R.string.enter_email_id) : userEmail);

        if(!profileImage.equals("") && profileImage != null)
        {
            FileSelection.setImagViewAsBitmapAndCircular(getActivity(),profileImage,binding.idImgProfileLogo);
        }

    }

    @Override
    public void onClick(View view)
    {
        if(view == binding.cardProfile)
        {
            Intent intent = new Intent(getActivity(), Profile_Activity.class);
            startActivity(intent);
            getActivity().finish();
        }
        else if(view == binding.rlLogout)
        {
            dialogConfirmationLogout(getString(R.string.logout),getString(R.string.are_you_sure_want_to_logout));
        }
        else if(view == binding.rlRefer)
        {
            Intent intent = new Intent(getActivity(), WebView_Activity.class);
            intent.putExtra("url", Constant.referToEarn);
            startActivity(intent);
        }
        else if(view == binding.rlOffers)
        {
            Intent intent = new Intent(getActivity(), WebView_Activity.class);
            intent.putExtra("url", Constant.offers);
            startActivity(intent);
        }
        else if(view == binding.rlHelp)
        {
            Intent intent = new Intent(getActivity(), WebView_Activity.class);
            intent.putExtra("url", Constant.help);
            startActivity(intent);
        }
        else if(view == binding.rlCall)
        {
            Intent intent = new Intent(getActivity(), WebView_Activity.class);
            intent.putExtra("url", Constant.callSupport);
            startActivity(intent);
        }
        else if(view == binding.rlAbout)
        {
            Intent intent = new Intent(getActivity(), WebView_Activity.class);
            intent.putExtra("url", Constant.aboutUs);
            startActivity(intent);
        }
        else if(view == binding.rlPrivacyPolicy)
        {
            Intent intent = new Intent(getActivity(), WebView_Activity.class);
            intent.putExtra("url", Constant.privacyPolicy);
            startActivity(intent);
        }
        else if(view == binding.rlShareApp)
        {
            String message = getString(R.string.download_app_today_for_faster_way)+"\n"+getString(R.string.app_name_url);
            UtilsHC.shareAPKLink(getActivity(),getString(R.string.share),message);
        }
        else if(view == binding.rlSmartCard)
        {
            String mobileNumber = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userMobile);

            String url = Constant.smartCardLink+mobileNumber;

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }

    public void dialogConfirmationLogout(final String title,
                                         final String message)
    {
        try
        {
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_confirmation);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(false);

            final TextView id_txt_title = dialog.findViewById(R.id.id_txt_title);
            final TextView id_txt_message = dialog.findViewById(R.id.id_txt_message);
            final Button id_btn_yes = dialog.findViewById(R.id.id_btn_yes);
            final Button id_btn_no = dialog.findViewById(R.id.id_btn_no);

            id_txt_title.setText(title);
            id_txt_message.setText(message);

            id_btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();

                    /* Clear All Shared Preference */
                    sharedPreferenceStorage.clearStorage();

                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();
                }
            });

            id_btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dialogNoInternet()
    {
        if(CheckInternetConnection.isConnectionToInternet(getActivity()) == false)
        {
            DialogBox_NoInternetConnection.dialogOkMessage(getActivity());
        }
    }

    private void onSetObservables()
    {
        // Complete
        viewModel.mutableCompleteMyAccount.removeObservers(getViewLifecycleOwner());
        viewModel.mutableCompleteMyAccount.observe(getViewLifecycleOwner(), new Observer<ApiResponceResource<Boolean>>() {
            @Override
            public void onChanged(ApiResponceResource<Boolean> responce) {
                switch (responce.status)
                {
                    case LOADING:
                        mProgressDialog.showProgressBar(true);
                        break;

                    case AUTHENTICATED:
                        mProgressDialog.showProgressBar(false);
                        setTheContent();
                        break;

                    case ERROR:
                        mProgressDialog.showProgressBar(false);
                        PrintMessage.showErrorMessage(getActivity(),responce.message,binding.idLayRoot);
                }
            }
        });
    }
}