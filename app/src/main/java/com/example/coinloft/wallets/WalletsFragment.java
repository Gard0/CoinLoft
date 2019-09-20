package com.example.coinloft.wallets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.coinloft.R;
import com.example.coinloft.main.MainViewModel;
import com.example.coinloft.util.PagerDecoration;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class WalletsFragment extends Fragment {

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Inject
    ViewModelProvider.Factory mVmFactory;

    @Inject
    WalletsAdapter mWalletsAdapter;

    @Inject
    TransactionsAdapter mTransactionsAdapter;

    private MainViewModel mMainViewModel;

    private WalletsViewModel mWalletsViewModel;

    private RecyclerView mWallets;

    private RecyclerView mTransactions;

    private SnapHelper mWalletsSnapHelper;

    private RecyclerView.OnScrollListener mOnWalletsScroll;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerWalletsComponent.builder()
                .fragment(this)
                .build()
                .inject(this);

        mMainViewModel = ViewModelProviders
                .of(requireActivity(), mVmFactory)
                .get(MainViewModel.class);

        mWalletsViewModel = ViewModelProviders
                .of(this, mVmFactory)
                .get(WalletsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMainViewModel.submitTitle(getString(R.string.wallets));

        mWallets = view.findViewById(R.id.wallets);
        mWallets.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mWallets.addItemDecoration(new PagerDecoration(view.getContext(), 16));

        mWalletsSnapHelper = new PagerSnapHelper();
        mWalletsSnapHelper.attachToRecyclerView(mWallets);

        mWallets.swapAdapter(mWalletsAdapter, false);

        final View walletsCard = view.findViewById(R.id.wallet_card);
        mDisposable.add(mWalletsViewModel.wallets().subscribe(wallets -> {
            walletsCard.setVisibility(wallets.isEmpty() ? View.VISIBLE : View.GONE);
            mWalletsAdapter.submitList(wallets);
        }));

        mOnWalletsScroll = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    final View snapView = mWalletsSnapHelper
                            .findSnapView(recyclerView.getLayoutManager());
                    if (snapView != null) {
                        mWalletsViewModel.submitWalletId(recyclerView.getChildItemId(snapView));
                    }
                }
            }
        };

        mWallets.addOnScrollListener(mOnWalletsScroll);

        mTransactions = view.findViewById(R.id.transactions);
        mTransactions.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mTransactions.setHasFixedSize(true);
        mTransactions.swapAdapter(mTransactionsAdapter, false);

        mDisposable.add(mWalletsViewModel.transactions()
                .subscribe(mTransactionsAdapter::submitList));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.wallets, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.add_wallet == item.getItemId()) {
            mDisposable.add(mWalletsViewModel
                    .createNextWallet()
                    .subscribe(
                            () -> Toast.makeText(requireContext(), R.string.wallet_created, Toast.LENGTH_SHORT).show(),
                            e -> Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show()
                    ));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        mWalletsSnapHelper.attachToRecyclerView(null);
        mWallets.removeOnScrollListener(mOnWalletsScroll);
        mWallets.swapAdapter(null, false);
        mTransactions.swapAdapter(null, false);
        super.onDestroyView();
    }

}