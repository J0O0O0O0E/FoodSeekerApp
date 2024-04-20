package com.example.myapplication.model;

public class FoodBank {
    // Unique identifier
    private String id;

    // Name
    private String name;

    // Street address
    private String street;

    // City
    private String city;

    // State/Province
    private String state;

    // Postal code
    private String postalCode;

    // Country
    private String country;


    // Phone number
    private String phoneNumber;

    // Email address
    private String email;

    // Operating status
    private boolean status;

    // Opening hours
    private String openHours;

    // Capacity
    private int capacity;

    // Uses a Location object to store location information
    private Location location;

    // Distance to user (meters)
    private double distanceToUser;

    // Foundation date
    private String foundationDate;


    // Constructor
    public FoodBank(String id, String name, Location location, Location userLocation) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.distanceToUser = location.calculateDistance(userLocation);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getOpenHours() {
        return openHours;
    }

    public void setOpenHours(String openHours) {
        this.openHours = openHours;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getDistanceToUser() {
        return distanceToUser;
    }

    public void setDistanceToUser(double distanceToUser) {
        this.distanceToUser = distanceToUser;
    }

    public String getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(String foundationDate) {
        this.foundationDate = foundationDate;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

