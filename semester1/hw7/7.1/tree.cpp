#include <stdio.h>
#include "tree.h"

struct Node {
    Node *l = nullptr;
    Node *r = nullptr;
    int value = 0;
    int quantity = 1;
};

void addNode(Node *&node, int const value) {
    if (node == nullptr) {
        node = new Node();
        node->value = value;
        return;
    }
    if (node->value == value) {
        node->quantity++;
        return;
    }
    if (value < node->value)
        addNode(node->l, value);
    else
        addNode(node->r, value);
}

void treeAdd(BinarySearchTree *tree, int const value) {
    addNode(tree->root, value);
    tree->size++;
}

bool findNode(Node *node, int const value) {
    if (node == nullptr)
        return false;
    if (node->value == value)
        return true;
    if (value < node->value)
        return findNode(node->l, value);
    else
        return findNode(node->r, value);
}

bool treeContains(BinarySearchTree *tree, int const value) {
    return findNode(tree->root, value);
}

void removeNode(Node *&node, int const value) {
    if (node->value == value) {
        if (node->quantity > 1) {
            node->quantity--;
            return;
        }
        if (node->l == nullptr && node->r == nullptr) {
            delete node;
            node = nullptr;
        } else if (node->l != nullptr && node->r != nullptr) {
            Node *tmp = node->l;
            while (tmp->r != nullptr)
                tmp = tmp->r;
            removeNode(node, tmp->value);
            node->value = tmp->value;
        } else if (node->l != nullptr) {
            Node *child = node->l;
            delete node;
            node = child;
        } else if (node->r != nullptr) {
            Node *child = node->r;
            delete node;
            node = child;
        }
        return;
    }
    if (value < node->value)
        removeNode(node->l, value);
    else
        removeNode(node->r, value);
}

void treeRemove(BinarySearchTree *tree, int const value) {
    if (treeContains(tree, value)) {
        removeNode(tree->root, value);
        printf("Value %d removed\n", value);
    } else
        printf("There is no value %d in tree\n", value);
}

void eraseNode(Node *node) {
    if (node == nullptr)
        return;
    eraseNode(node->l);
    eraseNode(node->r);
    delete node;
}

void treeErase(BinarySearchTree *tree) {
    eraseNode(tree->root);
    tree->root = nullptr;
    tree->size = 0;
}

void printDebugNode(Node *node) {
    if (node == nullptr) {
        printf("null");
        return;
    }
    if (node->l == nullptr && node->r == nullptr) {
        printf("{%d[%d]}", node->value, node->quantity);
        return;
    }
    printf("(%d[%d] ", node->value, node->quantity);
    printDebugNode(node->l);
    printf(" ");
    printDebugNode(node->r);
    printf(")");
}

void printIncNode(Node *node) {
    if (node == nullptr)
        return;
    printIncNode(node->l);
    printf("%d[%d] ", node->value, node->quantity);
    printIncNode(node->r);
}

void treePrintIncreasing(BinarySearchTree *tree) {
    printIncNode(tree->root);
    printf("\n");
}

void printDecrNode(Node *node) {
    if (node == nullptr)
        return;
    printDecrNode(node->r);
    printf("%d[%d] ", node->value, node->quantity);
    printDecrNode(node->l);
}

void treePrintDecreasing(BinarySearchTree *tree) {
    printDecrNode(tree->root);
    printf("\n");
}

void treePrintDebug(BinarySearchTree *tree) {
    printDebugNode(tree->root);
    printf("\n");
}
