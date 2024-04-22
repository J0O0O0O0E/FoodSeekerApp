
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
import com.example.myapplication.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

    //check if the user is signed in
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            startActivity( new Intent(LoginActivity.this, MainActivity.class));
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Initialize the buttons from the layout resource
        btnLogin = findViewById(R.id.login_btn);
        btnSignup = findViewById(R.id.signup_btn);
        username = (EditText) findViewById(R.id.login_name);
        pw =findViewById(R.id.login_passw);

//        // Set up a click listener for the login button
//        btnLogin.setOnClickListener(v -> {
//            // Here, we assume the login logic is handled, and the user has authenticated successfully
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish(); // Finish LoginActivity so the user cannot navigate back to it
//        });

        // Set up a click listener for the Signup button
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });

        btnLogin.setOnClickListener(this);


    }

    public void onClick(View view) {
        String email = username.getText().toString().trim();
        String password = pw.getText().toString().trim();

        //https://learn.microsoft.com/en-us/dotnet/api/android.views.view.requestfocus?view=net-android-34.0
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            username.setError("invalid email address");
            username.requestFocus();
            return;
        }
        // password length required by firebase
        if (password.length() < 6) {
            pw.setError("Length of password should be at least 6");
            pw.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserRepository.getInstance().setUser(user);
//
                        UserRepository.getInstance().loadUser();

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

}

