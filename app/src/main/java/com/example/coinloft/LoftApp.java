package com.example.coinloft;

import android.app.Application;
import android.os.StrictMode;

import com.example.coinloft.log.DebugTree;

import timber.log.Timber;

public class LoftApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG){
            StrictMode.enableDefaults();
            Timber.plant(new DebugTree());
        }
        Timber.d("%s", this);
    }

}
