package com.example.myapplication.ui.subscribedFoodBanks;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.repository.UserRepository;
import com.example.myapplication.ui.foodbankProfile.FoodBankProfileActivity;

import java.util.List;

public class SubscribedFoodBanksActivity extends AppCompatActivity implements FoodBankRecyclerViewInterface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subscribed_food_banks_page);



        try {
            EdgeToEdge.enable(this);

            RecyclerView recyclerView = findViewById(R.id.recyclerViewSubscribedFoodBanks);
            if (recyclerView == null) {
                throw new NullPointerException("RecyclerView is null. Make sure the ID is correct and exists in your layout.");
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            List<String> subscribedFoodBanks = UserRepository.getInstance().getUser().getSubscribedFoodBanks();
            if (subscribedFoodBanks == null) {
                throw new NullPointerException("Subscribed food banks list is null.");
            }

            SubscribedFoodBanksAdaptor adaptor = new SubscribedFoodBanksAdaptor(this.getApplicationContext(), subscribedFoodBanks, this);
            if(adaptor.getFoodBanks().isEmpty() || adaptor.getFoodBanks() == null){
                Log.e(TAG, "Error initializing subscribed food banks");

            }

            recyclerView.setAdapter(adaptor);

        } catch (Exception e) {

            Log.e(TAG, "Error initializing recyclerView", e);
        }




    }

    @Override
    public void onItemClick(FoodBank clickedFoodBank) {
        Intent detailIntent = new Intent(this, FoodBankProfileActivity.class);
        //send foodbank Information
        Bundle bundle = new Bundle();
        bundle.putString("fb_name", clickedFoodBank.getName());
        bundle.putString("fb_number", clickedFoodBank.getTel());
        bundle.putString("fb_email", clickedFoodBank.getEmail());

        if(clickedFoodBank.isStatus()){
            bundle.putString("fb_sate", "open");
        }else{
            bundle.putString("fb_sate", "close");
        }

        bundle.putString("fb_street", clickedFoodBank.getStreet());
        bundle.putString("fb_city", clickedFoodBank.getSuburb());
        bundle.putString("fb_postalCode", clickedFoodBank.getPostcode());
        bundle.putString("fb_country", clickedFoodBank.getCountry());
        bundle.putString("fb_openHours", clickedFoodBank.getOpen_hours());
        bundle.putInt("fb_capacity",clickedFoodBank.getCapacity());
        bundle.putDouble("fb_distance", clickedFoodBank.getDistanceToUser());
        bundle.putString("fb_foundDate", clickedFoodBank.getDoe());
        bundle.putDouble("fb_latitude",clickedFoodBank.getLat());
        bundle.putDouble("fb_longitude",clickedFoodBank.getLon());
        bundle.putInt("fb_foodBankId",clickedFoodBank.getId());


        detailIntent.putExtras(bundle);
        startActivity(detailIntent);
    }



    }
