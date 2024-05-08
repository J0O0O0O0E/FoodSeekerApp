package com.example.myapplication.ui.profile;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.User;
import com.example.myapplication.repository.UserRepository;

import java.util.Objects;

public class ProfileViewModel extends ViewModel {
//    private final MutableLiveData<String> mText;

    public static UserRepository userRepository;

    public User user;






    public ProfileViewModel() {
//        mText = new MutableLiveData<>();
        userRepository = UserRepository.getInstance();
//        String profileName = userRepository.getUser().email;
//        mText.setValue(profileName);
        user = userRepository.getUser();
    }

//    public LiveData<String> getText() {
//        return mText;
//    }

    public void updateUserName(String name){
        user.setUserName(name);
    }

    public User getUser() {
        return user;
    }

    public void updateUserNameToRepository(String name){
        userRepository.updateUserName(name);
    }


    public void updateUserprofileImg(Uri uri, Context context){
        userRepository.uploadImageToFirebase(uri, context);
    }
    public void updateContactNumberToRepository(String number){
        userRepository.updateContactNumber(number);
    }









//
//    public LiveData<String> getUserName() {
//    }
}


