package com.example.coinloft.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.coinloft.R;
import com.example.coinloft.main.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RatesFragment extends Fragment {

    private MainViewModel mMainViewModel;

    private RatesViewModel mRatesViewModel;

    private RatesAdapter mRatesAdapter;

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainViewModel = ViewModelProviders
                .of(requireActivity())
                .get(MainViewModel.class);
        mRatesViewModel = ViewModelProviders
                .of(this, new RatesViewModel.Factory(requireContext()))
                .get(RatesViewModel.class);
        mRatesAdapter = new RatesAdapter();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rates, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMainViewModel.submitTitle(getString(R.string.rates));
        mRecyclerView = view.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.swapAdapter(mRatesAdapter, false);
        mRecyclerView.setHasFixedSize(true);

        final SwipeRefreshLayout refresher = view.findViewById(R.id.refresher);
        refresher.setOnRefreshListener(mRatesViewModel::refresh);

        mRatesViewModel.error().observe(this, error ->
                Snackbar.make(view, error.getMessage(), Snackbar.LENGTH_SHORT).show());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.rates, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.currency == item.getItemId()) {
            final CurrencyDialog dialog = new CurrencyDialog();
            dialog.show(getChildFragmentManager(), CurrencyDialog.TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.swapAdapter(null, false);
    }

}