package com.shailesh.mybusappdagger2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.databinding.RowMyBookingBinding;
import com.shailesh.mybusappdagger2.model.MyBooking;
import com.shailesh.mybusappdagger2.ui.dashboard.my_bookings.Interface_Booking;
import com.shailesh.mybusappdagger2.util.UtilsHC;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_MyBooking extends RecyclerView.Adapter<Adapter_MyBooking.ViewHolder>
{
    private Context context;
    private ArrayList<MyBooking> arrayList = new ArrayList<MyBooking>();
    private Interface_Booking interface_booking;

    public Adapter_MyBooking(Context context)
    {
        this.context = context;
        this.arrayList = arrayList;
        this.interface_booking = interface_booking;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowMyBookingBinding view = DataBindingUtil.inflate(inflater, R.layout.row_my_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        final MyBooking booking = arrayList.get(position);
        holder.bind(booking);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        RowMyBookingBinding binding;

        public ViewHolder(@NonNull RowMyBookingBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final MyBooking booking)
        {
            try
            {
                binding.idTxtReferenceNumber.setText(booking.getTicket_number());
                binding.idTxtBusName.setText(booking.getName());
                binding.idTxtBordingName.setText(booking.getBoardig_name());
                binding.idTxtFinalAmount.setText(context.getString(R.string.rupee_symbol)+" "+booking.getFinal_amout());
                binding.idTxtTravellingDate.setText(booking.getTravelling_date());
                binding.idTxtBoardingTime.setText(booking.getBoarding_time());

                if(!booking.getReq_return_coupon().equals(""))
                {
                    binding.idTxtReturnJourney.setVisibility(View.VISIBLE);
                    binding.idLayReturnJourney.setVisibility(View.VISIBLE);
                    binding.idTxtReturnJourneyCoupon.setText(booking.getReq_return_coupon());
                }
                else
                {
                    binding.idTxtReturnJourney.setVisibility(View.GONE);
                    binding.idLayReturnJourney.setVisibility(View.GONE);
                }

                binding.idCardMainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        interface_booking.bookingDetails(booking);
                    }
                });

                binding.idIconCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UtilsHC.copyValue(context,booking.getReq_return_coupon());
                    }
                });
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setInterfaceBooking(Interface_Booking interface_booking)
    {
        this.interface_booking = interface_booking;
    }

    public void setArrayList(ArrayList<MyBooking> arrayList)
    {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}
