package com.example.coinloft.util;

import androidx.annotation.NonNull;


public interface Supplier<T> {
    @NonNull
    T get();
}

