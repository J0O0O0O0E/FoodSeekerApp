package com.example.myapplication.repository;

import com.example.myapplication.model.FoodBank;
import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.model.Location;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Repository class for handling FoodBank data interactions
public class FoodBankRepository {
    // Firebase database instance
    private FirebaseDatabase database;
    // List to hold food bank data fetched from database
    private ArrayList<FoodBank> foodBanks;
    // Interface for data status callbacks
    private DataStatus dataStatus;

    // Interface to notify status of data operations
    public interface DataStatus {
        void DataIsLoaded(ArrayList<FoodBank> foodBanks, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();

        void Error(Exception e);
    }

    // Constructor for the repository
    public FoodBankRepository() {
        // Get the Firebase database instance
        database = FirebaseDatabase.getInstance("https://comp2100-6442-4f828-default-rtdb.asia-southeast1.firebasedatabase.app");
        // Initialize the list to hold FoodBanks
        foodBanks = new ArrayList<>();
    }

    // Method to read food banks from Firebase and notify via callback
    public void readFoodBanks(final DataStatus dataStatus) {
        // Reference to the root in the database, root dir has no parameter for getReference method
        DatabaseReference ref = database.getReference();
        // Add value event listener to fetch data
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the existing list
                foodBanks.clear();
                // List to hold the keys of the nodes
                ArrayList<String> keys = new ArrayList<>();
                // Loop through the snapshot children
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    // Store the key
                    keys.add(keyNode.getKey());
                    // Get the FoodBank object from the snapshot
                    FoodBank foodBank = keyNode.getValue(FoodBank.class);
                    foodBank.setLocation(new Location(foodBank.getLat(),foodBank.getLon()));
                    // Add it to the food banks list
                    foodBanks.add(foodBank);
                }
                // Notify that data is loaded along with the keys of the nodes
                dataStatus.DataIsLoaded(foodBanks, keys);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Notify on error with the exception
                dataStatus.Error(databaseError.toException());
            }
        });
    }
}

