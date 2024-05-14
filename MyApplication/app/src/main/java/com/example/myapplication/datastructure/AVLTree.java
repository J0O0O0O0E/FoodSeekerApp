package com.example.myapplication.datastructure;

import com.example.myapplication.model.FoodBank;

import java.util.ArrayList;
import java.util.List;

public class AVLTree {
    private FoodBank value;
    private AVLTree leftNode;
    private AVLTree rightNode;
    private int height;

    public AVLTree() {
        this.value = null;
        this.height = 0;
    }

    public AVLTree(FoodBank value) {
        this.value = value;
        this.height = 1;
    }

    private int getHeight(AVLTree node) {
        if (node == null) return 0;
        return node.height;
    }

    private int getBalanceFactor(AVLTree node) {
        if (node == null) return 0;
        return getHeight(node.leftNode) - getHeight(node.rightNode);
    }

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

    private AVLTree rightRotate(AVLTree y) {
        AVLTree x = y.leftNode;
        AVLTree T2 = x.rightNode;
        x.rightNode = y;
        y.leftNode = T2;
        y.height = Math.max(getHeight(y.leftNode), getHeight(y.rightNode)) + 1;
        x.height = Math.max(getHeight(x.leftNode), getHeight(x.rightNode)) + 1;
        return x;
    }

    private AVLTree leftRotate(AVLTree x) {
        AVLTree y = x.rightNode;
        AVLTree T2 = y.leftNode;
        y.leftNode = x;
        x.rightNode = T2;
        x.height = Math.max(getHeight(x.leftNode), getHeight(x.rightNode)) + 1;
        y.height = Math.max(getHeight(y.leftNode), getHeight(y.rightNode)) + 1;
        return y;
    }



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

    interface CompareFunc {
        boolean compare(int a, int b);
    }
    private void printInOrder(AVLTree node) {
        if (node != null) {
            printInOrder(node.leftNode);
            if (node.value != null) {
                System.out.println("🐍🐍🐍🐍Node Capacity: " + node.value.getCapacity());
            }
            printInOrder(node.rightNode);
        }
    }
    public void countNodes(){
        System.out.println(countNodes(this));
    }
    public int countNodes(AVLTree node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.leftNode) + countNodes(node.rightNode);
    }
}
