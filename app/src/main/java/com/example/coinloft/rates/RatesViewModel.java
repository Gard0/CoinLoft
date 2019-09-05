package com.example.coinloft.rates;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coinloft.data.Coin;
import com.example.coinloft.data.CoinsRepository;
import com.example.coinloft.data.Currencies;
import com.example.coinloft.util.Function;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

class RatesViewModel extends ViewModel {

    private final CoinsRepository mRepository;

    private final Function<List<Coin>, List<CoinRate>> mRatesMapper;
    private final MutableLiveData<List<CoinRate>> mDataSet = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mOnTheFly = new MutableLiveData<>();
    private final MutableLiveData<Throwable> mError = new MutableLiveData<>();
    private final Currencies mCurrencies;

    @Inject
    RatesViewModel(CoinsRepository repository,
                   Function<List<Coin>, List<CoinRate>> ratesMapper,
                   Currencies currencies) {
        mRepository = repository;
        mRatesMapper = ratesMapper;
        mCurrencies = currencies;
        refresh();
    }

    void refresh() {
        mOnTheFly.postValue(true);
        final Pair<Currency, Locale> pair = mCurrencies.getCurrent();
        mRepository.listings(Objects.requireNonNull(pair.first).getCurrencyCode(), coins -> {
            mDataSet.postValue(mRatesMapper.apply(coins));
            mOnTheFly.postValue(false);
        }, error -> {
            mError.postValue(error);
            mOnTheFly.postValue(false);
        });
    }

    @NonNull
    LiveData<Boolean> onTheFly() {
        return mOnTheFly;
    }

    @NonNull
    LiveData<List<CoinRate>> dataSet() {
        return mDataSet;
    }

    @NonNull
    LiveData<Throwable> error() {
        return mError;
    }

}