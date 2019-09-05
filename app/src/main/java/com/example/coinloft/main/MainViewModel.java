package com.example.coinloft.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coinloft.R;

import java.util.Objects;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<String> mTitle = new MutableLiveData<>();

    private final MutableLiveData<Integer> mSelectedId = new MutableLiveData<>();

    @Inject
    MainViewModel() {
        mSelectedId.postValue(R.id.rates);
    }

    public void submitTitle(String title) {
        mTitle.postValue(title);
    }

    void submitSelectedId(int id) {
        if (!Objects.equals(id, mSelectedId.getValue())) {
            mSelectedId.postValue(id);
        }
    }

    @NonNull
    LiveData<String> title() {
        return mTitle;
    }

    @NonNull
    LiveData<Integer> selectedId() {
        return mSelectedId;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}