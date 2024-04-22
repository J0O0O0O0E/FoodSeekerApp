package com.example.myapplication.ui.foodbank;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.FoodBankInfo;

import java.util.ArrayList;
import java.util.List;

public class FoodbankViewModel extends ViewModel {


    private final MutableLiveData<String> mText;

    public FoodbankViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Foodbank fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    //TODO input information ,search for some foodbank
    public static List<FoodBankInfo> searchFb(String input){
        List<FoodBankInfo> list = FoodbankViewModel.fakeSearch2();
        return list;
    }

    //TODO at the start, check the current location, get the nearby foodbank list
    //input two Double: longitude and latitude
    public static List<FoodBankInfo> getNearByFb(Double longitude,Double latitude){
        //read the location data and input
        List<FoodBankInfo> list = FoodbankViewModel.fakeSearch();
        return list;
    }

    //TODO convert foodbank list to foodbankinfo list
    public static List<FoodBankInfo> convertFb (List<FoodBank> fb){
        return null;
    }

    //{for test only} create a fake foodbankinfo list
    public static List<FoodBankInfo> fakeSearch(){
        List<FoodBankInfo> list = new ArrayList<>();
        list.add(new FoodBankInfo("Hospital","open","230 Dikson St","100m"));
        list.add(new FoodBankInfo("Market","open","113 H St","500m"));
        list.add(new FoodBankInfo("Park","close","25 Y St","1.1km"));
        return  list;
    }

    public static List<FoodBankInfo> fakeSearch2(){
        List<FoodBankInfo> list = new ArrayList<>();
        list.add(new FoodBankInfo("KFC","open","230 Dikson St","500m"));
        list.add(new FoodBankInfo("Coles","open","113 H St","1.8km"));
        list.add(new FoodBankInfo("Bank","close","25 Y St","1.9km"));
        list.add(new FoodBankInfo("Mc Download","close","25 Y St","1.9km"));
        return  list;
    }
}


