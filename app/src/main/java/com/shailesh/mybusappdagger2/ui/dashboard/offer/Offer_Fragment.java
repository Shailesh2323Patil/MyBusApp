package com.shailesh.mybusappdagger2.ui.dashboard.offer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shailesh.mybusappdagger2.ApiResponceResource;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.adapter.Adapter_Offer;
import com.shailesh.mybusappdagger2.databinding.FragmentOfferBinding;
import com.shailesh.mybusappdagger2.model.Coupons;
import com.shailesh.mybusappdagger2.ui.dashboard.Dashboard_ViewModel;
import com.shailesh.mybusappdagger2.util.PrintMessage;
import com.shailesh.mybusappdagger2.util.dialog.DialogBox_NoInternetConnection;
import com.shailesh.mybusappdagger2.util.no_internet_connection.CheckInternetConnection;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;
import com.shailesh.mybusappdagger2.view_model.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;

public class Offer_Fragment extends DaggerFragment
{
    FragmentOfferBinding binding;
    Dashboard_ViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    ProgressDialogCustom mProgressDialog;

    Adapter_Offer adapter_offer;
    ArrayList<Coupons> arrayList;

    public Offer_Fragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_offer, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setRecycleView();
        onSetObservables();
        viewModel.apiGetOffers_Offers();
        dialogNoInternet();
    }

    private void setRecycleView()
    {
        arrayList = new ArrayList<Coupons>();

        adapter_offer = new Adapter_Offer(getActivity(), arrayList);
        binding.idRecycleOffers.setAdapter(adapter_offer);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.idRecycleOffers.setLayoutManager(manager);
    }

    private void onSetObservables()
    {
        // Coupons List
        viewModel.mutableCouponsListOffers.removeObservers(getViewLifecycleOwner());
        viewModel.mutableCouponsListOffers.observe(getViewLifecycleOwner(), new Observer<ApiResponceResource<ArrayList<Coupons>>>() {
            @Override
            public void onChanged(ApiResponceResource<ArrayList<Coupons>> responce)
            {
                switch (responce.status)
                {
                    case LOADING:
                        mProgressDialog.showProgressBar(true);
                        break;

                    case AUTHENTICATED:
                        mProgressDialog.showProgressBar(false);

                        ArrayList<Coupons> coupons = responce.data;

                        if(coupons.size() > 0)
                        {
                            arrayList.clear();
                            arrayList.addAll(coupons);
                            adapter_offer.notifyDataSetChanged();
                        }
                        else
                        {
                            binding.idRecycleOffers.setVisibility(View.GONE);
                            binding.idTxtNoDataFound.setVisibility(View.VISIBLE);
                        }
                        break;

                    case ERROR:
                        mProgressDialog.showProgressBar(false);
                        PrintMessage.showErrorMessage(getActivity(),responce.message,binding.idLayRoot);
                }
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
}