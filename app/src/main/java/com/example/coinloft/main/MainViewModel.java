package com.example.coinloft.main;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coinloft.R;

import java.util.Objects;


public class MainViewModel extends ViewModel {

    private final MutableLiveData<String> mTitle = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSelectedId = new MutableLiveData<>();

    public MainViewModel() {
        mSelectedId.postValue(R.id.rates);
    }

    public void submitTitle(String title) {
        mTitle.postValue(title);
    }

    public void submitSelectedId(int id) {
        if (!Objects.equals(id, mSelectedId.getValue())) {
            mSelectedId.postValue(id);
        }
    }

    @Nullable
    LiveData<String> title() {
        return mTitle;
    }

    @Nullable
    LiveData<Integer> selectedId() {
        return mSelectedId;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
