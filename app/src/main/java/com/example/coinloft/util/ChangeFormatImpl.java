package com.example.coinloft.util;

import androidx.annotation.NonNull;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Reusable;

@Reusable
class ChangeFormatImpl implements ChangeFormat {

    private final Provider<Locale> mLocale;

    @Inject
    ChangeFormatImpl(Provider<Locale> locale) {
        mLocale = locale;
    }

    @NonNull
    @Override
    public String format(double value) {
        return String.format(mLocale.get(), "%.4f%%", value);
    }

}