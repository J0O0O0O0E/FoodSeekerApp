package com.example.myapplication;
import com.example.myapplication.model.Announcement;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
import com.google.firebase.Timestamp;
import java.lang.reflect.Field;
import java.time.Instant;
public class TestAnnouncement {
    private Announcement announcement1;
    private Announcement announcement2;
    private Announcement announcement3;

    private Announcement announcement;
    private Timestamp timestamp;

    @Before
    public void setUp(){
        announcement1 = new Announcement();// To fit firebase, the constructor is empty
        announcement2 = new Announcement();
        announcement3 = new Announcement();
    }

    @Test
    public void testAnnouncement(){
        announcement1.setTitle("dog");
        announcement1.setDetail("Dog is human's friend.");
        announcement1.setImageUrl("www.dog.com");
        assertEquals("dog", announcement1.getTitle());
        assertEquals("Dog is human's friend.", announcement1.getDetail());
        assertEquals("www.dog.com", announcement1.getImageUrl());

        announcement2.setTitle("cat");
        announcement2.setDetail("Cat is human's friend.");
        announcement2.setImageUrl("www.cat.com");
        assertEquals("cat", announcement2.getTitle());
        assertEquals("Cat is human's friend.", announcement2.getDetail());
        assertEquals("www.cat.com", announcement2.getImageUrl());

        announcement3.setTitle("bird");
        announcement3.setDetail("Bird is human's friend.");
        announcement3.setImageUrl("www.bird.com");
        assertEquals("bird", announcement3.getTitle());
        assertEquals("Bird is human's friend.", announcement3.getDetail());
        assertEquals("www.bird.com", announcement3.getImageUrl());
    }
    // Here I test class Announcement itself, ignoring firebase, which will be tested in another place.


    @Before// This is a static test.
    public void setUpTimeStamp() throws NoSuchFieldException, IllegalAccessException {
        announcement = new Announcement();
        // New a time stamp
        timestamp = new Timestamp(1704067200, 0); // 2024-1-1-00:00
        // Use data field
        Field dateField = Announcement.class.getDeclaredField("date");
        dateField.setAccessible(true); // Field can visit private variable.
        dateField.set(announcement, timestamp);
    }

    @Test
    public void testGetTimestamp() {
        // Assert test
        assertEquals(timestamp, announcement.getTimestamp());
    }


    @Test// Test current time stamp.
    public void testGetTimestampWithCurrentTime() {
        Announcement announcement = new Announcement();
        // Get current
        Timestamp nowTimestamp = new Timestamp(Instant.now().getEpochSecond(), 0);
        // Use data field
        try {
            java.lang.reflect.Field dateField = Announcement.class.getDeclaredField("date");
            dateField.setAccessible(true);
            dateField.set(announcement, nowTimestamp);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // Assert
        assertEquals(nowTimestamp, announcement.getTimestamp());
    }




}
