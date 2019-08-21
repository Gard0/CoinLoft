package com.example.coinloft.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

class SettingsImpl implements Settings {
    private static final String KEY_SHOW_WELCOME_SCREEN = "show_welcome_screen";
    private final SharedPreferences mPreferences;

    SettingsImpl(@Nullable Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public boolean shouldShowWelcomeScreen() {
        return mPreferences.getBoolean(KEY_SHOW_WELCOME_SCREEN, true);
    }

    @Override
    public void doNotShowWelcomeScreenNextTime() {
        mPreferences.edit().putBoolean(KEY_SHOW_WELCOME_SCREEN, false).apply();
    }
}
