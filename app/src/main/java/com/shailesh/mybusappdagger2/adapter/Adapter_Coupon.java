package com.shailesh.mybusappdagger2.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.databinding.RowCouponBinding;
import com.shailesh.mybusappdagger2.model.Coupons;
import com.shailesh.mybusappdagger2.util.PrintMessage;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.CLIPBOARD_SERVICE;

public class Adapter_Coupon extends RecyclerView.Adapter<Adapter_Coupon.ViewHolder>
{
    private Context context;

    public Adapter_Coupon(Context context)
    {
        this.context = context;
    }

    private ArrayList<Coupons> arrayList = new ArrayList<Coupons>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowCouponBinding view = DataBindingUtil.inflate(inflater, R.layout.row_coupon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        final Coupons coupons = arrayList.get(position);
        holder.bind(coupons);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        RowCouponBinding binding;

        public ViewHolder(@NonNull RowCouponBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Coupons coupons)
        {
            binding.tvName.setText(coupons.getName());
            binding.tvDescription.setText(coupons.getDescription());
            binding.tvExpiresOn.setText(context.getString(R.string.expires_on)+" : "+coupons.getExpiredOn());

            binding.idIconCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    copyValue(binding.tvName.getText().toString());
                }
            });
        }
    }

    private void copyValue(String value)
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

    public void setCoupons(ArrayList<Coupons> arrayList)
    {
        this.arrayList.addAll(arrayList);
        notifyDataSetChanged();
    }
}
