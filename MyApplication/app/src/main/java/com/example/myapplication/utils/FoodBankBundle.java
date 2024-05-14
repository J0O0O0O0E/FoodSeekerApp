package com.example.myapplication.utils;


import android.os.Bundle;

import com.example.myapplication.model.FoodBank;

public class FoodBankBundle {

    public static Bundle createFoodBankBundle(FoodBank foodBank){
        Bundle bundle = new Bundle();
        bundle.putString("fb_name", foodBank.getName());
        bundle.putString("fb_number", foodBank.getTel());
        bundle.putString("fb_email", foodBank.getEmail());

        if (foodBank.isStatus()) {
            bundle.putString("fb_sate", "open");
        } else {
            bundle.putString("fb_sate", "close");
        }

        bundle.putString("fb_street", foodBank.getStreet());
        bundle.putString("fb_city", foodBank.getSuburb());
        bundle.putString("fb_postCode", foodBank.getPostcode());
        bundle.putString("fb_country", foodBank.getCountry());
        bundle.putString("fb_openHours", foodBank.getOpen_hours());
        bundle.putInt("fb_capacity", foodBank.getCapacity());
        bundle.putDouble("fb_distance", foodBank.getDistanceToUser());
        bundle.putString("fb_foundDate",foodBank.getDoe());
        bundle.putDouble("fb_latitude", foodBank.getLat());
        bundle.putDouble("fb_longitude", foodBank.getLon());
        bundle.putInt("fb_foodBankId", foodBank.getId());
        bundle.putDouble("fb_rate",foodBank.getRating());

        // Add food quantities
        bundle.putInt("Pasta", foodBank.getFood1_pasta());
        bundle.putInt("Bread", foodBank.getFood2_bread());
        bundle.putInt("Milk", foodBank.getFood3_milk());
        bundle.putInt("Pie", foodBank.getFood4_pie());
        bundle.putInt("Vegetable", foodBank.getFood5_vet());
        bundle.putDouble("fb_latitude",foodBank.getLat());
        bundle.putDouble("fb_longitude",foodBank.getLon());
        bundle.putInt("fb_foodBankId",foodBank.getId());

        return bundle;
    }



}
