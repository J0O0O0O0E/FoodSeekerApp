package com.example.myapplication.parser;

import com.example.myapplication.datastructure.DoubleAVLTree;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FoodBankParserTree {
    public static ArrayList<FoodBank> filterFoodBanks(List<Token> tokens, DoubleAVLTree doubleAVLTree) {
        //TODO check and catch error :  one key can only exits once(capacity,rating)

        ArrayList<FoodBank> foodBanks = new ArrayList<>();

        foodBanks = (ArrayList<FoodBank>) doubleAVLTree.searchCapacityEquals(5);


        return foodBanks;
    }






}

