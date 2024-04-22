package com.example.myapplication.repository;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.Announcement;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


// check this!:
//https://www.youtube.com/watch?v=YgjYVbg1oiA



//make it singleton!!!!!!!!!!!!!!!!




public class  AnnouncementRepository {
    // 私有变量db，用于访问Firebase Firestore数据库
    private FirebaseFirestore db;
    // 私有变量announcementsLiveData，类型为LiveData，用于存储并传递通告数据
    private MutableLiveData<List<Announcement>> announcementsLiveData;

    // 类的构造函数
    public AnnouncementRepository() {
        // 实例化FirebaseFirestore对象
        db = FirebaseFirestore.getInstance();
        // 实例化LiveData对象
        announcementsLiveData = new MutableLiveData<>();
        // 调用加载数据的私有方法
        loadAnnouncements();
    }

    // 定义私有方法loadAnnouncements，用于从Firestore实时加载数据
    private void loadAnnouncements() {
        // 访问数据库中的"announcements"集合，并添加实时更新的监听器
        db.collection("Announcement")
                .addSnapshotListener((snapshots, e) -> {
                    // 如果发生错误，设置LiveData为null，并返回
                    if (e != null) {
                        Log.e("AnnouncementRepo", "Error loading announcements", e);
                        announcementsLiveData.setValue(null);
                        return;
                    }
                    // 创建一个新的Announcement列表，用于存储转换后的数据
                    List<Announcement> newAnnouncements = new ArrayList<>();
                    // 遍历查询快照中的每个文档
                    for (QueryDocumentSnapshot doc : snapshots) {
                        // 将每个文档转换为Announcement对象，并添加到列表中
                        newAnnouncements.add(doc.toObject(Announcement.class));
                    }
                    // 将新的通告列表推送到LiveData中，触发观察者的更新
                    announcementsLiveData.postValue(newAnnouncements);
                });
    }

    // 公开方法getAnnouncements，返回包含通告数据的LiveData对象
    public LiveData<List<Announcement>> getAnnouncements() {
        return announcementsLiveData;
    }
}



