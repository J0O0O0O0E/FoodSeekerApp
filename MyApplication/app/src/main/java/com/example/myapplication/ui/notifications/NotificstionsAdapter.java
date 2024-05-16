package com.example.myapplication.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Notification;
import com.example.myapplication.repository.FoodBankRepository;
import com.example.myapplication.repository.UserRepository;
import com.example.myapplication.ui.subscribedFoodBanks.FoodBankRecyclerViewInterface;
import com.example.myapplication.ui.subscribedFoodBanks.SubscribedFoodBanksAdaptor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificstionsAdapter extends RecyclerView.Adapter<NotificstionsAdapter.NotificationViewHolder>{
    private Context context;
    private List<Notification> notifications;

    public NotificstionsAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.notificationMessage.setText(notification.getNotifyMessage());
        holder.time.setText(notification.getNotificationTime().toString());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView notificationMessage;
        TextView time;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            notificationMessage = itemView.findViewById(R.id.noti_message);
            time = itemView.findViewById(R.id.time);
        }
    }
}