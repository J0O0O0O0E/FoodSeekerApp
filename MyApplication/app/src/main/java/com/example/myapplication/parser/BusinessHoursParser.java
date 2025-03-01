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

/**
 * Utility class for parsing business hours from a string.
 * @author Shuhui Yang u7662582
 */
public class BusinessHoursParser {


    /**
     * Parses a string representing business hours and converts it into a BusinessHours object.
     *
     * @param hours the string representing the business hours, e.g., "Weekdays, 08:00-1900"
     * @return a BusinessHours object containing the parsed hours and a hash map
     */
    public static BusinessHours parseHours(String hours) {
        BusinessHours businessHours = new BusinessHours();

        // Split into day/time groups by looking ahead to "Weekdays" or "Weekends"
        String[] dayTimePairs = hours.split("(?=(Weekdays|Weekends))");
        for (String pair : dayTimePairs) {
            pair = pair.trim();  // Trim spaces that could lead to errors
            if (pair.isEmpty()) continue;

            // Split the pair into day part and hour part
            String[] parts = pair.split(",", 2);
            if (parts.length < 2) {
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
                    }
                } catch (DateTimeParseException e) {
                    Log.e("BusinessHoursParser", "Failed to parse times: " + startTime + " or " + endTime);
                }
            }
        }

        return businessHours;
    }


    /**
     * Parses a string representing days into a list of DayOfWeek.
     *
     * @param daysPart the string representing the days, e.g., "Weekdays" or "Weekends"
     * @return a list of DayOfWeek corresponding to the parsed days
     */
    private static List<DayOfWeek> parseDays(String daysPart) {
        List<DayOfWeek> days = new ArrayList<>();


        if (daysPart.contains("Weekdays")) {
            days.addAll(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));

        }
        if (daysPart.contains("Weekends")) {
            days.addAll(Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));

        }

        return days;
    }



}
