package com.example.myapplication.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.repository.FoodBankRepository;
import com.example.myapplication.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * SignupActivity is responsible for user registration.
 * It allows users to create a new account by providing an email and password.
 * @author zhi li, u7640966
 * @author penny
 *<a href="https://learn.microsoft.com/en-us/dotnet/api/android.views.view.requestfocus?view=net-android-34.0">...</a>
 *<a href="https://stackoverflow.com/questions/12947620/email-address-validation-in-android-on-edittext">...</a>
 */
public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";

    private FirebaseAuth mAuth;
    private Button btnSignup;
    private EditText username, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        btnSignup = findViewById(R.id.secsignup_btn);
        username = findViewById(R.id.signup_name);
        pw = findViewById(R.id.signup_passw);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Set up a click listener for the Signup button
        btnSignup.setOnClickListener(this);

        // Set up a click listener to navigate back to the login screen
        Button btnBackToLogin = findViewById(R.id.back_to_log_in_btn);
        btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Validates if the provided email is valid.
     *
     * @param email The email to be validated.
     * @return True if the email is valid, false otherwise.
     */
    public boolean isValidEmail(String email) {
        return !(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    /**
     * Checks if the length of the provided password is less than 6 characters.
     *
     * @param psw The password to be checked.
     * @return True if the password length is less than 6, false otherwise.
     */
    public boolean isLengthLessThan6(String psw) {
        return psw.length() < 6;
    }

    /**
     * Checks if the provided string contains at least one uppercase letter.
     *
     * @param str The string to be checked.
     * @return True if the string contains an uppercase letter, false otherwise.
     */
    public boolean containUpper(String str) {
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Handles click events for the Signup button.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        String email = username.getText().toString().trim();
        String password = pw.getText().toString().trim();
        UserRepository userRepository = UserRepository.getInstance();

        // Validate email
        if (!isValidEmail(email)) {
            username.setError("Invalid email address");
            username.requestFocus();
            return;
        }

        // Validate password length
        if (isLengthLessThan6(password)) {
            pw.setError("Length of password should be at least 6");
            pw.requestFocus();
            return;
        }

        // Validate password contains uppercase letter
        if (!containUpper(password)) {
            pw.setError("Password must contain capital letters!");
            pw.requestFocus();
            return;
        }

        // Sign up with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // User created successfully
                        Toast.makeText(SignupActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "createUserWithEmail:success");

                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            userRepository.createUserProfile(user);
                            userRepository.loadUser();
                            FoodBankRepository.getInstance();
                            updateUI(user);
                        } else {
                            updateUI(null);
                        }
                    }
                });
    }

    /**
     * Updates the UI based on the user's authentication status.
     *
     * @param user The authenticated FirebaseUser.
     */
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in, navigate to MainActivity
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is not signed in
            // Optionally clear input fields or do other interface updates
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
