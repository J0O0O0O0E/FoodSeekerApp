package com.example.myapplication.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        Button btnSignup = findViewById(R.id.secsignup_btn);


        // Set up a click listener for the signup button
        btnSignup.setOnClickListener(v -> {
            // Here, we assume the signup logic is handled, and the user has authenticated successfully
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finish signup Activity so the user cannot navigate back to it
        });


    }
}