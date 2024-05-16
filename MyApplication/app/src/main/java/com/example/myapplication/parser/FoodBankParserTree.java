package com.example.myapplication.parser;

import com.example.myapplication.datastructure.DoubleAVLTree;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * The FoodBankParserTree class provides functionality to parse and filter FoodBank objects
 * based on specific criteria provided in the form of tokens and searches within a DoubleAVLTree.
 * @author Si Chen u7756543, Zijian Yang u7724610
 */
public class FoodBankParserTree {

    // Constants representing comparison results
    private static final int NOTASSIGN = 5;
    private static final int LESS = -1;
    private static final int EQUAL = 0;
    private static final int GREATER = 1;

    /**
     * Filters FoodBank objects based on the criteria provided in tokens and the DoubleAVLTree.
     *
     * @param tokens        List of tokens containing the criteria for filtering.
     * @param doubleAVLTree DoubleAVLTree to search for matching FoodBank objects.
     * @return ArrayList of FoodBank objects matching the criteria.
     */
    public static ArrayList<FoodBank> filterFoodBanks(List<Token> tokens, DoubleAVLTree doubleAVLTree) {
        int capacityIndex = NOTASSIGN;
        int ratingIndex = NOTASSIGN;
        int capacityValue = 0;
        int ratingValue = 0;

        // Parse tokens to extract filtering criteria
        for (int i = 0; i < tokens.size(); i += 3) {
            Token keywordToken = tokens.get(i);
            Token comparisonToken = tokens.get(i + 1);
            Token intToken = tokens.get(i + 2);

            String keyword = keywordToken.getToken();
            String comparison = comparisonToken.getToken();
            int value = Integer.parseInt(intToken.getToken());

            // Determine the comparison criteria for rating and capacity
            if (keyword.equals("rating")) {
                ratingValue = value;
                if (comparison.equals(">")) {
                    ratingIndex = GREATER;
                } else if (comparison.equals("<")) {
                    ratingIndex = LESS;
                } else {
                    ratingIndex = EQUAL;
                }
            } else if (keyword.equals("capacity")) {
                capacityValue = value;
                if (comparison.equals(">")) {
                    capacityIndex = GREATER;
                } else if (comparison.equals("<")) {
                    capacityIndex = LESS;
                } else {
                    capacityIndex = EQUAL;
                }
            }
        }

        // Search the DoubleAVLTree with the parsed criteria
        return indexSearchInTree(capacityIndex, ratingIndex, capacityValue, ratingValue, doubleAVLTree);
    }

    /**
     * Searches the DoubleAVLTree for FoodBank objects matching the provided criteria.
     *
     * @param capacityIndex Comparison type for capacity (LESS, EQUAL, GREATER).
     * @param ratingIndex   Comparison type for rating (LESS, EQUAL, GREATER).
     * @param capacityValue Value to compare capacity against.
     * @param ratingValue   Value to compare rating against.
     * @param doubleAVLTree DoubleAVLTree to search for matching FoodBank objects.
     * @return ArrayList of FoodBank objects matching the criteria.
     */
    public static ArrayList<FoodBank> indexSearchInTree(int capacityIndex, int ratingIndex, int capacityValue, int ratingValue, DoubleAVLTree doubleAVLTree) {
        // Perform search based on the combination of capacity and rating criteria
        if (capacityIndex == LESS) {
            if (ratingIndex == LESS) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityLessThanRatingLessThan(capacityValue, ratingValue);
            } else if (ratingIndex == EQUAL) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityLessThanRatingEquals(capacityValue, ratingValue);
            } else if (ratingIndex == GREATER) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityLessThanRatingGreaterThan(capacityValue, ratingValue);
            } else {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityLessThan(capacityValue);
            }
        } else if (capacityIndex == EQUAL) {
            if (ratingIndex == LESS) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityEqualsRatingLessThan(capacityValue, ratingValue);
            } else if (ratingIndex == EQUAL) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityEqualsRatingEquals(capacityValue, ratingValue);
            } else if (ratingIndex == GREATER) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityEqualsRatingGreaterThan(capacityValue, ratingValue);
            } else {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityEquals(capacityValue);
            }
        } else if (capacityIndex == GREATER) {
            if (ratingIndex == LESS) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityGreaterThanRatingLessThan(capacityValue, ratingValue);
            } else if (ratingIndex == EQUAL) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityGreaterThanRatingEquals(capacityValue, ratingValue);
            } else if (ratingIndex == GREATER) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityGreaterThanRatingGreaterThan(capacityValue, ratingValue);
            } else {
                return (ArrayList<FoodBank>) doubleAVLTree.searchCapacityGreaterThan(capacityValue);
            }
        } else {
            if (ratingIndex == LESS) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchRatingLessThan(ratingValue);
            } else if (ratingIndex == EQUAL) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchRatingEquals(ratingValue);
            } else if (ratingIndex == GREATER) {
                return (ArrayList<FoodBank>) doubleAVLTree.searchRatingGreaterThan(ratingValue);
            }
        }
        return null;
    }
}
