package com.example.myapplication;
import com.example.myapplication.datastructure.AVLTree;
import com.example.myapplication.model.FoodBank;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Field;
import java.util.List;
import static org.junit.Assert.*;

/**
 * This class contains unit tests for the {@link AVLTree} class.
 * It tests various methods of the User class to ensure they function correctly.
 *
 * @author Haoxuan Xu, u7747847
 */

public class TestAVLTree {
    private AVLTree tree;

    @Before
    public void setUp() {
        tree = new AVLTree();
    }

    @Test
    public void testInsertAndBalance() {
        FoodBank fb1 = createFoodBank(1, "FoodBank1", 100);
        FoodBank fb2 = createFoodBank(2, "FoodBank2", 200);
        FoodBank fb3 = createFoodBank(3, "FoodBank3", 50);
        FoodBank fb4 = createFoodBank(4, "FoodBank4", 75);
        FoodBank fb5 = createFoodBank(5, "FoodBank5", 150);

        tree = tree.insert(tree, fb1);
        tree = tree.insert(tree, fb2);
        tree = tree.insert(tree, fb3);
        tree = tree.insert(tree, fb4);
        tree = tree.insert(tree, fb5);

        // Verify tree structure is balanced
        assertEquals(2, getValue(tree).getId());
        assertEquals(1, getValue(getLeftNode(tree)).getId());
        assertEquals(4, getValue(getRightNode(tree)).getId());
        assertEquals(3, getValue(getLeftNode(getRightNode(tree))).getId());
        assertEquals(5, getValue(getRightNode(getRightNode(tree))).getId());
    }

//    private void printTree(AVLTree node, int depth) {
//        if (node == null) return;
//        printTree(getRightNode(node), depth + 1);
//        for (int i = 0; i < depth; i++) System.out.print("  ");
//        System.out.println(getValue(node).getId());
//        printTree(getLeftNode(node), depth + 1);
//    }





    @Test
    public void testFindNodesByCapacityLess() {
        FoodBank fb1 = createFoodBank(1, "FoodBank1", 100);
        FoodBank fb2 = createFoodBank(2, "FoodBank2", 200);
        FoodBank fb3 = createFoodBank(3, "FoodBank3", 50);
        FoodBank fb4 = createFoodBank(4, "FoodBank4", 75);
        FoodBank fb5 = createFoodBank(5, "FoodBank5", 150);

        tree = tree.insert(tree, fb1);
        tree = tree.insert(tree, fb2);
        tree = tree.insert(tree, fb3);
        tree = tree.insert(tree, fb4);
        tree = tree.insert(tree, fb5);

        List<FoodBank> result = tree.findNodesByCapacity(tree, 100, "<");
        assertEquals(2, result.size());
        assertTrue(result.contains(fb3));
        assertTrue(result.contains(fb4));
    }

    @Test
    public void testFindNodesByCapacityEqual() {
        FoodBank fb1 = createFoodBank(1, "FoodBank1", 100);
        FoodBank fb2 = createFoodBank(2, "FoodBank2", 200);
        FoodBank fb3 = createFoodBank(3, "FoodBank3", 50);
        FoodBank fb4 = createFoodBank(4, "FoodBank4", 75);
        FoodBank fb5 = createFoodBank(5, "FoodBank5", 150);

        tree = tree.insert(tree, fb1);
        tree = tree.insert(tree, fb2);
        tree = tree.insert(tree, fb3);
        tree = tree.insert(tree, fb4);
        tree = tree.insert(tree, fb5);

        List<FoodBank> result = tree.findNodesByCapacity(tree, 100, "=");
        assertEquals(1, result.size());
        assertTrue(result.contains(fb1));
    }

    @Test
    public void testCountNodes() {
        FoodBank fb1 = createFoodBank(1, "FoodBank1", 100);
        FoodBank fb2 = createFoodBank(2, "FoodBank2", 200);
        FoodBank fb3 = createFoodBank(3, "FoodBank3", 50);

        tree = tree.insert(tree, fb1);
        tree = tree.insert(tree, fb2);
        tree = tree.insert(tree, fb3);

        assertEquals(3, tree.countNodes(tree));
    }

    private FoodBank createFoodBank(int id, String name, int capacity) {
        FoodBank fb = new FoodBank();
        fb.setId(id);
        fb.setName(name);
        fb.setCapacity(capacity);
        return fb;
    }

    private FoodBank getValue(AVLTree node) {
        try {
            Field valueField = AVLTree.class.getDeclaredField("value");
            valueField.setAccessible(true);
            return (FoodBank) valueField.get(node);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private AVLTree getLeftNode(AVLTree node) {
        try {
            Field leftNodeField = AVLTree.class.getDeclaredField("leftNode");
            leftNodeField.setAccessible(true);
            return (AVLTree) leftNodeField.get(node);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private AVLTree getRightNode(AVLTree node) {
        try {
            Field rightNodeField = AVLTree.class.getDeclaredField("rightNode");
            rightNodeField.setAccessible(true);
            return (AVLTree) rightNodeField.get(node);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
