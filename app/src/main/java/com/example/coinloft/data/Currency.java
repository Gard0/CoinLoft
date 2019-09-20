package com.example.coinloft.data;

import androidx.annotation.NonNull;

import com.example.coinloft.R;

public enum Currency {

    USD(R.string.usd, "$"),
    EUR(R.string.eur, "€"),
    RUB(R.string.rub, "₽");

    private final int nameResId;

    private final String sign;

    Currency(int nameResId, String sign) {
        this.nameResId = nameResId;
        this.sign = sign;
    }

    public int nameResId() {
        return nameResId;
    }

    @NonNull
    public String sign() {
        return sign;
    }

    @NonNull
    public String code() {
        return name();
    }

}