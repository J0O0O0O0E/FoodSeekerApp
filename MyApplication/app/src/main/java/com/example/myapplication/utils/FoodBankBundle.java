package com.example.myapplication.utils;

import android.os.Bundle;
import com.example.myapplication.model.FoodBank;

/**
 * Utility class for handling FoodBank objects and converting them into Bundles.
 * This class provides methods to create a Bundle containing details about a FoodBank.
 * @author Si Chen u7756543
 */

public class FoodBankBundle {
    public static final String KEY_FOODBANKBUNDLE_NAME = "fb_name";
    public static final String KEY_FOODBANKBUNDLE_NUMBER = "fb_number";
    public static final String KEY_FOODBANKBUNDLE_EMAIL = "fb_email";
    public static final String KEY_FOODBANKBUNDLE_STATE = "fb_state";
    public static final String KEY_FOODBANKBUNDLE_STREET = "fb_street";
    public static final String KEY_FOODBANKBUNDLE_CITY = "fb_city";
    public static final String KEY_FOODBANKBUNDLE_POSTCODE = "fb_postCode";
    public static final String KEY_FOODBANKBUNDLE_COUNTRY = "fb_country";
    public static final String KEY_FOODBANKBUNDLE_OPEN_HOURS = "fb_openHours";
    public static final String KEY_FOODBANKBUNDLE_CAPACITY = "fb_capacity";
    public static final String KEY_FOODBANKBUNDLE_DISTANCE = "fb_distance";
    public static final String KEY_FOODBANKBUNDLE_FOUND_DATE = "fb_foundDate";
    public static final String KEY_FOODBANKBUNDLE_LATITUDE = "fb_latitude";
    public static final String KEY_FOODBANKBUNDLE_LONGITUDE = "fb_longitude";
    public static final String KEY_FOODBANKBUNDLE_FOOD_BANK_ID = "fb_foodBankId";
    public static final String KEY_FOODBANKBUNDLE_RATE = "fb_rate";
    public static final String KEY_PASTA = "Pasta";
    public static final String KEY_BREAD = "Bread";
    public static final String KEY_MILK = "Milk";
    public static final String KEY_PIE = "Pie";
    public static final String KEY_VEGETABLE = "Vegetable";

    public static final String KEY_FOODBANKBUNDLE_REGION = "fb_region";

    /**
     * Creates a Bundle containing details about a FoodBank.
     *
     * This method takes a FoodBank object and extracts its properties to store them
     * in a Bundle. This Bundle can then be used to pass the FoodBank details between
     * different components or activities.
     *
     * The keys used to store the properties in the Bundle are predefined constants.
     * The following properties are included in the Bundle:
     * - State (open/close based on the status of the FoodBank)
     * - Region
     * - Name
     * - Telephone number
     * - Email
     * - Street
     * - City (Suburb)
     * - Postcode
     * - Country
     * - Opening hours
     * - Capacity
     * - Distance to the user
     * - Founding date
     * - Latitude
     * - Longitude
     * - Food bank ID
     * - Rating
     * - Quantities of specific food items (Pasta, Bread, Milk, Pie, Vegetables)
     *
     * @param foodBank the FoodBank object whose details are to be stored in the Bundle
     * @return a Bundle containing the FoodBank's details
     */

    public static Bundle createFoodBankBundle(FoodBank foodBank) {
        Bundle bundle = new Bundle();

        if (foodBank.isStatus()) {
            bundle.putString(KEY_FOODBANKBUNDLE_STATE, "open");
        } else {
            bundle.putString(KEY_FOODBANKBUNDLE_STATE, "close");
        }
        bundle.putString(KEY_FOODBANKBUNDLE_REGION,foodBank.getRegion());
        bundle.putString(KEY_FOODBANKBUNDLE_NAME, foodBank.getName());
        bundle.putString(KEY_FOODBANKBUNDLE_NUMBER, foodBank.getTel());
        bundle.putString(KEY_FOODBANKBUNDLE_EMAIL, foodBank.getEmail());
        bundle.putString(KEY_FOODBANKBUNDLE_STREET, foodBank.getStreet());
        bundle.putString(KEY_FOODBANKBUNDLE_CITY, foodBank.getSuburb());
        bundle.putString(KEY_FOODBANKBUNDLE_POSTCODE, foodBank.getPostcode());
        bundle.putString(KEY_FOODBANKBUNDLE_COUNTRY, foodBank.getCountry());
        bundle.putString(KEY_FOODBANKBUNDLE_OPEN_HOURS, foodBank.getOpen_hours());
        bundle.putInt(KEY_FOODBANKBUNDLE_CAPACITY, foodBank.getCapacity());
        bundle.putDouble(KEY_FOODBANKBUNDLE_DISTANCE, foodBank.getDistanceToUser());
        bundle.putString(KEY_FOODBANKBUNDLE_FOUND_DATE, foodBank.getDoe());
        bundle.putDouble(KEY_FOODBANKBUNDLE_LATITUDE, foodBank.getLat());
        bundle.putDouble(KEY_FOODBANKBUNDLE_LONGITUDE, foodBank.getLon());
        bundle.putInt(KEY_FOODBANKBUNDLE_FOOD_BANK_ID, foodBank.getId());
        bundle.putDouble(KEY_FOODBANKBUNDLE_RATE, foodBank.getRating());
        bundle.putInt(KEY_PASTA, foodBank.getFood1_pasta());
        bundle.putInt(KEY_BREAD, foodBank.getFood2_bread());
        bundle.putInt(KEY_MILK, foodBank.getFood3_milk());
        bundle.putInt(KEY_PIE, foodBank.getFood4_pie());
        bundle.putInt(KEY_VEGETABLE, foodBank.getFood5_vet());
        return bundle;
    }


}
