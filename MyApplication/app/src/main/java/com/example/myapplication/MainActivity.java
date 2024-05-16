package com.example.myapplication;

import static com.google.android.material.internal.ContextUtils.getActivity;
import static java.security.AccessController.getContext;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Notification;
import com.example.myapplication.repository.FoodBankRepository;
import com.example.myapplication.repository.UserRepository;
import com.example.myapplication.ui.foodbank.FoodbankViewModel;
import com.example.myapplication.ui.notifications.NotificationsViewModel;
import com.example.myapplication.ui.profile.ProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NotificationsViewModel notificationsViewModel;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_foodbank, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Set up a listener for navigation changes to show/hide the bottom navigation bar (for announcement)
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_announcement) {
                // Hide the bottom navigation bar when in the announcement fragment
                navView.setVisibility(View.GONE);
            } else {
                // Show the bottom navigation bar for all other destinations
                navView.setVisibility(View.VISIBLE);
            }
        });

        scheduleCheck();
    }

    private void scheduleCheck() {
        Runnable checkNotifications = new Runnable() {
            public void run() {
                try {
                    LocalDateTime currentTime = LocalDateTime.now();
                    Log.d("NotificationCheck", "Checking notifications at " + currentTime);
                    List<FoodBank> foodBanks = UserRepository.getInstance().getSubscribedFoodBanks();
                    if (!foodBanks.isEmpty()) {
                        List<Notification> notifications = new ArrayList<>();
                        for (FoodBank foodBank : foodBanks) {
                            if (foodBank.getBusinessHours().ifNotifyNeeded(currentTime)) {
                                notifications.add(new Notification(foodBank, currentTime));
                            }
                        }
                        notificationsViewModel.updateNotifications(notifications);
                    } else {
                        Log.d("NotificationCheck", "Empty foodBank list");
                    }
                } catch (Exception e) {
                    Log.e("NotificationCheck", "Error during checkNotifications", e);
                }
            }
        };
        scheduler.scheduleAtFixedRate(checkNotifications, 0, 60, TimeUnit.SECONDS);
    }
    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigateUp();
        // nav_host_... is the main activity fragments controller
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearData(); // Clear data before the activity is destroyed
        binding = null; // Clear binding to avoid memory leaks
    }

    private void clearData() {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.clearData();
    }
}