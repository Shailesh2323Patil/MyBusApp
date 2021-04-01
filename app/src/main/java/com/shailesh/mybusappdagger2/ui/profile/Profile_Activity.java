package com.shailesh.mybusappdagger2.ui.profile;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerAppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.databinding.ActivityProfileBinding;
import com.shailesh.mybusappdagger2.model.Areas;
import com.shailesh.mybusappdagger2.model.Profile;
import com.shailesh.mybusappdagger2.model.Routes;
import com.shailesh.mybusappdagger2.model.Villages;
import com.shailesh.mybusappdagger2.util.CallIntent;
import com.shailesh.mybusappdagger2.util.DateFormatCls;
import com.shailesh.mybusappdagger2.util.FieldsValidation;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.camera.FileUtils;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.file_selection.FileSelection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;
import com.shailesh.mybusappdagger2.util.shared_preference.SharedPreferenceStorage;
import com.shailesh.mybusappdagger2.view_model.ViewModelProviderFactory;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class Profile_Activity extends DaggerAppCompatActivity implements View.OnClickListener,Interface_Route
{
    ActivityProfileBinding binding;

    @Inject
    SharedPreferenceStorage sharedPreferenceStorage;

    @Inject
    ProgressDialogCustom mProgressDialog;

    @Inject
    ViewModelProviderFactory providerFactory;

    Profile_ViewModel viewModel;

    Calendar calendar;
    private static final int RESULT_LOAD_IMAGE_Gallery = 402;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 401;
    private static String imageStoragePath;
    private static String imageStorageByteArray = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this,providerFactory).get(Profile_ViewModel.class);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        calendar = Calendar.getInstance();

        imageStorageByteArray = new String();

        onInitilizer();
        setContent();
        onSetObservables();
        onClickListner();
        dialogNoInternet();
    }

    private void onClickListner()
    {
        binding.etDob.setOnClickListener(this);
        binding.tvRouteId.setOnClickListener(this);
        binding.tvVillageId.setOnClickListener(this);
        binding.tvAreaId.setOnClickListener(this);
        binding.tvCancel.setOnClickListener(this);
        binding.idImgProfileLogo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == binding.etDob)
        {
            DatePickerDialog datePickerDialog = new DatePickerDialog(Profile_Activity.this,
                    new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    //String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
                    String date = DateFormatCls.inputFormat.format(calendar.getTime());

                    try
                    {
                        if(DateFormatCls.getAge(year,monthOfYear,dayOfMonth) <= 0)
                        {
                            DialogBox.dialogOkMessage(Profile_Activity.this,getString(R.string.dob),getString(R.string.please_fill_valid_dob));
                        }
                        else
                        {
                            binding.etDob.setText(date);
                            viewModel.mutableDOB.setValue(date);
                        }
                    }
                    catch (Exception e)
                    {
                        e.getStackTrace();
                    }
                }
            }, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
        else if(view == binding.tvCancel)
        {
            onBackPressed();
        }
        else if(view == binding.idImgProfileLogo)
        {
            dialogSelectCamera();
        }
    }

    private void onSetObservables()
    {
        viewModel.mutableProfile.observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile)
            {
                if (!FieldsValidation.isEmailValid(profile.getEmail_id()) && !profile.getEmail_id().equals("")) {
                    PrintMessage.showErrorMessage(Profile_Activity.this,getString(R.string.enter_valid_email_id),binding.idLayRoot);
                }
                else if (!FieldsValidation.isPhoneNumberValid(profile.getMobile()) && !profile.getMobile().equals("")) {
                    PrintMessage.showErrorMessage(Profile_Activity.this,getString(R.string.enter_valid_mobile_number),binding.idLayRoot);
                }
                else {
                    try {
                        profile.setProfile_pic(imageStorageByteArray);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    viewModel.apiSaveProfile(profile);
                }
            }
        });


        viewModel.mutableMessageProfile.observe(this, new Observer<ApiResponceResource<String>>() {
            @Override
            public void onChanged(ApiResponceResource<String> responce) {
                switch (responce.status)
                {
                    case LOADING:
                        mProgressDialog.showProgressBar(true);
                        break;

                    case AUTHENTICATED:
                        mProgressDialog.showProgressBar(false);
                        onUpdateProfileMessage(responce.data,getResources().getString(R.string.ok),binding.idLayRoot);
                        break;

                    case ERROR:
                        mProgressDialog.showProgressBar(false);
                        PrintMessage.showErrorMessage(Profile_Activity.this,responce.message,binding.idLayRoot);
                }
            }
        });

    }

    private void onInitilizer()
    {
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                if(checkedId == R.id.radiomale)
                {
                    viewModel.mutableGender.setValue("1");
                }
                else
                {
                    viewModel.mutableGender.setValue("2");
                }
            }
        });
    }

    private void setContent()
    {
        try
        {
            String userName = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userName);
            String userGender = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userGender);
            String userMobile = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userMobile);
            String userEmail = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userEmail);
            String userDOB = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userDOB);
            String userAge = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userAge);
            String userUniqueCode = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userUniqueCode);
            String routeId = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userRoleId);
            String routeName = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userRouteName);
            String villageId = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userVillageId);
            String villageName = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userVillageName);
            String areaId = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userAreaId);
            String areaName = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userAreaName);
            String profilePic = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.userProfilePic);

            try
            {
                if(DateFormatCls.isDateValid(userDOB) && !userDOB.equals("0000-00-00"))
                {
                    Date date = DateFormatCls.inputFormat.parse(userDOB);
                    calendar = Calendar.getInstance();
                    calendar.setTime(date);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                calendar = Calendar.getInstance();
            }

            viewModel.mutableName.setValue(userName);
            viewModel.mutableMobile.setValue(userMobile);
            viewModel.mutableEmail.setValue(userEmail);
            viewModel.mutableDOB.setValue(userDOB);
            viewModel.mutableUniqueCode.setValue(userUniqueCode);
            viewModel.mutableRouteId.setValue(routeId);
            viewModel.mutableAreaId.setValue(areaId);
            viewModel.mutableVillageId.setValue(villageId);

            if(userGender.toLowerCase().equals(getString(R.string.male).toLowerCase()))
            {
                binding.radiomale.setChecked(true);
                viewModel.mutableGender.setValue("1");
            }
            else if(userGender.toLowerCase().equals(getString(R.string.female).toLowerCase()))
            {
                binding.radiofemale.setChecked(true);
                viewModel.mutableGender.setValue("2");
            }
            else
            {
                binding.radiomale.setChecked(true);
                viewModel.mutableGender.setValue("1");
            }

            binding.tvVillageId.setText(villageName);
            binding.tvAreaId.setText(areaName);
            binding.tvRouteId.setText(routeName);

            if(!profilePic.equals("") && profilePic != null)
            {
                FileSelection.setImagViewAsBitmapAndCircular(Profile_Activity.this,profilePic,binding.idImgProfileLogo);
                imageStorageByteArray = profilePic;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        CallIntent.callDashboard(this,"2");
    }

    private void dialogNoInternet()
    {
        if(CheckInternetConnection.isConnectionToInternet(this) == false)
        {
            DialogBox_NoInternetConnection.dialogOkMessage(this);
        }
    }

    @Override
    public void selectRoute(Routes routes) {
        viewModel.mutableRouteId.setValue(routes.getRoute_id());
        binding.tvRouteId.setText(routes.getName());

        viewModel.mutableVillageId.setValue("");
        binding.tvVillageId.setText("");
    }

    @Override
    public void selectVillage(Villages villages) {
        viewModel.mutableVillageId.setValue(villages.getVillage_id());
        binding.tvVillageId.setText(villages.getName());
    }

    @Override
    public void selectArea(Areas areas) {
        viewModel.mutableAreaId.setValue(areas.getArea_id());
        binding.tvAreaId.setText(areas.getName());
    }

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
                    CallIntent.callDashboard(context,"2");
                }
            });

            dialog.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void dialogSelectCamera()
    {
        try
        {
            final Dialog dialog = new Dialog(Profile_Activity.this);
            dialog.setContentView(R.layout.dialog_image_capture);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(false);

            final RelativeLayout layCamera = dialog.findViewById(R.id.id_lay_camera);
            final RelativeLayout layGallery = dialog.findViewById(R.id.id_lay_gallery);
            final ImageView imgClose = dialog.findViewById(R.id.id_close);

            layCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    requestCameraPermission(1);
                }
            });

            layGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    requestCameraPermission(2);
                }
            });

            imgClose.setOnClickListener(new View.OnClickListener() {
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

    private void requestCameraPermission(final int type)
    {
        Dexter.withActivity(Profile_Activity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report)
                    {
                        if (report.areAllPermissionsGranted())
                        {
                            if (type == 1)
                            {
                                // capture picture
                                imageStoragePath = FileSelection.captureImage(Profile_Activity.this,CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                            }
                            else
                            {
                                FileSelection.selectImageFromGallery(Profile_Activity.this,RESULT_LOAD_IMAGE_Gallery);
                            }
                        }
                        else if (report.isAnyPermissionPermanentlyDenied())
                        {
                            FileSelection.showPermissionsAlert(Profile_Activity.this,getString(R.string.permission_camera_message));
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
//              FileSelection.setImageView(Activity_Profile.this,imageStoragePath,binding.idImgProfileLogo);
                String profilePic = FileSelection.getBase64tringFile(Profile_Activity.this,new File(imageStoragePath));
                imageStorageByteArray = profilePic;
                FileSelection.setImagViewAsBitmapAndCircular(Profile_Activity.this,imageStorageByteArray,binding.idImgProfileLogo);
            }
            else if (resultCode == RESULT_CANCELED)
            {
                // user cancelled Image capture
                PrintMessage.toastMsg(getApplicationContext(),getString(R.string.user_camera_cancelled));
            }
            else
            {
                // failed to capture image
                PrintMessage.toastMsg(getApplicationContext(),getString(R.string.image_capture_failed));
            }
        }
        else if(requestCode == RESULT_LOAD_IMAGE_Gallery)
        {
            if (resultCode == RESULT_OK)
            {
                try
                {
                    Uri selectedImage = data.getData();
                    imageStoragePath = FileUtils.getPath(Profile_Activity.this,selectedImage);
                    //FileSelection.setImageView(Activity_Profile.this,imageStoragePath,binding.idImgProfileLogo);
                    String profilePic = FileSelection.getBase64tringFile(Profile_Activity.this,new File(imageStoragePath));
                    imageStorageByteArray = profilePic;
                    FileSelection.setImagViewAsBitmapAndCircular(Profile_Activity.this,imageStorageByteArray,binding.idImgProfileLogo);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else if (resultCode == RESULT_CANCELED)
            {
                // user cancelled Image capture
                PrintMessage.toastMsg(getApplicationContext(),getString(R.string.user_camera_cancelled));
            }
            else
            {
                // failed to capture image
                PrintMessage.toastMsg(getApplicationContext(),getString(R.string.image_capture_failed));
            }
        }
    }

    private void onUpdateProfileMessage(String message,String action, View view)
    {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.color_text_color_green))
                .show();
    }
}