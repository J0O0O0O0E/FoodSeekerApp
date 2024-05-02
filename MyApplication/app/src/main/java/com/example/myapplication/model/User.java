package com.example.myapplication.model;

import java.util.List;

public class User {


    private String contactNumber;

    public String email;

    public List<String> subscribedFoodBanks;


    private String userName;


    public User() {


    }

    public void setContactNumber(String number){
        contactNumber = number;
    }

    public String getContactNumber() {
        return contactNumber;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSubscribedFoodBanks(List<String> subscribedFoodBanks) {
        this.subscribedFoodBanks = subscribedFoodBanks;
    }

    public List<String> getSubscribedFoodBanks() {
        return subscribedFoodBanks;
    }



    public void setUserName(String name){
        userName = name;
    }


    public String getUserName() {
        return userName;
    }

    public void addSubscribedFoodBank(String foodBankId){
        subscribedFoodBanks.add(foodBankId);
    }

    public void removeSubscribedFoodBank(String foodBankId){
        subscribedFoodBanks.remove(foodBankId);
    }




}
