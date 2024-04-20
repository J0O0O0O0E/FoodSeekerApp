package com.example.myapplication.ui.foodbank;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.repository.FoodBankInfoRepository;
import com.example.myapplication.repository.FoodBankRepository;

import java.util.ArrayList;

public class FoodbankViewModel extends ViewModel {


    private final MutableLiveData<String> mText;

    public FoodbankViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Foodbank fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    //Input String to get 10 FoodBank instance
    public ArrayList<FoodBankRepository> getFoodbankList(String input){
        return null;
    }

    //Get info of foodbank arraylist
    public ArrayList<FoodBankInfoRepository> getFoodbankInfo(String input){

        return null;
    }


}


