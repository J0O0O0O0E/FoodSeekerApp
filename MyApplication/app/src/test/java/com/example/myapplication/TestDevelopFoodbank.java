package com.example.myapplication;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.utils.DevelopFoodbank;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TestDevelopFoodbank {


    ArrayList <FoodBank> foodbanks = new ArrayList<>(20);
    String[] names = {"JERSEY1", "TARAKAN2", "BULCOCK3", "REGENCY4", "OAKS5", "DALTON6", "FRANKLAND7",
            "COONGAN8", "STRADBROKE9", "EARL10", "FORESTRY11", "BIRKBECK12", "BRONBERG13", "OAK14",
            "MURRAY15", "VOLGA16", "BELMORE17", "MARK18", "CENTRAL19", "FLEMING20"};
    int index = 1;

    @Before
    public void setUp(){
        for (String name : names) {
            FoodBank foodBank = new FoodBank();
            foodBank.setName(name);
            foodBank.setId(index);
            index++;
            foodbanks.add(foodBank);
        }
        DevelopFoodbank.setFoodbanks(foodbanks);



    }
    @Test
    public void testGetFoodbanks(){
        assertEquals(foodbanks, DevelopFoodbank.getFoodbanks());
    }

    @Test
    public void testSearchId(){


        assertEquals(foodbanks.get(0), DevelopFoodbank.searchId(1));
        assertEquals(foodbanks.get(1), DevelopFoodbank.searchId(2));
        assertEquals(foodbanks.get(2), DevelopFoodbank.searchId(3));
        assertEquals(foodbanks.get(3), DevelopFoodbank.searchId(4));
        assertEquals(foodbanks.get(4), DevelopFoodbank.searchId(5));
        assertEquals(foodbanks.get(5), DevelopFoodbank.searchId(6));
        assertEquals(foodbanks.get(6), DevelopFoodbank.searchId(7));
        assertEquals(foodbanks.get(7), DevelopFoodbank.searchId(8));
        assertEquals(foodbanks.get(8), DevelopFoodbank.searchId(9));
        assertEquals(foodbanks.get(9), DevelopFoodbank.searchId(10));
        assertEquals(foodbanks.get(10), DevelopFoodbank.searchId(11));
        assertEquals(foodbanks.get(11), DevelopFoodbank.searchId(12));
        assertEquals(foodbanks.get(12), DevelopFoodbank.searchId(13));
        assertEquals(foodbanks.get(13), DevelopFoodbank.searchId(14));
        assertEquals(foodbanks.get(14), DevelopFoodbank.searchId(15));
        assertEquals(foodbanks.get(15), DevelopFoodbank.searchId(16));
        assertEquals(foodbanks.get(16), DevelopFoodbank.searchId(17));
        assertEquals(foodbanks.get(17), DevelopFoodbank.searchId(18));
        assertEquals(foodbanks.get(18), DevelopFoodbank.searchId(19));
        assertEquals(foodbanks.get(19), DevelopFoodbank.searchId(20));


    }
}
