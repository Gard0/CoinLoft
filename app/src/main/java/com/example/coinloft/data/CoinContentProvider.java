package com.example.coinloft.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CoinContentProvider extends ContentProvider {


    @Override
    public boolean onCreate() {
//        final Cursor cursor = getContext().getContentResolver()
//            .query(Uri.parse("content://com.loftschool.loftcoin19/rates"),
//                null, null, null, null);
        return false;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // content://com.loftschool.loftcoin19/rates   // dir
        // content://com.loftschool.loftcoin19/rates/1 // item
//        if (dir) {
//            return "vnd.android.cursor.dir/vnd." + BuildConfig.APPLICATION_ID;
//        } else {
//            return "vnd.android.cursor.item/vnd." + BuildConfig.APPLICATION_ID;
//        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // content://com.loftschool.loftcoin19/rates
        // long id = database.insert();
        // content://com.loftschool.loftcoin19/rates/{id}
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        // content://com.loftschool.loftcoin19/rates
        // content://com.loftschool.loftcoin19/rates/123 -> 1
        // db.query("rates", projection, selection, selectionArgs, sortOrder)
        // SELECT * FROM rates WHERE {symbol = ?} ORDER BY {price ASC};
        //       id | symbol | price | change
        //       1  | BTC    | 10000 | 5
        //       2  | ETH    | 1000  | 10
        // ->    3  | XRP    | 500   | 7
        //                             ^
        // Cursor c = db.query(...);
        // if (c.moveToFirst()) {
        //    int idIndex = c.getColumnIndex("id");
        //    do {
        //      c.getLong(idIndex); -> 1
        //    } while(c.moveToNext());
        // }
        return null;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

}