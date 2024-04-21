package com.example.myapplication.ui.foodbank;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentFoodbankBinding;

public class FoodbankFragment extends Fragment implements View.OnClickListener {

    private FragmentFoodbankBinding binding;

//    private FoodbankViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_foodbank, container, false);


        Button btn_search = root.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // when click search btn, use getFoodbank method to get a foodbank list,
    // than add adapter, show the list view
    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.btn_search){

        }


    }
}