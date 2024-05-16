package com.example.myapplication.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Notification;
import com.example.myapplication.model.User;
import com.example.myapplication.repository.FoodBankRepository;
import com.example.myapplication.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<List<Notification>> notificationsLiveData = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Notification>> getNotificationsLiveData() {
        return notificationsLiveData;
    }

    public void updateNotifications(List<Notification> newNotifications) {
        List<Notification> currentNotifications = notificationsLiveData.getValue();
        if (currentNotifications != null) {
            currentNotifications.addAll(newNotifications);
            currentNotifications.sort((a1, a2) -> a2.getNotificationTime().compareTo(a1.getNotificationTime()));
            notificationsLiveData.postValue(currentNotifications);
        }
    }
}