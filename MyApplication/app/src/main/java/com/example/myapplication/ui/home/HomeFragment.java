package com.example.myapplication.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Announcement;
import com.example.myapplication.ui.home.announcement.AnnouncementAdapter;
import com.example.myapplication.ui.home.announcement.RecyclerViewInterface;

import java.util.Collections;


// the view level in MVVM
public class HomeFragment extends Fragment implements RecyclerViewInterface {

    private HomeViewModel viewModel;
    private RecyclerView recyclerView;
    private AnnouncementAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAnnouncements);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // observer to observe livedata announcement set to see any changes
        viewModel.getAnnouncements().observe(getViewLifecycleOwner(), announcements -> {
            if (adapter == null) {
                announcements.sort((a1, a2) -> a2.getTimestamp().compareTo(a1.getTimestamp()));
                adapter = new AnnouncementAdapter(getContext(), announcements, this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateAnnouncements(announcements);
            }

        });

        return view;
    }


    // implement the recycler view interface. On item click
    @Override
    public void onItemClick(Announcement announcement) {
        // Create a Bundle object and populate it with title, image URL, detail, and date.
        // These data are passed to Fragment (announcement fragment) as parameters.
        Bundle bundle = new Bundle();
        bundle.putString("title", announcement.getTitle());
        bundle.putString("imageUrl", announcement.getImageUrl());
        bundle.putString("detail", announcement.getDetail());
        bundle.putString("date", announcement.getTimestamp().toDate().toString());


        // Get the NavController of the current Fragment through NavHostFragment.findNavController(this),
        // which is the primary tool used by the Navigation component to manage navigation.
        NavController navController = NavHostFragment.findNavController(this);
// Call the navigate method, passing the ID of the navigation action (defined in nav_graph.xml) and the Bundle containing the necessary data.
        // This way, when navigating to the target Fragment, this Bundle will be passed to it.
        navController.navigate(R.id.navigation_announcement, bundle);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null;
        viewModel = null;
        adapter = null;

    }


}
