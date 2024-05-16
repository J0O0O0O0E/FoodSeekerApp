package com.example.myapplication.ui.subscribedFoodBanks;

import com.example.myapplication.model.FoodBank;


/**
 * FoodBankRecyclerViewInterface defines a single method to handle item clicks in a RecyclerView
 * that displays a list of FoodBank objects.
 * @author Shuhui Yang
 */
public interface FoodBankRecyclerViewInterface {

    /**
     * Called when an item in the RecyclerView is clicked.
     *
     * @param foodBank The FoodBank object that was clicked.
     */
    void onItemClick(FoodBank foodBank);
}
