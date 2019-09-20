package com.example.coinloft.data;

import androidx.annotation.NonNull;

import com.example.coinloft.db.CoinEntity;

import java.util.List;

import io.reactivex.Observable;

public interface CoinsRepository {

    @NonNull
    Observable<List<CoinEntity>> listings(@NonNull String convert);
}