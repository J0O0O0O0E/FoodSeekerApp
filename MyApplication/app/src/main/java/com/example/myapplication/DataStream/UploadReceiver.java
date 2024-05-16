package com.example.myapplication.DataStream;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UploadReceiver extends BroadcastReceiver {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Uploader.shouldContinueUploading()) {
            String[] uploadData = Uploader.getNextUploadData();
            if (uploadData != null && uploadData.length >= 3) { // Ensure there are at least 3 elements
                uploadDataToFirebase(context, uploadData[0], uploadData[1], uploadData[2]);
                Uploader.incrementUploadCount();
            } else {
                Log.e("UploadReceiver", "Invalid upload data");
            }
        } else {
            // Stop future alarms if the maximum number of uploads is reached
            cancelAlarm(context);
        }
    }

    private void uploadDataToFirebase(Context context, String title, String details, String imgUrl) {
        Map<String, Object> upload = new HashMap<>();
        upload.put("title", title);
        upload.put("detail", details);
        upload.put("imageUrl", imgUrl);
        upload.put("date", new java.util.Date());

        db.collection("Uploads")
                .add(upload)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(context, "Upload added successfully", Toast.LENGTH_SHORT).show();
                    Log.d("UploadReceiver", "Upload added successfully: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to add upload", Toast.LENGTH_SHORT).show();
                    Log.e("UploadReceiver", "Failed to add upload", e);
                });
    }

    private void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UploadReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
        Log.d("UploadReceiver", "Alarm cancelled");
    }
}
