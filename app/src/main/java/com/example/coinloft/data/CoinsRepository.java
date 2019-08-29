package com.example.coinloft.data;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.function.Consumer;

public interface CoinsRepository {

    @NonNull
    static CoinsRepository get() {
        return CoinsRepositoryImpl.get();
    }

    void listings(@NonNull String convert,
                  @NonNull Consumer<List<Coin>> onSuccess,
                  @NonNull Consumer<Throwable> onError);

}