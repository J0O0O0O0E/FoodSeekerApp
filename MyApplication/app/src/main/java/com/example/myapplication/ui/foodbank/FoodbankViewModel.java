package com.example.myapplication.ui.foodbank;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Location;
import com.example.myapplication.repository.FoodBankRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ViewModel for managing UI-related data in the lifecycle of the Foodbank fragment.
 * This class is responsible for preparing and managing the data for the Foodbank fragment
 * within the application. It handles the fetching, updating, and processing of FoodBank data
 * from the FoodBankRepository and updates the UI as data changes. It also calculates and updates
 * the distances of food banks from a user-defined location.
 *
 * @author Zijian Yang
 */
public class FoodbankViewModel extends ViewModel {
    // Live data object for FoodBanks

    // Repository for FoodBank data operations
    private FoodBankRepository foodBankRepository;
    // Live data for display text
    private final MutableLiveData<String> mText;

    /**
     * Constructor for FoodbankViewModel. Initializes repository and LiveData objects,
     * sets default text for the fragment, and initiates the loading of FoodBank data.
     */
    public FoodbankViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Foodbank fragment");
        foodBankRepository = FoodBankRepository.getInstance();

    }

    /**
     * Loads food banks from the repository and observes data changes. Upon data loading,
     * the food banks are set to the LiveData object for observation and UI updates.
     */

    /**
     * Provides the LiveData object that contains the list of FoodBank objects.
     *
     * @return A LiveData object containing an ArrayList of FoodBank.
     */
    public LiveData<ArrayList<FoodBank>> getFoodBanksLiveData() {
        return FoodBankRepository.getInstance().getFoodBanksLiveData();
    }

    /**
     * Updates the distances of all food banks in the LiveData to a new user location and sorts them.
     * This method calculates the distance to each food bank from a given user location,
     * updates the food banks with the new distance, and then sorts the list by distance.
     *
     * @param latitude  The latitude of the user's location.
     * @param longitude The longitude of the user's location.
     */


    /**
     * Provides the LiveData object that contains the text for the UI.
     *
     * @return A LiveData object containing a String for UI display.
     */
    public LiveData<String> getText() {
        return mText;
    }
}


