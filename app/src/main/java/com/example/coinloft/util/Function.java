package com.example.coinloft.util;

public interface Function<T, R> {
    R apply(T value);
}