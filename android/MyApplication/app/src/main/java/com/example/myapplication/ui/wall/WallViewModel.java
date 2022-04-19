package com.example.myapplication.ui.wall;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WallViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WallViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Посты не найдены.");
    }

    public LiveData<String> getText() {
        return mText;
    }

}