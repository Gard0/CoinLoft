package com.example.coinloft.vm;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coinloft.R;


public class MainViewModel extends ViewModel {

    private final MutableLiveData<String> mTitle = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSelectedId = new MutableLiveData<>();

    public MainViewModel() {
        mSelectedId.postValue(R.id.wallets);
    }

    public void submitTitle(String title) {
        mTitle.postValue(title);
    }

    public void submitSelectedId(int id) {
        mSelectedId.postValue(id);
    }

    @Nullable
    public LiveData<String> title() {
        return mTitle;
    }

    @Nullable
    public LiveData<Integer> selectedId() {
        return mSelectedId;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
