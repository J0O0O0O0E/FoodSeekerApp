package com.example.myapplication.ui.home.announcement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

/**
 * A Fragment subclass used to display details of an announcement.
 * This fragment retrieves announcement data passed through a Bundle and
 * displays it, including handling images with Glide and formatting text.
 * <p>
 * @author Zhi LI, u7640966
 * <p>
 * Bibliography:
 * - <a href="https://www.techyourchance.com/android-fragment-lifecycle/">...</a>
 * - <a href="https://developer.android.com/reference/android/app/Fragment#onCreateView(android.view.LayoutInflater,%20android.view.ViewGroup,%20android.os.Bundle)">...</a>
 * - <a href="https://www.reddit.com/r/Firebase/comments/jessg6/newline_n_not_working_while_loading_text_from/">...</a>
 */
public class AnnouncementFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announcement, container, false);
        TextView titleView = view.findViewById(R.id.detail_title);
        ImageView imageView = view.findViewById(R.id.detail_image);
        TextView detailView = view.findViewById(R.id.detail_text);
        TextView dateView = view.findViewById(R.id.date_text);

        // Check for arguments passed to this fragment and update the UI accordingly
        if (getArguments() != null) {
            // getArguments retrieves data passed using bundle from the main fragment via the NavController
            String title = getArguments().getString("title");
            String imageUrl = getArguments().getString("imageUrl");
            String detail = getArguments().getString("detail");
            String date = getArguments().getString("date");
            titleView.setText(title);
            dateView.setText(date);

            // Handle special text formatting issues, specifically the newline character problem
            // sourced from Firestore data
            //https://www.reddit.com/r/Firebase/comments/jessg6/newline_n_not_working_while_loading_text_from/
            String displayText = detail.replace("\\n", "\n");
            detailView.setText(displayText);
            // Load the image using Glide into the ImageView
            Glide.with(this).load(imageUrl).into(imageView);

        }
        return view;
    }





}