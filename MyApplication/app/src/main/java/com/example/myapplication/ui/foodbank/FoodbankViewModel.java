package com.example.myapplication.ui.foodbank;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.myapplication.model.FoodBank;
import com.example.myapplication.repository.FoodBankRepository;
import com.example.myapplication.model.Location;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.FoodBankInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

    //TODO input information ,search for some foodbank
    public static List<FoodBankInfo> searchFb(String input){
        List<FoodBankInfo> list = FoodbankViewModel.fakeSearch2();
        return list;
    }

    //TODO at the start, check the current location, get the nearby foodbank list
    //input two Double: longitude and latitude
    public static List<FoodBankInfo> getNearByFb(Double longitude,Double latitude){
        //read the location data and input
        List<FoodBankInfo> list = FoodbankViewModel.fakeSearch();
        return list;
    }

    //TODO convert foodbank list to foodbankinfo list
    public static List<FoodBankInfo> convertFb (List<FoodBank> fb){
        return null;
    }

    //{for test only} create a fake foodbankinfo list
    public static List<FoodBankInfo> fakeSearch(){
        List<FoodBankInfo> list = new ArrayList<>();
        list.add(new FoodBankInfo("Hospital","open","230 Dikson St","100m"));
        list.add(new FoodBankInfo("Market","open","113 H St","500m"));
        list.add(new FoodBankInfo("Park","close","25 Y St","1.1km"));
        return  list;
    }

    public static List<FoodBankInfo> fakeSearch2(){
        List<FoodBankInfo> list = new ArrayList<>();
        list.add(new FoodBankInfo("KFC","open","230 Dikson St","500m"));
        list.add(new FoodBankInfo("Coles","open","113 H St","1.8km"));
        list.add(new FoodBankInfo("Bank","close","25 Y St","1.9km"));
        list.add(new FoodBankInfo("Mc Download","close","25 Y St","1.9km"));
        return  list;
    }
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


