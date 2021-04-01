package com.shailesh.mybusappdagger2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.databinding.RowBookingSeatArrangementBinding;
import com.shailesh.mybusappdagger2.databinding.RowPassengerInfoBinding;
import com.shailesh.mybusappdagger2.model.MySeats;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_Booking_Seat_Arrangement extends RecyclerView.Adapter<Adapter_Booking_Seat_Arrangement.ViewHolder>
{
    private Context context;
    private ArrayList<MySeats> arrayList = new ArrayList<MySeats>();

    public Adapter_Booking_Seat_Arrangement(Context context)
    {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowBookingSeatArrangementBinding view = DataBindingUtil.inflate(inflater, R.layout.row_booking_seat_arrangement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        MySeats mySeats = arrayList.get(position);
        holder.bind(mySeats);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        RowBookingSeatArrangementBinding binding;

        public ViewHolder(@NonNull RowBookingSeatArrangementBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MySeats mySeats)
        {
            binding.idTxtSeatNumber.setText("Seat Number : "+mySeats.getSeat_number());
            binding.idTxtPassengerName.setText(mySeats.getName());
        }
    }

    public void setArrayList(ArrayList<MySeats> arrayList)
    {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}
