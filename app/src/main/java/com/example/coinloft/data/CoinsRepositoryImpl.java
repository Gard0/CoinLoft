package com.example.coinloft.data;
import androidx.annotation.NonNull;

import com.example.coinloft.util.Consumer;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
class CoinsRepositoryImpl implements CoinsRepository {

    private final CoinMarketCapApi mApi;

    @Inject
    CoinsRepositoryImpl(CoinMarketCapApi api) {
        mApi = api;
    }

    @Override
    public void listings(@NonNull String convert,
                         @NonNull Consumer<List<Coin>> onSuccess,
                         @NonNull Consumer<Throwable> onError) {
        mApi.listings(convert).enqueue(new Callback<Listings>() {
            @Override
            public void onResponse(Call<Listings> call, Response<Listings> response) {
                final Listings listings = response.body();
                if (listings != null && listings.data != null) {
                    onSuccess.apply(Collections.unmodifiableList(listings.data));
                } else {
                    onSuccess.apply(Collections.emptyList());
                }
            }

            @Override
            public void onFailure(Call<Listings> call, Throwable t) {
                onError.apply(t);
            }
        });
    }

}