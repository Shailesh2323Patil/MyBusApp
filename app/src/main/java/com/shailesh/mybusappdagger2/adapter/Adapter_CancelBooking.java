package com.shailesh.mybusappdagger2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.databinding.RowCancelBookingBinding;
import com.shailesh.mybusappdagger2.model.MyBooking;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_CancelBooking extends RecyclerView.Adapter<Adapter_CancelBooking.ViewHolder>
{
    private Context context;
    private ArrayList<MyBooking> arrayList = new ArrayList<MyBooking>();

    public Adapter_CancelBooking(Context context)
    {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowCancelBookingBinding view = DataBindingUtil.inflate(inflater, R.layout.row_cancel_booking, parent, false);
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
        RowCancelBookingBinding binding;

        public ViewHolder(@NonNull RowCancelBookingBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MyBooking booking)
        {
            binding.idTxtReferenceNumber.setText(context.getString(R.string.ticket_no)+" : "+booking.getTicket_number());
            binding.idTxtBusName.setText(booking.getName());
            binding.idTxtBordingName.setText(booking.getBoardig_name());
            binding.idTxtFinalAmount.setText(context.getString(R.string.rupee_symbol)+" "+booking.getFinal_amout());
            binding.idTxtRefundDate.setText(context.getString(R.string.cancellation_date)+" : "+booking.getRefund_receipt_date());
            binding.idTxtRefundId.setText(booking.getRefund_id().equals("") ? context.getString(R.string.not_found) : context.getString(R.string.refund_no)+" : "+booking.getRefund_id());
        }
    }

    public void setArrayList(ArrayList<MyBooking> arrayList)
    {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}
