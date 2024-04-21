package com.example.myapplication.ui.foodbank;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



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



//    public ArrayList<FoodBankRepository> getFoodbankList(String input){
//
//        return null;
//    }


//    public ArrayList<FoodBankInfoRepository> getFoodbankInfo(String input){
//
//        //get foodbank list from database
//        ArrayList foodbankInfo = getFoodbankList(input);
//
//        //convert to foodbankinfo list and return
//
//        // fill here
//
//
//        //test foodbankinfo list
//        ArrayList fbinfo = new ArrayList<>();
//        FoodBankInfoRepository fb1 = new FoodBankInfoRepository("211 Moore St","open","1");
//        FoodBankInfoRepository fb2 = new FoodBankInfoRepository("241 Bunda St","close","2");
//        fbinfo.add(fb1);
//        fbinfo.add(fb2);
//        return fbinfo;
//        //test
//
//
//
////        return null;
//    }



}


