package com.example.coinloft.util;

import androidx.annotation.NonNull;

import com.example.coinloft.data.Currencies;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Reusable;

@Reusable
class PriceFormatImpl implements PriceFormat {

    private final Currencies mCurrencies;

    private final Provider<Locale> mLocale;

    @Inject
    PriceFormatImpl(Currencies currencies, Provider<Locale> locale) {
        mCurrencies = currencies;
        mLocale = locale;
    }

    @NonNull
    @Override
    public String format(double value) {
        return format(value, mCurrencies.getCurrent().sign());
    }

    @Override
    public String format(double value, String sign) {
        final NumberFormat format = NumberFormat.getCurrencyInstance(mLocale.get());
        final DecimalFormat decimalFormat = (DecimalFormat) format;
        final DecimalFormatSymbols symbols = decimalFormat.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(sign);
        decimalFormat.setDecimalFormatSymbols(symbols);
        return format.format(value);
    }

}