package com.example.myapplication.parser;

import android.util.Log;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BusinessHoursParser {
    public static BusinessHours parseHours(String hours) {
        BusinessHours businessHours = new BusinessHours();
        Log.d("BusinessHoursParser", "Parsing hours: " + hours);

        // Split the hours string by space to handle multiple day/time pairs
        String[] dayTimePairs = hours.split(" ");
        for (String pair : dayTimePairs) {
            String[] parts = pair.split(",");
            String daysPart = parts[0];
            String hoursPart = parts[1];

            List<DayOfWeek> days = parseDays(daysPart);
            String[] times = hoursPart.split("-");
            String startTime = times[0].trim();
            String endTime = times[1].trim();

            for (DayOfWeek day : days) {
                businessHours.addHours(day, startTime, endTime);
                Log.d("BusinessHoursParser", "Added hours for " + day.toString() + ": " + startTime + " to " + endTime);
            }
        }

        return businessHours;
    }

    private static List<DayOfWeek> parseDays(String daysPart) {
        List<DayOfWeek> days = new ArrayList<>();
        Log.d("BusinessHoursParser", "Parsing days: " + daysPart);

        if (daysPart.contains("Weekdays")) {
            days.addAll(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
            Log.d("BusinessHoursParser", "Weekdays added");
        }
        if (daysPart.contains("Weekends")) {
            days.addAll(Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));
            Log.d("BusinessHoursParser", "Weekends added");
        }

        Log.d("BusinessHoursParser", "Parsed days: " + days.toString());
        return days;
    }



}
