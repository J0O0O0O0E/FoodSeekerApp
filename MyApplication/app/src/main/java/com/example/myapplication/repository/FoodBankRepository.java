package com.example.myapplication.repository;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Location;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FoodBankRepository {
    private DatabaseReference databaseReference;

    public FoodBankRepository() {
        databaseReference = FirebaseDatabase.getInstance().getReference("https://console.firebase.google.com/u/3/project/comp2100-6442-4f828/database/comp2100-6442-4f828-default-rtdb/data/~2F");
    }

    public void getAllFoodBanks(FoodBankDataListener listener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<FoodBank> foodBanks = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodBank foodBank = snapshotToFoodBank(snapshot);
                    //System.out.println(foodBank.toString());
                    if (foodBank != null) {
                        foodBanks.add(foodBank);
                    }
                }
                listener.onFoodBankDataReceived(foodBanks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError.toException());
            }
        });
    }

    private FoodBank snapshotToFoodBank(DataSnapshot snapshot) {
        int id = snapshot.child("id").getValue(Integer.class);
        String name = snapshot.child("name").getValue(String.class);
        String street = snapshot.child("street").getValue(String.class);
        String city = snapshot.child("city").getValue(String.class);
        String state = snapshot.child("state").getValue(String.class);
        String postalCode = snapshot.child("postalCode").getValue(String.class);
        String country = snapshot.child("country").getValue(String.class);
        String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
        String email = snapshot.child("email").getValue(String.class);
        boolean status = snapshot.child("status").getValue(Boolean.class);
        String openHours = snapshot.child("openHours").getValue(String.class);
        int capacity = snapshot.child("capacity").getValue(Integer.class);
        double latitude = snapshot.child("latitude").getValue(Double.class);
        double longitude = snapshot.child("longitude").getValue(Double.class);
        String foundationDate = snapshot.child("foundationDate").getValue(String.class);
        int food1_pasta = snapshot.child("food1_pasta").getValue(Integer.class);
        int food2_bread = snapshot.child("food2_bread").getValue(Integer.class);
        int food3_milk = snapshot.child("food3_milk").getValue(Integer.class);
        int food4_pie = snapshot.child("food4_pie").getValue(Integer.class);
        int food5_vet = snapshot.child("food5_vet").getValue(Integer.class);
        double rating = snapshot.child("rating").getValue(Double.class);
        Location location = new Location(latitude, longitude);

        return new FoodBank(id, name, street, city, state, postalCode, country, phoneNumber, email, status, openHours, capacity, location, latitude, longitude, foundationDate, food1_pasta, food2_bread, food3_milk, food4_pie, food5_vet, rating);
    }

    public void addFoodBank(FoodBank foodBank) {
        databaseReference.push().setValue(foodBank);
    }

    public void updateFoodBank(String key, FoodBank foodBank) {
        databaseReference.child(key).setValue(foodBank);
    }

    public void deleteFoodBank(String key) {
        databaseReference.child(key).removeValue();
    }
}

