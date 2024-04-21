//package com.example.myapplication.repository;
//
//import android.app.Application;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Room;
//
//import com.example.myapplication.model.FoodBank;
//
//import java.util.List;
//
//public class FoodBankRepository {
//    private FoodBankDao foodBankDao;
//    private LiveData<List<FoodBank>> allFoodBanks;
//
//    public FoodBankRepository(Application application) {
//        AppDatabase db = Room.databaseBuilder(application,
//                AppDatabase.class, "database-name").build();
//        foodBankDao = db.foodBankDao();
//        allFoodBanks = foodBankDao.getAllFoodBanks();
//    }
//
//    public LiveData<List<FoodBank>> getAllFoodBanks() {
//        return allFoodBanks;
//    }
//
//    public void insert(FoodBank foodBank) {
//        new Thread(() -> foodBankDao.insertAll(foodBank)).start();
//    }
//}

