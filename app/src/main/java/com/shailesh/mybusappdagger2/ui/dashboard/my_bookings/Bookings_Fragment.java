package com.shailesh.mybusappdagger2.ui.dashboard.my_bookings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.databinding.FragmentBookingsBinding;
import com.shailesh.mybusappdagger2.model.MyBooking;
import com.shailesh.mybusappdagger2.ui.dashboard.Dashboard_ViewModel;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;
import com.shailesh.mybusappdagger2.view_model.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;

public class Bookings_Fragment extends DaggerFragment
{
    FragmentBookingsBinding binding;

    @Inject
    ProgressDialogCustom mProgressDialog;

    @Inject
    Dashboard_ViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    public Bookings_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(getActivity(),providerFactory).get(Dashboard_ViewModel.class);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bookings, container, false);

        /* Set Progress Bar */
        mProgressDialog = new ProgressDialogCustom(getActivity());
        mProgressDialog.setLoadImage(R.raw.progress_loader_gif);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        onIntiliser();
        onSetObservables();
        viewModel.apiGetMyBookings();
        dialogNoInternet();
    }

    private void onIntiliser()
    {
        binding.idTabLayout.addTab(binding.idTabLayout.newTab().setText(getText(R.string.my_bookings)));
        binding.idTabLayout.addTab(binding.idTabLayout.newTab().setText(getText(R.string.cancelled_bookings)));
    }

    private void onSetObservables()
    {
        // Booking List
        viewModel.mutableBookingList.removeObservers(getViewLifecycleOwner());
        viewModel.mutableBookingList.observe(getViewLifecycleOwner(), new Observer<ApiResponceResource<ArrayList<MyBooking>>>()
        {
            @Override
            public void onChanged(ApiResponceResource<ArrayList<MyBooking>> response)
            {

                switch (response.status)
                {
                    case LOADING:
                        mProgressDialog.showProgressBar(true);
                        break;

                    case AUTHENTICATED:
                        mProgressDialog.showProgressBar(false);

                        ArrayList<MyBooking> myBooking = new ArrayList<MyBooking>();
                        ArrayList<MyBooking> cancelBooking = new ArrayList<MyBooking>();

                        ArrayList<MyBooking> bookings = response.data;

                        for (int index = 0; index < bookings.size(); index++)
                        {
                            if(bookings.get(index).getPayment_status().equals("1"))
                            {
                                myBooking.add(bookings.get(index));
                            }
                            else
                            {
                                cancelBooking.add(bookings.get(index));
                            }
                        }

                        setFragment(myBooking,cancelBooking,getString(R.string.cancel_booking));
                        break;

                    case ERROR:
                        mProgressDialog.showProgressBar(false);
                        PrintMessage.showErrorMessage(getActivity(),response.message,binding.idLayRoot);
                        break;
                }
            }
        });
    }

    private void setFragment(ArrayList<MyBooking> myBooking,ArrayList<MyBooking> cancelBooking,String cancellationMessage)
    {
        Bundle bundleMyBooking = new Bundle();
        bundleMyBooking.putSerializable("booking",myBooking);

        final MyBooking_Fragment myBookingFragment = new MyBooking_Fragment();
        myBookingFragment.setArguments(bundleMyBooking);

        Bundle bundleCancelledBooking = new Bundle();
        bundleCancelledBooking.putSerializable("booking",cancelBooking);
        bundleCancelledBooking.putString("cancellationMessage",cancellationMessage);

        final CancelBooking_Fragment cancelBookingFragment = new CancelBooking_Fragment();
        cancelBookingFragment.setArguments(bundleCancelledBooking);

        FragmentReplacement(myBookingFragment,false,"Fragment_Boarding");

        binding.idTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                switch (tab.getPosition()) {
                    case 0:
                        FragmentReplacement(myBookingFragment,false,"Fragment_MyBooking");
                        break;
                    case 1:
                        FragmentReplacement(cancelBookingFragment,false,"Fragment_CancelBooking");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void FragmentReplacement(Fragment fragment, boolean addToBackStack, String TAG)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(TAG);
        }

        fragmentTransaction.replace(R.id.id_fragment_container_my_booking, fragment).commitAllowingStateLoss();
    }

    private void dialogNoInternet()
    {
        if(CheckInternetConnection.isConnectionToInternet(getActivity()) == false)
        {
            DialogBox_NoInternetConnection.dialogOkMessage(getActivity());
        }
    }
}