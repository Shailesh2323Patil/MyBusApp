package com.shailesh.mybusappdagger2.ui.dashboard.my_bookings;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.adapter.Adapter_Booking_Seat_Arrangement;
import com.shailesh.mybusappdagger2.databinding.ActivityMyBookingDetailsBinding;
import com.shailesh.mybusappdagger2.model.MyBooking;
import com.shailesh.mybusappdagger2.model.MySeats;
import com.shailesh.mybusappdagger2.network.Constant;
import com.shailesh.mybusappdagger2.util.CallIntent;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.UtilsHC;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;
import com.shailesh.mybusappdagger2.view_model.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;

public class My_Booking_Details_Activity extends DaggerAppCompatActivity implements View.OnClickListener
{
    ActivityMyBookingDetailsBinding binding;

    MyBooking booking;

    @Inject
    Adapter_Booking_Seat_Arrangement adapter_myBooking;

    @Inject
    ViewModelProviderFactory providerFactory;

    My_Booking_Details_ViewModel viewModel;

    @Inject
    ProgressDialogCustom mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_booking_details);

        viewModel = new ViewModelProvider(this,providerFactory).get(My_Booking_Details_ViewModel.class);

        booking = (MyBooking) getIntent().getSerializableExtra("booking");

        binding.idTxtTravellingDate.setText(booking.getTravelling_date());
        binding.idTxtBusName.setText(booking.getName());
        binding.idTxtTicketNumber.setText(booking.getTicket_number());
        binding.idTxtBordingName.setText(booking.getBoardig_name());
        binding.idTxtDroppingName.setText(booking.getDropping_name());
        binding.idTxtFinalAmount.setText(getString(R.string.rupee_symbol)+" "+booking.getFinal_amout());
        binding.idTxtBoardingTime.setText(booking.getBoarding_time());
        binding.idTxtDroppingTime.setText(booking.getDropping_time());

        if(!booking.getReq_return_coupon().equals(""))
        {
            binding.idTxtReturnJourney.setText(booking.getReq_return_coupon());
        }
        else
        {
            binding.idTxtHeadReturnJourney.setVisibility(View.GONE);
            binding.idLayReturnJourney.setVisibility(View.GONE);
        }

        String payment_status = booking.getPayment_status();
        String cancel_available = booking.getCancel_available();

        if(payment_status.equals("1") && cancel_available.equals("1"))
        {
            binding.btRefund.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.btRefund.setVisibility(View.GONE);
        }

        setRecycleView(booking.getSeats());
        toolBar();
        dialogNoInternet();
        onClickListner();
        onSetObservables();
    }

    private void onClickListner()
    {
        binding.btRefund.setOnClickListener(this);
        binding.idTxtDownloadInvoice.setOnClickListener(this);
        binding.idIconCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == binding.btRefund)
        {
            dialogConfirmationRefund(getString(R.string.cancel),getString(R.string.are_you_sure_want_to_cancel_this_booking));
        }
        else if(view == binding.idTxtDownloadInvoice)
        {
            try
            {
                String url = Constant.invoiceLink+booking.getTicket_number();

                Log.e("URL",url);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(view == binding.idIconCopy)
        {
            UtilsHC.copyValue(My_Booking_Details_Activity.this,binding.idTxtReturnJourney.getText().toString());
        }
    }

    private void setRecycleView(ArrayList<MySeats> arrayList)
    {
        binding.idRecyleSeatBooking.setAdapter(adapter_myBooking);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.idRecyleSeatBooking.setLayoutManager(manager);
        adapter_myBooking.setArrayList(arrayList);
    }

    private void onSetObservables()
    {
        viewModel.mutableRefundId.observe(this, new Observer<ApiResponceResource<String>>() {
            @Override
            public void onChanged(ApiResponceResource<String> responce) {
                switch (responce.status)
                {
                    case LOADING:
                        mProgressDialog.showProgressBar(true);
                        break;

                    case AUTHENTICATED:
                        mProgressDialog.showProgressBar(false);
                        break;

                    case ERROR:
                        mProgressDialog.showProgressBar(false);
                        PrintMessage.showErrorMessage(My_Booking_Details_Activity.this,responce.message,binding.idLayRoot);
                    break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CallIntent.callDashboard(this,"3");
    }

    private void toolBar()
    {
        setSupportActionBar(binding.idToolbar.idToolbar);
        getSupportActionBar().setTitle(R.string.my_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_backward_white);

        binding.idToolbar.idToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });
    }

    private void dialogNoInternet()
    {
        if(CheckInternetConnection.isConnectionToInternet(this) == false)
        {
            DialogBox_NoInternetConnection.dialogOkMessage(this);
        }
    }

    public void dialogConfirmationRefund(final String title,
                                         final String message)
    {
        try
        {
            final Dialog dialog = new Dialog(My_Booking_Details_Activity.this);
            dialog.setContentView(R.layout.dialog_confirmation);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(false);

            final TextView id_txt_title = dialog.findViewById(R.id.id_txt_title);
            final TextView id_txt_message = dialog.findViewById(R.id.id_txt_message);
            final Button id_btn_yes = dialog.findViewById(R.id.id_btn_yes);
            final Button id_btn_no = dialog.findViewById(R.id.id_btn_no);

            id_txt_title.setText(title);
            id_txt_message.setText(message);

            id_btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    viewModel.apiRefundBooking(booking.getTicket_number(),booking.getFinal_amout());
                }
            });

            id_btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
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
                    CallIntent.callDashboard(context,"3");
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