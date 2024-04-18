package com.example.myapplication.ui.foodbank;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodbankViewModel extends ViewModel {


    private final MutableLiveData<String> mText;

    public FoodbankViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Foodbank fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}