package com.example.myapplication.repository;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserRepository {

    private static UserRepository instance;
    public FirebaseAuth mAuth;
    public FirebaseFirestore fStore;
    public FirebaseUser currentUser;
    public MutableLiveData<User> liveUser;
    private final Lock lock = new ReentrantLock();
    private StorageReference storageReference;


    private UserRepository(){
        this.mAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();
        this.currentUser = mAuth.getCurrentUser();
        this.liveUser = new MutableLiveData<>();
        this.storageReference = FirebaseStorage.getInstance().getReference();

    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public void loadUser(){
        if(currentUser != null){
            String email = currentUser.getEmail();
            assert email != null;
            fStore.collection("User").document(email).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User userLoaded = documentSnapshot.toObject(User.class);
                            if(userLoaded != null){
                                lock.lock();
                                try {
                                    liveUser.setValue(userLoaded);
//                                    user = userLoaded;
                                } finally {
                                    lock.unlock();
                                }
                            } else {
                                Log.d(TAG, "No user data found in FireStore for ID: " + currentUser.getEmail());
                            }
                        }
                    });
        }
    }

    public void createUserProfile(FirebaseUser user) {
        String emailAddress = user.getEmail();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("author", false);
        userMap.put("contactNumber", "");
        userMap.put("email", emailAddress);
        userMap.put("imgUrl","");
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
        lock.lock();
        try {
            this.currentUser = firebaseUser;
        } finally {
            lock.unlock();
        }
    }

    public MutableLiveData<User> getLiveUser(){
        lock.lock();
        try{
            return liveUser;
        }finally{
            lock.unlock();
        }
    }

    public User getUser(){
        lock.lock();
        try {
            return liveUser.getValue();
        } finally {
            lock.unlock();
        }
    }

    public String getUserEmail(){
        lock.lock();
        try {
            return Objects.requireNonNull(liveUser.getValue()).getEmail();
        } finally {
            lock.unlock();
        }
    }

    public String getuserimg(){
        lock.lock();
        try {
            return Objects.requireNonNull(liveUser.getValue()).getimgUrl();
        } finally {
            lock.unlock();
        }
    }

    public void updateUserName(String name){
        lock.lock();
        try {
//            Objects.requireNonNull(liveUser.getValue()).setUserName(name);
            User currentUser = liveUser.getValue();
            assert currentUser != null;
            currentUser.setUserName(name);
            liveUser.postValue(currentUser);


            fStore.collection("User").document(this.getUserEmail())
                    .update("userName", this.getUser().getUserName())
                    .addOnSuccessListener(aVoid -> {
                        Log.d("UpdatedUser", "User name updated successfully.");
                    })
                    .addOnFailureListener(e -> {
                        Log.e("UpdatedUser", "Error updating user name", e);
                    });
//            liveUser.setValue(user);
        } finally {
            lock.unlock();
        }
    }


    public void uploadImageToFirebase(Uri uri, Context context) {
        lock.lock();
        try{
            if (uri != null) {
                StorageReference fileRef = storageReference.child("users/" + System.currentTimeMillis() + "-profile.jpg");
                fileRef.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                    fileRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                        String imageUrl = downloadUri.toString();
                        saveImageUrlToFirestore(imageUrl);
                        User currentUser = liveUser.getValue();
                        assert currentUser != null;
                        currentUser.setImgUrl(imageUrl);
                        liveUser.postValue(currentUser);

                        Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show();
                    });
                }).addOnFailureListener(e -> Toast.makeText(context, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());



            }
        }finally {
            lock.unlock();
        }

    }
    private void saveImageUrlToFirestore(String imageUrl) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("imgUrl", imageUrl);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = UserRepository.getInstance().getUser().getEmail(); // Assuming email as unique identifier
        db.collection("User").document(userId).update(updates);
    }

    public void updateContactNumber(String number){
        lock.lock();
        try {
//            Objects.requireNonNull(liveUser.getValue()).setContactNumber(number);
            User currentUser = liveUser.getValue();
            assert currentUser != null;
            currentUser.setContactNumber(number);
            liveUser.postValue(currentUser);


            fStore.collection("User").document(this.getUserEmail())
                    .update("contactNumber", this.getUser().getContactNumber())
                    .addOnSuccessListener(aVoid -> {
                        Log.d("UpdatedUser", "Contact number updated successfully.");
                    })
                    .addOnFailureListener(e -> {
                        Log.e("UpdatedUser", "Error updating contact number", e);
                    });
//            liveUser.setValue(user);
        } finally {
            lock.unlock();
        }
    }

    public void addSubscribedFoodBanks(String id){
        lock.lock();
        try {
//            Objects.requireNonNull(liveUser.getValue()).addSubscribedFoodBank(id);
            User currentUser = liveUser.getValue();
            assert currentUser != null;
            currentUser.addSubscribedFoodBank(id);
            liveUser.postValue(currentUser);

            fStore.collection("User").document(this.getUserEmail())
                    .update("subscribedFoodBanks", FieldValue.arrayUnion(id))
                    .addOnSuccessListener(aVoid -> {
                        Log.d("UpdatedFoodBanks", "Food banks updated successfully.");
                    })
                    .addOnFailureListener(e -> {
                        Log.e("UpdatedFoodBanks", "Error updating food banks", e);
                    });
//            liveUser.setValue(user);

        } finally {
            lock.unlock();
        }
    }

    public void removeSubscribedFoodBanks(String id){
        lock.lock();
        try {
//            Objects.requireNonNull(liveUser.getValue()).removeSubscribedFoodBank(id);
            User currentUser = liveUser.getValue();
            assert currentUser != null;
            currentUser.removeSubscribedFoodBank(id);
            liveUser.postValue(currentUser);

            fStore.collection("User").document(getUserEmail())
                    .update("subscribedFoodBanks", FieldValue.arrayRemove(id))
                    .addOnSuccessListener(aVoid -> {
                        Log.d("UpdatedFoodBanks", "Food banks updated successfully.");
                    })
                    .addOnFailureListener(e -> {
                        Log.e("UpdatedFoodBanks", "Error updating food banks", e);
                    });
//            liveUser.setValue(user);
        } finally {
            lock.unlock();
        }
    }











}
