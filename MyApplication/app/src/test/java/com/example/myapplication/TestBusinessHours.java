package com.example.myapplication;
import org.junit.Before;
import org.junit.Test;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.Assert.*;

import com.example.myapplication.parser.BusinessHours;

public class TestBusinessHours {

    private BusinessHours businessHours;

    @Before
    public void setUp() {
        businessHours = new BusinessHours();
        businessHours.addHours(DayOfWeek.MONDAY, "09:00", "17:00");
        businessHours.addHours(DayOfWeek.TUESDAY, "10:00", "18:00");
        businessHours.addHours(DayOfWeek.WEDNESDAY, "00:00", "23:59");
        businessHours.addHours(DayOfWeek.THURSDAY, "08:00", "18:00");
        businessHours.addHours(DayOfWeek.FRIDAY, "09:00", "17:00");
    }

    @Test
    public void testAddHours() {
        assertEquals(5, businessHours.weeklyHours.size()); // Check
        List<BusinessHours.TimeRange> mondayHours = businessHours.weeklyHours.get(DayOfWeek.MONDAY);
        assert mondayHours != null;
        assertEquals(1, mondayHours.size());
        assertEquals("09:00", mondayHours.get(0).start.toString());
        assertEquals("17:00", mondayHours.get(0).end.toString());
    }

    @Test
    public void testIsFoodBankClosed() {
        assertTrue(businessHours.isFoodBankClosed(LocalDateTime.of(2024, 5, 9, 18, 30))); // Thur, 18:30
        assertFalse(businessHours.isFoodBankClosed(LocalDateTime.of(2024, 5, 9, 12, 0))); // Thur, 12:00
        assertFalse(businessHours.isFoodBankClosed(LocalDateTime.of(2024, 5, 8, 15, 0))); // Wed, 15:00
        assertFalse(businessHours.isFoodBankClosed(LocalDateTime.of(2024, 5, 8, 7, 0))); // Wed, 07:00
        assertFalse(businessHours.isFoodBankClosed(LocalDateTime.of(2024, 5, 8, 23, 0))); // Wed, 23:00

    }

//    PASS
    @Test
    public void testIfNotifyNeeded() {
        assertFalse(businessHours.ifNotifyNeeded(LocalDateTime.of(2024, 5, 8, 12, 0))); // Wed, 12:00
        assertFalse(businessHours.ifNotifyNeeded(LocalDateTime.of(2024, 5, 8, 22, 0))); // Wed, 22:00
        assertTrue(businessHours.ifNotifyNeeded(LocalDateTime.of(2024, 5, 8, 0, 0))); // Wed, 24:00
        assertTrue(businessHours.ifNotifyNeeded(LocalDateTime.of(2024, 5, 6, 9, 0))); // Mon, 9:00
        assertTrue(businessHours.ifNotifyNeeded(LocalDateTime.of(2024, 5, 9, 18, 0))); // Thur, 18:00
    }

}
