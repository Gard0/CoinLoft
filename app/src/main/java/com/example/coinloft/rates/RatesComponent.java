package com.example.coinloft.rates;

import androidx.fragment.app.Fragment;

import com.example.coinloft.util.UtilModule;
import com.example.coinloft.vm.ViewModelModule;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
        RatesModule.class,
        ViewModelModule.class,
        UtilModule.class
})
interface RatesComponent {

    void inject(RatesFragment fmt);

    void inject(CurrencyDialog fmt);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder fragment(Fragment fmt);

        RatesComponent build();

    }

}