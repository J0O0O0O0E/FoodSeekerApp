package com.example.myapplication.repository;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.Announcement;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


// check this!:
//https://www.youtube.com/watch?v=YgjYVbg1oiA



//TODO: make it singleton!!!!!!!!!!!!!!!!




public class  AnnouncementRepository {
    // Private variable 'db' for accessing the Firebase Firestore database
    private FirebaseFirestore db;
    // Private variable 'announcementsLiveData', of type LiveData, to store and pass announcement data
    private MutableLiveData<List<Announcement>> announcementsLiveData;

    public AnnouncementRepository() {
        db = FirebaseFirestore.getInstance();
        announcementsLiveData = new MutableLiveData<>();
        // load data from cloud
        loadAnnouncements();
    }

    // load data from Firestore in real time
    private void loadAnnouncements() {
        // Access the "Announcement" collection in the fb and add a real-time update listener
        db.collection("Announcement")
                .addSnapshotListener((snapshots, e) -> {
                    // check if error
                    if (e != null) {
                        Log.e("AnnouncementRepo", "Error loading announcements, null", e);
                        announcementsLiveData.setValue(null);
                        return;
                    }
                    // list of Announcement to store the transformed data
                    List<Announcement> newAnnouncements = new ArrayList<>();
                    // Iterate over each document in the query snapshot
                    for (QueryDocumentSnapshot doc : snapshots) {
                        // Convert each document into an Announcement object and add it to the list
                        newAnnouncements.add(doc.toObject(Announcement.class));
                    }
                    // Push the new list of announcements to LiveData, triggering the observer (observer pattern) update
                    announcementsLiveData.postValue(newAnnouncements);
                });
    }

    public LiveData<List<Announcement>> getAnnouncements() {
        return announcementsLiveData;
    }
}



