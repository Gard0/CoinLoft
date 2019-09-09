package com.example.coinloft.rates;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.example.coinloft.AppComponent;
import com.example.coinloft.data.CoinsRepository;
import com.example.coinloft.data.Currencies;
import com.example.coinloft.main.MainViewModel;

import java.util.Locale;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
interface RatesModule {

    @Provides
    @Reusable
    static AppComponent appComponent(Fragment fmt) {
        return AppComponent.from(fmt.requireContext());
    }

    @Provides
    static CoinsRepository coinsRepository(AppComponent appComponent) {
        return appComponent.coinsRepository();
    }

    @Provides
    static Currencies currencies(AppComponent appComponent) {
        return appComponent.currencies();
    }

    @Provides
    static Locale locale(AppComponent appComponent) {
        return appComponent.locale().get();
    }

    @Binds
    @IntoMap
    @ClassKey(MainViewModel.class)
    ViewModel mainViewModel(MainViewModel impl);

    @Binds
    @IntoMap
    @ClassKey(RatesViewModel.class)
    ViewModel ratesViewModel(RatesViewModel impl);

}