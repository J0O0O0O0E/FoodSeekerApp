package com.example.myapplication.adapter;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.foodbankinfo_list,null);
        TextView tv_name =view.findViewById(R.id.tv_name);
        TextView tv_status =view.findViewById(R.id.tv_status);
        TextView tv_street =view.findViewById(R.id.tv_street);
        TextView tv_distance = view.findViewById(R.id.tv_distance);

        FoodBank foodBank =mFoodBankList.get(position);
        tv_name.setText(foodBank.getName());
        if(foodBank.isStatus()){
            tv_status.setText("open");
            tv_status.setTextColor(Color.parseColor("#32CD32"));
        }else{
            tv_status.setText("close");
            tv_status.setTextColor(Color.parseColor("#FF2400"));
        }
        tv_street.setText(foodBank.getStreet());


//        tv_distance.setText(Double.toString(foodBank.getDistanceToUser())+"m");
        tv_distance.setText(formatDistance(foodBank.getDistanceToUser()));
        return view;
    }

    //format the distance output
    private static String formatDistance(double input){
        if(input < 1000){
            return String.format("%.0fm",input);
        }else{
            double km = input / 1000;
            ////Create a NumberFormat object for formatting numbers,
            // ensuring that every three digits are separated by commas
            NumberFormat nf = NumberFormat.getInstance(Locale.US);
            nf.setMaximumFractionDigits(1); // Set up to one decimal place
            nf.setMinimumFractionDigits(1); // Set to at least one decimal place
            return nf.format(km) + "km";
        }
    }
}
