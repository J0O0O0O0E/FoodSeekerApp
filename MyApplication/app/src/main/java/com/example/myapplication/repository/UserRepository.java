package com.example.myapplication.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserRepository {

    private static UserRepository instance;
    public FirebaseAuth mAuth;
    public FirebaseFirestore fStore;

    public FirebaseUser currentUser;

    public MutableLiveData<User> liveUser;





    private UserRepository(){
        this.mAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();
        this.currentUser = mAuth.getCurrentUser();

    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }


    public void loadUser(){
        if(currentUser != null){
            String email = currentUser.getEmail();
            fStore.collection("User").
                    document(email).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User user = documentSnapshot.toObject(User.class);
                            if(user != null){
                                liveUser = new MutableLiveData<>();
                                liveUser.setValue(user);

                            }else {
                                Log.d(TAG, "No user data found in FireStore for ID: " + currentUser.getEmail());
                            }
                        }
                    });
        }
    }


    public void createUserProfile(FirebaseUser user) {
        String emailAddress = user.getEmail();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("contactNumber", "");
        userMap.put("email", emailAddress);
        userMap.put("subscribedFoodBanks", new ArrayList<>());
        userMap.put("userName", "");

        DocumentReference documentReference = fStore.collection("User").document(user.getEmail());
        documentReference.set(userMap).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "onSuccess: user Profile is created for" + emailAddress);
        }).addOnFailureListener(e -> {
            Log.w(TAG, "createUserWithEmail:failure", e);
        });
    }

    public void setUser(FirebaseUser firebaseUser){
        this.currentUser=firebaseUser;

    }

    public LiveData<User> getUser(){
        return liveUser;
    }










}
