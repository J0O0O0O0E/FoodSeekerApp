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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.FoodbankAdaptor;
import com.example.myapplication.databinding.FragmentFoodbankBinding;
import com.example.myapplication.model.FoodBankInfo;

import java.util.List;

public class FoodbankFragment extends Fragment  {

    private FragmentFoodbankBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_foodbank, container, false);

        //initial all views in layout
        EditText ed_input = root.findViewById(R.id.et_input);
        Button btn_search = root.findViewById(R.id.btn_search);
        ListView lv_foodbank = root.findViewById(R.id.lv_foodbank);

        //get list of nearby foodbank
        List<FoodBankInfo> list = FoodbankViewModel.getNearByFb(1.0,1.0);
        FoodbankAdaptor fbAdapter = new FoodbankAdaptor(getContext(),list);
        lv_foodbank.setAdapter(fbAdapter);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //use input to search foodbank
                List<FoodBankInfo> listNew = FoodbankViewModel.searchFb(ed_input.getText().toString());
                FoodbankAdaptor newAdapter = new FoodbankAdaptor(getContext(),listNew);
                lv_foodbank.setAdapter(newAdapter);
            }
        });
        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}