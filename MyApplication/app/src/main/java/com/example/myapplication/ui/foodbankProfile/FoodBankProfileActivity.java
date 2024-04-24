package com.example.myapplication.ui.foodbankProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;

public class FoodBankProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static int BOOKMARK_INDEX = 0;
    private static String shareMessage = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_bank_profile);

        //get data in bundle
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            // set data to textview
            ((TextView) findViewById(R.id.food_bank_name)).setText(bundle.getString("fb_name"));
            ((TextView) findViewById(R.id.food_bank_phone)).setText("Number: " + bundle.getString("fb_number"));
            ((TextView) findViewById(R.id.food_bank_email)).setText("Email : "+bundle.getString("fb_email"));
            ((TextView) findViewById(R.id.food_bank_state)).setText("Status: "+bundle.getString("fb_sate"));
            ((TextView) findViewById(R.id.food_bank_open_hours)).setText("Open hours: "+bundle.getString("fb_openHours"));
            ((TextView) findViewById(R.id.food_bank_capacity)).setText("Capacity: "+String.valueOf(bundle.getInt("fb_capacity")));
            ((TextView) findViewById(R.id.food_bank_distance)).setText("Distance: "+String.valueOf(bundle.getDouble("fb_distance")));
            ((TextView) findViewById(R.id.food_bank_foundation_date)).setText("Fundation Date: " + bundle.getString("fb_foundDate"));

            ImageView iv_back = findViewById(R.id.iv_back);
            ImageView iv_bookmark = findViewById(R.id.iv_bookmark);
            ImageView iv_share = findViewById(R.id.iv_share);

            iv_back.setOnClickListener(this);
            iv_bookmark.setOnClickListener(this);
            iv_share.setOnClickListener(this);

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
        } else if (v.getId()==R.id.iv_bookmark) {
            if(BOOKMARK_INDEX == 0){
                BOOKMARK_INDEX = 1;
                ImageView iv = findViewById(R.id.iv_bookmark);
                iv.setImageResource(R.drawable.baseline_bookmark_added_24);
            }else{
                BOOKMARK_INDEX = 0;
                ImageView iv = findViewById(R.id.iv_bookmark);
                iv.setImageResource(R.drawable.baseline_bookmark_add_24);
            }
        } else if (v.getId()==R.id.iv_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(intent, "Share via"));
        }
    }
}