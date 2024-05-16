package com.example.myapplication.model;

import com.google.firebase.firestore.PropertyName;

import java.util.List;

/**
 * Represents the User entity in the application.
 * A User can have various attributes such as email, contact number, profile image URL,
 * subscribed FoodBanks, etc.
 * @author Shuhui Yang u7662582
 */

public class User {

    private Boolean author; // Indicates if the user is an author
    private String contactNumber; // Contact number of the user
    private String email; // Email of the user
    private String imgUrl; // URL of the user's profile image
    private List<String> subscribedFoodBanks; // List of FoodBanks the user has subscribed to
    private String userName; // Username of the user


    /**
     * Default constructor required for calls to DataSnapshot.getValue(User.class).
     */
    public User() {


    }


    /**
     * Sets whether the user is an author.
     *
     * @param author A Boolean value indicating if the user is an author.
     */
    public void setAuthor(Boolean author) {
        this.author = author;
    }



    /**
     * Returns whether the user is an author.
     *
     * @return A Boolean value indicating if the user is an author.
     */
    @PropertyName("author")
    public Boolean getAuthor() {
        return author;
    }


    /**
     * Sets the contact number of the user.
     *
     * @param number The contact number to set.
     */
    public void setContactNumber(String number){
        contactNumber = number;
    }


    /**
     * Returns the contact number of the user.
     *
     * @return The contact number of the user.
     */
    public String getContactNumber() {
        return contactNumber;
    }


    /**
     * Sets the email of the user.
     *
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Returns the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets the profile image URL of the user.
     *
     * @param imgUrl The profile image URL to set.
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }



    /**
     * Returns the profile image URL of the user.
     *
     * @return The profile image URL of the user.
     */
    @PropertyName("imgUrl")
    public String getimgUrl() {
        return imgUrl;
    }



    /**
     * Sets the list of FoodBanks the user has subscribed to.
     *
     * @param subscribedFoodBanks The list of subscribed FoodBanks to set.
     */
    public void setSubscribedFoodBanks(List<String> subscribedFoodBanks) {
        this.subscribedFoodBanks = subscribedFoodBanks;
    }


    /**
     * Returns the list of FoodBanks the user has subscribed to.
     *
     * @return The list of subscribed FoodBanks.
     */

    public List<String> getSubscribedFoodBanks() {
        return subscribedFoodBanks;
    }



    /**
     * Sets the username of the user.
     *
     * @param name The username to set.
     */
    public void setUserName(String name){
        userName = name;
    }


    /**
     * Returns the username of the user.
     *
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }


    /**
     * Adds a FoodBank to the list of subscribed FoodBanks.
     *
     * @param foodBankId The ID of the FoodBank to add.
     */
    public void addSubscribedFoodBank(String foodBankId){
        subscribedFoodBanks.add(foodBankId);
    }


    /**
     * Removes a FoodBank from the list of subscribed FoodBanks.
     *
     * @param foodBankId The ID of the FoodBank to remove.
     */
    public void removeSubscribedFoodBank(String foodBankId){
        subscribedFoodBanks.remove(foodBankId);
    }




}
