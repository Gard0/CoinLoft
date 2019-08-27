package com.example.coinloft.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinloft.R;
import com.example.coinloft.adapter.WelcomePagerAdapter;
import com.example.coinloft.util.Settings;

import org.jetbrains.annotations.Nullable;

public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final RecyclerView pager = findViewById(R.id.pager);
        pager.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false));
        pager.setAdapter(new WelcomePagerAdapter());
        new PagerSnapHelper().attachToRecyclerView(pager);

        findViewById(R.id.button_start).setOnClickListener(view -> {
            Settings.of(view.getContext()).doNotShowWelcomeScreenNextTime();
            final Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

}