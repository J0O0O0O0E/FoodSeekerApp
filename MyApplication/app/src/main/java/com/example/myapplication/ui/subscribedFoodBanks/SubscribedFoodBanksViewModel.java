package com.example.myapplication.ui.subscribedFoodBanks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Announcement;
import com.example.myapplication.model.User;
import com.example.myapplication.repository.UserRepository;

import java.util.List;

public class SubscribedFoodBanksViewModel extends ViewModel {

    private MutableLiveData<User> liveUser;



    public SubscribedFoodBanksViewModel(){
        liveUser = UserRepository.getInstance().getLiveUser();
    }

    public MutableLiveData<User> getLiveUser(){
        return liveUser;
    }


}
