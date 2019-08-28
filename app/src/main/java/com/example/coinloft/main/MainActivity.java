package com.example.coinloft.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.coinloft.R;
import com.example.coinloft.converter.ConverterFragment;
import com.example.coinloft.fragment.ExchangeRatesFragment;
import com.example.coinloft.fragment.WalletsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;
import java.util.function.Supplier;

public class MainActivity extends AppCompatActivity {
    private static final SparseArrayCompat<Supplier<Fragment>> FRAGMENTS;

    static {
        FRAGMENTS = new SparseArrayCompat<>();
        FRAGMENTS.put(R.id.wallets, WalletsFragment::new);
        FRAGMENTS.put(R.id.rates, ExchangeRatesFragment::new);
        FRAGMENTS.put(R.id.converter, ConverterFragment::new);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel viewModel = ViewModelProviders
                .of(this)
                .get(MainViewModel.class);

        setSupportActionBar(findViewById(R.id.toolbar));

        final BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            viewModel.submitSelectedId(menuItem.getItemId());
            return true;
        });
        Objects.requireNonNull(viewModel.title()).observe(this, title -> Objects
                .requireNonNull(getSupportActionBar())
                .setTitle(title));

        Objects.requireNonNull(viewModel.selectedId()).observe(this, this::replaceFragment);
        Objects.requireNonNull(viewModel.selectedId()).observe(this, navigationView::setSelectedItemId);


    }

    @SuppressLint("NewApi")
    private void replaceFragment(int itemId) {
        final Supplier<Fragment> factory = FRAGMENTS.get(itemId);
        if (factory != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_host, factory.get())
                    .commit();
        }
    }
}
