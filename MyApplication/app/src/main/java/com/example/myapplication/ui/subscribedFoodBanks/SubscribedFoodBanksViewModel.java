package com.example.myapplication.ui.subscribedFoodBanks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Announcement;
import com.example.myapplication.model.User;
import com.example.myapplication.repository.UserRepository;

import java.util.List;

/**
 * Responsible for managing and preparing the data for the SubscribedFoodBanksFragment.
 * It interacts with the UserRepository to fetch and update user data.
 * @author Shuhui Yang
 */
public class SubscribedFoodBanksViewModel extends ViewModel {

    private MutableLiveData<User> liveUser;



    /**
     * Constructor that initializes the LiveData object with the current user's data
     * from the UserRepository.
     */
    public SubscribedFoodBanksViewModel(){
        liveUser = UserRepository.getInstance().getLiveUser();
    }


    /**
     * Returns the LiveData object containing the current user's data.
     *
     * @return A MutableLiveData object containing the current User.
     */
    public MutableLiveData<User> getLiveUser(){
        return liveUser;
    }


}
