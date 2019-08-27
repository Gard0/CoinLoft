package com.example.coinloft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coinloft.R;
import com.example.coinloft.util.Settings;

public class SplashActivity extends AppCompatActivity {


    private static final int DELAY_START_ACTIVITY = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Settings settings = Settings.of(this);
        new Handler().postDelayed(() -> {
            if (settings.shouldShowWelcomeScreen()) {
                startActivity(new Intent(this, WelcomeActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
        }, DELAY_START_ACTIVITY);
    }
}
