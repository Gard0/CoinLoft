package com.example.coinloft.data;

import androidx.annotation.NonNull;

import com.example.coinloft.util.Consumer;

import java.util.List;

public interface CoinsRepository {

    void listings(@NonNull String convert,
                  @NonNull Consumer<List<Coin>> onSuccess,
                  @NonNull Consumer<Throwable> onError);

}