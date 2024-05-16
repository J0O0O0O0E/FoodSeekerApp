package com.example.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AnnouncementSimulator extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Handler handler = new Handler(Looper.getMainLooper());
    private Context context;
    private ScheduledExecutorService scheduler;
    private int announcementCount = 0; // Add a counter to track the number of announcements
    private static final int MAX_ANNOUNCEMENTS = 2; // Set the maximum number of announcements

    public AnnouncementSimulator(Context context) {
        this.context = context;
    }

    public void startSimulation() {
        // Create a ScheduledExecutorService to schedule tasks at regular intervals
        scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the first task to run immediately, and then every 10 seconds
        scheduler.scheduleWithFixedDelay(this::uploadAnnouncement, 0, 10, TimeUnit.SECONDS);
    }

    private void uploadAnnouncement() {
        if (announcementCount < MAX_ANNOUNCEMENTS) {
            // Simulate the first announcement
            simulateUserAnnouncement();

            // Delay the second announcement by 5 seconds
            handler.postDelayed(this::simulateUserAnnouncement, 5000);

            announcementCount += 2; // Increment the counter by 2
        } else {
            // Stop the scheduler if the maximum number of announcements is reached
            scheduler.shutdown();
        }
    }

    private void simulateUserAnnouncement() {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String title = "simUserAnnouncement" +announcementCount+" "+currentTime;
        String details = "Details for announcement at " + currentTime;

        Map<String, Object> announcement = new HashMap<>();
        announcement.put("title", title);
        announcement.put("detail", details);
        announcement.put("imageUrl", ""); // No image URL for this simulation
        announcement.put("date", new Date());

        db.collection("Announcement")
                .add(announcement)
                .addOnSuccessListener(documentReference -> Toast.makeText(context, "Simulated announcement added", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to add simulated announcement", Toast.LENGTH_SHORT).show());
    }
}
