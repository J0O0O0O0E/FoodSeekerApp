//package com.example.myapplication;
//
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//import com.example.myapplication.parser.BusinessHours;
//import com.example.myapplication.parser.BusinessHoursParser;
//
//
//public class BusinessHoursParserTest {
//
//    @Test
//    public void testParseHours() {
//        String hours = "Weekdays, 09:00-17:00 AND Weekends, 10:00-15:00";
//        BusinessHours businessHours = BusinessHoursParser.parseHours(hours);
//
//        assertNotNull(businessHours);
//        assertEquals(2, businessHours.getDays().size());
//        assertTrue(businessHours.getDays().contains(DayOfWeek.MONDAY));
//        assertTrue(businessHours.getDays().contains(DayOfWeek.SATURDAY));
//
//        assertEquals(2, businessHours.getHours(DayOfWeek.MONDAY).size());
//        assertEquals(2, businessHours.getHours(DayOfWeek.SATURDAY).size());
//
//        assertEquals("09:00", businessHours.getHours(DayOfWeek.MONDAY).get(0).getStartTime().toString());
//        assertEquals("17:00", businessHours.getHours(DayOfWeek.MONDAY).get(0).getEndTime().toString());
//
//        assertEquals("10:00", businessHours.getHours(DayOfWeek.SATURDAY).get(0).getStartTime().toString());
//        assertEquals("15:00", businessHours.getHours(DayOfWeek.SATURDAY).get(0).getEndTime().toString());
//    }
//
//    @Test
//    public void testParseDays() {
//        List<DayOfWeek> weekdays = BusinessHoursParser.parseDays("Weekdays");
//        assertEquals(5, weekdays.size());
//        assertTrue(weekdays.contains(DayOfWeek.MONDAY));
//        assertTrue(weekdays.contains(DayOfWeek.FRIDAY));
//
//        List<DayOfWeek> weekends = BusinessHoursParser.parseDays("Weekends");
//        assertEquals(2, weekends.size());
//        assertTrue(weekends.contains(DayOfWeek.SATURDAY));
//        assertTrue(weekends.contains(DayOfWeek.SUNDAY));
//    }
//
//}
