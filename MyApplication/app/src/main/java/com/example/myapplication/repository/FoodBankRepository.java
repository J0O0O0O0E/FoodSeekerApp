package com.example.myapplication.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.datastructure.AVLTree;
import com.example.myapplication.datastructure.DoubleAVLTree;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Location;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
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

    // LiveData for observing changes to the list of FoodBanks
    private MutableLiveData<ArrayList<FoodBank>> foodBanksLiveData;
    // Firebase database instance
    private FirebaseDatabase database;
    // List to hold food bank data fetched from the database
    private ArrayList<FoodBank> foodBanks;
    // Interface for data status callbacks
    private DataStatus dataStatus;
    // AVLTree for FoodBank capacity
    private AVLTree avlTree;
    // DoubleAVLTree for FoodBank capacity and rating
    private DoubleAVLTree doubleAVLTree;

    // Lock for thread-safe operations
    private final Lock lock = new ReentrantLock();

    /**
     * The DataStatus interface defines the callback methods that handle various data operation statuses in the FoodBankRepository.
     * This interface is crucial for asynchronous communication between the database operations performed in FoodBankRepository
     * and other parts of the application that need to respond to these data changes or statuses.
     */
    public interface DataStatus {
        /**
         * Called when data has been successfully loaded from the Firebase database.
         *
         * @param foodBanks An ArrayList of FoodBank objects loaded from the Firebase database.
         * @param keys      A list of String objects representing the unique keys for each FoodBank record in the Firebase database.
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
        avlTree = new AVLTree();
        doubleAVLTree = new DoubleAVLTree();
        foodBanksLiveData = new MutableLiveData<>();
        loadFoodBanks();
    }

    /**
     * Gets the singleton instance of FoodBankRepository.
     *
     * @return The singleton instance of FoodBankRepository.
     */
    public static FoodBankRepository getInstance() {
        if (instance == null) {
            synchronized (FoodBankRepository.class) {
                if (instance == null) {
                    instance = new FoodBankRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Gets a list of FoodBank objects by their IDs.
     *
     * @param idList List of FoodBank IDs.
     * @return List of FoodBank objects.
     */
    public List<FoodBank> getFoodBankListByIdList(List<String> idList) {
        if (foodBanks.isEmpty()) {
            return Collections.emptyList();
        }

        return idList.stream()
                .map(id -> getFoodBankById(Integer.parseInt(id)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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



    /**
     * Reads the list of FoodBanks from Firebase and notifies the DataStatus callback interface upon completion
     * or if an error occurs.
     *
     * @param dataStatus The callback interface through which data load results or errors are communicated.
     */
    public void readFoodBanks(final DataStatus dataStatus) {
        lock.lock();
        // Reference to the root in the database, root dir has no parameter for getReference method
        try{DatabaseReference ref = database.getReference();
        // Add value event listener to fetch data
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the existing list
                foodBanks.clear();
                avlTree = new AVLTree();
                // List to hold the keys of the nodes
                ArrayList<String> keys = new ArrayList<>();
                // Loop through the snapshot children
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    // Store the key
                    keys.add(keyNode.getKey());
                    // Get the FoodBank object from the snapshot
                    FoodBank foodBank = keyNode.getValue(FoodBank.class);
                    // Set the Location for instance via Location class and latitude and longitude
                    foodBank.setLocation(new Location(foodBank.getLat(), foodBank.getLon()));
                    // Add it to the food banks list
                    foodBanks.add(foodBank);
                    avlTree = avlTree.insert(avlTree, foodBank);
                    doubleAVLTree.insert(foodBank);
                }

                // Log out the tree for debugging purposes
                // avlTree.printInOrder();
                avlTree.countNodes();
                doubleAVLTree.printAllNodes();
                doubleAVLTree.countNodes();

                // Notify that data is loaded along with the keys of the nodes
                dataStatus.DataIsLoaded(foodBanks, keys);
            }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                        dataStatus.Error(databaseError.toException());

                }
            });
        }
        finally{
            lock.unlock();
        }
        }

    /**
     * Loads the list of FoodBanks from Firebase.
     */
    private void loadFoodBanks() {
        readFoodBanks(new DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<FoodBank> foodBanks, List<String> keys) {
                foodBanksLiveData.setValue(foodBanks);
            }

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated() {}

            @Override
            public void DataIsDeleted() {}

            @Override
            public void Error(Exception e) {}
        });
    }

    public DoubleAVLTree getDoubleAVLTree(){
        return doubleAVLTree;
    }
}
