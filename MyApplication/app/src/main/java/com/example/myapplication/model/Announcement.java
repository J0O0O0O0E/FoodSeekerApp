package com.example.myapplication.model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;


/**
 * Model class representing an announcement inside the home fragment.
 * <P>
 * @author Zhi LI
 *
 */
public class Announcement {
    private String title;
    private String imageUrl;
    private String detail;
    private Timestamp date;

    /**
     * Default constructor required by Firestore.
     */
    public Announcement() {
        // Firestore requires an empty constructor
    }

    /**
     * Getter method for retrieving the title of the announcement.
     *
     * @return The title of the announcement.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter method for retrieving the timestamp of the announcement.
     *
     * @return The timestamp of the announcement.
     */
    @PropertyName("date")
    public Timestamp getTimestamp() {
        return date;
    }

    /**
     * Setter method for setting the title of the announcement.
     *
     * @param title The title of the announcement.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter method for retrieving the URL of the announcement image.
     *
     * @return The URL of the announcement image.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Setter method for setting the URL of the announcement image.
     *
     * @param imageUrl The URL of the announcement image.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Getter method for retrieving the detail of the announcement.
     *
     * @return The detail of the announcement.
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Setter method for setting the detail of the announcement.
     *
     * @param detail The detail of the announcement.
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
}





