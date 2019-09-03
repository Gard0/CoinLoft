package com.example.coinloft.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.coinloft.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    MainNavigator mNavigator;

    @Inject
    ViewModelProvider.Factory mVmFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainComponent.builder()
                .activity(this)
                .build()
                .inject(this);

        setContentView(R.layout.activity_main);

        final MainViewModel viewModel = ViewModelProviders
                .of(this, mVmFactory)
                .get(MainViewModel.class);

        setSupportActionBar(findViewById(R.id.toolbar));

        final BottomNavigationView navView = findViewById(R.id.bottom_nav);
        navView.setOnNavigationItemSelectedListener(menuItem -> {
            viewModel.submitSelectedId(menuItem.getItemId());
            return true;
        });

        viewModel.title().observe(this, title -> Objects
                .requireNonNull(getSupportActionBar())
                .setTitle(title));

        viewModel.selectedId().observe(this, mNavigator::navigateTo);
        viewModel.selectedId().observe(this, navView::setSelectedItemId);
    }

}