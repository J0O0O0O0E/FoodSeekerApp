package com.example.myapplication.utils;


import android.location.Location;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;

import java.util.ArrayList;

public class LocationChecker {
    private static final String[] stateArray =  {"All States","Tasmania","Western Australia","South Australia","New South Wales","Queensland","Victoria","Northern Territory"};
    public static String[] getStateArray() {
        return stateArray;
    }
    private static int ALL_STATES = 0;
    private static int INDEX_TASMANIA = 1;
    private static int INDEX_WESTERN_AUSTRALIA = 2;
    private static int INDEX_SOUTH_AUSTRALIA = 3;
    private static int INDEX_NEW_SOUTH_WALES = 4;
    private static int INDEX_QUEENSLAND = 5;
    private static int INDEX_VICTORIA = 6;
    private static int INDEX_NORTHERN_TERRITORY = 7;

    private static int INDEX_ERROR = -1;

    /**
     * Determines the state index based on the given latitude and longitude.
     *
     * This method checks the geographical coordinates to determine which state
     * in Australia the coordinates fall into. It returns a predefined index
     * for each state or an error index if the coordinates do not match any state.
     *
     * The state boundaries are defined by the following latitude and longitude ranges:
     * - Tasmania:        latitude -43.64 to -39.57, longitude 143.99 to 148.42
     * - Western Australia: latitude -35.14 to -13.95, longitude 112.92 to 129.00
     * - South Australia: latitude -38.06 to -26.00, longitude 129.00 to 141.00
     * - New South Wales: latitude -37.51 to -28.16, longitude 141.00 to 153.56
     * - Queensland:      latitude -29.18 to -10.41, longitude 138.00 to 153.54
     * - Victoria:        latitude -39.20 to -33.98, longitude 140.96 to 149.98
     * - Northern Territory: latitude -25.85 to -10.95, longitude 129.00 to 138.00
     *
     * @param latitude the latitude of the location
     * @param longitude the longitude of the location
     * @return the index representing the state, or an error index if the location is unrecognized
     */
    public static int determineState(double latitude,double longitude) {

        if ((latitude >= -43.64 && latitude <= -39.57) && (longitude >= 143.99 && longitude <= 148.42)) {
            return INDEX_TASMANIA;
        } else if ((latitude >= -35.14 && latitude <= -13.95) && (longitude >= 112.92 && longitude <= 129.00)) {
//            return "Western Australia";
            return INDEX_WESTERN_AUSTRALIA;
        } else if  ((latitude >= -38.06 && latitude <= -26.00) && (longitude >= 129.00 && longitude <= 141.00)) {
//            return "South Australia";
            return INDEX_SOUTH_AUSTRALIA;
        } else if ((latitude >= -37.51 && latitude <= -28.16) && (longitude >= 141.00 && longitude <= 153.56)) {
//            return "New South Wales";
            return INDEX_NEW_SOUTH_WALES;
        } else if ((latitude >= -29.18 && latitude <= -10.41) && (longitude >= 138.00 && longitude <= 153.54)) {
//            return "Queensland";
            return INDEX_QUEENSLAND;
        } else if ((latitude >= -39.20 && latitude <= -33.98) && (longitude >= 140.96 && longitude <= 149.98)) {
//            return "Victoria";
            return INDEX_VICTORIA;
        } else if ((latitude >= -25.85 && latitude <= -10.95) && (longitude >= 129.00 && longitude <= 138.00)) {
//            return "Northern Territory";
            return INDEX_NORTHERN_TERRITORY;
        } else {
//            return "Unrecognized location or in the ocean";
            return INDEX_ERROR;
        }

    }


    public static ArrayList<FoodBank> stateSelector(int position, ArrayList<FoodBank> fbListInput) {
        // Create a new ArrayList to store the FoodBank objects that meet the condition
        ArrayList<FoodBank> fbListOutput = new ArrayList<>();

        // If the position is ALL_STATES, return the input FoodBank list
        if(position == ALL_STATES){
            return fbListInput;
        } else {
            // Iterate through the input FoodBank list
            for (FoodBank foodBank : fbListInput) {
                // Call the determineState method to check if the FoodBank object's latitude and longitude match the specified position
                if(determineState(foodBank.getLat(), foodBank.getLon()) == position){
                    // If it matches, add the FoodBank object to the output list
                    fbListOutput.add(foodBank);
                }
            }
            // Return the list of FoodBank objects that meet the condition
            return fbListOutput;
        }
    }



}
