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

    public static int determineState(double latitude,double longitude) {

        // 判断位置所在的州
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


    public static ArrayList<FoodBank> stateSelector(int position,ArrayList<FoodBank> fbListInput){
        ArrayList<FoodBank> fbListOutput = new ArrayList<>();
        if(position == ALL_STATES){
            return fbListInput;
        }else {
            for (FoodBank foodBank : fbListInput) {
                //Log.d("testdetermine",Integer.toString(determineState(foodBank.getLat(),foodBank.getLon())));
                if(determineState(foodBank.getLat(),foodBank.getLon()) == position){
                    fbListOutput.add(foodBank);
                }
            }
            return fbListOutput;
        }
    }



}
