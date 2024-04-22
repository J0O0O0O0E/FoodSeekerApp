package com.example.myapplication.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Announcement;
import com.example.myapplication.repository.AnnouncementRepository;

import java.util.List;



/*
To use LiveData effectively in your Android application, there are a few key methods and patterns you'll need to implement.
These include setting up the LiveData within your ViewModel, observing the LiveData in your UI components
(such as an Activity or Fragment), and potentially transforming the LiveData if necessary.
Below is an overview of the essential steps and their significance:
 */

/*
ViewModel: The ViewModel acts as a middleman between the Model and the View.
It is responsible for handling the presentation logic and state of the View, fetching data from the Model, and preparing it for display.
The ViewModel exposes streams of data relevant to the UI but remains devoid of any knowledge of the View's implementation details. Android's ViewModel class
(part of the Android Architecture Components) is designed to store and manage UI-related data in a lifecycle-conscious way.
 This means it can survive configuration changes like screen rotations.
 */
public class HomeViewModel extends ViewModel {
    private AnnouncementRepository repository;

    // compare to MutableLiveData, which is A modifiable version of LiveData that can be changed.
    // LiveData is only a observable data holder class that holds read-only data
    //which is what we want in this case
    private LiveData<List<Announcement>> announcements;

    public HomeViewModel() {
        repository = new AnnouncementRepository();  // create repository instance
        announcements = repository.getAnnouncements();  // get announcements from firebase using repo
    }



    // get method for the view level in MvvM
    public LiveData<List<Announcement>> getAnnouncements() {
        return announcements;
    }
}

