package com.example.myapplication.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Announcement;
import com.example.myapplication.model.User;
import com.example.myapplication.repository.AnnouncementRepository;
import com.example.myapplication.repository.UserRepository;

import java.util.List;



/**
 * ViewModel part of the MVVM architecture that handles the data-related logic for the Home fragment.
 * It communicates between the repository and the UI, managing UI-related data that survives configuration changes like screen rotations.
 * The HomeViewModel is responsible for fetching announcements from the Repository and providing them to the UI through LiveData.
 * LiveData is used here to ensure that the data is observed and updated in a lifecycle-aware manner.
 * @author zhi LI
 */
public class HomeViewModel extends ViewModel {
    private AnnouncementRepository repository;

    // compare to MutableLiveData, which is A modifiable version of LiveData that can be changed.
    // LiveData is only a observable data holder class that holds read-only data
    //which is what we want in this case
    private LiveData<List<Announcement>> announcements;


    public HomeViewModel() {
        repository = AnnouncementRepository.getInstance();  // create repository instance
        announcements = repository.getAnnouncements();  // get announcements from firebase using repo
    }


    /**
     * Provides the announcements LiveData to the UI. This method allows the UI to observe changes to the data and update accordingly.
     * @return LiveData object containing the list of announcements, observable by the UI
     */
    public LiveData<List<Announcement>> getAnnouncements() {
        return announcements;
    }
}

