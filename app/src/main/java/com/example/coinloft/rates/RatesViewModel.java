package com.example.coinloft.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.coinloft.data.CoinsRepository;
import com.example.coinloft.data.Currencies;
import com.example.coinloft.data.Currency;
import com.example.coinloft.rx.RxSchedulers;

import javax.inject.Inject;

import dagger.Reusable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

@Reusable
class RatesViewModel extends ViewModel {

    private final CoinsRepository mRepository;

    private final Currencies mCurrencies;

    private final RxSchedulers mSchedulers;

    private final Subject<Boolean> mSourceOfTruth;

    private final Observable<RatesUiState> mUiState;

    @Inject
    RatesViewModel(CoinsRepository repository,
                   Currencies currencies,
                   RxSchedulers schedulers) {
        mRepository = repository;
        mCurrencies = currencies;
        mSchedulers = schedulers;

        mSourceOfTruth = BehaviorSubject.createDefault(true);

        mUiState = mSourceOfTruth
                .observeOn(schedulers.io())
                .flatMap(refresh -> mCurrencies.current())
                .map(Currency::code)
                .flatMap(currencyCode -> mRepository
                        .listings(currencyCode)
                        .map(RatesUiState::success)
                        .onErrorReturn(RatesUiState::failure)
                        .startWith(RatesUiState.loading()))
                .subscribeOn(schedulers.io());
    }

    void refresh() {
        mSourceOfTruth.onNext(true);
    }

    @NonNull
    Observable<RatesUiState> uiState() {
        return mUiState.observeOn(mSchedulers.main());
    }
}