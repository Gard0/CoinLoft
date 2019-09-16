package com.example.coinloft.util;

import androidx.annotation.NonNull;

import com.example.coinloft.BuildConfig;

import java.util.Locale;

import javax.inject.Inject;

import dagger.Reusable;

@Reusable
class ImgUrlFormatImpl implements ImgUrlFormat {

    @Inject
    ImgUrlFormatImpl() {
    }

    @NonNull
    @Override
    public String format(long id) {
        return String.format(Locale.US, "%scoins/64x64/%d.png", BuildConfig.CMC_IMG_ENDPOINT, id);
    }

}