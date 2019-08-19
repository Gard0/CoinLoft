package com.example.coinloft.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coinloft.R;

public class SplashActivity extends AppCompatActivity {


    public static final String KEY_SHOW_WELCOME_SCREEN = "show_welcome_screen";
    private static final int DELAY_START_ACTIVITY = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        new Handler().postDelayed(() -> {
            if (prefs.getBoolean(KEY_SHOW_WELCOME_SCREEN, true)) {
                startActivity(new Intent(this, WelcomeActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
        }, DELAY_START_ACTIVITY);
    }
}
