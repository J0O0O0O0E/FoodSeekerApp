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

    public List<Notification> notifications;

    public MutableLiveData<List<Notification>> notificationLiveData;


    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public NotificstionsAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
        notificationLiveData = new MutableLiveData<>();
        notificationLiveData.setValue(notifications);

    }

    @NonNull
    @Override
    public NotificstionsAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
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


    public void scheduleCheck() {
        Runnable checkNotifications = new Runnable() {
            public void run() {
                try {
                    LocalDateTime currentTime = LocalDateTime.now();
                    Log.d("NotificationCheck", "Checking notifications at " + currentTime);
                    List<FoodBank> foodBanks = UserRepository.getInstance().getSubscribedFoodBanks();
                    if(!foodBanks.isEmpty()){
                        for (FoodBank foodBank : foodBanks) {
                            Log.d("NotificationCheck", "Checking notifications for " + foodBank.getOpen_hours());
                            if (foodBank.getBusinessHours().ifNotifyNeeded(currentTime)) {
                                Log.d("NotificationCheck", "Notification needed for " + foodBank.getName());
                                updateNotifications(new Notification(foodBank, currentTime));
                            }
                        }

                    }
                    else{
                        Log.d("NotificationCheck", "Empty foodBank list");
                    }
                } catch (Exception e) {
                    Log.e("NotificationCheck", "Error during checkNotifications", e);
                }
            }

        };
        scheduler.scheduleAtFixedRate(checkNotifications, 0, 60, TimeUnit.SECONDS);
    }





    public void updateNotifications(Notification notification){
        notifications.add(notification);
        sortNotifications();
        notificationLiveData.postValue(notifications);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }



    public void sortNotifications(){
        notifications.sort((a1, a2) -> a2.getNotificationTime().compareTo(a1.getNotificationTime()));
    }


    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView notificationMessage;

        TextView time;


        public NotificationViewHolder(View itemView) {
            super(itemView);

            notificationMessage = itemView.findViewById(R.id.noti_message);
            time = itemView.findViewById(R.id.time);
//
//            foodBankName= itemView.findViewById(R.id.foodBankName);
//            status = itemView.findViewById(R.id.foodBankAddress);

//
//            itemView.setOnClickListener(v -> {
//                int pos = getAdapterPosition();
//                if(recyclerViewInterface != null){
//                    FoodBank clickedFoodBank = foodBanks.get(pos);
//                    if(pos!=RecyclerView.NO_POSITION){
//                        recyclerViewInterface.onItemClick(clickedFoodBank);
//                    }else {
//                        Log.e("FoodBankAdapter", "Invalid position detected");
//                    }
//
//                }else {
//                    Log.e("FoodBankAdapter", "FoodBankRecyclerViewInterface not implemented");
//                }
//            });
        }




    }

    public MutableLiveData<List<Notification>> getNotificationLiveData() {
        return notificationLiveData;
    }
}
