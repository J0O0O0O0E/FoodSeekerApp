package com.example.myapplication.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.User;
import com.example.myapplication.repository.UserRepository;

import java.util.Objects;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public static UserRepository userRepository;

    public static User user;

    public static String email;





    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        userRepository = UserRepository.getInstance();
        String profileName = userRepository.getUser().getValue().email;
        mText.setValue(profileName);
    }

    public LiveData<String> getText() {
        return mText;
    }




    public void setUserName(String newName){
        user.setUserName(newName);

    }

    public void setContactNumber(String number){
        user.setContactNumber(number);
    }






//
//    public LiveData<String> getUserName() {
//    }
}


