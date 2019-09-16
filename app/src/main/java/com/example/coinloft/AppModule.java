package com.example.coinloft;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.core.os.ConfigurationCompat;
import androidx.room.Room;

import com.example.coinloft.db.LoftDb;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
interface AppModule {
    @NonNull
    @Provides
    static Context context(Application app) {
        return app.getApplicationContext();
    }

    @NonNull
    @Provides
    static Locale locale(Context context) {
        final Configuration configuration = context.getResources().getConfiguration();
        return ConfigurationCompat.getLocales(configuration).get(0);
    }

    @NonNull
    @Provides
    @Singleton
    static LoftDb loftDb(Context context) {
        return Room.databaseBuilder(context, LoftDb.class, "loft")
                .fallbackToDestructiveMigration()
                .build();
    }

}