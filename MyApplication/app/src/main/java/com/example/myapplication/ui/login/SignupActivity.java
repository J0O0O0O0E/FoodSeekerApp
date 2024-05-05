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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";

    private FirebaseAuth mAuth;
    private Button btnSignup;
    private EditText username,pw;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        btnSignup = findViewById(R.id.secsignup_btn);
        username = (EditText) findViewById(R.id.signup_name);
        pw =findViewById(R.id.signup_passw);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

// directly jump to main method when clicked
//        // Set up a click listener for the signup button
//        btnSignup.setOnClickListener(v -> {
//            // Here, we assume the signup logic is handled, and the user has authenticated successfully
//            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish(); // Finish signup Activity so the user cannot navigate back to it
//        });
        btnSignup.setOnClickListener(this);

    }
    public boolean isValidEmail(String email){
        return !((email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()));
    }

    public boolean isLengthLessThan6(String psw){
        return(psw.length() < 6);
    }
    public boolean containUpper(String str){
        boolean flag = false;
        for(int i = 0;i<str.length();i++){
            if(Character.isUpperCase(str.charAt(i))){
                flag = true;
                break;
            }
        }
        return flag;
    }
    //https://stackoverflow.com/questions/12947620/email-address-validation-in-android-on-edittext
    public void onClick(View view) {
        String email = username.getText().toString().trim();
        String password = pw.getText().toString().trim();
        UserRepository userRepository = UserRepository.getInstance();

        //https://learn.microsoft.com/en-us/dotnet/api/android.views.view.requestfocus?view=net-android-34.0
        if (!isValidEmail(email)){
            username.setError("invalid email address");
            username.requestFocus();
            return;
        }
        // password length required by firebase
        if (isLengthLessThan6(password)){
            pw.setError("Length of password should be at least 6");
            pw.requestFocus();
            return;
        }
        // password must contain capital letters
        if (!containUpper(password)){
            pw.setError("Password must contain capital letters!");
            pw.requestFocus();
            return;
        }
        // Sign up with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {

                                Toast.makeText(SignupActivity.this, "User Created",
                                        Toast.LENGTH_SHORT).show();

                                Log.d(TAG, "createUserWithEmail:success");


                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user != null) {
                                    userRepository.createUserProfile(user);
                                    updateUI(user);
                                }
                                else{
                                    updateUI(null);
                                }
                            }
                        }


//                        assert user != null;
//                        String emailAddress = user.getEmail();
//                        Map<String, Object> userMap = new HashMap<>();
//
//                        userMap.put("contactNumber","");
//                        userMap.put("email",emailAddress);
//                        userMap.put("subscribedFoodBanks",new ArrayList<>());
//                        userMap.put("userName","");
//
//                        UserRepository userRepository = new UserRepository();
//                        FirebaseFirestore fireStore = userRepository.fStore;


                    );

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in
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