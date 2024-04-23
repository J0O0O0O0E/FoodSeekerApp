package com.example.myapplication.utils;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.FoodBankInfo;

import java.util.ArrayList;

public class FoodBankConverter {

    //convert foodbank arraylist to foodbankinfo arrayList

    public static ArrayList<FoodBankInfo> convert(ArrayList<FoodBank> foodBanks) {
        ArrayList<FoodBankInfo> foodBankInfos = new ArrayList<>();

        if(foodBanks.size() == 0){
            return null;
        }

        for (FoodBank foodBank : foodBanks) {
            FoodBankInfo info = new FoodBankInfo(
                    foodBank.getName(),
                    foodBank.getState(),
                    foodBank.getStreet(),
                    Double.toString(foodBank.getDistanceToUser()) + "m"
            );
            foodBankInfos.add(info);
        }
        return foodBankInfos;
    }

    //{test: for front-end test use only}
    public static ArrayList<FoodBank> testFoodBank1(){
        FoodBank fb1 = new FoodBank(1, "Central Food Bank", "123 Main St", "New York", "NY", "10001", "USA", "123-456-7890", "info@centralfoodbank.org", true, "9am - 5pm", 500, 40.7128, -74.0060, "2000-01-01", 100, 200, 300, 400, 500, 4.5);
        FoodBank fb2 = new FoodBank(2, "West Side Food Bank", "456 Elm St", "Los Angeles", "CA", "90001", "USA", "987-654-3210", "info@westsidefoodbank.org", true, "10am - 6pm", 300, 34.0522, -118.2437, "2005-06-15", 150, 250, 350, 450, 550, 4.2);
        FoodBank fb3 = new FoodBank(3, "North End Food Bank", "789 Pine St", "San Francisco", "CA", "94101", "USA", "555-111-2222", "info@northendfoodbank.org", true, "8am - 4pm", 450, 37.7749, -122.4194, "1998-03-15", 200, 300, 400, 500, 600, 4.3);
        ArrayList<FoodBank> fbList1 = new ArrayList<>();
        fbList1.add(fb1);
        fbList1.add(fb2);
        fbList1.add(fb3);
        return fbList1;
    }

}
