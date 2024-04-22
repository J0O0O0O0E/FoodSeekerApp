package com.example.myapplication.ui.home.announcement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Announcement;
import com.google.firebase.Timestamp;

import java.util.Collections;
import java.util.List;


//a class that helps recycle view to construct a view format
public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    // whats going on in activity/fragment/prgram/ etc that it passed in
    private Context context;
    private List<Announcement> announcements;
//    private OnItemClickListener listener;
    private final RecyclerViewInterface recyclerViewInterface;



    public interface OnItemClickListener {
        void onItemClick(Announcement announcement);
    }
    public AnnouncementAdapter(Context context, List<Announcement> announcements,RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.announcements = announcements;
        this.recyclerViewInterface = recyclerViewInterface;

    }



    // will be called by recycle view when they need a view holder to represent the items
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.announcement_item, parent, false); // The inflated view is created but not attached to the parent ViewGroup (recycler view) it will add to it later inside fragment
        return new ViewHolder(view);
    }



    // This method is called by the RecyclerView to display the data at the specified position
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Announcement announcement = announcements.get(position);
        holder.title.setText(announcement.getTitle());
        holder.date.setText(announcement.getTimestamp().toDate().toString());
        Glide.with(context).load(announcement.getImageUrl()).into(holder.imageView);  // use GLIDe to load the images
    }


    // recycle view use to determine the total number of items in the adapter, to manage the state
    @Override
    public int getItemCount() {
        return announcements.size();
    }



    // To update the list of announcement in the recycler view
    public void updateAnnouncements(List<Announcement> newAnnouncements) {
        newAnnouncements.sort((a1, a2) -> a2.getTimestamp().compareTo(a1.getTimestamp()));
        this.announcements = newAnnouncements;
        notifyDataSetChanged();  // notify adapter that there is a change in data
    }


    //it should be static class here to prevent memory leak etc
    // but non static means easy access to the adapter’s fields and methods, which can simplify certain types of data binding logic.

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewAnnouncementTitle);
            imageView = itemView.findViewById(R.id.imageViewAnnouncement);
            date = itemView.findViewById(R.id.textViewAnnouncementDate);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(recyclerViewInterface != null){
                    Announcement clickedAnnouncement = announcements.get(pos);
                    if(pos!=RecyclerView.NO_POSITION){ //This condition checks whether the adapter position is valid
                        recyclerViewInterface.onItemClick(clickedAnnouncement); // to pass the choosen announcement to the fragment and let the fragment decide what should do when interact, not the adapter.
                    }else {
                        Log.e("AnnouncementAdapter", "Invalid position detected");
                    }

                }else {
                    Log.e("AnnouncementAdapter", "RecyclerViewInterface not implemented");
                }
            });
        }
    }
}