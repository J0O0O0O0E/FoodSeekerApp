package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.myapplication.R;
import com.example.myapplication.model.FoodBankInfo;

import java.util.List;

public class FoodbankAdaptor extends BaseAdapter {
    private Context mContext;
    private List<FoodBankInfo> mFoodbankInfoList;

    public FoodbankAdaptor() {

    }

    public FoodbankAdaptor(Context mContext, List<FoodBankInfo> mFoodbankInfoList) {
        this.mContext = mContext;
        this.mFoodbankInfoList = mFoodbankInfoList;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setmFoodbankInfoList(List<FoodBankInfo> mFoodbankInfoList) {
        this.mFoodbankInfoList = mFoodbankInfoList;
    }

    @Override
    public int getCount() {
        return mFoodbankInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFoodbankInfoList.get(position);
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

        FoodBankInfo foodbankInfo =mFoodbankInfoList.get(position);
        tv_name.setText(foodbankInfo.name);
        tv_status.setText(foodbankInfo.status);
        tv_street.setText(foodbankInfo.street);
        tv_distance.setText(foodbankInfo.distance);

        return view;
    }
}
