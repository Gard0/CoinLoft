package com.example.coinloft;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.coinloft.data.CoinsRepository;
import com.example.coinloft.data.Currencies;
import com.example.coinloft.data.DataModule;
import com.example.coinloft.data.WalletsRepository;
import com.example.coinloft.rx.RxModule;
import com.example.coinloft.rx.RxSchedulers;

import java.util.Locale;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        DataModule.class,
        RxModule.class
})
public interface AppComponent {

    @NonNull
    static AppComponent from(@NonNull Context context) {
        final Context appContext = context.getApplicationContext();
        if (appContext instanceof LoftApp) {
            return ((LoftApp) appContext).getAppComponent();
        }
        throw new IllegalArgumentException("ApplicationContext should be an instance of LoftApp");
    }

    Provider<Locale> locale();

    CoinsRepository coinsRepository();

    WalletsRepository walletsRepository();

    Currencies currencies();

    RxSchedulers schedulers();

    @Component.Factory
    interface Factory {
        AppComponent create(@BindsInstance Application app);
    }

}