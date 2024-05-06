package com.example.myapplication.utils;


import android.location.Location;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;

import java.util.ArrayList;

public class LocationChecker {
    private static final String[] stateArray =  {"All States","Tasmania","Western Australia","South Australia","New South Wales","Queensland","Victoria"};
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
        if (latitude < -39.0 && longitude < 146.0) {
            return INDEX_TASMANIA;
        } else if (latitude > -29.0 && latitude < -25.0 && longitude > 113.0 && longitude < 129.0) {
//            return "Western Australia";
            return INDEX_WESTERN_AUSTRALIA;
        } else if (latitude < -25.0 && latitude > -29.0 && longitude > 138.0 && longitude < 141.0) {
//            return "South Australia";
            return INDEX_SOUTH_AUSTRALIA;
        } else if (latitude < -29.0 && longitude > 141.0 && longitude < 153.0) {
//            return "New South Wales";
            return INDEX_NEW_SOUTH_WALES;
        } else if (latitude > -25.0 && longitude > 138.0 && longitude < 146.0) {
//            return "Queensland";
            return INDEX_QUEENSLAND;
        } else if (latitude < -36.0 && longitude > 140.0 && longitude < 150.0) {
//            return "Victoria";
            return INDEX_VICTORIA;
        } else if (latitude > -35.0 && latitude < -31.0 && longitude > 115.0 && longitude < 129.0) {
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
