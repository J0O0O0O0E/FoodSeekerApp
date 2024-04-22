package com.example.myapplication.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Announcement;
import com.example.myapplication.ui.home.announcement.AnnouncementAdapter;
import com.example.myapplication.ui.home.announcement.RecyclerViewInterface;

import java.util.Collections;


// the view level in MVVM
public class HomeFragment extends Fragment implements RecyclerViewInterface {

    private HomeViewModel viewModel;
    private RecyclerView recyclerView;
    private AnnouncementAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAnnouncements);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// which layout

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getAnnouncements().observe(getViewLifecycleOwner(), announcements -> {
            // set the adapter for
            if (adapter == null) {
                announcements.sort((a1, a2) -> a2.getTimestamp().compareTo(a1.getTimestamp()));
                adapter = new AnnouncementAdapter(getContext(), announcements, this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateAnnouncements(announcements);
            }

        });



        return view;
    }


    // implement the recycler view interface. On item click
    @Override
    public void onItemClick(Announcement announcement) {
        //创建一个 Bundle 对象并填充它，包括标题、图片 URL 和详细信息 URL。这些数据被用作传递给另一个 Fragment(announcement fragment) 的参数。
        Bundle bundle = new Bundle();
        bundle.putString("title", announcement.getTitle());
        bundle.putString("imageUrl", announcement.getImageUrl());
        bundle.putString("detail", announcement.getDetail());
        bundle.putString("date", announcement.getTimestamp().toDate().toString());


        //通过 NavHostFragment.findNavController(this) 获取当前 Fragment 的 NavController，这是 Navigation 组件用来管理导航的主要工具。
        NavController navController = NavHostFragment.findNavController(this);
        //调用 navigate 方法，传递导航操作的 ID（在 nav_graph.xml 中定义）和包含所需数据的 Bundle。这样，当导航到目标 Fragment 时，这个 Bundle 将传递给它。
        navController.navigate(R.id.navigation_announcement, bundle);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null; // 清理适配器
        viewModel = null;
        adapter = null;

        // 可以考虑清理其他资源
    }


}
