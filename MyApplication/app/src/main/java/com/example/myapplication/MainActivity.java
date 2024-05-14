package com.example.myapplication;

import static com.google.android.material.internal.ContextUtils.getActivity;
import static java.security.AccessController.getContext;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_foodbank, R.id.navigation_search, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Set up a listener for navigation changes to show/hide the bottom navigation bar (for announcement )
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_announcement) {
                // Hide the bottom navigation bar when in the announcement fragment
                navView.setVisibility(View.GONE);
                //check if user try enter foodbank fragment
            }
            else if(destination.getId() == R.id.navigation_foodbank){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, getResources().getInteger(R.integer.LOCATION_PERMISSION_REQUEST_CODE));
                    controller.navigate(R.id.navigation_home);
                }else{
                    navView.setVisibility(View.VISIBLE);
                }


            }
            else{
                // Show the bottom navigation bar for all other destinations
                navView.setVisibility(View.VISIBLE);
            }
        });
    }

    // method that handle the navigation when the user presses the back button in the ActionBar in announcement fragment
    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigateUp();
        // nav_host_... is the main activity fragments controller
    }


    //Ask for location permission when try to visit foodbank_fragment
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("NavigationActivity", "Location permission granted");
        } else {
            Log.d("NavigationActivity", "Location permission denied");
            View contextView = findViewById(android.R.id.content);
            Snackbar.make(contextView, "Location permission is required to access the foodbank.", Snackbar.LENGTH_LONG).show(); //
        }
    }
}