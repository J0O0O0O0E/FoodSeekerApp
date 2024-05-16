package com.example.myapplication.datastructure;

import android.util.Log;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * AVLNode class represents a node in an AVL tree.
 * @author Zijian Yang u7724610
 */
class AVLNode {
    FoodBank data;  // The FoodBank data stored in this node
    AVLNode left, right;  // Left and right children nodes
    int height;  // Height of the node for balancing purposes

    /**
     * Constructor to create an AVL node with given FoodBank data.
     *
     * @param data The FoodBank object to be stored in this node.
     */
    AVLNode(FoodBank data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}

/**
 * DoubleAVLTree class represents an AVL tree specifically designed for FoodBank objects.
 * @author Zijian Yang
 */
public class DoubleAVLTree {
    AVLNode root;  // Root node of the AVL tree

    /**
     * Constructor to create an empty DoubleAVLTree.
     */
    public DoubleAVLTree() {
        this.root = null;
    }

    /**
     * Inserts a FoodBank item into the AVL tree.
     *
     * @param item The FoodBank object to be inserted.
     */
    public void insert(FoodBank item) {
        root = insert(root, item);
    }

    /**
     * Helper method to insert a FoodBank item into the AVL tree.
     *
     * @param node The root node of the subtree.
     * @param item The FoodBank object to be inserted.
     * @return The new root of the subtree.
     */
    private AVLNode insert(AVLNode node, FoodBank item) {
        if (node == null) {
            return new AVLNode(item);
        }

        // Adjusted comparison logic to include id as the final tiebreaker
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
     * Gets the height of the given node.
     *
     * @param node The node whose height is to be determined.
     * @return The height of the node, or 0 if the node is null.
     */
    private int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * Gets the balance factor of the given node.
     *
     * @param node The node whose balance factor is to be determined.
     * @return The balance factor of the node.
     */
    private int getBalanceFactor(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * Performs a right rotation on the given node.
     *
     * @param y The node to be rotated.
     * @return The new root of the rotated subtree.
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
     * Performs a left rotation on the given node.
     *
     * @param x The node to be rotated.
     * @return The new root of the rotated subtree.
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
     * Balances the given node.
     *
     * @param node The node to be balanced.
     * @return The new root of the balanced subtree.
     */
    private AVLNode balance(AVLNode node) {
        int balanceFactor = getBalanceFactor(node);

        // Left Left Case
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // Left Right Case
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right Case
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // Right Left Case
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Searches the AVL tree for FoodBank items that match a given condition.
     *
     * @param condition The condition to be tested.
     * @return A list of FoodBank items that match the condition.
     */
    public List<FoodBank> search(Predicate<FoodBank> condition) {
        List<FoodBank> results = new ArrayList<>();
        collectIf(root, condition, results);
        return results;
    }

    /**
     * Helper method to collect FoodBank items that match a given condition.
     *
     * @param node The root node of the subtree.
     * @param condition The condition to be tested.
     * @param results The list to collect matching FoodBank items.
     */
    private void collectIf(AVLNode node, Predicate<FoodBank> condition, List<FoodBank> results) {
        if (node == null) return;
        if (condition.test(node.data)) {
            results.add(node.data);
        }
        collectIf(node.left, condition, results);
        collectIf(node.right, condition, results);
    }

    // Search methods for specific conditions on capacity and rating

    public List<FoodBank> searchCapacityGreaterThan(int n) {
        return search(fb -> fb.getCapacity() > n);
    }

    public List<FoodBank> searchCapacityLessThan(int n) {
        return search(fb -> fb.getCapacity() < n);
    }

    public List<FoodBank> searchCapacityEquals(int n) {
        return search(fb -> fb.getCapacity() == n);
    }

    public List<FoodBank> searchRatingGreaterThan(int n) {
        return search(fb -> fb.getRating() > n);
    }

    public List<FoodBank> searchRatingLessThan(int n) {
        return search(fb -> fb.getRating() < n);
    }

    public List<FoodBank> searchRatingEquals(int n) {
        return search(fb -> fb.getRating() == n);
    }

    // Combined search methods for capacity and rating conditions

    public List<FoodBank> searchCapacityGreaterThanRatingGreaterThan(int capacity, int rating) {
        return search(fb -> fb.getCapacity() > capacity && fb.getRating() > rating);
    }

    public List<FoodBank> searchCapacityGreaterThanRatingLessThan(int capacity, int rating) {
        return search(fb -> fb.getCapacity() > capacity && fb.getRating() < rating);
    }

    public List<FoodBank> searchCapacityGreaterThanRatingEquals(int capacity, int rating) {
        return search(fb -> fb.getCapacity() > capacity && fb.getRating() == rating);
    }

    public List<FoodBank> searchCapacityLessThanRatingGreaterThan(int capacity, int rating) {
        return search(fb -> fb.getCapacity() < capacity && fb.getRating() > rating);
    }

    public List<FoodBank> searchCapacityLessThanRatingLessThan(int capacity, int rating) {
        return search(fb -> fb.getCapacity() < capacity && fb.getRating() < rating);
    }

    public List<FoodBank> searchCapacityLessThanRatingEquals(int capacity, int rating) {
        return search(fb -> fb.getCapacity() < capacity && fb.getRating() == rating);
    }

    public List<FoodBank> searchCapacityEqualsRatingGreaterThan(int capacity, int rating) {
        return search(fb -> fb.getCapacity() == capacity && fb.getRating() > rating);
    }

    public List<FoodBank> searchCapacityEqualsRatingLessThan(int capacity, int rating) {
        return search(fb -> fb.getCapacity() == capacity && fb.getRating() < rating);
    }

    public List<FoodBank> searchCapacityEqualsRatingEquals(int capacity, int rating) {
        return search(fb -> fb.getCapacity() == capacity && fb.getRating() == rating);
    }

    /**
     * Counts the total number of nodes in the AVL tree.
     *
     * @return The total number of nodes.
     */
    public int countNodes() {
        return countNodes(root);
    }

    /**
     * Helper method to count the number of nodes in a subtree.
     *
     * @param node The root node of the subtree.
     * @return The number of nodes in the subtree.
     */
    private int countNodes(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    /**
     * Prints all nodes in the AVL tree in in-order traversal.
     */
    public void printAllNodes() {
        printAllNodes(root);
    }

    /**
     * Helper method to print all nodes in a subtree in in-order traversal.
     *
     * @param node The root node of the subtree.
     */
    private void printAllNodes(AVLNode node) {
        if (node != null) {
            printAllNodes(node.left);

            // Uncomment the line below to print node details
            /*System.out.println("❤️❤️ID: " + node.data.getId() +
                    ", Capacity: " + node.data.getCapacity() +
                    ", Rating: " + node.data.getRating());*/

            printAllNodes(node.right);
        }
    }

    /**
     * Sets the distances from a given user location to all FoodBank locations in the AVL tree.
     *
     * @param userLocation The user's location.
     */
    public void setDistancesForAll(Location userLocation) {
        setDistancesForAll(root, userLocation);
    }

    /**
     * Helper method to set the distances from a given user location to all FoodBank locations in a subtree.
     *
     * @param node The root node of the subtree.
     * @param userLocation The user's location.
     */
    private void setDistancesForAll(AVLNode node, Location userLocation) {
        if (node != null) {
            if (node.data.getLocation() != null) {
                // Calculate the distance and set it to the FoodBank object
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
