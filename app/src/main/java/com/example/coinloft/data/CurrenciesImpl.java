package com.example.coinloft.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
class CurrenciesImpl implements Currencies {

    private static final String KEY_CODE = "code";

    private final SharedPreferences mPrefs;

    @Inject
    CurrenciesImpl(Context context) {
        mPrefs = context.getSharedPreferences("currencies", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public List<Currency> getAvailableCurrencies() {
        return Arrays.asList(Currency.values());
    }

    @NonNull
    @Override
    public Currency getCurrent() {
        return Currency.valueOf(mPrefs.getString(KEY_CODE, Currency.USD.code()));
    }

    @Override
    public void setDefault(@NonNull Currency currency) {
        mPrefs.edit()
                .putString(KEY_CODE, currency.code())
                .apply();
    }

    @Nullable
    @Override
    public Observable<Currency> current() {
        return Observable.create(emitter -> {
            final SharedPreferences.OnSharedPreferenceChangeListener listener = (prefs, key) -> emitter.onNext(getCurrent());
            emitter.setCancellable(() -> mPrefs.unregisterOnSharedPreferenceChangeListener(listener));
            mPrefs.registerOnSharedPreferenceChangeListener(listener);
            emitter.onNext(getCurrent());
        });
    }

}