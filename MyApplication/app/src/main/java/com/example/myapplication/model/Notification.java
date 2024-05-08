package com.example.myapplication.model;

import java.time.LocalDateTime;

public class Notification {
    private FoodBank foodBank;
    private boolean status;
    private LocalDateTime notificationTime;

    private String foodBankName;

    private String content;

    public Notification(FoodBank foodBank, LocalDateTime notificationTime){
        this.foodBank = foodBank;
        this.notificationTime = notificationTime;
        this.foodBankName = foodBank.getName();
    }


    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public String getFoodBankName(){
        return foodBankName;
    }

    public boolean ifNotify(){
        return foodBank.getBusinessHours().ifNotifyNeeded(notificationTime);
    }

    public String getNotifyMessage(){
        if (foodBank.getBusinessHours().isFoodBankClosed(notificationTime)) {
            return foodBank.getName() + " is now closed.";
        } else {
            return foodBank.getName() + " is now open.";
        }
    }


    }


