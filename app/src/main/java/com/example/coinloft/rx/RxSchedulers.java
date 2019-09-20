package com.example.coinloft.rx;

import androidx.annotation.NonNull;

import io.reactivex.Scheduler;

public interface RxSchedulers {
    @NonNull
    Scheduler io();

    @NonNull
    Scheduler main();
}
