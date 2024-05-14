package com.example.myapplication.model;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.repository.FoodBankRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeServer {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private LocalTime currentTime;

    private static TimeServer instance;

//    private MutableLiveData<LocalTime> timeMutableLiveData;

    private TimeServer() {
        scheduler.scheduleAtFixedRate(this::updateTime, 1/60, 1, TimeUnit.MINUTES);
//        this.timeMutableLiveData = new MutableLiveData<>();
    }

    public static TimeServer getInstance(){
        if(instance == null){
            return new TimeServer();
        }
        return instance;
    }



    private void updateTime() {
        this.currentTime = LocalTime.now();
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public void checkFoodBanks(){

    }
}
