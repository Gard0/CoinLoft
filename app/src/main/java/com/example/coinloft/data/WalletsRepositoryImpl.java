package com.example.coinloft.data;

import androidx.annotation.NonNull;

import com.example.coinloft.db.CoinEntity;
import com.example.coinloft.db.LoftDb;
import com.example.coinloft.db.Transaction;
import com.example.coinloft.db.Wallet;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
class WalletsRepositoryImpl implements WalletsRepository {

    private final LoftDb mDb;

    @Inject
    WalletsRepositoryImpl(LoftDb db) {
        mDb = db;
    }

    @NonNull
    @Override
    public Observable<List<Wallet.View>> wallets() {
        return mDb.coins().coinsCount()
                .flatMap(count -> mDb.wallets()
                        .fetchAllWallets());
    }

    @NonNull
    @Override
    public Observable<List<Transaction.View>> transactions(long walletId) {
        return mDb.wallets().fetchAllTransactions(walletId);
    }

    @NonNull
    @Override
    public Single<CoinEntity> findNextCoin() {
        return mDb.wallets().findNextCoin();
    }

    @NonNull
    @Override
    public Single<Long> saveWallet(Wallet wallet) {
        return mDb.wallets().insertWallet(wallet);
    }

    @NonNull
    @Override
    public Completable saveTransactions(List<Transaction> transactions) {
        return mDb.wallets().insertTransactions(transactions);
    }

}