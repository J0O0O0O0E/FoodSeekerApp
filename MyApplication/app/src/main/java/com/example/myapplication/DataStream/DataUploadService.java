package com.example.myapplication.DataStream;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a simulator that is used to simulate automatically
 * registering a new user every thirty seconds. The new user's account and password
 * information will be uploaded to firebase.
 *
 * @author Haoxuan Xu, u7747847
 */

public class DataUploadService {

    private static final String TAG = "DataUploadService";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Handler handler;
    private Runnable runnable;
    private Context context;

    public DataUploadService(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        handler = new Handler();

        // Start uploading data
        startPeriodicDataUpload();
    }

    private void startPeriodicDataUpload() {
        runnable = new Runnable() {
            @Override
            public void run() {
                readDataFromStreamAndUpload();
                handler.postDelayed(this, 30000); // A timer
            }
        };
        handler.post(runnable); // Start the initial action
    }

    private void readDataFromStreamAndUpload() {
        List<String[]> data = readDataFromCSV();

        // Upload data to Firestore
        if (data != null && !data.isEmpty()) {
            for (String[] row : data) {
                if (row.length >= 2) {
                    String email = row[0];
                    String password = row[1];
                    createUserAndUploadData(email, password);
                }
            }
        }
    }

    //Read CSV
    private List<String[]> readDataFromCSV() {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("datastream.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                data.add(columns);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading CSV file", e);
        }

        return data;
    }

    private void createUserAndUploadData(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            uploadDataToFirestore(user);
                        }
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                });
    }

    private void uploadDataToFirestore(FirebaseUser user) {
        String userId = user.getUid();
        String email = user.getEmail();
        db.collection("User").document(userId).set(new DataModel(email, ""))
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Data successfully uploaded"))
                .addOnFailureListener(e -> Log.w(TAG, "Error uploading data", e));
    }

    public void stopPeriodicDataUpload() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable); // Stop the task
        }
    }

    private void updateUI(FirebaseUser user) {
        // Update UI
    }
}

class DataModel {
    private String email;
    private String password;

    public DataModel() {}

    public DataModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
