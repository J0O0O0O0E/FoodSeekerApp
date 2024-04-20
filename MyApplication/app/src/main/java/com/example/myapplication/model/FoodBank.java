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
    private boolean isOperating;

    // Opening hours
    private String operatingHours;

    // Service type
    private String serviceType;

    // Target group
    private String targetGroup;

    // Capacity
    private int capacity;

    // Available resources
    private String resources;

    // Staff information
    private int staffNumber;

    // Volunteer information
    private int volunteerNumber;

    // Uses a Location object to store location information
    private Location location;

    // Distance to user (meters)
    private double distanceToUser;

    // Foundation date
    private String foundationDate;

    // Rating
    private double rating;

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

    public boolean isOperating() {
        return isOperating;
    }

    public void setOperating(boolean operating) {
        isOperating = operating;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public int getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(int staffNumber) {
        this.staffNumber = staffNumber;
    }

    public int getVolunteerNumber() {
        return volunteerNumber;
    }

    public void setVolunteerNumber(int volunteerNumber) {
        this.volunteerNumber = volunteerNumber;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

