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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

/**
 * Repository class for handling data interactions with FoodBank objects within a Firebase database.
 * This class provides functionality to read, insert, update, and delete FoodBank data asynchronously.
 * It uses Firebase's real-time database capabilities to manage data flow and inform observers
 * or activities through a callback interface once data operations are completed or if an error occurs.
 *
 * This class implements a singleton pattern to ensure that only one instance of the repository exists.
 * It also uses AVL and DoubleAVL trees to efficiently manage and query FoodBank data.
 * @author Zijian Yang
 */
public class FoodBankRepository {

    // Singleton instance of the repository
    private static FoodBankRepository instance;

    // LiveData for observing changes to the list of FoodBanks
    private MutableLiveData<ArrayList<FoodBank>> foodBanksLiveData;
    // Firebase database instance
    private FirebaseDatabase database;
    // List to hold FoodBank data fetched from the database
    private ArrayList<FoodBank> foodBanks;
    // Interface for data status callbacks
    private DataStatus dataStatus;
    // AVLTree for managing FoodBank data based on capacity
    private AVLTree avlTree;
    // DoubleAVLTree for managing FoodBank data based on capacity and rating
    private DoubleAVLTree doubleAVLTree;
    // Future to track data loading completion
    private CompletableFuture<Void> dataLoadedFuture;
    // Executor service for asynchronous operations
    private ExecutorService executorService;
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
         * @param foodBanks An ArrayList of FoodBank objects that were loaded from the Firebase database.
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
        database = FirebaseDatabase.getInstance("https://comp2100-6442-4f828-default-rtdb.asia-southeast1.firebasedatabase.app");
        foodBanks = new ArrayList<>();
        avlTree = new AVLTree();
        doubleAVLTree = new DoubleAVLTree();
        foodBanksLiveData = new MutableLiveData<>();
        dataLoadedFuture = new CompletableFuture<>();
        executorService = Executors.newSingleThreadExecutor();
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
     * Retrieves a list of FoodBank objects matching the given list of IDs.
     *
     * @param idList The list of FoodBank IDs to search for.
     * @return A list of FoodBank objects matching the given IDs.
     */
    public List<FoodBank> getFoodBankListByIdList(List<String> idList) {
        try {
            awaitDataLoaded();
            if (foodBanks.isEmpty()) {
                return new ArrayList<>();
            }
            return idList.stream()
                    .map(id -> avlTree.findById(Integer.parseInt(id)))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Retrieves a FoodBank object by its ID.
     *
     * @param id The ID of the FoodBank to retrieve.
     * @return The FoodBank object with the given ID, or null if not found.
     */
    public FoodBank getFoodBankById(int id) {
        try {
            awaitDataLoaded();
            if (foodBanks.isEmpty()) {
                return null;
            }
            Optional<FoodBank> result = foodBanks.stream()
                    .filter(foodBank -> foodBank.getId() == id)
                    .findFirst();
            return result.orElse(null);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Reads the FoodBank data from the Firebase database.
     *
     * @param dataStatus The callback interface to handle data status.
     */
    public void readFoodBanks(final DataStatus dataStatus) {
        lock.lock();
        try {
            DatabaseReference ref = database.getReference();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    foodBanks.clear();
                    avlTree = new AVLTree();
                    ArrayList<String> keys = new ArrayList<>();
                    for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                        keys.add(keyNode.getKey());
                        FoodBank foodBank = keyNode.getValue(FoodBank.class);
                        foodBank.setLocation(new Location(foodBank.getLat(), foodBank.getLon()));
                        foodBanks.add(foodBank);
                        avlTree = avlTree.insert(avlTree, foodBank);
                        doubleAVLTree.insert(foodBank);
                    }
                    avlTree.countNodes();
                    doubleAVLTree.printAllNodes();
                    doubleAVLTree.countNodes();
                    dataStatus.DataIsLoaded(foodBanks, keys);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    dataStatus.Error(databaseError.toException());
                }
            });
        } finally {
            lock.unlock();
        }
    }

    /**
     * Loads the FoodBank data from the Firebase database asynchronously.
     */
    private void loadFoodBanks() {
        executorService.execute(() -> {
            readFoodBanks(new DataStatus() {
                @Override
                public void DataIsLoaded(ArrayList<FoodBank> foodBanks, List<String> keys) {
                    foodBanksLiveData.postValue(foodBanks);
                    dataLoadedFuture.complete(null);
                }

                @Override
                public void DataIsInserted() {}

                @Override
                public void DataIsUpdated() {}

                @Override
                public void DataIsDeleted() {}

                @Override
                public void Error(Exception e) {
                    dataLoadedFuture.completeExceptionally(e);
                }
            });
        });
    }

    /**
     * Gets the DoubleAVLTree instance used for managing FoodBank data.
     *
     * @return The DoubleAVLTree instance.
     */
    public DoubleAVLTree getDoubleAVLTree() {
        return doubleAVLTree;
    }

    /**
     * Waits for the data to be loaded from the Firebase database.
     *
     * @throws InterruptedException if the current thread was interrupted while waiting.
     * @throws ExecutionException if the computation threw an exception.
     */
    public void awaitDataLoaded() throws InterruptedException, ExecutionException {
        dataLoadedFuture.get();
    }

    /**
     * Checks if the data has been loaded from the Firebase database.
     *
     * @return true if the data is loaded, false otherwise.
     */
    public boolean isDataLoaded() {
        return dataLoadedFuture.isDone();
    }

    /**
     * Gets the LiveData object for observing changes to the list of FoodBanks.
     *
     * @return The LiveData object for FoodBanks.
     */
    public MutableLiveData<ArrayList<FoodBank>> getFoodBanksLiveData() {
        return foodBanksLiveData;
    }
}
