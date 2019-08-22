package com.example.coinloft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.coinloft.adapter.WelcomePagerAdapter;
import com.example.coinloft.util.Settings;

import static com.example.coinloft.R.id;
import static com.example.coinloft.R.layout;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_welcome);

        ViewPager mViewPager = findViewById(id.pager);
        mViewPager.setAdapter((new WelcomePagerAdapter(getLayoutInflater())));

        TextView mButtonStart = findViewById((id.button_start));
        mButtonStart.setOnClickListener(View -> {
            Settings.of(View.getContext()).doNotShowWelcomeScreenNextTime();
            final Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }
}
