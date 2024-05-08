package com.example.myapplication.parser;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class BusinessHoursParser {
    public static BusinessHours parseHours(String hours) {
        BusinessHours businessHours = new BusinessHours();

        String[] parts = hours.split(", ");
        String daysPart = parts[0];
        String hoursPart = parts[1];

        List<DayOfWeek> days = parseDays(daysPart);
        String[] timeSlots = hoursPart.split(" AND ");

        for (String timeSlot : timeSlots) {
            String[] times = timeSlot.split("-");
            String startTime = times[0].trim();
            String endTime = times[1].trim();

            for (DayOfWeek day : days) {
                businessHours.addHours(day, startTime, endTime);
            }

    }
        return businessHours;
    }


    private static List<DayOfWeek> parseDays(String daysPart) {
        List<DayOfWeek> days = new ArrayList<>();
        if (daysPart.contains("Weekdays")) {
            days.add(DayOfWeek.MONDAY);
            days.add(DayOfWeek.TUESDAY);
            days.add(DayOfWeek.WEDNESDAY);
            days.add(DayOfWeek.THURSDAY);
            days.add(DayOfWeek.FRIDAY);
        }
        if (daysPart.contains("Weekends")) {
            days.add(DayOfWeek.SATURDAY);
            days.add(DayOfWeek.SUNDAY);
        }
        return days;
    }



}
