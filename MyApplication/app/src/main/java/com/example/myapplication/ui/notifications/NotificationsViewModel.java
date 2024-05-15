package com.example.myapplication.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.User;
import com.example.myapplication.repository.FoodBankRepository;
import com.example.myapplication.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public static UserRepository userRepository = UserRepository.getInstance();

    private MutableLiveData<User> liveUser;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        this.liveUser = UserRepository.getInstance().getLiveUser();

    }

    public LiveData<String> getText() {
        return mText;
    }
}