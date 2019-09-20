package com.example.coinloft.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;

public interface Currencies {

    @NonNull
    List<Currency> getAvailableCurrencies();

    @NonNull
    Currency getCurrent();

    void setDefault(@NonNull Currency currency);

    @Nullable
    Observable<Currency> current();

}