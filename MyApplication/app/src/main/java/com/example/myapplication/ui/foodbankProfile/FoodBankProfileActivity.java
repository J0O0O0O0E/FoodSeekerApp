package com.example.myapplication.ui.foodbankProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.utils.FoodBankBundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.myapplication.model.User;
import com.example.myapplication.repository.UserRepository;

/**
 * Activity to display detailed information about a FoodBank.
 * This activity includes a bar chart showing food quantities, a WebView for displaying the location on Google Maps,
 * and buttons for sharing and subscribing to the FoodBank.
 * @author Si Chen u7756543
 */
public class FoodBankProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private BarChart barChart;  // Bar chart to display food quantities
    private static String shareMessage = "";  // Message to be shared
    private static int foodBankId;  // ID of the FoodBank

    private User user = UserRepository.getInstance().getUser();  // Current user

    /**
     * Called when the activity is first created.
     * Initializes the UI components and loads the FoodBank data.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState}. <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_bank_profile);

        // Initialize chart
        barChart = findViewById(R.id.barChart);

        // Load FoodBank data from bundle and display
        Bundle bundle = getIntent().getExtras();
        foodBankId = bundle.getInt(FoodBankBundle.KEY_FOODBANKBUNDLE_FOOD_BANK_ID);

        if (bundle != null) {
            ((TextView) findViewById(R.id.food_bank_name)).setText(bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_NAME));
            ((TextView) findViewById(R.id.food_bank_phone)).setText("Number: " + bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_NUMBER));
            ((TextView) findViewById(R.id.food_bank_email)).setText("Email : " + bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_EMAIL));
            ((TextView) findViewById(R.id.food_bank_state)).setText("State: " + bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_STATE));
            ((TextView) findViewById(R.id.food_bank_open_hours)).setText("Open hours: " + bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_OPEN_HOURS));
            ((TextView) findViewById(R.id.food_bank_capacity)).setText("Capacity: " + String.valueOf(bundle.getInt(FoodBankBundle.KEY_FOODBANKBUNDLE_CAPACITY)));
            ((TextView) findViewById(R.id.food_bank_distance)).setText("Distance: " + String.valueOf(bundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_DISTANCE)));
            ((TextView) findViewById(R.id.food_bank_foundation_date)).setText("Foundation Date: " + bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_FOUND_DATE));
            ((TextView) findViewById(R.id.food_bank_postcode)).setText("Post code: " + bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_POSTCODE));
            ((TextView) findViewById(R.id.food_bank_rate)).setText("Rate: " + String.valueOf(bundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_RATE)));

            String location = "Location: " + String.format("%s , %s , %s ",
                    bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_COUNTRY),
                    bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_CITY),
                    bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_STREET));
            ((TextView) findViewById(R.id.food_bank_location)).setText(location);
            shareMessage = bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_NAME) + "\n" + bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_STREET) + "\n" + bundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_NUMBER);

            // Create the chart
            setupBarChart(bundle);

            // Initialize the navigation bar
            ImageView iv_back = findViewById(R.id.iv_back);
            ImageView iv_share = findViewById(R.id.iv_share);
            ImageView iv_subscribe = findViewById(R.id.iv_subscribe);
            iv_back.setOnClickListener(this);
            iv_share.setOnClickListener(this);
            iv_subscribe.setOnClickListener(this);
            if (!user.getSubscribedFoodBanks().contains(Integer.toString(foodBankId))) {
                iv_subscribe.setImageResource(R.drawable.baseline_bookmark_add_24);
            } else {
                iv_subscribe.setImageResource(R.drawable.baseline_bookmark_added_24);
            }

            initializeWebView(bundle);
        }
    }

    /**
     * Initializes the WebView to display the FoodBank location on Google Maps.
     *
     * @param bundle The Bundle containing the latitude and longitude of the FoodBank.
     */
    private void initializeWebView(Bundle bundle) {
        WebView wv_map = findViewById(R.id.wv_map);
        WebSettings webSettings = wv_map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv_map.setWebViewClient(new WebViewClient());

        double latitude = bundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_LATITUDE);
        double longitude = bundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_LONGITUDE);
        String mapUrl = "https://www.google.com/maps?q=" + latitude + "," + longitude;

        wv_map.post(() -> wv_map.loadUrl(mapUrl));
    }

    /**
     * Handles click events for the back, share, and subscribe buttons.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.iv_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(intent, "Share via"));
        } else if (v.getId() == R.id.iv_subscribe) {
            ImageView iv = findViewById(R.id.iv_subscribe);
            if (!user.getSubscribedFoodBanks().contains(Integer.toString(foodBankId))) {
                UserRepository.getInstance().addSubscribedFoodBanks(Integer.toString(foodBankId));
                iv.setImageResource(R.drawable.baseline_bookmark_added_24);
            } else {
                UserRepository.getInstance().removeSubscribedFoodBanks(Integer.toString(foodBankId));
                iv.setImageResource(R.drawable.baseline_bookmark_add_24);
            }
        }
    }

    /**
     * Sets up the bar chart with food quantities data.
     *
     * @param bundle The Bundle containing the food quantities data.
     */
    private void setupBarChart(Bundle bundle) {
        // Create a list to hold the bar entries (x-axis value and y-axis value)
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, bundle.getInt("Pasta")));
        entries.add(new BarEntry(2, bundle.getInt("Bread")));
        entries.add(new BarEntry(3, bundle.getInt("Milk")));
        entries.add(new BarEntry(4, bundle.getInt("Pie")));
        entries.add(new BarEntry(5, bundle.getInt("Vegetable")));

        BarDataSet dataSet = new BarDataSet(entries, "Food Quantities");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setValueTextSize(12f);
        String[] labels = new String[]{"Pasta", "Bread", "Milk", "Pie", "Vegetable"};

        dataSet.setLabel(Arrays.toString(labels));

        BarData data = new BarData(dataSet);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        // Refresh and display the data
        barChart.invalidate();
    }
}
