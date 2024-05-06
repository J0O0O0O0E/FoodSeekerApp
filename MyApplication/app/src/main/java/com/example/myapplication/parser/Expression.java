package com.example.myapplication.parser;

import com.example.myapplication.datastructure.Tree;
import com.example.myapplication.model.FoodBank;

import java.util.List;

public abstract class Expression {
    public abstract List<FoodBank> evaluate(Tree tree);
}

