package com.example.myapplication.ui.notifications;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotificationsBinding;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Notification;
import com.example.myapplication.model.User;
import com.example.myapplication.parser.BusinessHours;
import com.example.myapplication.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<FoodBank> subscribedFoodBanks = UserRepository.getInstance().getSubscribedFoodBanks();
        List<Notification> notifications = new ArrayList<>();
        NotificstionsAdapter adapter = new NotificstionsAdapter(getContext(),notifications);

        RecyclerView notificationViews = root.findViewById(R.id.notification_list);
        notificationViews.setLayoutManager(new LinearLayoutManager(this.getContext()));

        TextView emptyMessage = root.findViewById(R.id.text_notifications);

        notificationViews.setAdapter(adapter);
        adapter.scheduleCheck();

        adapter.getNotificationLiveData().observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<Notification> notifications) {
                if(notifications.isEmpty()){
                    emptyMessage.setText("There is no notification");
                    notificationViews.setVisibility(View.INVISIBLE);
                    emptyMessage.setVisibility(View.VISIBLE);
                }
                else{
                    notificationViews.setVisibility(View.VISIBLE);
                    emptyMessage.setVisibility(View.INVISIBLE);
                }
            }
        });








//
//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//
//        MutableLiveData<LocalDateTime> timeLiveData = UserRepository.getInstance().getTimeLiveData();
//
//        timeLiveData.observe(requireActivity(), new Observer<LocalDateTime>() {
//            @Override
//            public void onChanged(LocalDateTime time) {
//                if(hours.ifNotifyNeeded(time)){
//                    if(hours.isFoodBankClosed(time)){
////                        Notification notification = new Notification()
//                    }
//                }
//
//
//            }
//        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}