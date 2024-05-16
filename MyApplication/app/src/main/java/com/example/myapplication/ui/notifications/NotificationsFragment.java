package com.example.myapplication.ui.notifications;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotificationsBinding;
import com.example.myapplication.model.Notification;

import java.util.List;

/**
 * NotificationsFragment is responsible for displaying the list of notifications.
 * It observes changes in the NotificationsViewModel and updates the UI accordingly.
 * @author : zhi li，u7640966
 * @author : Shuhui Yang u7662582
 */
public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private NotificationsViewModel notificationsViewModel;
    private NotificstionsAdapter adapter;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment,
     *                           from XML.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     *                           The fragment should not add the view itself, but this can be used to generate the
     *                           LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialize the ViewModel
        notificationsViewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get the initial list of notifications
        List<Notification> notifications = notificationsViewModel.getNotificationsLiveData().getValue();

        // Initialize the adapter with the context and notifications list
        adapter = new NotificstionsAdapter(getContext(), notifications);

        // Set up the RecyclerView with a linear layout manager and the adapter
        RecyclerView notificationViews = root.findViewById(R.id.notification_list);
        notificationViews.setLayoutManager(new LinearLayoutManager(this.getContext()));
        notificationViews.setAdapter(adapter);

        // TextView to display a message when there are no notifications
        TextView emptyMessage = root.findViewById(R.id.text_notifications);

        // Observe the notifications live data and update the UI when it changes
        notificationsViewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<Notification> notifications) {
                if (notifications.isEmpty()) {
                    // Show a message when there are no notifications
                    emptyMessage.setText("There is no notification");
                    notificationViews.setVisibility(View.INVISIBLE);
                    emptyMessage.setVisibility(View.VISIBLE);
                } else {
                    // Display the list of notifications
                    notificationViews.setVisibility(View.VISIBLE);
                    emptyMessage.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return root;
    }

    /**
     * Called when the view previously created by onCreateView() has been detached from the fragment.
     * It is called after onDestroy() and before onDetach().
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
