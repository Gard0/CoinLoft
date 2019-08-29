package com.example.coinloft.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.coinloft.R;

public class CurrencyDialog extends DialogFragment {

    static final String TAG = "CurrencyDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.currency_dialog, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RatesViewModel ratesViewModel = ViewModelProviders
                .of(requireParentFragment(), new RatesViewModel.Factory(requireContext()))
                .get(RatesViewModel.class);


    }

}