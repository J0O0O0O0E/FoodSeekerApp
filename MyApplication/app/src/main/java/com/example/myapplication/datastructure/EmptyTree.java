package com.example.myapplication.datastructure;

import com.example.myapplication.model.FoodBank;

public abstract class EmptyTree extends Tree {
    public abstract Tree insert(FoodBank element);
    @Override
    public FoodBank find(String listingId) {
        return null;
    }

    @Override
    public int getHeight() {
        return -1;
    }
}