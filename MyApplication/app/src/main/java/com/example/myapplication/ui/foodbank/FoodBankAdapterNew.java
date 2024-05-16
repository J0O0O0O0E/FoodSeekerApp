package com.example.myapplication.ui.foodbank;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FoodBankAdapterNew extends BaseAdapter {
    private Context mContext;
    private List<FoodBank> mFoodBankList;

    private static final int LENGTH_KM = 1000;

    public FoodBankAdapterNew() {
    }

    public FoodBankAdapterNew(Context mContext, List<FoodBank> mFoodBankList) {
        this.mContext = mContext;
        this.mFoodBankList = mFoodBankList;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setmFoodBankList(List<FoodBank> mFoodbankList) {
        this.mFoodBankList = mFoodBankList;
    }

    @Override
    public int getCount() {
        return mFoodBankList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFoodBankList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.).
     *
     * This method inflates a layout for each item in the list and populates the view with
     * the corresponding FoodBank's data. It sets the name, status, street, and distance
     * of the FoodBank to the respective TextViews.
     *
     * @param position the position of the item within the adapter's data set
     * @param convertView the old view to reuse, if possible (null if a new view is needed)
     * @param parent the parent that this view will eventually be attached to
     * @return a View corresponding to the data at the specified position
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout for the list item
        View view = LayoutInflater.from(mContext).inflate(R.layout.foodbankinfo_list,null);
        // Get references to the TextViews in the layout
        TextView tv_name =view.findViewById(R.id.tv_name);
        TextView tv_status =view.findViewById(R.id.tv_status);
        TextView tv_street =view.findViewById(R.id.tv_street);
        TextView tv_distance = view.findViewById(R.id.tv_distance);

        FoodBank foodBank =mFoodBankList.get(position);
        tv_name.setText(foodBank.getName());
        // Set the status of the FoodBank (open or close) and the text color
        if(foodBank.isStatus()){
            tv_status.setText("open");
            tv_status.setTextColor(Color.parseColor("#32CD32"));
        }else{
            tv_status.setText("close");
            tv_status.setTextColor(Color.parseColor("#FF2400"));
        }
        // Set the street of the FoodBank
        tv_street.setText(foodBank.getStreet());

// Set the distance of the FoodBank to the user, formatted as meters or kilometers
        tv_distance.setText(formatDistance(foodBank.getDistanceToUser()));
        return view;
    }

    /**
     * Formats a distance value into a human-readable string.
     *
     * This method takes a distance value in meters and formats it as a string. If the distance is
     * less than the defined LENGTH_KM, it formats the distance in meters (m). If the distance is
     * equal to or greater than LENGTH_KM, it converts the distance to kilometers (km) and formats it
     * with one decimal place, using commas as thousand separators.
     *
     * @param input the distance value in meters to be formatted
     * @return a formatted string representing the distance in meters or kilometers
     */
    private static String formatDistance(double input){
        // If the distance is less than LENGTH_KM, format it in meters
        if(input < FoodBankAdapterNew.LENGTH_KM){
            return String.format("%.0fm",input);
        }else{
            // Convert the distance to kilometers
            double km = input / FoodBankAdapterNew.LENGTH_KM;
            ////Create a NumberFormat object for formatting numbers,
            // ensuring that every three digits are separated by commas
            NumberFormat nf = NumberFormat.getInstance(Locale.US);
            nf.setMaximumFractionDigits(1); // Set up to one decimal place
            nf.setMinimumFractionDigits(1); // Set to at least one decimal place
            return nf.format(km) + "km";
        }
    }
}
