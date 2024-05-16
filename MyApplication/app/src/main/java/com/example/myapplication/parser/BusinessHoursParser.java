package com.example.myapplication.parser;

import android.util.Log;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessHoursParser {
    public static BusinessHours parseHours(String hours) {
        BusinessHours businessHours = new BusinessHours();
        Log.d("BusinessHoursParser", "Parsing hours: " + hours);

        // Split into day/time groups by looking ahead to "Weekdays" or "Weekends"
        String[] dayTimePairs = hours.split("(?=(Weekdays|Weekends))");
        for (String pair : dayTimePairs) {
            pair = pair.trim();  // Trim spaces that could lead to errors
            if (pair.isEmpty()) continue;

            // Split the pair into day part and hour part
            String[] parts = pair.split(",", 2);
            if (parts.length < 2) {
                Log.e("BusinessHoursParser", "Skipping invalid entry: " + pair);
                continue;  // Skip invalid entries
            }
            String daysPart = parts[0].trim();
            String hoursPart = parts[1].trim();

            List<DayOfWeek> days = parseDays(daysPart);

            // Handle potentially multiple time ranges separated by "AND"
            String[] timeRanges = hoursPart.split("\\sAND\\s");
            for (String range : timeRanges) {
                range = range.trim();
                String[] times = range.split("-");
                if (times.length < 2) {
                    Log.e("BusinessHoursParser", "Skipping invalid time range: " + range);
                    continue;  // Skip invalid time ranges
                }
                String startTime = times[0].trim();
                String endTime = times[1].trim();

                // Handle "24:00" as "00:00" of the next day
                if (endTime.equals("24:00")) {
                    endTime = "00:00";
                }

                // Avoid any parsing errors from improperly formatted times
                try {
                    for (DayOfWeek day : days) {
                        businessHours.addHours(day, startTime, endTime);
                        Log.d("BusinessHoursParser", "Added hours for " + day + ": " + startTime + " to " + endTime);
                    }
                } catch (DateTimeParseException e) {
                    Log.e("BusinessHoursParser", "Failed to parse times: " + startTime + " or " + endTime);
                }
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
