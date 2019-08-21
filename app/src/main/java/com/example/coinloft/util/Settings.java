package com.example.coinloft.util;

import android.content.Context;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public interface Settings {

    @NotNull
    static Settings of(@Nullable Context context) {
        return new SettingsImpl(context);
    }

    boolean shouldShowWelcomeScreen();

    void doNotShowWelcomeScreenNextTime();
}

