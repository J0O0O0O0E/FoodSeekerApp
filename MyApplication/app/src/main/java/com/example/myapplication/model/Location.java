package com.example.myapplication.model;

/**
 * Represents a geographic location defined by latitude and longitude coordinates.
 * This class provides methods to set and retrieve geographic coordinates and to calculate
 * the distance to another location using the Haversine formula, which is designed for
 * determining the great-circle distance between two points on a sphere based on their
 * longitudes and latitudes.
 *
 * @author Zijian Yang
 * @package com.example.myapplication.model
 */
public class Location {
    // Latitude of the location
    private double latitude;

    // Longitude of the location
    private double longitude;

    /**
     * Constructs a new Location with specified latitude and longitude.
     *
     * @param latitude  The latitude of the location in degrees.
     * @param longitude The longitude of the location in degrees.
     */
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

    /**
     * Calculates and returns the distance from this location to another location using the
     * Haversine formula, which is essential for calculating the shortest distance over the earth's surface.
     *
     * @param userLocation The other location to which the distance is calculated.
     * @return The distance to the user location in meters.
     */
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
