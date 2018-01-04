#include <stdio.h>
#include <algorithm>
#include "set.h"

struct Node {
    Node *l = nullptr;
    Node *r = nullptr;
    int value = 0;
    int unsigned height = 1;
    int unsigned quantity = 1;
};

int unsigned getHeight(Node *node) {
    return (node == nullptr ? 0 : node->height);
}

void fixHeight(Node *node) {
    node->height = std::max(getHeight(node->l), getHeight(node->r)) + 1;
}

Node* rotateRight(Node *p) {
    Node *q = p->l;
    p->l = q->r;
    q->r = p;
    fixHeight(p);
    fixHeight(q);
    return q;
}

Node* rotateLeft(Node *p) {
    Node *q = p->r;
    p->r = q->l;
    q->l = p;
    fixHeight(p);
    fixHeight(q);
    return q;
}

int bFactor(Node *p) {
    return (int)getHeight(p->r) - getHeight(p->l);
}

Node* balanceNode(Node *p) {
    fixHeight(p);
    int balanceFactor = bFactor(p);
    if (balanceFactor == 2) {
        if (bFactor(p->r) < 0) {
            p->r = rotateRight(p->r);
        }
        return rotateLeft(p);
    } else if (balanceFactor == -2) {
        if (bFactor(p->l) > 0) {
            p->l = rotateLeft(p->l);
        }
        return rotateRight(p);
    }
    return p;
}

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
    node = balanceNode(node);
}

void setInsert(Set *tree, int const value) {
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

bool setContains(Set *tree, int const value) {
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
            int tempValue = tmp->value;
            removeNode(node, tmp->value);
            node->value = tempValue;
            node = balanceNode(node);
        } else if (node->l != nullptr) {
            Node *child = node->l;
            delete node;
            node = child;
            node = balanceNode(node);
        } else if (node->r != nullptr) {
            Node *child = node->r;
            delete node;
            node = child;
            node = balanceNode(node);
        }
        return;
    }
    if (value < node->value)
        removeNode(node->l, value);
    else
        removeNode(node->r, value);
    node = balanceNode(node);
}

void setRemove(Set *tree, int const value) {
    if (setContains(tree, value))
        removeNode(tree->root, value);
}

void eraseNode(Node *node) {
    if (node == nullptr)
        return;
    eraseNode(node->l);
    eraseNode(node->r);
    delete node;
}

Set* setCreate() {
    Set *result = new Set();
    return result;
}

void setErase(Set *tree) {
    eraseNode(tree->root);
    tree->root = nullptr;
    tree->size = 0;
}

void setDelete(Set *&tree) {
    setErase(tree);
    delete tree;
    tree = nullptr;
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
    printf("(%d[%d, %d] ", node->value, node->height, node->quantity);
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

void setPrintIncreasing(Set *tree) {
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

void setPrintDecreasing(Set *tree) {
    printDecrNode(tree->root);
    printf("\n");
}

void setPrintDebug(Set *tree) {
    printDebugNode(tree->root);
    printf("\n");
}
