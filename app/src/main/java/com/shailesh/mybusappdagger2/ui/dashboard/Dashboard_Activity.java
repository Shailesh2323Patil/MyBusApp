package com.shailesh.mybusappdagger2.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerAppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.ui.dashboard.home.Home_Fragment;
import com.shailesh.mybusappdagger2.ui.dashboard.my_account.MyAccount_Fragment;
import com.shailesh.mybusappdagger2.ui.dashboard.my_bookings.Bookings_Fragment;
import com.shailesh.mybusappdagger2.ui.dashboard.offer.Offer_Fragment;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;
import com.shailesh.mybusappdagger2.view_model.ViewModelProviderFactory;

import javax.inject.Inject;

public class Dashboard_Activity extends DaggerAppCompatActivity
{
    private static final String TAG = "Activity_Dashboard";

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    ProgressDialogCustom progressDialogCustom;

    String selectedFrag;

    Dashboard_ViewModel viewModel;

    BottomNavigationView bottomNavigation;

    boolean doubleBackToExitPressedOnce = false;

    RelativeLayout idLayRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        viewModel = new ViewModelProvider(this,providerFactory).get(Dashboard_ViewModel.class);

        selectedFrag = new String();

        idLayRoot = (RelativeLayout) findViewById(R.id.id_lay_root);

        String flag = getIntent().getStringExtra("flag");

        bottomNavigation(flag);

        // Get Profile
        viewModel.apiGetProfile();

        dialogNoInternet();
        onSetObservables();
    }

    private void onSetObservables()
    {
        viewModel.mutableShowProfileBox.observe(this, new Observer<ApiResponceResource<Boolean>>() {
            @Override
            public void onChanged(ApiResponceResource<Boolean> response) {
                switch(response.status)
                {
                    case LOADING:
                        //progressDialogCustom.showProgressBar(true);
                        break;

                    case AUTHENTICATED:
                        //progressDialogCustom.showProgressBar(false);
                        dialogOkMessage(Dashboard_Activity.this,getString(R.string.profile),getString(R.string.please_fill_your_profile));
                        break;

                    case ERROR:
                        //progressDialogCustom.showProgressBar(false);
                        PrintMessage.showErrorMessage(Dashboard_Activity.this,response.message,idLayRoot);
                }
            }
        });
    }

    private void bottomNavigation(String flag)
    {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        if(flag.equals("1"))
        {
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        }
        else if(flag.equals("2"))
        {
            bottomNavigation.setSelectedItemId(R.id.navigation_my_account);
        }
        else if(flag.equals("3"))
        {
            bottomNavigation.setSelectedItemId(R.id.navigation_my_bookings);
        }
        else
        {
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        }
   }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {
                    Fragment selectedFragment = null;

                    switch (item.getItemId())
                    {
                        case R.id.navigation_home:

                            if(!selectedFrag.equals("Fragment_Home"))
                            {
                                selectedFrag = "Fragment_Home";
                                selectedFragment = new Home_Fragment();
                                FragmentReplacement(selectedFragment,false,"Fragment_Home");
                            }
                            return true;

                        case R.id.navigation_my_bookings:

                            if(!selectedFrag.equals("Fragment_My_Bookings"))
                            {
                                selectedFrag = "Fragment_My_Bookings";
                                selectedFragment = new Bookings_Fragment();
                                FragmentReplacement(selectedFragment,false,"Fragment_My_Bookings");
                            }
                            return true;

                        case R.id.navigation_offer:

                            if(!selectedFrag.equals("Fragment_Offer"))
                            {
                                selectedFrag = "Fragment_Offer";
                                selectedFragment = new Offer_Fragment();
                                FragmentReplacement(selectedFragment,false,"Fragment_Offer");
                            }
                            return true;

                        case R.id.navigation_my_account:

                            if(!selectedFrag.equals("Fragment_MyAccount"))
                            {
                                selectedFrag = "Fragment_MyAccount";
                                selectedFragment = new MyAccount_Fragment();
                                FragmentReplacement(selectedFragment,false,"Fragment_MyAccount");
                            }
                            return true;
                    }
                    return false;
                }
            };

    public void FragmentReplacement(Fragment fragment, boolean addToBackStack, String TAG) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(TAG);
        }

        fragmentTransaction.replace(R.id.id_fragment_container_dashboard, fragment).commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        PrintMessage.toastMsg(Dashboard_Activity.this,getString(R.string.please_click_back_again_to_exit));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void dialogNoInternet()
    {
        if(CheckInternetConnection.isConnectionToInternet(this) == false)
        {
            DialogBox_NoInternetConnection.dialogOkMessage(this);
        }
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