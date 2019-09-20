package com.example.coinloft.rx;

import dagger.Binds;
import dagger.Module;

@Module
public interface RxModule {

    @Binds
    RxSchedulers schedulers(RxSchedulersImpl impl);

}