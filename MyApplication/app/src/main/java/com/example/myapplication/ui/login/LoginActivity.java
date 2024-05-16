
package com.example.myapplication.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.repository.FoodBankRepository;
import com.example.myapplication.repository.UserRepository;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


/**
 * LoginActivity is the activity responsible for user authentication.
 * This activity provides a user interface where users can login to the app.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private Button btnLogin;
    private Button btnSignup;
    private EditText username,pw;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, getResources().getInteger(R.integer.LOCATION_PERMISSION_REQUEST_CODE));
        }


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Initialize the buttons from the layout resource
        btnLogin = findViewById(R.id.login_btn);
        btnSignup = findViewById(R.id.signup_btn);
        username = (EditText) findViewById(R.id.login_name);
        pw =findViewById(R.id.login_passw);


        // Set up a click listener for the Signup button
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });

        btnLogin.setOnClickListener(this);



    }
    public boolean isValidEmail(String email){
        return !((email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()));
    }

    public boolean isLengthLessThan6(String psw){
        return(psw.length() < 6);
    }


    public boolean containUpper(String psw){
        boolean flag = false;
        for(int i = 0;i<psw.length();i++){
            if(!Character.isUpperCase(psw.charAt(i))){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void onClick(View view) {
        String email = username.getText().toString().trim();
        String password = pw.getText().toString().trim();

        //https://learn.microsoft.com/en-us/dotnet/api/android.views.view.requestfocus?view=net-android-34.0
        if (!isValidEmail(email)) {
            username.setError("Invalid email address");
            username.requestFocus();
            return;
        }
        // password length required by firebase
        if (isLengthLessThan6(password)) {
            pw.setError("Length of password should be at least 6");
            pw.requestFocus();
            return;
        }
        // password must contain capital letters
//        if (!containUpper(password)){
//            pw.setError("Password must contain capital letters!");
//            pw.requestFocus();
//            return;
//        }


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserRepository.getInstance().setUser(user);
//
                        UserRepository.getInstance().loadUser();

                        FoodBankRepository.getInstance();

                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == getResources().getInteger(R.integer.LOCATION_PERMISSION_REQUEST_CODE) && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else if(requestCode == getResources().getInteger(R.integer.LOCATION_PERMISSION_REQUEST_CODE) && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
            Log.d("NavigationActivity", "Location permission denied");
            View contextView = findViewById(android.R.id.content);
            Snackbar.make(contextView, "Location permission is required to access the foodbank.", Snackbar.LENGTH_LONG).show();


            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 3000);
        }
    }

}

