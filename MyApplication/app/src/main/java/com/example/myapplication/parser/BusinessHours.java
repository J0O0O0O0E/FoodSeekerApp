package com.example.myapplication.parser;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BusinessHours {
    public Map<DayOfWeek, List<TimeRange>> weeklyHours = new HashMap<>();

    public BusinessHours(){}

    public void addHours(DayOfWeek day, String startTime, String endTime) {
        TimeRange timeRange = new TimeRange(LocalTime.parse(startTime), LocalTime.parse(endTime));
        weeklyHours.computeIfAbsent(day, k -> new ArrayList<>()).add(timeRange);
    }

    public boolean isFoodBankClosed(LocalDateTime currentTime) {
        DayOfWeek currentDayOfWeek = currentTime.getDayOfWeek();
        LocalTime localTime = currentTime.toLocalTime();
        for (TimeRange timeRange : (Objects.requireNonNull(weeklyHours.get(currentDayOfWeek)))) {
            if (timeRange.end.equals(localTime)) {
                return true;
            }
        }
        return false;
    }


    public boolean ifNotifyNeeded(LocalDateTime currentTime){
        DayOfWeek currentDayOfWeek = currentTime.getDayOfWeek();
        LocalTime localTime = currentTime.toLocalTime();
        if(currentDayOfWeek != null){
            for (TimeRange timeRange : (Objects.requireNonNull(weeklyHours.get(currentDayOfWeek)))) {
                if (timeRange.start.equals(localTime) || timeRange.end.equals(localTime)) {
                    return true;
                }
            }
        }

        return false;
    }






    static class TimeRange {
        LocalTime start;
        LocalTime end;

        public TimeRange(LocalTime start, LocalTime end) {
            this.start = start;
            this.end = end;
        }

        public boolean isTimeInRange(LocalTime time) {
            return !time.isBefore(start) && !time.isAfter(end);
        }


    }



}
