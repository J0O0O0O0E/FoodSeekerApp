package com.example.myapplication.ui.home;

import android.content.Intent;
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
import com.example.myapplication.repository.UserRepository;
import com.example.myapplication.ui.home.announcement.AnnouncementAdapter;
import com.example.myapplication.ui.home.announcement.RecyclerViewClickInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A Fragment that serves as the home view in an MVVM architecture for displaying announcements.
 * This Fragment implements RecyclerViewClickInterface to handle interactions with a list of announcements.
 * It uses LiveData to observe changes in the list of announcements and user authentication status.
 * <p>
 * @author Zhi LI
 */
public class HomeFragment extends Fragment implements RecyclerViewClickInterface {
    private FloatingActionButton fab;

    private HomeViewModel viewModel;
    private RecyclerView recyclerView;
    private AnnouncementAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAnnouncements);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fab = view.findViewById(R.id.fab_add_announcement);

        // Set up the ViewModel and observe LiveData for announcements.
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // observer to observe livedata announcement set to see any changes
        viewModel.getAnnouncements().observe(getViewLifecycleOwner(), announcements -> {
            // Sort announcements by timestamp and update the RecyclerView.
            if (adapter == null) {
                announcements.sort((a1, a2) -> a2.getTimestamp().compareTo(a1.getTimestamp()));
                adapter = new  AnnouncementAdapter(getContext(), announcements, this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateAnnouncements(announcements);
            }

        });

        //check user's authorization level.
        observeUser();
        return view;
    }


    /**
     * Observes the current user's status to toggle visibility of the FloatingActionButton
     * based on the user's authorization level.
     */
    private void observeUser() {
        UserRepository userRepository = UserRepository.getInstance();
        userRepository.getLiveUser().observe(getViewLifecycleOwner(), user -> {
            // Show or hide the FAB based on the user's author status.
            if (user != null && user.getAuthor()) {
                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), AddAnnouncementActivity.class);
                    startActivity(intent);
                });
            } else {
                fab.setVisibility(View.GONE);
            }
        });
    }



    /**
     * Handles item clicks on the RecyclerView. Each item click navigates to a detail view
     * with the selected announcement's details.
     *
     * @param announcement the announcement data that was clicked.
     */
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
        if (fab != null) {
            fab.setOnClickListener(null);
        }
        if (recyclerView != null) {
            recyclerView.setAdapter(null);
        }
    }



}
