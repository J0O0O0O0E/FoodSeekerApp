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

import java.util.Comparator;
import java.util.List;


/**
 * A RecyclerView Adapter for displaying a list of subscribed food banks.
 * @author Shuhui Yang
 */
public class SubscribedFoodBanksAdaptor extends RecyclerView.Adapter<SubscribedFoodBanksAdaptor.SubscribedFoodBanksViewHolder> {

    private Context context;

    private List<String> foodBanksId;

    public static List<FoodBank> foodBanks;

    private static FoodBankRecyclerViewInterface recyclerViewInterface;


    /**
     * Constructor for the SubscribedFoodBanksAdaptor.
     *
     * @param context                The context in which the adapter is being used.
     * @param foodBanksId            List of food bank IDs the user is subscribed to.
     * @param recyclerViewInterface  Interface for handling item clicks.
     */
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




    /**
     * Updates the list of subscribed food banks and notifies the adapter of the data change.
     *
     * @param newFoodBanksId List of new food bank IDs the user is subscribed to.
     */
    public void updateSubscribedFoodBanks(List<String> newFoodBanksId){
        List<FoodBank> newFoodBanks = FoodBankRepository.getInstance().
                getFoodBankListByIdList(newFoodBanksId);
        newFoodBanks.sort(Comparator.comparingDouble(FoodBank::getDistanceToUser));
        foodBanks = newFoodBanks;
        notifyDataSetChanged();
    }



    /**
     * Sorts the food banks by their distance to the user.
     */
    public void sortFoodBanks(){
        foodBanks.sort(Comparator.comparingDouble(FoodBank::getDistanceToUser));
    }



    /**
     * ViewHolder class for the RecyclerView items.
     */
    public static class SubscribedFoodBanksViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        TextView address;



        /**
         * Constructor for the SubscribedFoodBanksViewHolder.
         *
         * @param itemView The view for the individual item in the RecyclerView.
         */
        public SubscribedFoodBanksViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.foodBankName);
            address = itemView.findViewById(R.id.foodBankAddress);


            // Set an OnClickListener for the itemView to handle item clicks
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

}
