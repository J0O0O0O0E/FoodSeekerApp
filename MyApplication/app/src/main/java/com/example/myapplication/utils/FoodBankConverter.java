package com.example.myapplication.utils;

import com.example.myapplication.model.FoodBank;


import java.util.ArrayList;

public class FoodBankConverter {

    //convert foodbank arraylist to foodbankinfo arrayList

//    public static ArrayList<FoodBankInfo> convert(ArrayList<FoodBank> foodBanks) {
//        ArrayList<FoodBankInfo> foodBankInfos = new ArrayList<>();
//
//        if(foodBanks.size() == 0){
//            return null;
//        }
//
//        for (FoodBank foodBank : foodBanks) {
//            FoodBankInfo info = new FoodBankInfo(
//                    foodBank.getName(),
//                    foodBank.getState(),
//                    foodBank.getStreet(),
//                    Double.toString(foodBank.getDistanceToUser()) + "m"
//            );
//            foodBankInfos.add(info);
//        }
//        return foodBankInfos;
//    }

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

    public static ArrayList<FoodBank> testFoodBank2(){
        FoodBank fb4 = new FoodBank(4, "South Gate Food Bank", "135 Oak St", "Houston", "TX", "77001", "USA", "333-444-5555", "info@southgatefoodbank.org", true, "7am - 3pm", 600, 29.7604, -95.3698, "2002-10-21", 250, 350, 450, 550, 650, 4.6);

        FoodBank fb5 = new FoodBank(5, "East Side Food Bank", "246 Birch St", "Chicago", "IL", "60601", "USA", "666-777-8888", "info@eastsidefoodbank.org", true, "11am - 7pm", 400, 41.8781, -87.6298, "2010-04-17", 300, 400, 500, 600, 700, 4.7);

        FoodBank fb6 = new FoodBank(6, "Downtown Food Bank", "357 Maple St", "Miami", "FL", "33101", "USA", "999-000-1111", "info@downtownfoodbank.org", true, "6am - 2pm", 700, 25.7617, -80.1918, "1995-09-30", 350, 450, 550, 650, 750, 4.8);

        FoodBank fb7 = new FoodBank(7, "Riverdale Food Bank", "468 Cedar St", "Seattle", "WA", "98101", "USA", "123-333-4444", "info@riverdalefoodbank.org", true, "7am - 3pm", 500, 47.6062, -122.3321, "2008-12-11", 400, 500, 600, 700, 800, 4.9);
        ArrayList<FoodBank> fbList1 = new ArrayList<>();
        fbList1.add(fb4);
        fbList1.add(fb5);
        fbList1.add(fb6);
        fbList1.add(fb7);
        return fbList1;
    }

}
