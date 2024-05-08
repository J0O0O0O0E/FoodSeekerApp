package com.example.myapplication.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Notification;
import com.example.myapplication.repository.FoodBankRepository;
import com.example.myapplication.ui.subscribedFoodBanks.FoodBankRecyclerViewInterface;
import com.example.myapplication.ui.subscribedFoodBanks.SubscribedFoodBanksAdaptor;

import java.util.Comparator;
import java.util.List;

public class NotificstionsAdapter extends RecyclerView.Adapter<NotificstionsAdapter.NotificationViewHolder>{

    private Context context;

    public List<Notification> notifications;

    public NotificstionsAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;

    }

    @NonNull
    @Override
    public NotificstionsAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subsribed_food_banks_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.foodBankName.setText(notification.getFoodBankName());
//        holder.status.setText(
//                if()
//        );

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public void updateNotifications(Notification notification){
        notifications.add(notification);
        sortNotifications();
        notifyDataSetChanged();
    }


    public void sortNotifications(){
        notifications.sort((a1, a2) -> a2.getNotificationTime().compareTo(a1.getNotificationTime()));
    }


    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView foodBankName;

        TextView status;

        public NotificationViewHolder(View itemView) {
            super(itemView);

            foodBankName= itemView.findViewById(R.id.foodBankName);
            status = itemView.findViewById(R.id.foodBankAddress);

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



}
