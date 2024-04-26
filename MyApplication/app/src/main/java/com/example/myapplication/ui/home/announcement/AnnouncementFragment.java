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


        if (getArguments() != null) {
            // getArguments retrieves data passed using bundle from the main fragment via the NavController
            String title = getArguments().getString("title");
            String imageUrl = getArguments().getString("imageUrl");
            String detail = getArguments().getString("detail");
            String date = getArguments().getString("date");
            titleView.setText(title);
            dateView.setText(date);

            //https://www.reddit.com/r/Firebase/comments/jessg6/newline_n_not_working_while_loading_text_from/
            String displayText = detail.replace("\\n", "\n");
            detailView.setText(displayText);
            // Use Glide to load the image
            Glide.with(this).load(imageUrl).into(imageView);

        }
        return view;
    }





}