package com.example.myapplication.utils;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.ui.foodbank.FoodbankFragment;

import java.util.ArrayList;

public class DevelopFoodbank {
    public static ArrayList<FoodBank> foodbanks ;

    public static ArrayList<FoodBank> getFoodbanks() {
        return foodbanks;
    }

    public static void setFoodbanks(ArrayList<FoodBank> foodbanks) {
        DevelopFoodbank.foodbanks = foodbanks;
    }

    public static FoodBank searchId(int id){
        for (FoodBank foodbank : DevelopFoodbank.foodbanks) {
            if(id==foodbank.getId()){
                return foodbank;
            }
        }
        return null;
    }
}
