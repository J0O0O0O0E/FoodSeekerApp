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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.adapter.FoodBankAdapterNew;
import com.example.myapplication.databinding.FragmentFoodbankBinding;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.parser.FoodBankParser;
import com.example.myapplication.tokenizer.MainTokenizer;
import com.example.myapplication.tokenizer.Token;
import com.example.myapplication.ui.foodbankProfile.FoodBankProfileActivity;
import com.example.myapplication.utils.DevelopFoodbank;
import com.example.myapplication.utils.LocationChecker;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A Fragment representing a list of Food Banks within the application.
 * It is responsible for displaying food banks sorted by distance to the user, which is obtained via GPS.
 * The Fragment handles permission requests for location access, retrieves and displays GPS coordinates,
 * initializes user interaction components like search and list view, and handles navigation to detailed
 * food bank profiles when an item in the list is tapped.
 *
 * @author Si Chen, Zijian Yang
 */
public class FoodbankFragment extends Fragment {
    private FragmentFoodbankBinding binding;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null.
     * This method is called between onCreate(Bundle) and onActivityCreated(Bundle).
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI, or null.
     * @author Si Chen, Zijian Yang
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_foodbank, container, false);
        // Obtain the ViewModel from the ViewModelProvider
        FoodbankViewModel foodbankViewModel = new ViewModelProvider(this).get(FoodbankViewModel.class);


        // Find the TextView for displaying GPS coordinates
//        TextView tv_gps = root.findViewById(R.id.tv_gps);
        // Get the system LocationManager to retrieve GPS location
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Consider calling ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // Initialize latitude and longitude
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        // Display the location coordinates if available, otherwise show error message
        if (location == null) {
//            tv_gps.setText("location is empty");
        } else {
            //tv_gps.setText(Double.toString(location.getLatitude()) + "\n" + Double.toString(location.getLongitude()));
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        // Create a new Location object for user based on retrieved coordinates
        com.example.myapplication.model.Location userLocation = new com.example.myapplication.model.Location(latitude, longitude);
        // Initialize input field and search button in the layout
        EditText ed_input = root.findViewById(R.id.et_input);
        Button btn_search = root.findViewById(R.id.btn_search);
        ListView lv_foodbank = root.findViewById(R.id.lv_foodbank);
        Spinner sp_states = root.findViewById(R.id.sp_states);

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(getContext(),R.layout.state_item_list, LocationChecker.getStateArray());
        sp_states.setAdapter(stateAdapter);
        sp_states.setSelection(0);

        // Initialize an empty list to hold FoodBank objects
        ArrayList<FoodBank> fbList = new ArrayList<>();
        final ArrayList<FoodBank> localFoodBankList = new ArrayList<>();
        //{test}
        final FoodBankAdapterNew foodBankAdapterNew3 = new FoodBankAdapterNew(getContext(),new ArrayList<FoodBank>());
        // Observe changes in FoodBank data from the ViewModel
        foodbankViewModel.getFoodBanksLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<FoodBank>>() {
            @Override
            public void onChanged(ArrayList<FoodBank> foodBanks) {
                fbList.clear();
                fbList.addAll(foodBanks);
                if (fbList != null) {
                    // Calculate and set distances from the user location for each FoodBank
                    for (FoodBank foodBank : fbList) {
                        if (foodBank.getLocation() != null) {
                            double distance = Math.round(foodBank.getLocation().calculateDistance(userLocation));
                            foodBank.setDistanceToUser(distance);
                        } else {
                            Log.d("UpdateDistances", "Location is null for FoodBank ID:" + foodBank.getId());
                        }
                    }
                    // Sort the list of FoodBanks by distance from the user
                    Collections.sort(fbList, new Comparator<FoodBank>() {
                        @Override
                        public int compare(FoodBank fb1, FoodBank fb2) {
                            return Double.compare(fb1.getDistanceToUser(), fb2.getDistanceToUser());
                        }
                    });
                }

                //{test}
//                DevelopFoodbank.foodbanks = fbList;
//                Log.d("test",DevelopFoodbank.searchId(204).getName());

                for (FoodBank foodBank : fbList) {
                    localFoodBankList.add(foodBank);
                }
                //add information to adapter
                FoodBankAdapterNew foodBankAdapterNew = new FoodBankAdapterNew(getContext(),localFoodBankList);
                lv_foodbank.setAdapter(foodBankAdapterNew);
                // refresh list view
            }
        });

        //search button click
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //read the input text and clear it
                String input = ed_input.getText().toString();
                //ed_input.setText("");

                //call method and get a foodbank list
                if(input==null||input.trim().isEmpty()){
                    Snackbar.make(getView(), "Empty input!", Snackbar.LENGTH_SHORT).show();
                }else{
                    MainTokenizer tokenizer = new MainTokenizer(input);
                    List<Token> tokens = tokenizer.getAllTokens();

                    //{test: print log}
                    for (Token token : tokens) {
                        Log.d("test",token.toString());
                    }
                    for (int i = 0; i < 20; i++) {
                        Log.d("test","test");
                    }

                    ArrayList<FoodBank> results = FoodBankParser.filterFoodBanks(tokens, localFoodBankList);
                    FoodBankAdapterNew foodBankAdapterNew = new FoodBankAdapterNew(getContext(),results);
                    lv_foodbank.setAdapter(foodBankAdapterNew);
                }


            }
        });

        //Handle state select
        sp_states.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //{test}
//                ArrayList<FoodBank> fbList2 = fbList;
//                fbList2 = LocationChecker.stateSelector(position,fbList2);
//                FoodBankAdapterNew foodBankAdapterNew1 = new FoodBankAdapterNew(getContext(),fbList2);
//                lv_foodbank.setAdapter(foodBankAdapterNew1);

                localFoodBankList.clear();
                for (FoodBank foodBank : LocationChecker.stateSelector(position, fbList)) {
                    localFoodBankList.add(foodBank);
                }
                FoodBankAdapterNew foodBankAdapterNew1 = new FoodBankAdapterNew(getContext(),localFoodBankList);
                lv_foodbank.setAdapter(foodBankAdapterNew1);


//                ArrayList<FoodBank> fbList2 = fbList;
//                fbList2 = LocationChecker.stateSelector(position,fbList2);
//                localFoodBankList.clear();
//
//
//                FoodBankAdapterNew foodBankAdapterNew1 = new FoodBankAdapterNew(getContext(),fbList2);
//                lv_foodbank.setAdapter(foodBankAdapterNew1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Handle item clicks in the ListView
        lv_foodbank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodBank clickedFoodBank = (FoodBank) parent.getItemAtPosition(position);
                Intent detailIntent = new Intent(getActivity(), FoodBankProfileActivity.class);
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
                bundle.putString("fb_postCode", clickedFoodBank.getPostcode());
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
        });
        return root;
    }

    /**
     * Called when the view previously created by onCreateView has been detached from the fragment.
     * The next time the fragment needs to be displayed, a new view will be created.
     * This is called after onStop() and before onDestroy(). It is called regardless of whether onCreateView returned a non-null view.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}