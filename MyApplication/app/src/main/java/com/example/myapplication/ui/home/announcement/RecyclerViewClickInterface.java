package com.example.myapplication.ui.home.announcement;

import com.example.myapplication.model.Announcement;


/**
 * An interface to define the callback method for click events on items within a RecyclerView.
 * This interface is used to facilitate communication between the RecyclerView adapter and the
 * view (Home Fragment) that handles the interaction logic.
 * <p>
 * @author Zhi LI
 * <p>
 * Bibliography:
 * - <a href="https://www.youtube.com/watch?v=7GPUpvcU1FE">...</a>
 */
public interface RecyclerViewClickInterface {
    void onItemClick(Announcement announcement);
}
