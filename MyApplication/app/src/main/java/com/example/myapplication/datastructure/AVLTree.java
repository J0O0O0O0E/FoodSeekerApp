package com.example.myapplication.datastructure;

import com.example.myapplication.model.FoodBank;

public class AVLTree extends Tree{
    public static class EmptyAVL extends EmptyTree {
        @Override
        public Tree insert(FoodBank element) {
            return new AVLTree(element);
        }
    }
    public AVLTree(FoodBank value){
        super(value);
        this.leftNode=new EmptyAVL();
    }

    public AVLTree(FoodBank value, Tree leftNode, Tree rightNode){
        super(value,leftNode,rightNode);
    }

    public int getBalanceFactor(){
        return leftNode.getHeight()-rightNode.getHeight();
    }

    @Override
    public AVLTree insert(FoodBank element) {
        AVLTree newTree ;
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");

        if (value!=null && element.getId()>value.getId()) {
            newTree = new AVLTree(value, leftNode, rightNode.insert(element));
        } else if (value!=null && element.getId()<value.getId()) {
            newTree = new AVLTree(value, leftNode.insert(element), rightNode);
        } else {
            newTree = new AVLTree(value, leftNode, rightNode);
        }
        if (newTree.getBalanceFactor() < -1) {
            if(newTree.rightNode.value!=null && element.getId()>value.getId()){
                newTree=newTree.leftRotate();
            } else{
                newTree.rightNode=((AVLTree)newTree.rightNode).rightRotate();
                newTree=newTree.leftRotate();
            }
        }
        if (newTree.getBalanceFactor() > 1){
            if(newTree.leftNode.value!=null &&element.getId()<value.getId())
                newTree= newTree.rightRotate();
            else {
                newTree.leftNode=((AVLTree)newTree.leftNode).leftRotate();
                newTree=newTree.rightRotate();
            }
        }
        return newTree;
    }

    // leftRotate according to balance Factor
    public AVLTree leftRotate() {
        Tree newParent = this.rightNode;
        this.rightNode = newParent.leftNode;
        newParent.leftNode = this;
        return (AVLTree) newParent;
    }

    // rightRotate according to balance Factor
    public AVLTree rightRotate() {
        Tree newParent = this.leftNode;
        this.leftNode = newParent.rightNode;
        newParent.rightNode = this;
        return (AVLTree) newParent;
    }

    @Override
    public FoodBank find(String Id) {
        if (Id == null)
            throw new IllegalArgumentException("Input cant be null");
        if (value!=null && Integer.parseInt(Id)==value.getId()) {
            return this.value;
        } else if (value!=null && Integer.parseInt(Id)<value.getId()){
            return leftNode.find(Id);
        } else {
            return rightNode.find(Id);
        }
    }
}
