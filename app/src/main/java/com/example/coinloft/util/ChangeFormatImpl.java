package com.example.coinloft.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;

import java.util.Locale;

public class ChangeFormatImpl implements ChangeFormat {

    private final Locale mLocale;

    public ChangeFormatImpl(@NonNull Context context) {
        final LocaleListCompat locales = ConfigurationCompat
                .getLocales(context.getResources()
                        .getConfiguration());
        mLocale = locales.get(0);
    }

    @NonNull
    @Override
    public String format(double value) {
        return String.format(mLocale, "%.4f%%", value);
    }

}