package com.example.myapplication;
import com.example.myapplication.datastructure.DoubleAVLTree;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Location;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import java.util.List;

/**
 * This class contains unit tests for the {@link DoubleAVLTree} class.
 * It tests various methods of the User class to ensure they function correctly.
 *
 * @author Haoxuan Xu, u7747847
 */

public class TestDoubleAVLTree {
    private DoubleAVLTree tree;
    private FoodBank fb1, fb2, fb3, fb4, fb5;

    @Before
    public void setUp() {
        tree = new DoubleAVLTree();
        fb1 = createFoodBank(1, 100, 5); // ID, Capacity, Rating
        fb2 = createFoodBank(2, 200, 4);
        fb3 = createFoodBank(3, 100, 3);
        fb4 = createFoodBank(4, 200, 5);
        fb5 = createFoodBank(5, 150, 5);
    }

    @Test
    public void testInsertAndCountNodes() {
        assertEquals(0, tree.countNodes());
        tree.insert(fb1);
        assertEquals(1, tree.countNodes());
        tree.insert(fb2);
        assertEquals(2, tree.countNodes());
        tree.insert(fb3);
        assertEquals(3, tree.countNodes());
        tree.insert(fb4);
        assertEquals(4, tree.countNodes());
        tree.insert(fb5);
        assertEquals(5, tree.countNodes());
    }

    @Test
    public void testSearch() {
        tree.insert(fb1);
        tree.insert(fb2);
        tree.insert(fb3);
        tree.insert(fb4);
        tree.insert(fb5);

        List<FoodBank> result = tree.searchCapacityGreaterThan(150);
        System.out.println("searchCapacityGreaterThan(150): " + result);
        assertEquals(2, result.size());
        assertTrue(result.contains(fb2));
        assertTrue(result.contains(fb4));

        result = tree.searchCapacityLessThan(150);
        System.out.println("searchCapacityLessThan(150): " + result);
        assertEquals(2, result.size());
        assertTrue(result.contains(fb1));
        assertTrue(result.contains(fb3));

        result = tree.searchCapacityEqualsRatingGreaterThan(100, 4);
        System.out.println("searchCapacityEqualsRatingGreaterThan(100, 4): " + result);
        assertEquals(1, result.size());
        assertTrue(result.contains(fb1));
    }

    @Test
    public void testPrintAllNodes() {
        tree.insert(fb1);
        tree.insert(fb2);
        tree.insert(fb3);
        tree.insert(fb4);
        tree.insert(fb5);

        // Redirect output to check correctness
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        tree.printAllNodes();

        String expectedOutput = "❤️❤️ID: 3, Capacity: 100, Rating: 3.0\n" +
                "❤️❤️ID: 1, Capacity: 100, Rating: 5.0\n" +
                "❤️❤️ID: 5, Capacity: 150, Rating: 5.0\n" +
                "❤️❤️ID: 2, Capacity: 200, Rating: 4.0\n" +
                "❤️❤️ID: 4, Capacity: 200, Rating: 5.0\n";

        assertEquals(expectedOutput, outContent.toString());
    }




    private FoodBank createFoodBank(int id, int capacity, int rating) {
        FoodBank fb = new FoodBank();
        fb.setId(id);
        fb.setCapacity(capacity);
        fb.setRating(rating);
        return fb;
    }
}