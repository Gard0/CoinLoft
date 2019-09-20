package com.example.coinloft.wallets;

import androidx.fragment.app.Fragment;

import com.example.coinloft.util.UtilModule;
import com.example.coinloft.vm.ViewModelModule;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
        WalletsModule.class,
        ViewModelModule.class,
        UtilModule.class
})
interface WalletsComponent {

    void inject(WalletsFragment fmt);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder fragment(Fragment fmt);

        WalletsComponent build();

    }

}