package com.example.coinloft.data;
import androidx.annotation.NonNull;

import com.example.coinloft.BuildConfig;
import com.example.coinloft.db.CoinEntity;
import com.example.coinloft.db.LoftDb;
import com.example.coinloft.rx.RxSchedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
class CoinsRepositoryImpl implements CoinsRepository {

    private final CoinMarketCapApi mApi;

    private final LoftDb mDb;

    private final RxSchedulers mShedulers;

    @Inject
    CoinsRepositoryImpl(CoinMarketCapApi api, LoftDb db, RxSchedulers schedulers) {
        mApi = api;
        mDb = db;
        mShedulers = schedulers;
    }

    @NonNull
    @Override
    public Observable<List<CoinEntity>> listings(@NonNull String convert) {
        if (BuildConfig.DEBUG) {
            return mDb.coins().fetchAllCoins();
        }
        return Observable.merge(
                mDb.coins().fetchAllCoins(),
                mApi.listings(convert)
                        .map(this::fromListings)
                        .doOnNext(mDb.coins()::insertAll)
                        .skip(1)
                        .subscribeOn(mShedulers.io())
        );
    }

    private List<CoinEntity> fromListings(Listings listings) {
        if (listings != null && listings.data != null) {
            final List<CoinEntity> entities = new ArrayList<>();
            for (final Coin coin : listings.data) {
                double price = 0d;
                double change24 = 0d;
                final Iterator<Quote> quotes = coin.getQuotes().values().iterator();
                if (quotes.hasNext()) {
                    final Quote quote = quotes.next();
                    if (quote != null) {
                        price = quote.getPrice();
                        change24 = quote.getChange24h();
                    }
                }
                entities.add(CoinEntity.create(
                        coin.getId(),
                        coin.getSymbol(),
                        price,
                        change24
                ));
            }
            return Collections.unmodifiableList(entities);
        }
        return Collections.emptyList();
    }

}