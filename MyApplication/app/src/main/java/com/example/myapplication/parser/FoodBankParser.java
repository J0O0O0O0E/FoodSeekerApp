package com.example.myapplication.parser;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.tokenizer.Token;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

public class FoodBankParser {
    public static ArrayList<FoodBank> filterFoodBanks(List<Token> tokens, List<FoodBank> foodBanks) {
        if(tokens.isEmpty()){
            return null;
        }

        ArrayList<Predicate<FoodBank>> predicates = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i += 3) {
            String key = tokens.get(i).getToken();
            String operator = tokens.get(i + 1).getToken();
            int value = Integer.parseInt(tokens.get(i + 2).getToken());

            switch (key) {
                case "distance":
                    predicates.add(createPredicate("distance", operator, value));
                    break;
                case "capacity":
                    predicates.add(createPredicate("capacity", operator, value));
                    break;
                case "rating":
                    predicates.add(createPredicate("rating", operator, value));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown key: " + key);
            }
        }

        return filterByPredicates(foodBanks, predicates);
    }

    private static Predicate<FoodBank> createPredicate(String key, String operator, int value) {
        switch (key) {
            case "distance":
                return fb -> compare(fb.getDistanceToUser(), operator, value);
            case "capacity":
                return fb -> compare(fb.getCapacity(), operator, value);
            case "rating":
                return fb -> compare(fb.getRating(), operator, value);
            default:
                throw new IllegalArgumentException("Invalid key");
        }
    }

//    private static boolean compare(int attributeValue, String operator, int value) {
//        switch (operator) {
//            case ">":
//                return attributeValue > value;
//            case "=":
//                return attributeValue == value;
//            default:
//                throw new IllegalArgumentException("Unsupported operator: " + operator);
//        }
//    }

    private static boolean compare(double attributeValue, String operator, double value) {
        final double EPSILON = 0.000001; // 定义一个足够小的误差范围来比较浮点数
        switch (operator) {
            case ">":
                return attributeValue > value;
            case "=":
                return Math.abs(attributeValue - value) < EPSILON; // 使用误差范围来判断相等性
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    private static ArrayList<FoodBank> filterByPredicates(List<FoodBank> foodBanks, List<Predicate<FoodBank>> predicates) {
       ArrayList<FoodBank> results = new ArrayList<>();
        for (FoodBank fb : foodBanks) {
            boolean matches = predicates.stream().allMatch(pred -> pred.test(fb));
            if (matches) {
                results.add(fb);
            }
        }
        return results;
    }
}

