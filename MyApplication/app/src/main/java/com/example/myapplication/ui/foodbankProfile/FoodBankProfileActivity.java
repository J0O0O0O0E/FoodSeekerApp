package com.example.myapplication.ui.foodbankProfile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;

public class FoodBankProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_bank_profile);

        //get foodbankinfo name
        Bundle bundle = getIntent().getExtras();
        String  foodBankName = bundle.getString("foodBankName");
        //search by name
        FoodBank foodBank = FoodBankProfileViemModel.searchFbName(foodBankName);


        //{test code}
        TextView tv = findViewById(R.id.tv_foodbank_name);
        tv.setText(foodBankName);

        //TODO
        if (foodBank != null) {
            // 获取 XML 布局中的视图
            TextView tvFoodbankName = findViewById(R.id.tv_foodbank_name);
            TextView tvFoodbankAddress = findViewById(R.id.tv_foodbank_address);
            TextView tvFoodbankPhone = findViewById(R.id.tv_foodbank_phone);
            TextView tvFoodbankEmail = findViewById(R.id.tv_foodbank_email);
            TextView tvFoodbankStatus = findViewById(R.id.tv_foodbank_status);
            TextView tvFoodbankOpenHours = findViewById(R.id.tv_foodbank_open_hours);
            TextView tvFoodbankCapacity = findViewById(R.id.tv_foodbank_capacity);
            TextView tvFoodbankDistance = findViewById(R.id.tv_foodbank_distance);
            TextView tvFoodbankFoundationDate = findViewById(R.id.tv_foodbank_foundation_date);

            // 设置视图的内容
            tvFoodbankName.setText(foodBank.getName());
            tvFoodbankAddress.setText(
                    "Address: " + foodBank.getStreet() + ", " + foodBank.getSuburb() + ", " + foodBank.getRegion() + ", " + foodBank.getPostcode() + ", " + foodBank.getCountry()
            );
            tvFoodbankPhone.setText("Phone: " + foodBank.getTel());
            tvFoodbankEmail.setText("Email: " + foodBank.getEmail());
            tvFoodbankStatus.setText("Status: " + (foodBank.isStatus() ? "Open" : "Closed"));
            tvFoodbankOpenHours.setText("Open Hours: " + foodBank.getOpen_hours());
            tvFoodbankCapacity.setText("Capacity: " + foodBank.getCapacity() + " people");
            tvFoodbankDistance.setText("Distance: " + foodBank.getDistanceToUser() + " meters");
            tvFoodbankFoundationDate.setText("Founded: " + foodBank.getDoe());
        }










    }
}