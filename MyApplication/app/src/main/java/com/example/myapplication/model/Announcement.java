package com.example.myapplication.model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;


// announcement for home fragment

public class Announcement {
    private String title;
    private String imageUrl;
    private String detail;
    private Timestamp date;

    public Announcement() {
        // firestore requires a empty constructor
    }

    public String getTitle() {
        return title;
    }

    @PropertyName("date")
    public Timestamp getTimestamp() { return date;}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }





}
