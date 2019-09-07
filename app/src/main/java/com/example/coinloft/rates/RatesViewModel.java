package com.example.coinloft.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coinloft.data.CoinsRepository;
import com.example.coinloft.data.Currencies;
import com.example.coinloft.data.Currency;
import com.example.coinloft.db.CoinEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.Reusable;

@Reusable
class RatesViewModel extends ViewModel {

    private final CoinsRepository mRepository;

    private final Currencies mCurrencies;

    private final LiveData<List<CoinEntity>> mDataSet;

    private final MutableLiveData<Boolean> mOnTheFly = new MutableLiveData<>();

    private final MutableLiveData<Throwable> mError = new MutableLiveData<>();

    @Inject
    RatesViewModel(CoinsRepository repository,
                   Currencies currencies) {
        mRepository = repository;
        mCurrencies = currencies;
        mDataSet = mRepository.listings();
        refresh();
    }

    void refresh() {
        mOnTheFly.postValue(true);
        final Currency currency = mCurrencies.getCurrent();
        mRepository.refresh(currency.code(),
                () -> mOnTheFly.postValue(false),
                error -> {
                    mError.postValue(error);
                    mOnTheFly.postValue(false);
                });
    }

    @NonNull
    LiveData<Boolean> onTheFly() {
        return mOnTheFly;
    }

    @NonNull
    LiveData<List<CoinEntity>> dataSet() {
        return mDataSet;
    }

    @NonNull
    LiveData<Throwable> error() {
        return mError;
    }

    void updateCurrency(@NonNull Currency currency) {
        mCurrencies.setCurrent(currency);
        refresh();
    }

}