package com.example.coinloft.main;

import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.example.coinloft.R;
import com.example.coinloft.converter.ConverterFragment;
import com.example.coinloft.rates.RatesFragment;
import com.example.coinloft.util.Supplier;
import com.example.coinloft.wallets.WalletsFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
interface MainModule {

    @Provides
    @Reusable
    static SparseArrayCompat<Supplier<Fragment>> fragments() {
        final SparseArrayCompat<Supplier<Fragment>> fragments = new SparseArrayCompat<>();
        fragments.put(R.id.wallets, WalletsFragment::new);
        fragments.put(R.id.rates, RatesFragment::new);
        fragments.put(R.id.converter, ConverterFragment::new);
        return fragments;
    }

    @Binds
    @IntoMap
    @ClassKey(MainViewModel.class)
    ViewModel mainViewModel(MainViewModel impl);

}