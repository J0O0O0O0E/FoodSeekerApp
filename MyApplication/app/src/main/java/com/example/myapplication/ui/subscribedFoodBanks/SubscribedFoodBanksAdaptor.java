package com.example.myapplication.ui.subscribedFoodBanks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.home.announcement.RecyclerViewInterface;

import java.util.List;

public class SubscribedFoodBanksAdaptor extends RecyclerView.Adapter<SubscribedFoodBanksAdaptor.SubscribedFoodBanksViewHolder> {

    private Context context;

    private List<String> foodBanksId;

    private final RecyclerViewInterface recyclerViewInterface;

    public SubscribedFoodBanksAdaptor(Context context, List<String> foodBanksId, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.foodBanksId = foodBanksId;

    }

    @NonNull
    @Override
    public SubscribedFoodBanksAdaptor.SubscribedFoodBanksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subsribed_food_banks_item, parent, false);
        return new SubscribedFoodBanksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribedFoodBanksAdaptor.SubscribedFoodBanksViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class SubscribedFoodBanksViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public SubscribedFoodBanksViewHolder(View itemView) {
            super(itemView);
//            textView = itemView.findViewById(R.id.text_view);
        }


    }
}
