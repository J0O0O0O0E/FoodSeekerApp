package com.example.myapplication.ui.foodbankProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.myapplication.model.User;
import com.example.myapplication.repository.UserRepository;

public class FoodBankProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private BarChart barChart;

    private static int BOOKMARK_INDEX = 0;
    private static String shareMessage = "";
    private static int foodBankId;

    private User user = UserRepository.getInstance().getUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_bank_profile);

        //initialize chart
        barChart = findViewById(R.id.barChart);

        //get data in bundle
        Bundle bundle = getIntent().getExtras();
        foodBankId = bundle.getInt("fb_foodBankId");


        if (bundle != null) {
            // set data to textview
            ((TextView) findViewById(R.id.food_bank_name)).setText(bundle.getString("fb_name"));
            ((TextView) findViewById(R.id.food_bank_phone)).setText("Number: " + bundle.getString("fb_number"));
            ((TextView) findViewById(R.id.food_bank_email)).setText("Email : "+bundle.getString("fb_email"));
            ((TextView) findViewById(R.id.food_bank_state)).setText("State: "+bundle.getString("fb_sate"));
            ((TextView) findViewById(R.id.food_bank_open_hours)).setText("Open hours: "+bundle.getString("fb_openHours"));
            ((TextView) findViewById(R.id.food_bank_capacity)).setText("Capacity: "+String.valueOf(bundle.getInt("fb_capacity")));
            ((TextView) findViewById(R.id.food_bank_distance)).setText("Distance: "+String.valueOf(bundle.getDouble("fb_distance")));
            ((TextView) findViewById(R.id.food_bank_foundation_date)).setText("Fundation Date: " + bundle.getString("fb_foundDate"));
            ((TextView) findViewById(R.id.food_bank_postcode)).setText("Post code: " + bundle.getString( "fb_postCode"));

            //creat the chart
            setupBarChart(bundle);


            ImageView iv_back = findViewById(R.id.iv_back);
            ImageView iv_share = findViewById(R.id.iv_share);
            ImageView iv_subscribe = findViewById(R.id.iv_subscribe);
            TextView tvZ_test = findViewById(R.id.tv_test);


//            {test for gps}
            WebView wv_map = findViewById(R.id.wv_map);
            WebSettings webSettings = wv_map.getSettings();
            webSettings.setJavaScriptEnabled(true); // Active JavaScript
            wv_map.setWebViewClient(new WebViewClient()); // Prevent external browsers from opening links
            double latitude = bundle.getDouble("fb_latitude");
            double longitude = bundle.getDouble("fb_longitude");
            String mapUrl = "https://www.google.com/maps?q=" + latitude + "," + longitude;
            wv_map.loadUrl(mapUrl);


            iv_back.setOnClickListener(this);
            iv_share.setOnClickListener(this);
            iv_subscribe.setOnClickListener(this);



            // city street country
            String location = "Location: " + String.format("%s , %s , %s ",
                    bundle.getString("fb_country"),
                    bundle.getString("fb_city"),
                    bundle.getString("fb_street")
            );

            // 将拼接后的地址设置到TextView
            ((TextView) findViewById(R.id.food_bank_location)).setText(location);

            //{}
            shareMessage = bundle.getString("fb_name")+" "+bundle.getString("fb_street")+ "\n"+bundle.getString("fb_number");
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back){
            finish();
        } else if (v.getId()==R.id.iv_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(intent, "Share via"));
        } else if (v.getId() == R.id.iv_subscribe) {
            if (!user.subscribedFoodBanks.contains(Integer.toString(foodBankId))) {
                UserRepository.getInstance().addSubscribedFoodBanks(Integer.toString(foodBankId));
            } else {
                UserRepository.getInstance().removeSubscribedFoodBanks(Integer.toString(foodBankId));
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
        //refresh and display the data
        barChart.invalidate();
    }
}