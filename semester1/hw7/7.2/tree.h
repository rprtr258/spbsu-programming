#pragma once

struct Node;

struct BinarySearchTree {
    Node *root = nullptr;
    int size = 0;
};

// TODO: make adding several equal elements
void treeAdd(BinarySearchTree *tree, int const value);
void treeRemove(BinarySearchTree *tree, int const value);
bool treeContains(BinarySearchTree *tree, int const value);

void treePrintIncreasing(BinarySearchTree *tree);
void treePrintDecreasing(BinarySearchTree *tree);
void treePrintDebug(BinarySearchTree *tree);

void treeErase(BinarySearchTree *tree);
