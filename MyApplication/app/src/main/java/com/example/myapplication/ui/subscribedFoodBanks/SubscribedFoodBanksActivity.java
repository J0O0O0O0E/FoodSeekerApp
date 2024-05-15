package com.example.myapplication.ui.subscribedFoodBanks;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;


import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SubscribedFoodBanksActivity extends AppCompatActivity implements FoodBankRecyclerViewInterface {

    private SubscribedFoodBanksViewModel viewModel;
    private RecyclerView recyclerView;
    private SubscribedFoodBanksAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subscribed_food_banks_page);

        viewModel = new ViewModelProvider(this).get(SubscribedFoodBanksViewModel.class);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        try {
            EdgeToEdge.enable(this);

            RecyclerView recyclerView = findViewById(R.id.recyclerViewSubscribedFoodBanks);
            if (recyclerView == null) {
                throw new NullPointerException("RecyclerView is null. Make sure the ID is correct and exists in your layout.");
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            List<String> subscribedFoodBanks = UserRepository.getInstance().getUser().getSubscribedFoodBanks();


            adapter = new SubscribedFoodBanksAdaptor(this.getApplicationContext(), subscribedFoodBanks, this);
            if (adapter.getFoodBanks().isEmpty() || adapter.getFoodBanks() == null) {
                Log.e(TAG, "Error initializing subscribed food banks");

            }

            recyclerView.setAdapter(adapter);
//
//            DocumentReference docRef = viewModel.getFStore().
//                    collection("User").document(
//                            Objects.requireNonNull(viewModel.getLiveUser().getValue()).getEmail());


//            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    User user = viewModel.getLiveUser().getValue();
//                    if (user != null && value != null) {
//                        adapter.updateSubscribedFoodBanks(user.getSubscribedFoodBanks());
//                    }
//                }
//            });

            UserRepository.getInstance().updateFoodBanks();

            viewModel.getLiveUser().observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if(user != null){
                        adapter.updateSubscribedFoodBanks(user.getSubscribedFoodBanks());
                    }

                }
            });

        } catch (Exception e) {

            Log.e(TAG, "Error initializing recyclerView", e);
        }


    }
//


//    }

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

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    }
