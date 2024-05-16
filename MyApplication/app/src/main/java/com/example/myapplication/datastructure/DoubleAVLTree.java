package com.example.myapplication.datastructure;

import android.util.Log;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Represents a node in the AVL tree with additional data and height properties.
 * @author Zijian Yang
 */
class AVLNode {
    FoodBank data;
    AVLNode left, right;
    int height;

    /**
     * Constructor to create a node with the given data.
     *
     * @param data The FoodBank object to store in this node.
     */
    AVLNode(FoodBank data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}

/**
 * Represents a double criteria AVL Tree with capacity and rating as the first and second attributes, where nodes are organized by multiple criteria from FoodBank objects.
 * @author Zijian Yang
 */
public class DoubleAVLTree {
    private AVLNode root;

    /**
     * Initializes an empty DoubleAVLTree.
     */
    public DoubleAVLTree() {
        this.root = null;
    }

    /**
     * Inserts a FoodBank item into the tree according to its capacity, rating, and ID.
     *
     * @param item The FoodBank item to insert.
     */
    public void insert(FoodBank item) {
        root = insert(root, item);
    }

    /**
     * Recursive helper method to insert a new node.
     *
     * @param node The current node in the traversal.
     * @param item The FoodBank item to insert.
     * @return The new or adjusted node after insertion.
     */
    private AVLNode insert(AVLNode node, FoodBank item) {
        if (node == null) {
            return new AVLNode(item);
        }

        // Sorting logic based on capacity, then rating, and then ID as the final tiebreaker
        if (item.getCapacity() < node.data.getCapacity() ||
                (item.getCapacity() == node.data.getCapacity() && item.getRating() < node.data.getRating()) ||
                (item.getCapacity() == node.data.getCapacity() && item.getRating() == node.data.getRating() && item.getId() < node.data.getId())) {
            node.left = insert(node.left, item);
        } else if (item.getCapacity() > node.data.getCapacity() ||
                (item.getCapacity() == node.data.getCapacity() && item.getRating() > node.data.getRating()) ||
                (item.getCapacity() == node.data.getCapacity() && item.getRating() == node.data.getRating() && item.getId() > node.data.getId())) {
            node.right = insert(node.right, item);
        } else {
            return node;
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        return balance(node);
    }

    /**
     * Returns the height of a node.
     *
     * @param node The node whose height is to be determined.
     * @return The height of the node.
     */
    private int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * Calculates the balance factor of a node to determine if rotations are needed.
     *
     * @param node The node whose balance factor is to be calculated.
     * @return The balance factor of the node.
     */
    private int getBalanceFactor(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * Performs a right rotation to balance the tree.
     *
     * @param y The node around which the right rotation is to be performed.
     * @return The new root after rotation.
     */
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        return x;
    }

    /**
     * Performs a left rotation to balance the tree.
     *
     * @param x The node around which the left rotation is to be performed.
     * @return The new root after rotation.
     */
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        return y;
    }

    /**
     * Balances the tree at a given node by performing necessary rotations.
     *
     * @param node The node to balance.
     * @return The balanced node.
     */
    private AVLNode balance(AVLNode node) {
        int balanceFactor = getBalanceFactor(node);

        // Check and perform necessary rotations
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Searches for nodes meeting a specific condition defined by a predicate.
     *
     * @param condition The predicate to evaluate each FoodBank item against.
     * @return A list of FoodBank items that meet the condition.
     */
    public List<FoodBank> search(Predicate<FoodBank> condition) {
        List<FoodBank> results = new ArrayList<>();
        collectIf(root, condition, results);
        return results;
    }

    /**
     * Recursive helper to collect nodes that satisfy the given condition.
     *
     * @param node The current node.
     * @param condition The predicate condition to check.
     * @param results The list to accumulate results.
     */
    private void collectIf(AVLNode node, Predicate<FoodBank> condition, List<FoodBank> results) {
        if (node == null) return;
        if (condition.test(node.data)) {
            results.add(node.data);
        }
        collectIf(node.left, condition, results);
        collectIf(node.right, condition, results);
    }

    // Other search methods are similar, applying specific predicates to search for specific properties.

    /**
     * Counts all nodes in the tree.
     *
     * @return The total number of nodes in the tree.
     */
    public int countNodes() {
        return countNodes(root);
    }

    /**
     * Recursively counts nodes starting from the given node.
     *
     * @param node The node to start counting from.
     * @return The number of nodes in the subtree.
     */
    private int countNodes(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    /**
     * Prints all node values in the tree for debugging purposes.
     */
    public void printAllNodes() {
        printAllNodes(root);
    }

    /**
     * Helper method to print all nodes starting from the given node.
     *
     * @param node The node to start printing from.
     */
    private void printAllNodes(AVLNode node) {
        if (node != null) {
            printAllNodes(node.left);
            // Uncomment to enable actual node printing.
            // System.out.println("❤️❤️ID: " + node.data.getId() +
            //     ", Capacity: " + node.data.getCapacity() +
            //     ", Rating: " + node.data.getRating());
            printAllNodes(node.right);
        }
    }

    /**
     * Updates the distance from a user location for all FoodBank entries in the tree.
     *
     * @param userLocation The user's location to calculate distances to each FoodBank.
     */
    public void setDistancesForAll(Location userLocation) {
        setDistancesForAll(root, userLocation);
    }

    /**
     * Recursive helper to update distances for all nodes starting from the given node.
     *
     * @param node The current node.
     * @param userLocation The user's location.
     */
    private void setDistancesForAll(AVLNode node, Location userLocation) {
        if (node != null) {
            if (node.data.getLocation() != null) {
                double distance = Math.round(node.data.getLocation().calculateDistance(userLocation));
                node.data.setDistanceToUser(distance);
            } else {
                Log.d("UpdateDistances", "Location is null for FoodBank ID:" + node.data.getId());
            }
            setDistancesForAll(node.left, userLocation);
            setDistancesForAll(node.right, userLocation);
        }
    }
}
