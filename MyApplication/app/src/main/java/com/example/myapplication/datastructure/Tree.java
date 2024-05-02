package com.example.myapplication.datastructure;
import com.example.myapplication.model.FoodBank;

import java.util.ArrayList;
import java.util.List;

public abstract class Tree {

    public final FoodBank value;
    public Tree leftNode; // store less than value
    public Tree rightNode; // store greater than value

    public List<FoodBank> higherCapacityList = new ArrayList<>();
    public List<FoodBank> higherRatingList= new ArrayList<>();
    public List<FoodBank> higherFood1_pastaList= new ArrayList<>();
    public List<FoodBank> higherfood2_breadList= new ArrayList<>();
    public List<FoodBank> higherfood3_milkList= new ArrayList<>();
    public List<FoodBank> higherfood4_pieList= new ArrayList<>();
    public List<FoodBank> higherfood5_vetList= new ArrayList<>();
    public List<FoodBank> nameFoodBankList= new ArrayList<>();
    public List<FoodBank> locationFoodBankList= new ArrayList<>();

    public Tree() {
        this.value = null;
    }

    public Tree(FoodBank value) {
        if (value == null)
            throw new IllegalArgumentException("Input cannot be null");
        this.value = value;
    }

    public Tree(FoodBank value, Tree leftNode, Tree rightNode) {
        if (value == null || leftNode == null || rightNode == null)
            throw new IllegalArgumentException("Inputs cannot be null");
        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public abstract FoodBank find(String listingId);   // Find element according to listing id and return FoodBank object
    public abstract Tree insert(FoodBank element);   // Inserts the element

    public int getHeight() {
        int leftNodeHeight = leftNode instanceof EmptyTree ? 0 : 1 + leftNode.getHeight();
        int rightNodeHeight = rightNode instanceof EmptyTree ? 0 : 1 + rightNode.getHeight();
        return Math.max(leftNodeHeight, rightNodeHeight);
    }

    // return List of FoodBanks with Higher rating than the given rating
    public List<FoodBank> getHigherRatingFoodbanks(Tree node, int rating) {
        if (null == node) {
            return higherCapacityList;
        } else if (node.leftNode != null) {
            getHigherRatingFoodbanks(node.leftNode, rating);
        }
        if (node.value!=null) {
            if (node.value.getRating()> rating) {
                higherRatingList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            getHigherRatingFoodbanks(node.rightNode, rating);
        }
        return higherRatingList;
    }




}
