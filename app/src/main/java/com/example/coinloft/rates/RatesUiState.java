package com.example.coinloft.rates;

import androidx.annotation.Nullable;

import com.example.coinloft.db.CoinEntity;
import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

@AutoValue
abstract class RatesUiState {

    static RatesUiState loading() {
        return new AutoValue_RatesUiState(Collections.emptyList(), null, true);
    }

    static RatesUiState success(List<CoinEntity> rates) {
        return new AutoValue_RatesUiState(rates, null, false);
    }

    static RatesUiState failure(Throwable e) {
        return new AutoValue_RatesUiState(Collections.emptyList(), e.getMessage(), false);
    }

    abstract List<CoinEntity> rates();

    @Nullable
    abstract String error();

    abstract boolean isRefreshing();

}
