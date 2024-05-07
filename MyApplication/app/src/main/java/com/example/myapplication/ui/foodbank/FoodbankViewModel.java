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
    private MutableLiveData<ArrayList<FoodBank>> foodBanksLiveData;
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
        foodBankRepository = new FoodBankRepository();
        foodBanksLiveData = new MutableLiveData<>();
        loadFoodBanks();
    }

    /**
     * Loads food banks from the repository and observes data changes. Upon data loading,
     * the food banks are set to the LiveData object for observation and UI updates.
     */
    private void loadFoodBanks() {
        foodBankRepository.readFoodBanks(new FoodBankRepository.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<FoodBank> foodBanks, List<String> keys) {
                foodBanksLiveData.setValue(foodBanks);
            }

            @Override
            public void DataIsInserted() {
            }

            @Override
            public void DataIsUpdated() {
            }

            @Override
            public void DataIsDeleted() {
            }

            @Override
            public void Error(Exception e) {
                // TODO Log or handle errors
            }
        });
    }

    /**
     * Provides the LiveData object that contains the list of FoodBank objects.
     *
     * @return A LiveData object containing an ArrayList of FoodBank.
     */
    public LiveData<ArrayList<FoodBank>> getFoodBanksLiveData() {
        return foodBanksLiveData;
    }

    /**
     * Updates the distances of all food banks in the LiveData to a new user location and sorts them.
     * This method calculates the distance to each food bank from a given user location,
     * updates the food banks with the new distance, and then sorts the list by distance.
     *
     * @param latitude  The latitude of the user's location.
     * @param longitude The longitude of the user's location.
     */
    public void setUserLocationAndUpdateDistances(double latitude, double longitude) {
        Location userLocation = new Location(latitude, longitude);
        ArrayList<FoodBank> foodBanks = foodBanksLiveData.getValue();

        if (foodBanks != null) {
            for (FoodBank foodBank : foodBanks) {
                double distance = foodBank.getLocation().calculateDistance(userLocation);
                Log.d("UpdateDistances", "ID:" + foodBank.getId() + " distance:" + distance);
                foodBank.setDistanceToUser(distance);
            }

            // Sort the list based on distance to user
            Collections.sort(foodBanks, new Comparator<FoodBank>() {
                @Override
                public int compare(FoodBank fb1, FoodBank fb2) {
                    return Double.compare(fb1.getDistanceToUser(), fb2.getDistanceToUser());
                }
            });

            // Update the LiveData with the sorted list
            foodBanksLiveData.setValue(foodBanks);
        }
    }

    /**
     * Provides the LiveData object that contains the text for the UI.
     *
     * @return A LiveData object containing a String for UI display.
     */
    public LiveData<String> getText() {
        return mText;
    }


    // return true  if input string only contains Englis characters,number and space
    public static boolean containsOnlyEnglishDigitsAndSpace(String input) {
        String pattern = "^[a-zA-Z0-9\\s]+$";
        return input.matches(pattern);
    }

    //search foodbank by name
    public static ArrayList<FoodBank> searchFoodBankByName(String input, ArrayList<FoodBank> allFoodBank) {
        ArrayList<FoodBank> result = new ArrayList<>();
        input = convertToUpperCase(input);
        for (FoodBank foodBank : allFoodBank) {
            if (foodBank.getName().contains(input)) {
                result.add(foodBank);
            }
        }
        return result;
    }

    // convert input string to upperCase
    public static String convertToUpperCase(String input) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (Character.isLetter(currentChar)) {
                output.append(Character.toUpperCase(currentChar));
            } else {
                output.append(currentChar);
            }
        }
        return output.toString();
    }


}


