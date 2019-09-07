package com.example.coinloft.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.coinloft.db.CoinEntity;
import com.example.coinloft.util.Consumer;

import java.util.List;

public interface CoinsRepository {

    void listings(@NonNull String convert,
                  @NonNull Consumer<List<Coin>> onSuccess,
                  @NonNull Consumer<Throwable> onError);

    LiveData<List<CoinEntity>> listings();

    void refresh(@NonNull String convert, @NonNull Runnable onSucces, @NonNull Consumer<Throwable> onError);
}