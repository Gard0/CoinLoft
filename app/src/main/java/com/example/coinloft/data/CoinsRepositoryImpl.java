package com.example.coinloft.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.coinloft.db.CoinEntity;
import com.example.coinloft.db.LoftDb;
import com.example.coinloft.util.Consumer;

import java.util.ArrayList;
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

    private final LoftDb mDb;

    @Inject
    CoinsRepositoryImpl(CoinMarketCapApi api, LoftDb db) {
        mApi = api;
        mDb = db;
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

    @Override
    public LiveData<List<CoinEntity>> listings() {
        return mDb.coins().fetchAll();
    }

    @Override
    public void refresh(@NonNull String convert, @NonNull Runnable onSuccess, @NonNull Consumer<Throwable> onError) {
        listings(convert, coins -> {
            final List<CoinEntity> entities = new ArrayList<>();
            for (final Coin coin : coins) {
                double price = 0d;
                double change24 = 0d;
                final Quote quote = coin.getQuotes().get(convert);
                if (quote != null) {
                    price = quote.getPrice();
                    change24 = quote.getChange24h();
                }
                entities.add(CoinEntity.create(
                        coin.getId(),
                        coin.getSymbol(),
                        price,
                        change24
                ));
            }
            mDb.coins().insertAll(entities);
            onSuccess.run();
        }, onError);
    }

}