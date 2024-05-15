package com.example.myapplication.parser;

import android.util.Log;

import androidx.annotation.NonNull;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        LocalTime localTime = currentTime.toLocalTime().withSecond(0).withNano(0);
        for (TimeRange timeRange : (Objects.requireNonNull(weeklyHours.get(currentDayOfWeek)))) {
            if (timeRange.end.equals(localTime)) {
                return true;
            }
        }
        return false;
    }


    public boolean ifNotifyNeeded(LocalDateTime currentTime){
        DayOfWeek currentDayOfWeek = currentTime.getDayOfWeek();

        LocalTime localTime = currentTime.toLocalTime().truncatedTo(ChronoUnit.MINUTES);

        if (currentDayOfWeek != null) {
            for (TimeRange timeRange : Objects.requireNonNull(weeklyHours.get(currentDayOfWeek))) {

                LocalTime startTime = timeRange.start.truncatedTo(ChronoUnit.MINUTES);
                LocalTime endTime = timeRange.end.truncatedTo(ChronoUnit.MINUTES);


                Log.d("NotificationCheck", "Current Time: " + localTime.toString() + ", Start Time: " + startTime.toString() + ", End Time: " + endTime.toString());


                if (localTime.equals(startTime) || localTime.equals(endTime)) {
                    Log.d("NotificationCheck", "Notification needed: YES");
                    return true;
                }
            }
        }

        Log.d("NotificationCheck", "Notification needed: NO");
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

        @NonNull
        public String toString(){
            return "TimeRange = "+ start.toString() + " " + end.toString();
        }


    }



}
