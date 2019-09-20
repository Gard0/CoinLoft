package com.example.coinloft.data;


import androidx.annotation.NonNull;

import com.example.coinloft.db.CoinEntity;
import com.example.coinloft.db.Transaction;
import com.example.coinloft.db.Wallet;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface WalletsRepository {

    @NonNull
    Observable<List<Wallet.View>> wallets();

    @NonNull
    Observable<List<Transaction.View>> transactions(long walletId);

    @NonNull
    Single<CoinEntity> findNextCoin();

    @NonNull
    Single<Long> saveWallet(Wallet wallet);

    @NonNull
    Completable saveTransactions(List<Transaction> transactions);

}