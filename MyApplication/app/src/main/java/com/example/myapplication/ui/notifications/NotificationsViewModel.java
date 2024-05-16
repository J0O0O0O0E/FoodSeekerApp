package com.example.myapplication.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * NotificationsViewModel is a ViewModel that manages and stores UI-related data for notifications.
 * It provides live data for a list of notifications and methods to update this list.
 * @author : zhili, u7640966
 * @author : Shuhui Yang u7662582
 */
public class NotificationsViewModel extends ViewModel {

    // LiveData to hold the list of notifications
    private final MutableLiveData<List<Notification>> notificationsLiveData = new MutableLiveData<>(new ArrayList<>());

    /**
     * Returns the LiveData object containing the list of notifications.
     *
     * @return LiveData<List<Notification>> object holding the current list of notifications.
     */
    public LiveData<List<Notification>> getNotificationsLiveData() {
        return notificationsLiveData;
    }

    /**
     * Updates the list of notifications with new notifications.
     * The new notifications are added to the current list, and the list is sorted by notification time in descending order.
     *
     * @param newNotifications The list of new notifications to be added.
     */
    public void updateNotifications(List<Notification> newNotifications) {
        // Get the current list of notifications
        List<Notification> currentNotifications = notificationsLiveData.getValue();
        if (currentNotifications != null) {
            // Add new notifications to the current list
            currentNotifications.addAll(newNotifications);
            // Sort the notifications by time in descending order
            currentNotifications.sort((a1, a2) -> a2.getNotificationTime().compareTo(a1.getNotificationTime()));
            // Post the updated list to LiveData
            notificationsLiveData.postValue(currentNotifications);
        }
    }
}
