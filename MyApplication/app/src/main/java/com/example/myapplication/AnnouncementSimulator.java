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

/**
 * AnnouncementSimulator is a class that simulates uploading user announcements to Firebase Firestore
 * at regular intervals. Simulate data stream.
 *
 * @author Zhi Li, u7640966
 * <p>
 * Bibliography:
 * - <a href="https://stackoverflow.com/questions/7814089/how-to-schedule-a-periodic-task-in-java">...</a>
 */
public class AnnouncementSimulator extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Handler handler = new Handler(Looper.getMainLooper());
    private Context context;
    private ScheduledExecutorService scheduler;
    private int announcementCount = 0; // Counter to track the number of announcements
    private static final int MAX_ANNOUNCEMENTS = 2; // Maximum number of announcements

    /**
     * Constructor for AnnouncementSimulator.
     *
     * @param context the context in which the simulator is running
     */
    public AnnouncementSimulator(Context context) {
        this.context = context;
    }

    /**
     * Starts the simulation of uploading announcements.
     * Schedules the first task to run immediately, and then every 10 seconds.
     */
    public void startSimulation() {
        // Create a ScheduledExecutorService to schedule tasks at regular intervals
        scheduler = Executors.newScheduledThreadPool(1);
        // Schedule the first task to run 5 sec after, and then every 10 seconds
        scheduler.scheduleWithFixedDelay(this::uploadAnnouncement, 5, 10, TimeUnit.SECONDS);
    }

    /**
     * Uploads an announcement to Firebase Firestore.
     * If the maximum number of announcements is reached, the scheduler is stopped.
     */
    private void uploadAnnouncement() {
        if (announcementCount < MAX_ANNOUNCEMENTS) {
            //upload the first announcement
            simulateUserAnnouncement();

            // Delay the second announcement by 5 seconds
            handler.postDelayed(this::simulateUserAnnouncement, 5000);
            announcementCount += 2; // Increment the counter by 2
        } else {
            // Stop the scheduler if the maximum number of announcements is reached which is 2 in this case
            scheduler.shutdown();
        }
    }

    /**
     * Simulates a user announcement and uploads it to Firebase Firestore.
     * Displays a Toast message to indicate success or failure.
     */
    private void simulateUserAnnouncement() {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String title = "simUserAnn " + (announcementCount==0?"1":"2") + " " + currentTime;
        String details = "Details for announcement at " + currentTime + " Announcement count is: "+ (announcementCount==0?"1":"2");

        Map<String, Object> announcement = new HashMap<>();
        announcement.put("title", title);
        announcement.put("detail", details);
        announcement.put("imageUrl", "");
        announcement.put("date", new Date());

        db.collection("Announcement")
                .add(announcement)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(context, "Simulated announcement added", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Failed to add simulated announcement", Toast.LENGTH_SHORT).show());
    }
}
