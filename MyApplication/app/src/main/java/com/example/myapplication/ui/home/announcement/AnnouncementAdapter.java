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

import java.util.List;


/**
 * Adapter for managing a list of Announcement items in a RecyclerView.
 * This adapter is responsible for inflating announcement item views, binding Announcement data to them,
 * and handling item click events through a provided RecyclerViewClickInterface.
 * <p>
 * @author Zhi LI
 * <p>
 * Bibliography:
 * - <a href="https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter">...</a>
 * - <a href="https://www.youtube.com/watch?v=Mc0XT58A1Z4">...</a>
 */

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    // whats going on in activity/fragment/prgram/ etc that it passed in
    private Context context;
    private List<Announcement> announcements;
//    private OnItemClickListener listener;
    private final RecyclerViewClickInterface recyclerViewClickInterface;


    /**
     * Constructs an AnnouncementAdapter.
     *
     * @param context                   The context in which the adapter is being used.
     * @param announcements             The list of announcements to display.
     * @param recyclerViewClickInterface The interface for handling item clicks in the RecyclerView.
     */
    public AnnouncementAdapter(Context context, List<Announcement> announcements, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.announcements = announcements;
        this.recyclerViewClickInterface = recyclerViewClickInterface;

    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent
     * an item within the RecyclerView. This method inflates the layout for the announcement item view.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.announcement_item, parent, false); // The inflated view is created but not attached to the parent ViewGroup (recycler view) it will add to it later inside fragment
        return new ViewHolder(view);
    }



    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * updates the contents of the ViewHolder to reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Announcement announcement = announcements.get(position);
        holder.title.setText(announcement.getTitle());
        holder.date.setText(announcement.getTimestamp().toDate().toString());
        Glide.with(context).load(announcement.getImageUrl()).into(holder.imageView);  // use GLIDe to load the images
    }


    // Returns the total number of announcements.
    @Override
    public int getItemCount() {
        return announcements.size();
    }



    /**
     * Updates the list of announcements in the RecyclerView.
     *
     * @param newAnnouncements The new list of announcements.
     */    public void updateAnnouncements(List<Announcement> newAnnouncements) {
        // Sorts announcements by timestamp and notifies the adapter of the change.
        newAnnouncements.sort((a1, a2) -> a2.getTimestamp().compareTo(a1.getTimestamp()));
        this.announcements = newAnnouncements;
        notifyDataSetChanged();
    }



    /**
     * ViewHolder for holding views of announcement items.
     * Should be static class here to prevent memory leak etc
     * Non static gives easy access to the adapter’s fields and methods,
     * which can simplify certain types of data binding logic.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            // Initializes views within the ViewHolder.
            title = itemView.findViewById(R.id.textViewAnnouncementTitle);
            imageView = itemView.findViewById(R.id.imageViewAnnouncement);
            date = itemView.findViewById(R.id.textViewAnnouncementDate);

            // Sets click listener for the item view.
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(recyclerViewClickInterface != null){
                    Announcement clickedAnnouncement = announcements.get(pos);
                    if(pos!=RecyclerView.NO_POSITION){ //This condition checks whether the adapter position is valid
                        recyclerViewClickInterface.onItemClick(clickedAnnouncement); // to pass the chosen announcement to the fragment and let the fragment decide what should do when interact, not the adapter.
                    }else {
                        Log.e("AnnouncementAdapter", "Invalid position clicked");
                    }
                }else {
                    Log.e("AnnouncementAdapter", "RecyclerViewInterface not implemented (null)");
                }
            });
        }
    }
}