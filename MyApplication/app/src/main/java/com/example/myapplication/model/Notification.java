package com.example.myapplication.model;

import java.time.LocalDateTime;


/**
 * Represents a notification related to the operational status of a FoodBank at
 * a specified time.
 * This class is responsible for determining and formatting the message based on
 * the food bank's business hours.
 * @author Shuhui Yang u7662582
 */
public class Notification {
    private FoodBank foodBank;
    private boolean status;
    private LocalDateTime notificationTime;

    private String foodBankName;



    /**
     * Constructs a Notification object with a specific FoodBank and the time for which the notification is relevant.
     *
     * @param foodBank The FoodBank to which this notification pertains.
     * @param notificationTime The time at which the notification status is evaluated.
     */
    public Notification(FoodBank foodBank, LocalDateTime notificationTime){
        this.foodBank = foodBank;
        this.notificationTime = notificationTime;
        this.foodBankName = foodBank.getName();
    }


    /**
     * Retrieves the notification time.
     *
     * @return The time at which this notification is relevant.
     */
    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }


    /**
     * Retrieves the name of the FoodBank associated with this notification.
     *
     * @return The name of the FoodBank.
     */
    public String getFoodBankName(){
        return foodBankName;
    }



    /**
     * Generates a notification message indicating
     * whether the FoodBank is open or closed at the notification time.
     *
     * @return A string message stating whether the FoodBank is currently open or closed.
     */
    public String getNotifyMessage(){
        if (foodBank.getBusinessHours().isFoodBankClosed(notificationTime)) {
            return foodBank.getName() + " is now closed";
        } else {
            return foodBank.getName() + " is now open";
        }
    }


    }


