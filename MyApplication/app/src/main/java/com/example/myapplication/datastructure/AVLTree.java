package com.example.myapplication.datastructure;

import com.example.myapplication.model.FoodBank;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an AVL Tree by ID, a self-balancing binary search tree, where the heights of the two child subtrees of any node differ by at most one.
 * @author Zijian Yang u7724610
 */
public class AVLTree {
    private FoodBank value; // Stores the value at this node
    private AVLTree leftNode; // Left subtree
    private AVLTree rightNode; // Right subtree
    private int height; // Height of this node

    /**
     * Default constructor. Initializes the tree as empty.
     */
    public AVLTree() {
        this.value = null;
        this.height = 0;
    }

    /**
     * Constructs a new node with the specified value.
     *
     * @param value The value to store in this node.
     */
    public AVLTree(FoodBank value) {
        this.value = value;
        this.height = 1;
    }

    /**
     * Helper method to get the height of a node.
     *
     * @param node The node whose height is to be retrieved.
     * @return The height of the given node.
     */
    private int getHeight(AVLTree node) {
        if (node == null) return 0;
        return node.height;
    }

    /**
     * Calculates the balance factor of a node.
     *
     * @param node The node whose balance factor is to be calculated.
     * @return The balance factor of the node.
     */
    private int getBalanceFactor(AVLTree node) {
        if (node == null) return 0;
        return getHeight(node.leftNode) - getHeight(node.rightNode);
    }

    /**
     * Inserts a new key into the AVL tree.
     *
     * @param node The root node of the subtree in which to insert the key.
     * @param key  The value to insert into the tree.
     * @return The new root after potential rotations.
     */
    public AVLTree insert(AVLTree node, FoodBank key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot insert a null FoodBank into AVLTree.");
        }
        if (node == null) {
            return new AVLTree(key);
        }
        if (node.value == null) {
            node.value = key;
            node.height = 1;
            return node;
        }
        if (key.getId() < node.value.getId()) {
            node.leftNode = insert(node.leftNode, key);
        } else if (key.getId() > node.value.getId()) {
            node.rightNode = insert(node.rightNode, key);
        } else {
            return node;
        }

        node.height = 1 + Math.max(getHeight(node.leftNode), getHeight(node.rightNode));
        return balanceTree(node);
    }

    /**
     * Balances the AVL tree at the given node.
     *
     * @param node The node to balance.
     * @return The new root after potential rotations.
     */
    private AVLTree balanceTree(AVLTree node) {
        int balance = getBalanceFactor(node);

        // Left Left Case
        if (balance > 1 && getBalanceFactor(node.leftNode) >= 0) {
            return rightRotate(node);
        }

        // Left Right Case
        if (balance > 1 && getBalanceFactor(node.leftNode) < 0) {
            node.leftNode = leftRotate(node.leftNode);
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && getBalanceFactor(node.rightNode) <= 0) {
            return leftRotate(node);
        }

        // Right Left Case
        if (balance < -1 && getBalanceFactor(node.rightNode) > 0) {
            node.rightNode = rightRotate(node.rightNode);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Performs a right rotation on the subtree rooted at y.
     *
     * @param y The root of the subtree.
     * @return The new root of the subtree.
     */
    private AVLTree rightRotate(AVLTree y) {
        AVLTree x = y.leftNode;
        AVLTree T2 = x.rightNode;
        x.rightNode = y;
        y.leftNode = T2;
        y.height = Math.max(getHeight(y.leftNode), getHeight(y.rightNode)) + 1;
        x.height = Math.max(getHeight(x.leftNode), getHeight(x.rightNode)) + 1;
        return x;
    }

    /**
     * Performs a left rotation on the subtree rooted at x.
     *
     * @param x The root of the subtree.
     * @return The new root of the subtree.
     */
    private AVLTree leftRotate(AVLTree x) {
        AVLTree y = x.rightNode;
        AVLTree T2 = y.leftNode;
        y.leftNode = x;
        x.rightNode = T2;
        x.height = Math.max(getHeight(x.leftNode), getHeight(x.rightNode)) + 1;
        y.height = Math.max(getHeight(y.leftNode), getHeight(y.rightNode)) + 1;
        return y;
    }

    /**
     * Searches for nodes with a specific capacity based on a comparison operation.
     *
     * @param node       The starting node for the search.
     * @param capacity   The capacity to compare against.
     * @param comparison The comparison operation (">", "<", "=").
     * @return A list of FoodBank nodes that meet the criteria.
     */
    public List<FoodBank> findNodesByCapacity(AVLTree node, int capacity, String comparison) {
        List<FoodBank> result = new ArrayList<>();
        switch (comparison) {
            case ">":
                findGreater(node, capacity, result, (a, b) -> a > b);
                break;
            case "<":
                findLess(node, capacity, result, (a, b) -> a < b);
                break;
            case "=":
                findEqual(node, capacity, result, (a, b) -> a == b);
                break;
        }
        return result;
    }

    // Helper methods to perform capacity-based searches.
    private void findGreater(AVLTree node, int capacity, List<FoodBank> result, CompareFunc func) {
        if (node == null) return;
        if (func.compare(node.value.getCapacity(), capacity)) {
            result.add(node.value);
            findGreater(node.rightNode, capacity, result, func);
        }
        findGreater(node.leftNode, capacity, result, func);
    }

    private void findLess(AVLTree node, int capacity, List<FoodBank> result, CompareFunc func) {
        if (node == null) return;
        if (func.compare(node.value.getCapacity(), capacity)) {
            result.add(node.value);
            findLess(node.leftNode, capacity, result, func);
        }
        findLess(node.rightNode, capacity, result, func);
    }

    private void findEqual(AVLTree node, int capacity, List<FoodBank> result, CompareFunc func) {
        if (node == null) return;
        if (func.compare(node.value.getCapacity(), capacity)) {
            result.add(node.value);
        }
        findEqual(node.leftNode, capacity, result, func);
        findEqual(node.rightNode, capacity, result, func);
    }

    /**
     * Functional interface for comparison operations.
     */
    interface CompareFunc {
        boolean compare(int a, int b);
    }

    /**
     * Finds a FoodBank by its ID.
     *
     * @param id The ID to search for.
     * @return The FoodBank with the given ID, or null if not found.
     */
    public FoodBank findById(int id) {
        return findById(this, id);
    }

    private FoodBank findById(AVLTree node, int id) {
        if (node == null) return null;
        if (id < node.value.getId()) {
            return findById(node.leftNode, id);
        } else if (id > node.value.getId()) {
            return findById(node.rightNode, id);
        } else {
            return node.value;
        }
    }

    /**
     * Triggers a print of the total number of nodes in the tree.
     */
    public void countNodes() {
        System.out.println(countNodes(this));
    }

    /**
     * Recursively counts the nodes in the AVL tree.
     *
     * @param node The node to start counting from.
     * @return The total number of nodes in the subtree rooted at the given node.
     */
    public int countNodes(AVLTree node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.leftNode) + countNodes(node.rightNode);
    }
}
