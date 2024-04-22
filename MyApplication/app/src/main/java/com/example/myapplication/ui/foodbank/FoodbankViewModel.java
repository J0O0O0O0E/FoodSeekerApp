package com.example.myapplication.ui.foodbank;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.myapplication.model.FoodBank;
import com.example.myapplication.repository.FoodBankRepository;
import com.example.myapplication.model.Location;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FoodbankViewModel extends ViewModel {
    private MutableLiveData<ArrayList<FoodBank>> foodBanksLiveData;
    private FoodBankRepository foodBankRepository;
    private final MutableLiveData<String> mText;

    public FoodbankViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Foodbank fragment");
        foodBankRepository = new FoodBankRepository();
        foodBanksLiveData = new MutableLiveData<>();
        loadFoodBanks();
    }

    private void loadFoodBanks() {
        foodBankRepository.readFoodBanks(new FoodBankRepository.DataStatus() {
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
            public void Error(Exception e) {
                // TODO pass error msg
            }
        });
    }

    public LiveData<ArrayList<FoodBank>> getFoodBanksLiveData() {
        return foodBanksLiveData;
    }

    public void setUserLocationAndUpdateDistances(double latitude, double longitude) {
        Location userLocation = new Location(latitude, longitude);
        ArrayList<FoodBank> foodBanks = foodBanksLiveData.getValue();

        if (foodBanks != null) {
            for (FoodBank foodBank : foodBanks) {
                double distance = foodBank.getLocation().calculateDistance(userLocation);
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

    public LiveData<String> getText() {
        return mText;
    }



//    public ArrayList<FoodBankRepository> getFoodbankList(String input){
//
//        return null;
//    }


//    public ArrayList<FoodBankInfoRepository> getFoodbankInfo(String input){
//
//        //get foodbank list from database
//        ArrayList foodbankInfo = getFoodbankList(input);
//
//        //convert to foodbankinfo list and return
//
//        // fill here
//
//
//        //test foodbankinfo list
//        ArrayList fbinfo = new ArrayList<>();
//        FoodBankInfoRepository fb1 = new FoodBankInfoRepository("211 Moore St","open","1");
//        FoodBankInfoRepository fb2 = new FoodBankInfoRepository("241 Bunda St","close","2");
//        fbinfo.add(fb1);
//        fbinfo.add(fb2);
//        return fbinfo;
//        //test
//
//
//
////        return null;
//    }

    /*
    //can be used to frament or activity
    public class FoodBankActivity extends AppCompatActivity {
    private FoodBankViewModel foodBankViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_bank);

        foodBankViewModel = new ViewModelProvider(this).get(FoodBankViewModel.class);
        foodBankViewModel.getFoodBanksLiveData().observe(this, new Observer<ArrayList<FoodBank>>() {
            @Override
            public void onChanged(ArrayList<FoodBank> foodBanks) {
                // refresh listview
            }
        });
    }
}
     */

}


