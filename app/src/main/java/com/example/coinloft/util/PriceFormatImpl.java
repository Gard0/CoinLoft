package com.example.coinloft.util;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.example.coinloft.data.Currencies;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import dagger.Reusable;

@Reusable
class PriceFormatImpl implements PriceFormat {

    private Currencies mCurrencies;

    @Inject
    PriceFormatImpl(Currencies currencies) {
        mCurrencies = currencies;
    }

    @NonNull
    @Override
    public String format(double value) {
        final Pair<Currency, Locale> pair = mCurrencies.getCurrent();
        final Locale locale = Objects.requireNonNull(pair.second);
        return NumberFormat.getCurrencyInstance(locale).format(value);
    }

}