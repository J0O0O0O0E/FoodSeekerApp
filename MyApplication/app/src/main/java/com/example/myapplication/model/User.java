package com.example.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    public List<FoodBank> subscribedFoodBanks;

    private String contactNumber;

    private String userName = null;


    public final String account;


    public User(String account) {
        this.account=account;
        subscribedFoodBanks = new ArrayList<>();

    }

    public void setUserName(String name){
        userName = name;
    }

    public void setContactNumber(String number){
        contactNumber = number;
    }

    public void addSubscribedFoodBank(FoodBank foodBank){
        subscribedFoodBanks.add(foodBank);
    }

    public String getUserName() {
        return userName;
    }

    public String getAccount() {
        return account;
    }

    public List<FoodBank> getSubscribedFoodBanks() {
        return subscribedFoodBanks;
    }

    public String getContactNumber() {
        return contactNumber;
    }





}
