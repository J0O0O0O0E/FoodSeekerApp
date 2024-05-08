package com.example.myapplication;

import android.content.Intent;

import com.example.myapplication.ui.profile.ProfileFragment;

public class ImagePickerUtils {

    public static void openImagePicker(ProfileFragment activity, int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
    }
}
