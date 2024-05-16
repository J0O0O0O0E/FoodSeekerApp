package com.example.myapplication.parser;

import android.util.Log;

import androidx.annotation.NonNull;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class storing business hours for a food bank in different days of the week.
 * This class supports handling multiple time ranges per day, including special cases like 24-hour operations.
 * @author Shuhui Yang
 */
public class BusinessHours {
    // Map storing business hours by day of the week with multiple time ranges.
    public Map<DayOfWeek, List<TimeRange>> weeklyHours = new HashMap<>();



    /**
     * Default constructor initializing the BusinessHours object.
     */
    public BusinessHours(){}


    /**
     * Adds business hours for a specified day of the week into the map.
     * Handles the special case where end time is specified as "24:00".
     *
     * @param day        The day of the week to add hours to.
     * @param startTime  Start time in HH:mm format.
     * @param endTime    End time in HH:mm format, handles "24:00" as "00:00".
     */
    public void addHours(DayOfWeek day, String startTime, String endTime) {
        // Handling the special case of "24:00"
        if (endTime.equals("24:00")) {
            endTime = "00:00";
        }

        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));

        // Check if end time is midnight, it should be treated as the end of the current day
        if (end.equals(LocalTime.MIDNIGHT) && !startTime.equals("00:00")) {
            end = LocalTime.MAX; // Use the maximum representable time of the day
        }

        TimeRange timeRange = new TimeRange(start, end);
        weeklyHours.computeIfAbsent(day, k -> new ArrayList<>()).add(timeRange);
    }


    /**
     * Checks if the food bank is closed at a given datetime.
     *
     * @param currentTime The current datetime to check.
     * @return true if the food bank is closed at the specified time, false otherwise.
     */
    public boolean isFoodBankClosed(LocalDateTime currentTime) {
        DayOfWeek currentDayOfWeek = currentTime.getDayOfWeek();
        LocalTime localTime = currentTime.toLocalTime().truncatedTo(ChronoUnit.MINUTES);

        List<TimeRange> ranges = weeklyHours.get(currentDayOfWeek);
        if (ranges != null) {
            for (TimeRange range : ranges) {
                if (range.isTimeInRange(localTime)) {
                    return false; // If time is within any range, it's not closed
                }
            }
        }
        return true; // Closed if no time range covers the current time
    }


    /**
     * Determines if a notification is needed based on the current time and the business hours.
     *
     * @param currentTime The current datetime to check against the business hours.
     * @return true if a notification is needed, false otherwise.
     */
    public boolean ifNotifyNeeded(LocalDateTime currentTime) {
        DayOfWeek currentDayOfWeek = currentTime.getDayOfWeek();
        LocalTime localTime = currentTime.toLocalTime().truncatedTo(ChronoUnit.MINUTES);

        if (currentDayOfWeek != null) {
            List<TimeRange> timeRanges = weeklyHours.get(currentDayOfWeek);
            if (timeRanges == null) {
                Log.d("NotificationCheck", "No time ranges for " + currentDayOfWeek + "; Notification needed: NO");
                return false; // No time ranges defined, no notification needed
            }

            for (TimeRange timeRange : timeRanges) {
                LocalTime startTime = timeRange.start.truncatedTo(ChronoUnit.MINUTES);
                LocalTime endTime = timeRange.end.truncatedTo(ChronoUnit.MINUTES);

                // Check if the time range is for 24 hours
                if (startTime.equals(LocalTime.MIDNIGHT) && endTime.equals(LocalTime.MIDNIGHT)) {
                    continue; // Skip notification for 24-hour open businesses
                }

                Log.d("NotificationCheck", "Current Time: " + localTime + ", Start Time: " + startTime + ", End Time: " + endTime);

                if (localTime.equals(startTime) || localTime.equals(endTime)) {
                    Log.d("NotificationCheck", "Notification needed: YES");
                    return true; // Notification needed at the start or end of a non-24-hour period
                }
            }
        }

        Log.d("NotificationCheck", "Notification needed: NO");
        return false; // Default to no notification if none of the conditions are met
    }






    /**
     * Inner class to represent a range of time.
     */
    public static class TimeRange {
        public LocalTime start;
        public LocalTime end;

        public TimeRange(LocalTime start, LocalTime end) {
            this.start = start;
            this.end = end;
        }

        public boolean isTimeInRange(LocalTime time) {
            // Check if both start and end are 00:00
            if (start.equals(LocalTime.MIDNIGHT) && end.equals(LocalTime.MIDNIGHT)) {
                return true;
            }

            return !time.isBefore(start) && !time.isAfter(end);
        }

        @NonNull
        public String toString(){
            return "TimeRange = "+ start.toString() + " " + end.toString();
        }


    }



}
