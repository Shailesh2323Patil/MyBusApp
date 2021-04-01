package com.shailesh.mybusappdagger2.ui.dashboard.my_bookings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.adapter.Adapter_CancelBooking;
import com.shailesh.mybusappdagger2.databinding.FragmentCancelBookingBinding;
import com.shailesh.mybusappdagger2.model.MyBooking;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;

import java.util.ArrayList;

import javax.inject.Inject;

public class CancelBooking_Fragment extends DaggerFragment
{
    FragmentCancelBookingBinding binding;

    @Inject
    Adapter_CancelBooking adapter_cancelBooking;

    public CancelBooking_Fragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cancel_booking, container, false);
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
        String cancellationMessage = getArguments().getString("cancellationMessage");
        ArrayList<MyBooking> arrayList = (ArrayList<MyBooking>) getArguments().getSerializable("booking");

        if(arrayList.size() != 0)
        {
            binding.idTxtCancelMessage.setText(cancellationMessage);
            binding.idRecycleBooking.setAdapter(adapter_cancelBooking);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
            binding.idRecycleBooking.setLayoutManager(manager);
            adapter_cancelBooking.setArrayList(arrayList);
        }
        else
        {
            binding.idRecycleBooking.setVisibility(View.GONE);
            binding.idTxtCancelMessage.setVisibility(View.GONE);
            binding.idTxtNotFound.setVisibility(View.VISIBLE);
        }
    }

    private void dialogNoInternet()
    {
        if(CheckInternetConnection.isConnectionToInternet(getActivity()) == false)
        {
            DialogBox_NoInternetConnection.dialogOkMessage(getActivity());
        }
    }
}