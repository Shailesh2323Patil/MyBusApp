package com.shailesh.mybusappdagger2.ui.dashboard.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.adapter.Adapter_Coupon;
import com.shailesh.mybusappdagger2.databinding.FragmentHomeBinding;
import com.shailesh.mybusappdagger2.model.City;
import com.shailesh.mybusappdagger2.model.Coupons;
import com.shailesh.mybusappdagger2.ui.dashboard.Dashboard_ViewModel;
import com.shailesh.mybusappdagger2.util.DateFormatCls;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;
import com.shailesh.mybusappdagger2.util.shared_preference.SharedPreferenceStorage;
import com.shailesh.mybusappdagger2.view_model.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class Home_Fragment extends DaggerFragment implements View.OnClickListener
{
    private static final String TAG = "Home_Fragment";

    FragmentHomeBinding binding;

    @Inject
    SharedPreferenceStorage sharedPreferenceStorage;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    ProgressDialogCustom mProgressDialogCity,mProgressDialogOffers;

    @Inject
    Adapter_Coupon adapter_coupon;
    ArrayList<Coupons> arrayList;

    Dashboard_ViewModel viewModel;

    Calendar calendar,calendarToday;

    public Home_Fragment() {
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

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        calendar = Calendar.getInstance();
        calendarToday = Calendar.getInstance();

        onClickListner();
        onSetObservables();
        recycleCoupon();

        // Get The City Data
        viewModel.apiGetCity();
        viewModel.apiGetOffers();

        dialogNoInternet();

        return binding.getRoot();
    }

    private void onClickListner()
    {
        String returnMessage = sharedPreferenceStorage.getInformation(sharedPreferenceStorage.returnJourneyMessage);

        if(!returnMessage.equals(""))
        {
            binding.idCheckReturnJourney.setText(returnMessage);
        }

        binding.idEditDate.setOnClickListener(this);
        binding.btLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == binding.idEditDate)
        {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    //String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
                    String date = DateFormatCls.inputFormat.format(calendar.getTime());
                    try
                    {
                        binding.idEditDate.setText(date);
                    }
                    catch (Exception e)
                    {
                        e.getStackTrace();
                    }
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.getDatePicker().setMinDate(calendarToday.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate((calendarToday.getTimeInMillis()) + (1000*60*60*24*24));
            datePickerDialog.show();
        }
        else if(view == binding.btLogin)
        {
            String source = binding.idEditSource.getText().toString();
            String destination = binding.idEditDestination.getText().toString();
            String date = binding.idEditDate.getText().toString();

            if(source.equals(""))
            {
                PrintMessage.showErrorMessage(getActivity(),getString(R.string.please_enter_source),binding.idLayRoot);
            }
            else if(destination.equals(""))
            {
                PrintMessage.showErrorMessage(getActivity(),getString(R.string.please_enter_destination),binding.idLayRoot);
            }
            else if(date.equals(""))
            {
                PrintMessage.showErrorMessage(getActivity(),getString(R.string.please_enter_date),binding.idLayRoot);
            }
            else
            {
//                Intent intent = new Intent(getActivity(), Activity_Bus_List.class);
//                intent.putExtra("source",source);
//                intent.putExtra("destination",destination);
//                intent.putExtra("date",date);
//                startActivity(intent);
            }
        }
    }

    private void onSetObservables()
    {
        // City
        viewModel.mutableArrayListCity.removeObservers(getViewLifecycleOwner());
        viewModel.mutableArrayListCity.observe(getViewLifecycleOwner(), new Observer<ApiResponceResource<ArrayList<City>>>() {
            @Override
            public void onChanged(ApiResponceResource<ArrayList<City>> response) {
                switch (response.status)
                {
                    case LOADING:
                        mProgressDialogCity.showProgressBar(true);
                        break;

                    case AUTHENTICATED:
                        mProgressDialogCity.showProgressBar(false);
                        adapterSettingEnterDestination(response.data);
                        adapterSettingEnterSource(response.data);
                        break;

                    case ERROR:
                        mProgressDialogCity.showProgressBar(false);
                        PrintMessage.showErrorMessage(getActivity(),response.message,binding.idLayRoot);
                }
            }
        });

        // Coupons List
        viewModel.mutableCouponsList.removeObservers(getViewLifecycleOwner());
        viewModel.mutableCouponsList.observe(getViewLifecycleOwner(), new Observer<ApiResponceResource<ArrayList<Coupons>>>() {
            @Override
            public void onChanged(ApiResponceResource<ArrayList<Coupons>> response)
            {
                switch (response.status)
                {
                    case LOADING:
                        //mProgressDialogOffers.showProgressBar(true);
                        break;

                    case AUTHENTICATED:
                        //mProgressDialogOffers.showProgressBar(false);
                        if(response.data.size() > 0)
                        {
                            binding.idTxtCoupons.setVisibility(View.VISIBLE);

                            arrayList.clear();
                            arrayList.addAll(response.data);
                            adapter_coupon.setCoupons(arrayList);
                        }
                        else
                        {
                            binding.idTxtCoupons.setVisibility(View.GONE);
                        }
                        break;

                    case ERROR:
                        //mProgressDialogOffers.showProgressBar(false);
                        PrintMessage.showErrorMessage(getActivity(),response.message,binding.idLayRoot);
                }
            }
        });
    }

    private void adapterSettingEnterSource(List list)
    {
        ArrayList arrayList = new ArrayList<>();

        arrayList.addAll(list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayList);
        binding.idEditSource.setAdapter(adapter);

        binding.idEditSource.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                InputMethodManager imm = (InputMethodManager) Home_Fragment.this.getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                //PrintMessage.showErrorMessage(getActivity(),adapterView.getItemAtPosition(position).toString(),binding.idLayRoot);
            }
        });
    }

    private void adapterSettingEnterDestination(List list)
    {
        ArrayList arrayList = new ArrayList<>();

        arrayList.addAll(list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayList);
        binding.idEditDestination.setAdapter(adapter);

        binding.idEditDestination.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                InputMethodManager imm = (InputMethodManager) Home_Fragment.this.getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                //PrintMessage.showErrorMessage(getActivity(),"Selected "+adapterView.getItemAtPosition(position).toString(),binding.idLayRoot);
            }
        });
    }

    private void dialogNoInternet()
    {
        if(CheckInternetConnection.isConnectionToInternet(getActivity()) == false)
        {
            DialogBox_NoInternetConnection.dialogOkMessage(getActivity());
        }
    }

    private void recycleCoupon()
    {
        arrayList = new ArrayList<Coupons>();

        binding.recyclerviewCoupon.setAdapter(adapter_coupon);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerviewCoupon.setLayoutManager(manager);
    }
}