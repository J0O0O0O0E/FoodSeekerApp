package com.example.myapplication.model;

public class FoodBank {
    // Unique identifier
    private int id;

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

    private double latitude;

    private double longitude;

    // Distance to user (meters)
    private double distanceToUser;

    // Foundation date
    private String foundationDate;

    private int food1_pasta;
    private int food2_bread;
    private int food3_milk;
    private int food4_pie;
    private int food5_vet;

    private double rating;

    // Constructor


    public FoodBank() {
    }

    public FoodBank(int id, String name, String street, String city, String state, String postalCode, String country, String phoneNumber, String email, boolean status, String openHours, int capacity, double latitude, double longitude, String foundationDate, int food1_pasta, int food2_bread, int food3_milk, int food4_pie, int food5_vet, double rating) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = status;
        this.openHours = openHours;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.foundationDate = foundationDate;
        this.food1_pasta = food1_pasta;
        this.food2_bread = food2_bread;
        this.food3_milk = food3_milk;
        this.food4_pie = food4_pie;
        this.food5_vet = food5_vet;
        this.rating = rating;
        this.location = new Location(latitude, longitude);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public int getFood1_pasta() {
        return food1_pasta;
    }

    public void setFood1_pasta(int food1_pasta) {
        this.food1_pasta = food1_pasta;
    }

    public int getFood2_bread() {
        return food2_bread;
    }

    public void setFood2_bread(int food2_bread) {
        this.food2_bread = food2_bread;
    }

    public int getFood3_milk() {
        return food3_milk;
    }

    public void setFood3_milk(int food3_milk) {
        this.food3_milk = food3_milk;
    }

    public int getFood4_pie() {
        return food4_pie;
    }

    public void setFood4_pie(int food4_pie) {
        this.food4_pie = food4_pie;
    }

    public int getFood5_vet() {
        return food5_vet;
    }

    public void setFood5_vet(int food5_vet) {
        this.food5_vet = food5_vet;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "FoodBank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", openHours='" + openHours + '\'' +
                ", capacity=" + capacity +
                ", location=" + location +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distanceToUser=" + distanceToUser +
                ", foundationDate='" + foundationDate + '\'' +
                ", food1_pasta=" + food1_pasta +
                ", food2_bread=" + food2_bread +
                ", food3_milk=" + food3_milk +
                ", food4_pie=" + food4_pie +
                ", food5_vet=" + food5_vet +
                ", rating=" + rating +
                '}';
    }
}

