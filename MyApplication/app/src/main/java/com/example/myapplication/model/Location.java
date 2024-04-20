package com.example.myapplication.model;

// Defines the Location class to handle geographic coordinates.
public class Location {
    // Latitude of the location
    private double latitude;

    // Longitude of the location
    private double longitude;

    // Constructor to initialize a location with latitude and longitude
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
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

    // Calculates the distance between this location and another location
    // using the Haversine formula to determine the great-circle distance
    // return meters
    public double calculateDistance(Location userLocation) {
        double earthRadius = 6371000.0;
        double dLat = Math.toRadians(userLocation.latitude - this.latitude);
        double dLon = Math.toRadians(userLocation.longitude - this.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(userLocation.latitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}
