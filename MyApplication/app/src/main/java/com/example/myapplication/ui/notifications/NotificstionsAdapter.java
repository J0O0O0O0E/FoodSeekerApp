package com.example.myapplication.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Notification;

import java.util.List;

/**
 * NotificstionsAdapter is a RecyclerView Adapter for displaying notifications.
 * It binds notification data to the views in the RecyclerView.
 * @author : zhili, u7640966
 */
public class NotificstionsAdapter extends RecyclerView.Adapter<NotificstionsAdapter.NotificationViewHolder> {

    private Context context;
    private List<Notification> notifications;

    /**
     * Constructor for NotificstionsAdapter.
     *
     * @param context      The context in which the adapter is used.
     * @param notifications The list of notifications to be displayed.
     */
    public NotificstionsAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new NotificationViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method updates the contents of the ViewHolder to reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.notificationMessage.setText(notification.getNotifyMessage());
        holder.time.setText(notification.getNotificationTime().toString());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return notifications.size();
    }

    /**
     * ViewHolder class for NotificstionsAdapter.
     * It holds the views for each notification item in the RecyclerView.
     */
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView notificationMessage;
        TextView time;

        /**
         * Constructor for NotificationViewHolder.
         *
         * @param itemView The view of the notification item.
         */
        public NotificationViewHolder(View itemView) {
            super(itemView);
            notificationMessage = itemView.findViewById(R.id.noti_message);
            time = itemView.findViewById(R.id.time);
        }
    }
}
