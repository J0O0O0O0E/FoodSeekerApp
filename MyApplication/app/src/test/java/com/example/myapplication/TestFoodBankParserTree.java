package com.example.myapplication;
import com.example.myapplication.datastructure.DoubleAVLTree;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.parser.FoodBankParserTree;
import com.example.myapplication.tokenizer.Token;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the {@link FoodBankParserTree} class.
 * It tests various methods of the User class to ensure they function correctly.
 *
 * @author Haoxuan Xu, u7747847
 */

public class TestFoodBankParserTree {

    private DoubleAVLTree doubleAVLTree;

    @Before
    public void setUp() {
        doubleAVLTree = mock(DoubleAVLTree.class);
    }

    @Test
    public void testFilterFoodBanksWithGreaterThanCondition() {
        FoodBank fb1;
        fb1 = createFoodBank("FB1", 10, 5);
        FoodBank fb2;
        fb2 = createFoodBank("FB2", 20, 4);
        ArrayList<FoodBank> expectedFoodBanks = new ArrayList<>();
        expectedFoodBanks.add(fb1);
        expectedFoodBanks.add(fb2);

        // Mock the behavior of doubleAVLTree
        when(doubleAVLTree.searchCapacityGreaterThanRatingGreaterThan(10, 3)).thenReturn(expectedFoodBanks);

        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token("capacity", Token.Type.KEYWORD));
        tokens.add(new Token(">", Token.Type.COMPARISON));
        tokens.add(new Token("10", Token.Type.INT));
        tokens.add(new Token("rating", Token.Type.KEYWORD));
        tokens.add(new Token(">", Token.Type.COMPARISON));
        tokens.add(new Token("3", Token.Type.INT));

        // Test the filterFoodBanks method
        ArrayList<FoodBank> result = FoodBankParserTree.filterFoodBanks(tokens, doubleAVLTree);

        // Verify the result
        assertEquals(expectedFoodBanks, result);
    }

    @Test
    public void testFilterFoodBanksWithLessThanCondition() {
        FoodBank fb1;
        fb1 = createFoodBank("FB1", 5, 2);
        ArrayList<FoodBank> expectedFoodBanks = new ArrayList<>();
        expectedFoodBanks.add(fb1);

        // Mock the behavior of doubleAVLTree
        when(doubleAVLTree.searchCapacityLessThanRatingLessThan(10, 3)).thenReturn(expectedFoodBanks);

        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token("capacity", Token.Type.KEYWORD));
        tokens.add(new Token("<", Token.Type.COMPARISON));
        tokens.add(new Token("10", Token.Type.INT));
        tokens.add(new Token("rating", Token.Type.KEYWORD));
        tokens.add(new Token("<", Token.Type.COMPARISON));
        tokens.add(new Token("3", Token.Type.INT));

        // Test the filterFoodBanks method
        ArrayList<FoodBank> result = FoodBankParserTree.filterFoodBanks(tokens, doubleAVLTree);

        // Verify the result
        assertEquals(expectedFoodBanks, result);
    }

    @Test
    public void testFilterFoodBanksWithEqualCondition() {
        FoodBank fb1;
        fb1 = createFoodBank("FB1", 10, 3);
        ArrayList<FoodBank> expectedFoodBanks = new ArrayList<>();
        expectedFoodBanks.add(fb1);

        // Mock the behavior of doubleAVLTree
        when(doubleAVLTree.searchCapacityEqualsRatingEquals(10, 3)).thenReturn(expectedFoodBanks);

        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token("capacity", Token.Type.KEYWORD));
        tokens.add(new Token("=", Token.Type.COMPARISON));
        tokens.add(new Token("10", Token.Type.INT));
        tokens.add(new Token("rating", Token.Type.KEYWORD));
        tokens.add(new Token("=", Token.Type.COMPARISON));
        tokens.add(new Token("3", Token.Type.INT));

        // Test the filterFoodBanks method
        ArrayList<FoodBank> result = FoodBankParserTree.filterFoodBanks(tokens, doubleAVLTree);

        // Verify the result
        assertEquals(expectedFoodBanks, result);
    }

    @Test
    public void testFilterFoodBanksWithNoMatchingCondition() {
        // Mock the behavior of doubleAVLTree
        when(doubleAVLTree.searchCapacityGreaterThanRatingGreaterThan(10, 3)).thenReturn(new ArrayList<>());

        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token("capacity", Token.Type.KEYWORD));
        tokens.add(new Token(">", Token.Type.COMPARISON));
        tokens.add(new Token("10", Token.Type.INT));
        tokens.add(new Token("rating", Token.Type.KEYWORD));
        tokens.add(new Token(">", Token.Type.COMPARISON));
        tokens.add(new Token("3", Token.Type.INT));

        // Test the filterFoodBanks method
        ArrayList<FoodBank> result = FoodBankParserTree.filterFoodBanks(tokens, doubleAVLTree);

        // Verify the result
        assertEquals(0, result.size());
    }

    private FoodBank createFoodBank(String name, int capacity, int rating) {
        FoodBank fb = new FoodBank();
        fb.setName(name);
        fb.setCapacity(capacity);
        fb.setRating(rating);
        return fb;
    }
}
