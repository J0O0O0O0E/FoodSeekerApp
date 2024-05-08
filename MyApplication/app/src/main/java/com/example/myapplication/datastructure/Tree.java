package com.example.myapplication.datastructure;

import com.example.myapplication.model.FoodBank;

import java.util.ArrayList;
import java.util.List;

public abstract class Tree {

    public final FoodBank value;
    public Tree leftNode; // store less than value
    public Tree rightNode; // store greater than value

    public List<FoodBank> higherCapacityFoodBankList = new ArrayList<>();
    public List<FoodBank> higherRatingFoodBankList = new ArrayList<>();
    /*public List<FoodBank> higherFood1_pastaFoodBankList = new ArrayList<>();
    public List<FoodBank> higherfood2_breadFoodBankList = new ArrayList<>();
    public List<FoodBank> higherfood3_milkFoodBankList = new ArrayList<>();
    public List<FoodBank> higherfood4_pieFoodBankList = new ArrayList<>();
    public List<FoodBank> higherfood5_vetFoodBankList = new ArrayList<>();*/
    public List<FoodBank> nameFoodBankFoodBankList = new ArrayList<>();
/*
    public List<FoodBank> locationFoodBankList = new ArrayList<>();
*/

    public Tree() {
        this.value = null;
    }

    public Tree(FoodBank value) {
        if (value == null)
            throw new IllegalArgumentException("Input cannot be empty");
        this.value = value;
    }

    public Tree(FoodBank value, Tree leftNode, Tree rightNode) {
        if (value == null || leftNode == null || rightNode == null)
            throw new IllegalArgumentException("Inputs cannot be empty");
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
    public List<FoodBank> getHigherRatingFoodBankList(Tree node, int rating) {
        if (null == node) {
            return higherRatingFoodBankList;
        } else if (node.leftNode != null) {
            getHigherRatingFoodBankList(node.leftNode, rating);
        }
        if (node.value != null) {
            if (node.value.getRating() > rating) {
                higherRatingFoodBankList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            getHigherRatingFoodBankList(node.rightNode, rating);
        }
        return higherRatingFoodBankList;
    }

    public List<FoodBank> getHigherCapacityFoodBankList(Tree node, int capacity) {
        if (null == node) {
            return higherCapacityFoodBankList;
        } else if (node.leftNode != null) {
            getHigherCapacityFoodBankList(node.leftNode, capacity);
        }
        if (node.value != null) {
            if (node.value.getCapacity() > capacity) {
                higherCapacityFoodBankList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            getHigherCapacityFoodBankList(node.rightNode, capacity);
        }
        return higherCapacityFoodBankList;
    }

    public List<FoodBank> findFoodBankName(Tree node, String name) {
        if (null == node) {
            return nameFoodBankFoodBankList;
        } else if (node.leftNode != null) {
            findFoodBankName(node.leftNode, name);
        }
        if (node.value!=null && node.value.getName()!=null) {
            if (node.value.getName().toLowerCase().contains(name.toLowerCase())) {
                nameFoodBankFoodBankList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            findFoodBankName(node.rightNode, name);
        }
        return nameFoodBankFoodBankList;
    }

}
