package com.shailesh.mybusappdagger2.ui.dashboard.my_bookings;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.adapter.Adapter_MyBooking;
import com.shailesh.mybusappdagger2.databinding.FragmentMyBookingBinding;
import com.shailesh.mybusappdagger2.model.MyBooking;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;

import java.util.ArrayList;

import javax.inject.Inject;

public class MyBooking_Fragment extends DaggerFragment implements Interface_Booking
{
    FragmentMyBookingBinding binding;

    @Inject
    Adapter_MyBooking adapter_myBooking;

    public MyBooking_Fragment() {
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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_booking, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setRecycleView();
        dialogNoInternet();
    }

    private void setRecycleView()
    {
        ArrayList<MyBooking> arrayList = (ArrayList<MyBooking>) getArguments().getSerializable("booking");
        if (arrayList.size() != 0)
        {
            binding.idRecycleBooking.setAdapter(adapter_myBooking);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            binding.idRecycleBooking.setLayoutManager(manager);
            adapter_myBooking.setInterfaceBooking(this);
            adapter_myBooking.setArrayList(arrayList);
        }
        else
        {
            binding.idRecycleBooking.setVisibility(View.GONE);
            binding.idTxtNotFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void bookingDetails(MyBooking booking)
    {
        Intent intent = new Intent(getActivity(), My_Booking_Details_Activity.class);
        intent.putExtra("booking",booking);
        startActivity(intent);
        getActivity().finish();
    }

    private void dialogNoInternet()
    {
        if(CheckInternetConnection.isConnectionToInternet(getActivity()) == false)
        {
            DialogBox_NoInternetConnection.dialogOkMessage(getActivity());
        }
    }
}