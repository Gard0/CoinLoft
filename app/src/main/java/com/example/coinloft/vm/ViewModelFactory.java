package com.example.coinloft.vm;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<?>, Provider<ViewModel>> mProviders;

    @Inject
    ViewModelFactory(Map<Class<?>, Provider<ViewModel>> providers) {
        mProviders = providers;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        final Provider<ViewModel> provider = mProviders.get(modelClass);
        if (provider != null) {
            return (T) provider.get();
        }
        for (final Map.Entry<Class<?>, Provider<ViewModel>> entry : mProviders.entrySet()) {
            if (modelClass.isAssignableFrom(entry.getKey())) {
                return (T) entry.getValue().get();
            }
        }
        throw new IllegalArgumentException("No such provider for " + modelClass);
    }

}