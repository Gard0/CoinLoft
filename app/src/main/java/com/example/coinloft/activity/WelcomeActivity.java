package com.example.coinloft.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.coinloft.adapter.WelcomePagerAdapter;

import static com.example.coinloft.R.id;
import static com.example.coinloft.R.layout;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TextView mButtonStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_welcome);
        mViewPager = findViewById(id.pager);
        mViewPager.setAdapter((new WelcomePagerAdapter(getLayoutInflater())));
        mButtonStart = findViewById((id.button_start));
        mButtonStart.setOnClickListener(View -> {
            final SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(this);
            prefs.edit().putBoolean(SplashActivity.KEY_SHOW_WELCOME_SCREEN, false).apply();
            final Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }
}