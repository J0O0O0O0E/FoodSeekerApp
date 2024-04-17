
package com.example.myapplication.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

/**
 * LoginActivity is the activity responsible for user authentication.
 * This activity provides a user interface where users can login to the app.
 */
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        // Initialize the buttons from the layout resource
        Button btnLogin = findViewById(R.id.login_btn);
        Button btnSignup = findViewById(R.id.signup_btn);

        // Set up a click listener for the login button
        btnLogin.setOnClickListener(v -> {
            // Here, we assume the login logic is handled, and the user has authenticated successfully
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finish LoginActivity so the user cannot navigate back to it
        });

        // Set up a click listener for the Signup button
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });


    }

}

