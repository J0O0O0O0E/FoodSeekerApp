package com.example.myapplication.datastructure;

import android.util.Log;

import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

class AVLNode {
    FoodBank data;
    AVLNode left, right;
    int height;

    AVLNode(FoodBank data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}

public class DoubleAVLTree {
    AVLNode root;

    public DoubleAVLTree() {
        this.root = null;
    }

    public void insert(FoodBank item) {
        root = insert(root, item);
    }

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

    private int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalanceFactor(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        return y;
    }

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
    public List<FoodBank> search(Predicate<FoodBank> condition) {
        List<FoodBank> results = new ArrayList<>();
        collectIf(root, condition, results);
        return results;
    }

    private void collectIf(AVLNode node, Predicate<FoodBank> condition, List<FoodBank> results) {
        if (node == null) return;
        if (condition.test(node.data)) {
            results.add(node.data);
        }
        collectIf(node.left, condition, results);
        collectIf(node.right, condition, results);
    }
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
    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    public void printAllNodes() {
        printAllNodes(root);
    }

    private void printAllNodes(AVLNode node) {
        if (node != null) {
            printAllNodes(node.left);

            System.out.println("❤️❤️ID: " + node.data.getId() +
                    ", Capacity: " + node.data.getCapacity() +
                    ", Rating: " + node.data.getRating());

            printAllNodes(node.right);
        }
    }

    public void setDistancesForAll(Location userLocation) {
        setDistancesForAll(root, userLocation);
    }

    private void setDistancesForAll(AVLNode node, Location userLocation) {
        if (node != null) {
            if (node.data.getLocation() != null) {
                //double distance = calculateDistance(userLocation, node.data.getLocation());
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