package com.example.myapplication.ui.subscribedFoodBanks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Announcement;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.repository.FoodBankRepository;
import com.example.myapplication.ui.home.announcement.RecyclerViewInterface;

import java.util.Comparator;
import java.util.List;

public class SubscribedFoodBanksAdaptor extends RecyclerView.Adapter<SubscribedFoodBanksAdaptor.SubscribedFoodBanksViewHolder> {

    private Context context;

    private List<String> foodBanksId;

    public static List<FoodBank> foodBanks;

    private static FoodBankRecyclerViewInterface recyclerViewInterface;

    public SubscribedFoodBanksAdaptor(Context context, List<String> foodBanksId, FoodBankRecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.foodBanksId = foodBanksId;
        this.foodBanks = FoodBankRepository.getInstance().getFoodBankListByIdList(foodBanksId);
        sortFoodBanks();

    }

    @NonNull
    @Override
    public SubscribedFoodBanksAdaptor.SubscribedFoodBanksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subsribed_food_banks_item, parent, false);
        return new SubscribedFoodBanksViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SubscribedFoodBanksAdaptor.SubscribedFoodBanksViewHolder holder, int position) {
        FoodBank foodBank = foodBanks.get(position);
        holder.name.setText(foodBank.getName());
        holder.address.setText(foodBank.getStreet() + " " + foodBank.getSuburb()
                + " " + foodBank.getPostcode());

    }

    @Override
    public int getItemCount() {
        return foodBanks.size();
    }

    public void updateSubscribedFoodBanks(List<String> newFoodBanksId){
        List<FoodBank> newFoodBanks = FoodBankRepository.getInstance().
                getFoodBankListByIdList(newFoodBanksId);
        newFoodBanks.sort(Comparator.comparingDouble(FoodBank::getDistanceToUser));
        foodBanks = newFoodBanks;
        notifyDataSetChanged();
    }


    public void sortFoodBanks(){
        foodBanks.sort(Comparator.comparingDouble(FoodBank::getDistanceToUser));
    }


    public static class SubscribedFoodBanksViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        TextView address;

        public SubscribedFoodBanksViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.foodBankName);
            address = itemView.findViewById(R.id.foodBankAddress);


            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(recyclerViewInterface != null){
                    FoodBank clickedFoodBank = foodBanks.get(pos);
                    if(pos!=RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemClick(clickedFoodBank);
                    }else {
                        Log.e("FoodBankAdapter", "Invalid position detected");
                    }

                }else {
                    Log.e("FoodBankAdapter", "FoodBankRecyclerViewInterface not implemented");
                }
            });
        }


    }

    public List<FoodBank> getFoodBanks(){
        return foodBanks;
        }
}
