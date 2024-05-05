package com.example.myapplication.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Location;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Repository class for handling data interactions with FoodBank objects within a Firebase database.
 * This class provides functionality to read, insert, update, and delete FoodBank data asynchronously.
 * It uses Firebase's real-time database capabilities to manage data flow and inform observers
 * or activities through a callback interface once data operations are completed or if an error occurs.
 *
 * @author Zijian Yang
 * @package com.example.myapplication.repository
 */
public class FoodBankRepository {

    private static FoodBankRepository instance;

    private MutableLiveData<ArrayList<FoodBank>> foodBanksLiveData;
    // Firebase database instance
    private FirebaseDatabase database;
    // List to hold food bank data fetched from database
    private ArrayList<FoodBank> foodBanks;
    // Interface for data status callbacks
    private DataStatus dataStatus;

    private final Lock lock = new ReentrantLock();

    /**
     * The DataStatus interface defines the callback methods that handle various data operation statuses in the FoodBankRepository.
     * This interface is crucial for asynchronous communication between the database operations performed in FoodBankRepository
     * and other parts of the application that need to respond to these data changes or statuses.
     */
    public interface DataStatus {
        /**
         * Called when data has been successfully loaded from the Firebase database. This method is invoked after
         * a successful read operation from the database, which asynchronously fetches all entries of FoodBanks and passes them along
         * with their database keys to the calling context.
         *
         * @param foodBanks An ArrayList of FoodBank objects that were loaded from the Firebase database. Each FoodBank object
         *                  represents a record in the Firebase database, containing information about a specific food bank such as
         *                  its capacity, location, and food inventory.
         * @param keys      A list of String objects representing the unique keys for each FoodBank record in the Firebase database.
         *                  These keys are important for operations that require specific database entries to be updated or deleted,
         *                  as they uniquely identify each record.
         *                  <p>
         *                  This method enables the application to update UI components or perform other actions in response to the data being
         *                  loaded, such as displaying the food banks on a map or in a list.
         */
        void DataIsLoaded(ArrayList<FoodBank> foodBanks, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();

        void Error(Exception e);
    }

    /**
     * Constructor initializes a new instance of FoodBankRepository.
     * It sets up the Firebase database connection and initializes the list for storing FoodBank objects.
     */
    private FoodBankRepository() {
        // Get the Firebase database instance
        database = FirebaseDatabase.getInstance("https://comp2100-6442-4f828-default-rtdb.asia-southeast1.firebasedatabase.app");
        // Initialize the list to hold FoodBanks
        foodBanks = new ArrayList<>();
        foodBanksLiveData = new MutableLiveData<>();
        loadFoodBanks();
    }

    public static FoodBankRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new FoodBankRepository();
                }
            }
        }
        return instance;
    }




    public FoodBank getFoodBankById(int id) {
        if (foodBanks.isEmpty()) {
            return null;
        }

        Optional<FoodBank> result = foodBanks.stream()
                .filter(foodBank -> foodBank.getId() == id)
                .findFirst();

        return result.orElse(null);
    }

    public List<FoodBank> getFoodBankListByIdList(List<String> idList){
        if (foodBanks.isEmpty()) {
            return Collections.emptyList();
        }

        return idList.stream()
                .map(id -> getFoodBankById(Integer.parseInt(id)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    /**
     * Reads the list of FoodBanks from Firebase and notifies the DataStatus callback interface upon completion
     * or if an error occurs. Each FoodBank object is parsed from the Firebase snapshot, and the geographic
     * coordinates are converted to a Location object before adding the FoodBank to the list.
     *
     * @param dataStatus The callback interface through which data load results or errors are communicated.
     */
    public void readFoodBanks(final DataStatus dataStatus) {
        lock.lock();
        try {
            DatabaseReference ref = database.getReference();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        lock.lock();
                        foodBanks.clear();
                        ArrayList<String> keys = new ArrayList<>();
                        for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                            keys.add(keyNode.getKey());
                            FoodBank foodBank = keyNode.getValue(FoodBank.class);
                            foodBank.setLocation(new Location(foodBank.getLat(), foodBank.getLon()));
                            foodBanks.add(foodBank);
                        }
                        dataStatus.DataIsLoaded(foodBanks, keys);
                    } finally {
                        lock.unlock();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    try {
                        lock.lock();
                        dataStatus.Error(databaseError.toException());
                    } finally {
                        lock.unlock();
                    }
                }
            });
        } finally {
            lock.unlock();
        }
    }

    private void loadFoodBanks() {
        readFoodBanks(new FoodBankRepository.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<FoodBank> foodBanks, List<String> keys) {
                foodBanksLiveData.setValue(foodBanks);
            }

            @Override
            public void DataIsInserted() {
            }

            @Override
            public void DataIsUpdated() {
            }

            @Override
            public void DataIsDeleted() {
            }

            @Override
            public void Error(Exception e) {
                // TODO Log or handle errors
            }
        });
    }

    public void setUserLocationAndUpdateDistances(double latitude, double longitude) {
        Location userLocation = new Location(latitude, longitude);
        ArrayList<FoodBank> foodBanks = foodBanksLiveData.getValue();

        if (foodBanks != null) {
            for (FoodBank foodBank : foodBanks) {
                double distance = foodBank.getLocation().calculateDistance(userLocation);
                Log.d("UpdateDistances", "ID:" + foodBank.getId() + " distance:" + distance);
                foodBank.setDistanceToUser(distance);
            }

            // Sort the list based on distance to user
            Collections.sort(foodBanks, new Comparator<FoodBank>() {
                @Override
                public int compare(FoodBank fb1, FoodBank fb2) {
                    return Double.compare(fb1.getDistanceToUser(), fb2.getDistanceToUser());
                }
            });

            // Update the LiveData with the sorted list
            foodBanksLiveData.setValue(foodBanks);
        }
    }

    public LiveData<ArrayList<FoodBank>> getFoodBanksLiveData() {
        return foodBanksLiveData;
    }

}

