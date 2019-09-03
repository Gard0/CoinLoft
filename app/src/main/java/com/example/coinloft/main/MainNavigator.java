package com.example.coinloft.main;

import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.coinloft.R;
import com.example.coinloft.util.Supplier;

import javax.inject.Inject;

class MainNavigator {

    private final FragmentActivity mActivity;

    private final SparseArrayCompat<Supplier<Fragment>> mFragments;

    @Inject
    MainNavigator(FragmentActivity activity,
                  SparseArrayCompat<Supplier<Fragment>> fragments) {
        mActivity = activity;
        mFragments = fragments;
    }

    void navigateTo(int id) {
        final Supplier<Fragment> factory = mFragments.get(id);
        if (factory != null) {
            mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_host, factory.get())
                    .commit();
        }
    }

}