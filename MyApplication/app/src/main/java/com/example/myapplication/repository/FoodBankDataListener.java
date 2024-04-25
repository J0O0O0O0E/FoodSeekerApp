package com.example.myapplication.repository;

import com.example.myapplication.model.FoodBank;

import java.util.List;

public interface FoodBankDataListener {
    void onFoodBankDataReceived(List<FoodBank> foodBanks);

    void onError(Exception e);
}
