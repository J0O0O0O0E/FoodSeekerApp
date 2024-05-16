package com.example.myapplication.ui.subscribedFoodBanks;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;


import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.User;
import com.example.myapplication.repository.FoodBankRepository;
import com.example.myapplication.repository.UserRepository;
import com.example.myapplication.ui.foodbankProfile.FoodBankProfileActivity;
import com.example.myapplication.ui.home.HomeViewModel;
import com.example.myapplication.ui.home.announcement.AnnouncementAdapter;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




/**
 * SubscribedFoodBanksActivity displays a list of food banks the user is subscribed to.
 * It implements the FoodBankRecyclerViewInterface to handle item clicks.
 * @author Shuhui Yang u7662582
 */
public class SubscribedFoodBanksActivity extends AppCompatActivity implements FoodBankRecyclerViewInterface {

    private SubscribedFoodBanksViewModel viewModel;
    private RecyclerView recyclerView;
    private SubscribedFoodBanksAdaptor adapter;

    private ProgressBar progressBar;

    private ExecutorService executorService;




    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the most recent data supplied in onSaveInstanceState(Bundle).
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed_food_banks_page);


        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(SubscribedFoodBanksViewModel.class);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        executorService = Executors.newSingleThreadExecutor();

        progressBar = findViewById(R.id.progressBar); // Ensure you have a ProgressBar in your layout

        try {
            recyclerView = findViewById(R.id.recyclerViewSubscribedFoodBanks);
            if (recyclerView == null) {
                throw new NullPointerException("RecyclerView is null. Make sure the ID is correct and exists in your layout.");
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            // Load subscribed food banks if data is already loaded, otherwise show loading indicator
            if (FoodBankRepository.getInstance().isDataLoaded()) {
                hideLoading();
                loadSubscribedFoodBanks();
            } else {
                showLoading();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing recyclerView", e);
        }
    }


    /**
     * Show the loading indicator and hide the RecyclerView.
     */
    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }



    /**
     * Hide the loading indicator and show the RecyclerView.
     */
    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    /**
     * Load the subscribed food banks from UserRepository and set up the adapter.
     */
    private void loadSubscribedFoodBanks() {
        executorService.execute(() -> {
            List<String> subscribedFoodBanks = UserRepository.getInstance().getUser().getSubscribedFoodBanks();
            runOnUiThread(() -> {
                if (subscribedFoodBanks != null && !subscribedFoodBanks.isEmpty()) {
                    adapter = new SubscribedFoodBanksAdaptor(getApplicationContext(), subscribedFoodBanks, SubscribedFoodBanksActivity.this);
                    recyclerView.setAdapter(adapter);


                    // Update food banks in the repository
                    UserRepository.getInstance().updateFoodBanks();


                    // Observe changes to the user's data
                    viewModel.getLiveUser().observe(SubscribedFoodBanksActivity.this, new Observer<User>() {
                        @Override
                        public void onChanged(@Nullable User user) {
                            if (user != null) {
                                adapter.updateSubscribedFoodBanks(user.getSubscribedFoodBanks());
                            }
                        }
                    });
                } else {
                    Log.e(TAG, "No subscribed food banks found or user data is null");
                }
            });
        });
    }



    /**
     * Handle item selections in the options menu.
     *
     * @param item The selected menu item.
     * @return true if the item was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * Handle item clicks in the RecyclerView.
     *
     * @param clickedFoodBank The FoodBank object that was clicked.
     */
    @Override
    public void onItemClick(FoodBank clickedFoodBank) {
        Intent detailIntent = new Intent(this, FoodBankProfileActivity.class);
        //send foodbank Information
        Bundle bundle = new Bundle();
        bundle.putString("fb_name", clickedFoodBank.getName());
        bundle.putString("fb_number", clickedFoodBank.getTel());
        bundle.putString("fb_email", clickedFoodBank.getEmail());

        if (clickedFoodBank.isStatus()) {
            bundle.putString("fb_sate", "open");
        } else {
            bundle.putString("fb_sate", "close");
        }



        // Add food bank details to the bundle
        bundle.putString("fb_street", clickedFoodBank.getStreet());
        bundle.putString("fb_city", clickedFoodBank.getSuburb());
        bundle.putString("fb_postCode", clickedFoodBank.getPostcode());
        bundle.putString("fb_country", clickedFoodBank.getCountry());
        bundle.putString("fb_openHours", clickedFoodBank.getOpen_hours());
        bundle.putInt("fb_capacity", clickedFoodBank.getCapacity());
        bundle.putDouble("fb_distance", clickedFoodBank.getDistanceToUser());
        bundle.putString("fb_foundDate", clickedFoodBank.getDoe());
        bundle.putDouble("fb_latitude", clickedFoodBank.getLat());
        bundle.putDouble("fb_longitude", clickedFoodBank.getLon());
        bundle.putInt("fb_foodBankId", clickedFoodBank.getId());
        bundle.putDouble("fb_rate", clickedFoodBank.getRating());


        // Add food quantities
        bundle.putInt("Pasta", clickedFoodBank.getFood1_pasta());
        bundle.putInt("Bread", clickedFoodBank.getFood2_bread());
        bundle.putInt("Milk", clickedFoodBank.getFood3_milk());
        bundle.putInt("Pie", clickedFoodBank.getFood4_pie());
        bundle.putInt("Vegetable", clickedFoodBank.getFood5_vet());

        bundle.putDouble("fb_latitude",clickedFoodBank.getLat());
        bundle.putDouble("fb_longitude",clickedFoodBank.getLon());
        bundle.putInt("fb_foodBankId",clickedFoodBank.getId());


        detailIntent.putExtras(bundle);
        startActivity(detailIntent);
    }




    }
