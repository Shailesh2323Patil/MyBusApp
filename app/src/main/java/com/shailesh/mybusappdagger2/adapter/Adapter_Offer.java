package com.shailesh.mybusappdagger2.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.databinding.RowOfferBinding;
import com.shailesh.mybusappdagger2.model.Coupons;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.UtilsHC;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.CLIPBOARD_SERVICE;

public class Adapter_Offer extends RecyclerView.Adapter<Adapter_Offer.ViewHolder>
{
    private Context context;
    private ArrayList<Coupons> arrayList;

    public Adapter_Offer(Context context, ArrayList<Coupons> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowOfferBinding view = DataBindingUtil.inflate(inflater, R.layout.row_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        final Coupons coupons = arrayList.get(position);

        holder.binding.tvName.setText(coupons.getName());
        holder.binding.tvDescription.setText(coupons.getDescription());
        holder.binding.tvExpiresOn.setText(context.getString(R.string.expires_on)+" : "+coupons.getExpiredOn());

        holder.binding.idIconCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyValue(holder.binding.tvName.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        RowOfferBinding binding;

        public ViewHolder(@NonNull RowOfferBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void copyValue(String value)
    {
        try
        {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", value);
            clipboard.setPrimaryClip(clip);

            PrintMessage.toastMsg(context,context.getString(R.string.coupon_copied));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
