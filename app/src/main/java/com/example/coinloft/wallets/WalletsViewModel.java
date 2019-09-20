package com.example.coinloft.wallets;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.coinloft.data.WalletsRepository;
import com.example.coinloft.db.CoinEntity;
import com.example.coinloft.db.Transaction;
import com.example.coinloft.db.Wallet;
import com.example.coinloft.rx.RxSchedulers;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

class WalletsViewModel extends ViewModel {

    private static final Random PRNG = new SecureRandom();

    private final WalletsRepository mRepository;

    private final RxSchedulers mSchedulers;

    private final BehaviorSubject<Long> mWalletId = BehaviorSubject.create();

    @Inject
    WalletsViewModel(WalletsRepository repository, RxSchedulers schedulers) {
        mRepository = repository;
        mSchedulers = schedulers;
    }

    @NonNull
    Completable createNextWallet() {
        return mRepository.findNextCoin()
                .map(this::createFakeWallet)
                .flatMap(mRepository::saveWallet)
                .map(this::generateFakeTransactions)
                .flatMapCompletable(mRepository::saveTransactions)
                .subscribeOn(mSchedulers.io())
                .observeOn(mSchedulers.main());
    }

    @NonNull
    Observable<List<Wallet.View>> wallets() {
        return mRepository.wallets()
                .subscribeOn(mSchedulers.io())
                .observeOn(mSchedulers.main());
    }

    @NonNull
    Observable<List<Transaction.View>> transactions() {
        return mWalletId
                .distinctUntilChanged()
                .flatMap(mRepository::transactions)
                .subscribeOn(mSchedulers.io())
                .observeOn(mSchedulers.main());
    }

    void submitWalletId(long id) {
        mWalletId.onNext(id);
    }

    private Wallet createFakeWallet(CoinEntity coin) {
        return Wallet.create(
                0,
                PRNG.nextDouble() * (1 + PRNG.nextInt(100)),
                coin.id()
        );
    }

    private List<Transaction> generateFakeTransactions(long walletId) {
        final int count = 1 + PRNG.nextInt(20);
        final List<Transaction> transactions = new ArrayList<>(count);
        final long now = System.currentTimeMillis();
        for (int i = 0; i < count; ++i) {
            transactions.add(Transaction.create(
                    0,
                    now - TimeUnit.HOURS.toMillis(12 + PRNG.nextInt(120)),
                    PRNG.nextDouble() * (PRNG.nextInt(100) - 50),
                    walletId
            ));
        }
        return transactions;
    }

}