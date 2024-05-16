package com.example.myapplication.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity class to handle the addition of new announcements to Firestore.
 * Users can input a title and details for the announcement, choose an image from their device,
 * and then upload all data to Firebase Firestore and Firebase Storage.
 *
 * This class provides UI for selecting an image, entering text, and uploading data. It interacts
 * with Firebase to store data and handle files.
 * <p>
 * @author Zhi LI, u7640966
 * <p>
 * Bibliography:
 * - <a href="https://firebase.google.com/docs/firestore/manage-data/add-data">...</a>
 * - <a href="https://firebase.google.com/docs/storage/android/upload-files#java">...</a>
 * - <a href="https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/">...</a>
 */
public class AddAnnouncementActivity extends AppCompatActivity {
    private Uri imageUri;
    private EditText titleEditText, detailsEditText;
    private ImageView imageView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Create a storage reference from our app
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);
        titleEditText = findViewById(R.id.editTitle);
        detailsEditText = findViewById(R.id.editDetails);
        imageView = findViewById(R.id.imagePreview);
        Button uploadButton = findViewById(R.id.btnUploadAnnouncement);
        Button chooseImageButton = findViewById(R.id.btnChooseImage);

        chooseImageButton.setOnClickListener(v -> openFileChooser());
        uploadButton.setOnClickListener(v -> uploadData());
    }

    /**
     * Opens the file chooser to select an image from the device's storage.
     * Only images can be selected.
     */
    //https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/
    private void openFileChooser() {
        // create an instance of the intent of the type image
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the url of the image from data
            imageUri = data.getData();
            // update the preview image in the layout
            imageView.setImageURI(imageUri);
        }
    }


    /**
     * Uploads the announcement data to Firebase. If an image is selected, it uploads the image first
     * and then the announcement data with the image URL. If no image is selected, it uploads
     * the data without an image URL.
     */
    //https://firebase.google.com/docs/storage/android/upload-files#java
    private void uploadData() {
        if (imageUri != null) {
            StorageReference fileRef = storageReference.child("images/Announcement" + System.currentTimeMillis() + ".jpg");
            fileRef.putFile(imageUri)//use to upload and manage and monitor the status of the upload.(Firebase storage)
                    .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        saveAnnouncement(imageUrl);// firestore
                    }))
                    .addOnFailureListener(e -> Toast.makeText(AddAnnouncementActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show());
        } else {
            saveAnnouncement(""); // Save without an image to firestore
        }
    }


    /**
     * Saves the announcement data to Firebase Firestore with the given image URL.
     * This method gathers all user input and builds a map to represent the announcement data.
     *
     * @param imageUrl URL of the uploaded image or an empty string if no image was uploaded.
     */
    //https://firebase.google.com/docs/firestore/manage-data/add-data
    private void saveAnnouncement(String imageUrl) {
        String title = titleEditText.getText().toString();
        String details = detailsEditText.getText().toString();
        Map<String, Object> announcement = new HashMap<>();
        announcement.put("title", title);
        announcement.put("detail", details);
        announcement.put("imageUrl", imageUrl);
        announcement.put("date", new java.util.Date()); // (timestamp)Current date and time

        db.collection("Announcement")
                .add(announcement)
                .addOnSuccessListener(documentReference -> Toast.makeText(AddAnnouncementActivity.this, "Announcement added", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(AddAnnouncementActivity.this, "Failed to add announcement", Toast.LENGTH_SHORT).show());
    }
}
