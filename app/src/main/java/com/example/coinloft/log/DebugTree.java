package com.example.coinloft.log;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class DebugTree extends Timber.DebugTree {
    @Override
    protected void log(int priority, String tag, @NotNull String message, Throwable t) {
        super.log(priority, tag,"[" + Thread.currentThread().getName() +"] " + message, t);
    }
}
