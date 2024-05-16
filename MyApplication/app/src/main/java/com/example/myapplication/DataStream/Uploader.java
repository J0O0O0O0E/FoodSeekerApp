package com.example.myapplication.DataStream;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Uploader extends AppCompatActivity {
    private static final int UPLOAD_INTERVAL_MS = 30000; // 30 seconds
    private static final int MAX_UPLOADS = 10; // Set the maximum number of uploads
    private static int uploadCount = 0; // Add a counter to track the number of uploads
    private static List<String[]> dataList = new ArrayList<>(); // List to hold data from CSV
    private static final Object lock = new Object(); // Synchronization lock

    public void startUploading(Context context) {
        Log.d("PeriodicUploader", "Starting to read CSV data");
        readCsvData(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UploadReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        long firstTriggerTime = SystemClock.elapsedRealtime() + UPLOAD_INTERVAL_MS;
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTriggerTime, UPLOAD_INTERVAL_MS, pendingIntent);
    }

    public static void incrementUploadCount() {
        synchronized (lock) {
            uploadCount++;
        }
    }

    public static boolean shouldContinueUploading() {
        synchronized (lock) {
            return uploadCount < MAX_UPLOADS && uploadCount < dataList.size();
        }
    }

    public static String[] getNextUploadData() {
        synchronized (lock) {
            return shouldContinueUploading() ? dataList.get(uploadCount) : null;
        }
    }

    private void readCsvData(Context context) {
        try {
            File file = new File(context.getFilesDir(), "datastream.csv"); // Assuming CSV is stored in internal storage
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] data = line.split(",");
                    if (data.length > 0) { // Ensure there is at least one column
                        dataList.add(data);
                    }
                }
            }
            reader.close();
            Log.d("PeriodicUploader", "CSV data read successfully: " + dataList.size() + " entries");
        } catch (Exception e) {
            Log.e("PeriodicUploader", "Error reading CSV file", e);
        }
    }
}
