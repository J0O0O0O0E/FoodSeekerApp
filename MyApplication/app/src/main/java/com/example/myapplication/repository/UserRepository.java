package com.example.myapplication.repository;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Repository class for handling user data operations including authentication,
 * Firestore interactions, and real-time updates.
 *
 * This class follows the Singleton design pattern to ensure only one instance
 * of UserRepository exists throughout the application lifecycle.
 *
 * This class provides methods to load, update, and manage user data in Firestore,
 * including user profile information, subscribed food banks, and profile images.
 *
 * @autor Shuhui Yang u7662582, Zhi Li u7640966
 */
public class UserRepository {


    private static volatile UserRepository instance;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    public FirebaseUser currentUser;
    public MutableLiveData<User> liveUser;
    private final Lock lock = new ReentrantLock();
    private StorageReference storageReference;

    private List<FoodBank> subscribedFoodBanks = new ArrayList<>();




    /**
     * Private constructor to initialize Firebase authentication, Firestore,
     * current user, and live data.
     */

    private UserRepository(){
        this.mAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();
        this.currentUser = mAuth.getCurrentUser();
        this.liveUser = new MutableLiveData<>();
        this.storageReference = FirebaseStorage.getInstance().getReference();

    }


    /**
     * Gets the singleton instance of UserRepository.
     *
     * @return The singleton instance of UserRepository.
     */
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



    /**
     * Loads the current user's profile from Firestore and updates liveUser.
     */
    public void loadUser() {
        if (currentUser != null) {
            String email = currentUser.getEmail();
            assert email != null;
            fStore.collection("User").document(email).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User userLoaded = documentSnapshot.toObject(User.class);
                            if (userLoaded != null) {
                                liveUser.postValue(userLoaded);
                                CompletableFuture.runAsync(() -> {
                                    try {
                                        // Ensure data is loaded
                                        FoodBankRepository.getInstance().awaitDataLoaded();

                                        lock.lock();
                                        try {
                                            subscribedFoodBanks = FoodBankRepository.getInstance()
                                                    .getFoodBankListByIdList(userLoaded.getSubscribedFoodBanks());
                                        } finally {
                                            lock.unlock();
                                        }
                                    } catch (InterruptedException | ExecutionException e) {
                                        Log.e(TAG, "Error while waiting for FoodBanks to load", e);
                                    } catch (Exception e) {
                                        Log.e(TAG, "Error while loading subscribed FoodBanks", e);
                                    }
                                });
                            } else {
                                Log.d(TAG, "No user data found in FireStore for ID: " + currentUser.getEmail());
                            }
                        }
                    });
        }
    }



    /**
     * Creates a new user profile in Firestore.
     *
     * @param user The FirebaseUser object containing the user's information.
     */
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
        loadUser();
    }


    /**
     * Sets the current FirebaseUser.
     *
     * @param firebaseUser The FirebaseUser to set as the current user.
     */
    public void setUser(FirebaseUser firebaseUser){
        lock.lock();
        try {
            this.currentUser = firebaseUser;
        } finally {
            lock.unlock();
        }
    }


    /**
     * Returns the live data object containing the current user's data.
     *
     * @return The MutableLiveData object containing the current user's data.
     */
    public MutableLiveData<User> getLiveUser(){
        lock.lock();
        try{
            return liveUser;
        }finally{
            lock.unlock();
        }
    }


    /**
     * Returns the current user object.
     *
     * @return The current User object.
     */

    public User getUser(){
        lock.lock();
        try {
            return liveUser.getValue();
        } finally {
            lock.unlock();
        }
    }



    /**
     * Returns the current user's email.
     *
     * @return The current user's email.
     */
    public String getUserEmail(){
        try {
            return Objects.requireNonNull(liveUser.getValue()).getEmail();
        } finally {
        }
    }


    /**
     * Returns the current user's profile image URL.
     *
     * @return The current user's profile image URL.
     */
    public String getuserimg(){
        lock.lock();
        try {
            return Objects.requireNonNull(liveUser.getValue()).getimgUrl();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Updates the current user's username.
     *
     * @param name The new username to set.
     */
    public void updateUserName(String name){
        lock.lock();
        try {

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

        } finally {
            lock.unlock();
        }
    }


    /**
     * Uploads the user's profile image to Firebase Storage and updates the user's profile.
     *
     * @param uri     The Uri of the image to upload.
     * @param context The context to use for displaying Toast messages.
     */
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



    /**
     * Saves the user's profile image URL to Firestore.
     *
     * @param imageUrl The URL of the uploaded image.
     */
    private void saveImageUrlToFirestore(String imageUrl) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("imgUrl", imageUrl);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = UserRepository.getInstance().getUser().getEmail(); // Assuming email as unique identifier
        db.collection("User").document(userId).update(updates);
    }


    /**
     * Updates the user's contact number in Firestore and live data.
     *
     * @param number The new contact number to set.
     */
    public void updateContactNumber(String number){
        lock.lock();
        try {
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

        } finally {
            lock.unlock();
        }
    }





    /**
     * Adds a food bank to the user's list of subscribed food banks.
     *
     * @param id The ID of the food bank to add.
     */
    public void addSubscribedFoodBanks(String id){
        lock.lock();
        try {
            User currentUser = liveUser.getValue();
            assert currentUser != null;
            currentUser.addSubscribedFoodBank(id);
            liveUser.postValue(currentUser);
            FoodBank addedFoodBank = FoodBankRepository.getInstance().getFoodBankById(Integer.parseInt(id));
            subscribedFoodBanks.add(addedFoodBank);


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



    /**
     * Removes a food bank from the user's list of subscribed food banks.
     *
     * @param id The ID of the food bank to remove.
     */
    public void removeSubscribedFoodBanks(String id){
        lock.lock();
        try {

            User currentUser = liveUser.getValue();
            assert currentUser != null;
            currentUser.removeSubscribedFoodBank(id);
            liveUser.postValue(currentUser);
            FoodBank removedFoodBank = FoodBankRepository.getInstance().getFoodBankById(Integer.parseInt(id));
            subscribedFoodBanks.remove(removedFoodBank);

            fStore.collection("User").document(getUserEmail())
                    .update("subscribedFoodBanks", FieldValue.arrayRemove(id))
                    .addOnSuccessListener(aVoid -> {
                        Log.d("UpdatedFoodBanks", "Food banks updated successfully.");
                    })
                    .addOnFailureListener(e -> {
                        Log.e("UpdatedFoodBanks", "Error updating food banks", e);
                    });

        } finally {
            lock.unlock();
        }
    }



    /**
     * Updates the user's subscribed food banks in real-time by adding a snapshot listener
     * to the Firestore document representing the user.
     */
    public void updateFoodBanks(){
        DocumentReference docRef = fStore.
                collection("User").document(
                        getUserEmail());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {Map<String, Object> data = snapshot.getData();
                    if (data != null) {
                        List<String> foodBankIds = (List<String>) data.get("subscribedFoodBanks");
                        if (foodBankIds != null) {
                            updateLiveUserFoodBanks(foodBankIds);
                        } else {
                            Log.d(TAG, "No subscribed food banks found or field is missing.");
                        }

                    } else {
                        Log.d(TAG, "No subscribed food banks found or field is missing.");
                    }
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }

        });
    }



    /**
     * Updates the user's subscribed food banks in live data.
     *
     * @param foodBankIds The list of food bank IDs to update.
     */
    private void updateLiveUserFoodBanks(List<String> foodBankIds) {
        lock.lock();
        try {
            User currentUser = liveUser.getValue();
            if (currentUser != null) {
                currentUser.setSubscribedFoodBanks(foodBankIds);
                liveUser.postValue(currentUser);
                subscribedFoodBanks = FoodBankRepository.getInstance().
                        getFoodBankListByIdList(foodBankIds);
            }
        } finally {
            lock.unlock();
        }
    }



    /**
     * Returns the list of subscribed food banks.
     *
     * @return The list of subscribed food banks.
     */
    public List<FoodBank> getSubscribedFoodBanks() {
        return subscribedFoodBanks;
    }
}
