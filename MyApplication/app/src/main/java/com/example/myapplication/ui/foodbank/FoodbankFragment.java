package com.example.myapplication.ui.foodbank;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.adapter.FoodbankAdaptor;
import com.example.myapplication.databinding.FragmentFoodbankBinding;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.FoodBankInfo;
import com.example.myapplication.ui.foodbankProfile.FoodBankProfileActivity;
import com.example.myapplication.utils.FoodBankConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FoodbankFragment extends Fragment {


    private FragmentFoodbankBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_foodbank, container, false);

        //new foodbank view module
        FoodbankViewModel foodbankViewModel = new ViewModelProvider(this).get(FoodbankViewModel.class);


        //get location permission
        //ask for location permission
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, getContext().getResources().getInteger(R.integer.LOCATION_PERMISSION_REQUEST_CODE));
        }

        //{for test}
        TextView tv_gps = root.findViewById(R.id.tv_gps);

        //read the location
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        if (location == null) {
            tv_gps.setText("location is empty");

        } else {
            tv_gps.setText(Double.toString(location.getLatitude()) + "\n" + Double.toString(location.getLongitude()));
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        com.example.myapplication.model.Location userLocation = new com.example.myapplication.model.Location(latitude,longitude);
        //gps has been get
        //foodbankViewModel.setUserLocationAndUpdateDistances(latitude,longitude);


        //initial all views in layout
        EditText ed_input = root.findViewById(R.id.et_input);
        Button btn_search = root.findViewById(R.id.btn_search);
        ListView lv_foodbank = root.findViewById(R.id.lv_foodbank);


        //get list of nearby foodbank
        ArrayList<FoodBank> fbList = new ArrayList<>();
        foodbankViewModel.getFoodBanksLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<FoodBank>>() {
            @Override
            public void onChanged(ArrayList<FoodBank> foodBanks) {
                fbList.clear();
                fbList.addAll(foodBanks);
                //
                //
                if (fbList != null) {
                    for (FoodBank foodBank : fbList) {
                        if (foodBank.getLocation() != null) {
                            double distance = Math.round(foodBank.getLocation().calculateDistance(userLocation));
                            Log.d("UpdateDistances", "ID:" + foodBank.getId() + " distance:" + distance);
                            foodBank.setDistanceToUser(distance);
                        } else {
                            Log.d("UpdateDistances", "Location is null for FoodBank ID:" + foodBank.getId());
                        }

                    }

                    // Sort the list based on distance to user
                    Collections.sort(fbList, new Comparator<FoodBank>() {
                        @Override
                        public int compare(FoodBank fb1, FoodBank fb2) {
                            return Double.compare(fb1.getDistanceToUser(), fb2.getDistanceToUser());
                        }
                    });}










                List<FoodBankInfo> fbInfoList = FoodBankConverter.convert(fbList);
                FoodbankAdaptor fbAdapter = new FoodbankAdaptor(getContext(), fbInfoList);
                lv_foodbank.setAdapter(fbAdapter);
                // refresh list view
            }
        });





//        List<FoodBankInfo> fbInfoList = FoodBankConverter.convert(FoodBankConverter.testFoodBank1());
        //List<FoodBankInfo> fbInfoList = FoodBankConverter.convert(fbList);


        //set data to listview
        //FoodbankAdaptor fbAdapter = new FoodbankAdaptor(getContext(), fbInfoList);
        //lv_foodbank.setAdapter(fbAdapter);


        //search button click
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get location

                //use input to search foodbank
                List<FoodBankInfo> listNew = FoodbankViewModel.searchFb(ed_input.getText().toString());
                FoodbankAdaptor newAdapter = new FoodbankAdaptor(getContext(), listNew);
                lv_foodbank.setAdapter(newAdapter);
            }
        });

        //list view click
        lv_foodbank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodBankInfo clickedFoodBankInfo = (FoodBankInfo) parent.getItemAtPosition(position);
                Intent detailIntent = new Intent(getActivity(), FoodBankProfileActivity.class);

                //send foodbankinfo name
                Bundle bundle = new Bundle();
                bundle.putString("foodBankName", clickedFoodBankInfo.name);
                detailIntent.putExtras(bundle);
                startActivity(detailIntent);
            }
        });


        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}