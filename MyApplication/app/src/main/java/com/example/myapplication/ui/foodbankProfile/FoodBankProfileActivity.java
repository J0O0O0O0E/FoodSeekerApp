package com.example.myapplication.ui.foodbankProfile;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_bank_profile);

        //get data in bundle
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            // 设置其他数据到TextView
            ((TextView) findViewById(R.id.food_bank_name)).setText(bundle.getString("fb_name"));
            ((TextView) findViewById(R.id.food_bank_phone)).setText(bundle.getString("fb_number"));
            ((TextView) findViewById(R.id.food_bank_email)).setText(bundle.getString("fb_email"));
            ((TextView) findViewById(R.id.food_bank_state)).setText(bundle.getString("fb_sate"));
            ((TextView) findViewById(R.id.food_bank_open_hours)).setText(bundle.getString("fb_openHours"));
            ((TextView) findViewById(R.id.food_bank_capacity)).setText(String.valueOf(bundle.getInt("fb_capacity")));
            ((TextView) findViewById(R.id.food_bank_distance)).setText(String.valueOf(bundle.getDouble("fb_distance")));
            ((TextView) findViewById(R.id.food_bank_foundation_date)).setText(bundle.getString("fb_foundDate"));

            ImageView iv_back = findViewById(R.id.iv_back);
            iv_back.setOnClickListener(this);
            // city street country
            String location = String.format("%s , %s , %s ",
                    bundle.getString("fb_country"),
                    bundle.getString("fb_city"),
                    bundle.getString("fb_street")
                    );
            // 将拼接后的地址设置到TextView
            ((TextView) findViewById(R.id.food_bank_location)).setText(location);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back){
            finish();
        }
    }
}