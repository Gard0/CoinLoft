package com.example.coinloft.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        CoinEntity.class,
        Wallet.class,
        Transaction.class
}, views = {
        Wallet.View.class,
        Transaction.View.class
}, version = 5)
public abstract class LoftDb extends RoomDatabase {

    public abstract CoinsDao coins();

    public abstract WalletsDao wallets();

}