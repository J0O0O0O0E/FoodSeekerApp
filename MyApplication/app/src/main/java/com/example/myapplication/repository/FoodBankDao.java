package com.example.myapplication.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.model.FoodBank;

import java.util.List;

@Dao
public interface FoodBankDao {
    @Query("SELECT * FROM foodbanks")
    LiveData<List<FoodBank>> getAllFoodBanks();

    @Query("SELECT * FROM foodbanks WHERE id = :id")
    LiveData<FoodBank> getFoodBankById(int id);

    @Insert
    void insertAll(FoodBank... foodbanks);

    @Delete
    void delete(FoodBank foodbank);
}
