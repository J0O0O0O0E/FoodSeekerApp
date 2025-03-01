package com.example.myapplication.model;

import com.example.myapplication.parser.BusinessHours;
import com.example.myapplication.parser.BusinessHoursParser;

/**
 * Represents a food bank entity with detailed attributes relating to its operation, location,
 * and food inventory. This class manages a food bank's data, including storage capacities,
 * various types of food items it stocks (like pasta, bread, milk, etc.), and operational details
 * such as opening hours and contact information. It also includes location-specific data like
 * geographical coordinates, address details, and proximity to the user, facilitating mapping
 * and location-based services. The class is designed to interact with systems that require details
 * about food bank operations, logistic, analytic and UI integration in applications
 * that aim to connect food banks with users.
 *
 * @package com.example.myapplication.model
 * @author Zijian Yang u7724610
 */

public class FoodBank {
    private int capacity;
    private String country;
    private String doe;
    private String email;
    private int food1_pasta;
    private int food2_bread;
    private int food3_milk;
    private int food4_pie;
    private int food5_vet;
    private int id;
    private int index;
    private double lat;
    private double lon;
    private String name;
    private String open_hours;
    private String postcode;
    private String prefix;
    private double rating;
    private String region;
    private boolean status;
    private String street;
    private String suburb;
    private String suffix;
    private String tel;
    private String unit_type;
    // Uses a Location object to store location information
    private Location location;
    // Distance to user (meter)
    private double distanceToUser;

    private BusinessHours businessHours;

    // Constructor
    public FoodBank() {
    }

    // Getters and setters
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDoe() {
        return doe;
    }

    public void setDoe(String doe) {
        this.doe = doe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen_hours() {
        return open_hours;
    }

    public void setOpen_hours(String open_hours) {
        this.open_hours = open_hours;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(String unit_type) {
        this.unit_type = unit_type;
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

    public BusinessHours getBusinessHours(){
        if(businessHours == null){
            this.businessHours = BusinessHoursParser.parseHours(open_hours);
        }
        return businessHours;
    }

}

