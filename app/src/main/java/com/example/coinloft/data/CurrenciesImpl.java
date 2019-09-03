package com.example.coinloft.data;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import java.util.Currency;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
class CurrenciesImpl implements Currencies {

    private Provider<Locale> mLocale;

    @Inject
    CurrenciesImpl(Provider<Locale> locale) {
        mLocale = locale;
    }

    @NonNull
    @Override
    public Pair<Currency, Locale> getCurrent() {
        final Locale locale = mLocale.get();
        return Pair.create(
                Currency.getInstance(locale),
                locale
        );
    }

}