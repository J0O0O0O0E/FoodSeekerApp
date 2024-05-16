package com.example.myapplication.ui.profile;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.User;
import com.example.myapplication.repository.UserRepository;

import java.util.Objects;


/**
 * ProfileViewModel is responsible for preparing and managing the data for ProfileFragment.
 * It handles the interaction between the UI and the UserRepository.
 * @author Shuhui Yang
 */
public class ProfileViewModel extends ViewModel {

    public static UserRepository userRepository;

    public User user;


    /**
     * Constructor that initializes the UserRepository and retrieves the current user's data.
     */
    public ProfileViewModel() {
        userRepository = UserRepository.getInstance();
        user = userRepository.getUser();
    }




    /**
     * Returns the current user.
     *
     * @return The current User object.
     */
    public User getUser() {
        return user;
    }



    /**
     * Updates the user's name in the UserRepository.
     *
     * @param name The new name to be updated.
     */
    public void updateUserNameToRepository(String name){
        userRepository.updateUserName(name);
    }




    /**
     * Updates the user's contact number in the UserRepository.
     *
     * @param number The new contact number to be updated.
     */
    public void updateContactNumberToRepository(String number){
        userRepository.updateContactNumber(number);
    }







    /**
     * Clears the user data by setting the user object to null.
     */
    public void clearData() {
        user = null; // Clear user object
    }

}


