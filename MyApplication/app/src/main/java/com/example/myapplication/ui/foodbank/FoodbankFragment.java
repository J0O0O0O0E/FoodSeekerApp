package com.example.myapplication.ui.foodbank;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentFoodbankBinding;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.ui.home.HomeViewModel;

public class FoodbankFragment extends Fragment {


    private FragmentFoodbankBinding binding;

//    private FoodbankViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FoodbankViewModel foodbankViewModel =
                new ViewModelProvider(this).get(FoodbankViewModel.class);

        binding = FragmentFoodbankBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFoodbank;
        foodbankViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}