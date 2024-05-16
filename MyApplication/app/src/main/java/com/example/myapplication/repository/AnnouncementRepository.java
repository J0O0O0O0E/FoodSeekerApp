package com.example.myapplication.repository;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.Announcement;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;



/**
 * A repository class to manage the retrieval and handling of announcement data
 * from Firebase Firestore. This class follows the Singleton pattern to ensure
 * only one instance of the repository is created throughout the application.
 * <p>
 * @author Zhi LI
 * <p>
 * Bibliography:
 * - <a href="https://www.youtube.com/watch?v=YgjYVbg1oiA">...</a>
 * - <a href="https://firebase.google.com/docs/firestore/query-data/listen">...</a>
 */
public class  AnnouncementRepository {

    // Instance of FirebaseFirestore to interact with Firestore database
    private FirebaseFirestore db;

    // LiveData to store and update announcement data observed by the UI
    private MutableLiveData<List<Announcement>> announcementsLiveData;

    // Singleton instance of the AnnouncementRepository
    private static volatile AnnouncementRepository instance;

    /**
     * Private constructor to initialize the Firestore instance and LiveData.
     * Also, triggers loading of announcements from Firestore.
     */
    public AnnouncementRepository() {
        db = FirebaseFirestore.getInstance();
        announcementsLiveData = new MutableLiveData<>();
        // load data from cloud
        loadAnnouncements();
    }

    /**
     * Provides the global access point for the AnnouncementRepository instance.
     * If the instance doesn't exist, a new one is created.
     *
     * @return the singleton instance of AnnouncementRepository
     */
    public static AnnouncementRepository getInstance() {
        if (instance == null) {
            synchronized (AnnouncementRepository.class) {
                if (instance == null) {
                    instance = new AnnouncementRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Loads announcements from the "Announcement" collection in Firestore in real-time.
     * If any changes occur in the Firestore collection,
     * the LiveData is updated using addSnapshotListener.
     */
    private void loadAnnouncements() {
        // Access the "Announcement" collection in the fb and add a real-time update listener
        db.collection("Announcement")
                .addSnapshotListener((snapshots, e) -> {
                    // check if error
                    if (e != null) {
                        Log.e("AnnouncementRepo", "Error loading announcements, empty", e);
                        announcementsLiveData.setValue(new ArrayList<>());// Handle error by setting LiveData to null
                        return;
                    }
                    // list of Announcement to store the data
                    List<Announcement> newAnnouncements = new ArrayList<>();

                    // Iterate over each document in the query snapshot
                    for (QueryDocumentSnapshot doc : snapshots) {
                        // Convert each document into an Announcement object and add it to the list
                        newAnnouncements.add(doc.toObject(Announcement.class));
                    }
                    // Push the new list of announcements to LiveData, triggering the observer update
                    announcementsLiveData.postValue(newAnnouncements);
                });
    }

    /**
     * Returns the LiveData containing the list of announcements. This allows UI components
     * to observe changes and update accordingly.
     *
     * @return LiveData containing the current list of announcements
     */
    public LiveData<List<Announcement>> getAnnouncements() {
        return announcementsLiveData;
    }
}



